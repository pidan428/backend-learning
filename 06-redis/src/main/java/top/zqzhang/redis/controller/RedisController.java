package top.zqzhang.redis.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/redis")
@RequiredArgsConstructor
public class RedisController {

    private final StringRedisTemplate stringRedisTemplate;

    /** String：最基础的类型，文本、数字、序列化后的 JSON 都放它里面 */
    @PostMapping("/string")
    public Map<String, Object> setString(@RequestParam String key,
                                         @RequestParam String value,
                                         @RequestParam(defaultValue = "600") long ttlSeconds) {
        stringRedisTemplate.opsForValue().set(key, value, Duration.ofSeconds(ttlSeconds));
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("key", key);
        result.put("value", value);
        result.put("ttlSeconds", stringRedisTemplate.getExpire(key));
        return result;
    }

    /** Hash：一个 key 下挂多个字段，适合只读写对象的某几个属性 */
    @PostMapping("/hash")
    public Map<Object, Object> putHash(@RequestParam String key, @RequestParam Map<String, String> fields) {
        // RequestParam 收到的 Map 里包含 key 这个参数本身，写进 hash 前先剔掉
        Map<String, String> values = new LinkedHashMap<>(fields);
        values.remove("key");
        stringRedisTemplate.opsForHash().putAll(key, values);
        return stringRedisTemplate.opsForHash().entries(key);
    }

    /** ZSet：元素带分数，写入后天然按分数排序 */
    @GetMapping("/zset/top")
    public List<Map<String, Object>> topZSet(@RequestParam String key,
                                             @RequestParam(defaultValue = "5") int top) {
        Set<ZSetOperations.TypedTuple<String>> tuples =
                stringRedisTemplate.opsForZSet().reverseRangeWithScores(key, 0, top - 1L);
        if (tuples == null) {
            return List.of();
        }
        return tuples.stream()
                .map(tuple -> Map.<String, Object>of("member", tuple.getValue(), "score", tuple.getScore()))
                .toList();
    }

    /** 计数器：INCR 在 Redis 里是单线程原子的，适合统计访问量、做限流 */
    @PostMapping("/counter/incr")
    public Map<String, Object> increment(@RequestParam String key,
                                         @RequestParam(defaultValue = "1") long delta) {
        Long current = stringRedisTemplate.opsForValue().increment(key, delta);
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("key", key);
        result.put("current", current);
        return result;
    }
}

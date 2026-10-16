package top.zqzhang.mybatis;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import top.zqzhang.mybatis.entity.User;
import top.zqzhang.mybatis.mapper.UserMapper;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * UserMapper 测试
 *
 * <p>@Transactional + @Rollback(false)：仍然开事务，但跑完提交而不是回滚，
 * 所以 INSERT 的数据会真正落库，方便在 MySQL 里查看到。
 */
@SpringBootTest
@Transactional
@Rollback(false)
@Slf4j
class UserMapperTest {

    @Resource
    private UserMapper userMapper;

    @Test
    void testInsert() {
        User user = new User();
        user.setUsername("zhangsan");
        user.setAge(20);
        user.setCreateTime(LocalDateTime.now());
        int result = userMapper.insert(user);

        assertEquals(1, result, "应插入 1 条");
        assertNotNull(user.getId(), "自增主键应回填到实体");
        log.info("插入条数：{}，自增id：{}", result, user.getId());
    }

    @Test
    void testSearch() {
        // 先插一条，再按它的 id 查
        User user = new User();
        user.setUsername("lisi");
        user.setAge(25);
        user.setCreateTime(LocalDateTime.now());
        userMapper.insert(user);

        User dbUser = userMapper.selectById(user.getId());
        assertNotNull(dbUser, "按 id 应能查到刚插入的数据");
        assertEquals("lisi", dbUser.getUsername());
        assertEquals(25, dbUser.getAge().intValue());
        log.info("查询到：{}", dbUser);
    }

    /**
     * 演示用的"靶子"：要删掉的那一行的主键。
     *
     * <p>填成 {@link #testInsert()} 日志里打印出来的"自增id"即可。
     * 想让演示固定在 id=8，先在 MySQL 里执行：
     * <pre>
     * truncate table t_user;
     * alter table t_user auto_increment = 8;
     * </pre>
     * 之后 testInsert 生成的那一行就正好是 id=8。
     */
    private static final Long TARGET_ID = 9L;

    @Test
    void testDelete() {
        // 这次不先插数据，直接删数据库里"已经存在"的那一行，MySQL 里能直接看到效果
        int result = userMapper.deleteById(TARGET_ID);
        log.info("删除条数：{}（目标 id={}）", result, TARGET_ID);
        assertEquals(1, result,
                "没删到 id=" + TARGET_ID + " 这条：表里现在没有这个 id 的行（可能已被删过，或 id 填错了）");
    }
}

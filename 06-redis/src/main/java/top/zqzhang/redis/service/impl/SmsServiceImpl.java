package top.zqzhang.redis.service.impl;

import com.cloopen.rest.sdk.BodyType;
import com.cloopen.rest.sdk.CCPRestSmsSDK;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import top.zqzhang.redis.config.CloopenConfig;
import top.zqzhang.redis.service.SmsService;

import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
@Slf4j
public class SmsServiceImpl implements SmsService {

    private final CloopenConfig cloopenConfig;

    @Override
    public boolean sendSms(String phone) {
        CCPRestSmsSDK sdk = new CCPRestSmsSDK();
        sdk.init(cloopenConfig.getServerIp(), cloopenConfig.getPort());
        sdk.setAccount(cloopenConfig.getAccountSID(), cloopenConfig.getAccountToken());
        sdk.setAppId(cloopenConfig.getAppId());
        sdk.setBodyType(BodyType.Type_JSON);
        String templateId = cloopenConfig.getTemplateId();
        // ↓ 课件里写死的是 {"1234"}，作业要求换成随机四位整数（这行是我补的）
        String code = String.format("%04d", ThreadLocalRandom.current().nextInt(10000));
        String[] codes = {code};
        HashMap<String, Object> result = sdk.sendTemplateSMS(phone, templateId, codes);
        if ("000000".equals(result.get("statusCode"))) {
            HashMap<String, Object> data = (HashMap<String, Object>) result.get("data");
            Set<String> keySet = data.keySet();
            for (String key : keySet) {
                Object object = data.get(key);
                log.info("key = {}", object);
            }
            return true;
        } else {
            log.info("错误码：{}，错误信息：{}", result.get("statusCode"), result.get("statusMsg"));
            return false;
        }
    }
}


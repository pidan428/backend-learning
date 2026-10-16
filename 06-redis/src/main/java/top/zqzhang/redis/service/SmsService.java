package top.zqzhang.redis.service;

public interface SmsService {
    /**
     * 发送短信
     * @param phone 手机号
     * @return boolean
     */
    boolean sendSms(String phone);
}


package top.zqzhang.redis.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.zqzhang.redis.common.ErrorCode;
import top.zqzhang.redis.common.Result;
import top.zqzhang.redis.service.SmsService;

@RestController
@RequestMapping("sms")
@RequiredArgsConstructor
public class SmsController {

    private final SmsService smsService;

    @PostMapping("send")
    public Result<String> send(@RequestParam String phone) {
        boolean flag = smsService.sendSms(phone);
        if (flag) {
            return Result.ok("短信发送成功");
        } else {
            return Result.fail(ErrorCode.SEND_MESSAGE_ERROR);
        }
    }
}


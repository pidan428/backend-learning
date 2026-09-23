package top.zqzhang.logging.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.zqzhang.logging.common.LogMaskUtil;

/**
 * 日志演示接口
 */
@Slf4j
@RestController
@RequestMapping("/log")
public class LogController {

    @GetMapping("/demo")
    public String demo() {
        log.info("用户手机号：{}", LogMaskUtil.maskPhone("13812345678"));
        log.info("处理 /log/demo 请求");
        log.warn("这是一条警告日志");
        log.error("这是一条错误日志");

        String name = "张三";
        log.info("用户 {} 登录成功", name);
        return "ok";
    }
}


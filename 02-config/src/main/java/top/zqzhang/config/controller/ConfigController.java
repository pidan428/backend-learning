package top.zqzhang.config.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/config")
public class ConfigController {

    @Value("${server.port}")
    public Integer serverPort;
    @Value("${spring.application.name}")
    public String appName;

    @GetMapping("/basic")
    public String getBasicInfo(){
        return "服务器端口是: " +this.serverPort + "应用名称是: " +appName;
    }
    @Value("${zqzhang.name}")
    public String myName;

    @Value("${zqzhang.job}")
    public String myjob;

    @GetMapping("/my")
    public String getMyInfo(){
        return "我的姓名是: " +this.myName + "我的工作是: " +myjob;
    }
}

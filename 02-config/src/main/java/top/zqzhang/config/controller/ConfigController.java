/*package top.zqzhang.config.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.zqzhang.config.properties.AppProperties;

@RestController
@RequestMapping("/config")
@RequiredArgsConstructor

public class ConfigController {
    private final AppProperties appProperties;
    @GetMapping("/app")
    public AppProperties getApp() {
        return appProperties;
    }
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

    @Value("${app.author}")
    private String author;

    // 默认值：app.remark 没配置时用冒号后的值兜底
    @Value("${app.remark:暂无备注}")
    private String remark;

    // 随机值
    @Value("${random.uuid}")
    private String randomUuid;

    @Value("${random.int(1,100)}")
    private Integer randomInt;

    // SpEL：先解析 ${student.age}，再算三元表达式
    @Value("#{${student.age} >= 18 ? '成年' : '未成年'}")
    private String adult;

    @GetMapping("/value")
    public String getValueCases() {
        return "author=" + author
            + "；remark=" + remark
            + "；randomUuid=" + randomUuid
            + "；randomInt=" + randomInt
            + "；adult=" + adult;
    }

}*/
package top.zqzhang.config.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.zqzhang.config.properties.AppProperties;
import top.zqzhang.config.properties.StudentProperties;
import top.zqzhang.config.service.IEnvService;

@RestController
@RequestMapping("/config")
@RequiredArgsConstructor
public class ConfigController {

    private final AppProperties appProperties;
    private final StudentProperties studentProperties;
    private final IEnvService envService;

    @Value("${server.port}")
    public Integer serverPort;
    @Value("${spring.application.name}")
    public String appName;

    @GetMapping("/basic")
    public String getBasicInfo() {
        return "服务器端口是: " + this.serverPort + "应用名称是: " + appName;
    }

    @Value("${zqzhang.name}")
    public String myName;
    @Value("${zqzhang.job}")
    public String myjob;

    @GetMapping("/my")
    public String getMyInfo() {
        return "我的姓名是: " + this.myName + "我的工作是: " + myjob;
    }

    @Value("${app.author}")
    private String author;
    @Value("${app.remark:暂无备注}")
    private String remark;
    @Value("${random.uuid}")
    private String randomUuid;
    @Value("${random.int(1,100)}")
    private Integer randomInt;
    @Value("#{${student.age} >= 18 ? '成年' : '未成年'}")
    private String adult;

    @GetMapping("/value")
    public String getValueCases() {
        return "author=" + author
                + "；remark=" + remark
                + "；randomUuid=" + randomUuid
                + "；randomInt=" + randomInt
                + "；adult=" + adult;
    }

    @GetMapping("/app")
    public AppProperties getApp() {
        return appProperties;
    }

    @GetMapping("/student")
    public StudentProperties getStudent() {
        return studentProperties;
    }

    @GetMapping("/env")
    public String getEnv() {
        return envService.envInfo();
    }
}



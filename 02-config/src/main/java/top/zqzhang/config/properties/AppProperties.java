package top.zqzhang.config.properties;

import java.time.LocalDate;

import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Data
@Validated
@Component
@ConfigurationProperties(prefix = "app")
public class AppProperties {

    @NotBlank(message = "应用名称 app.name 不能为空")
    private String name;

    private String author;

    @Min(value = 1, message = "app.port 必须大于等于 1")
    @Max(value = 65535, message = "app.port 必须小于等于 65535")
    private Integer port;

    @Min(1)
    @Max(1000)
    private Integer maxCount;

    @Email(message = "app.email 邮箱格式不正确")
    private String email;

    @Past(message = "app.birthday 必须是过去的日期")
    private LocalDate birthday;


}


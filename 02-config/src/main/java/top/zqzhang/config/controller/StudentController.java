package top.zqzhang.config.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.zqzhang.config.properties.StudentProperties;

@RestController
@RequestMapping("/student")
@AllArgsConstructor
public class StudentController {
    private final StudentProperties studentProperties;

    @GetMapping("/info")
    public StudentProperties getStudent(){
        return studentProperties;
    }
}

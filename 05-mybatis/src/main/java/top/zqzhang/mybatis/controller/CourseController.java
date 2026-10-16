package top.zqzhang.mybatis.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import top.zqzhang.mybatis.entity.Course;
import top.zqzhang.mybatis.service.CourseService;

import java.util.List;

@RestController
@RequestMapping("/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    /* 增：单条 */
    @PostMapping
    public Course create(@RequestBody Course course) {
        courseService.createCourse(course);
        return course;
    }

    /* 增：批量（练习 saveBatch） */
    @PostMapping("/batch")
    public int createBatch(@RequestBody List<Course> courses) {
        courseService.createCourseBatch(courses);
        return courses.size();
    }

    /* 查 */
    @GetMapping("/{id}")
    public Course getById(@PathVariable Long id) {
        return courseService.getCourse(id);
    }

    @GetMapping
    public List<Course> list() {
        return courseService.listCourses();
    }

    /* 删：单条 */
    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable Long id) {
        return courseService.removeCourse(id);
    }

    /* 删：批量（练习 removeByIds） */
    @DeleteMapping("/batch")
    public boolean deleteBatch(@RequestParam List<Long> ids) {
        return courseService.removeCourseBatch(ids);
    }
}

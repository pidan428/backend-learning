package top.zqzhang.mybatis.service;

import com.baomidou.mybatisplus.spring.service.IService;
import top.zqzhang.mybatis.entity.Course;

import java.util.List;

public interface CourseService extends IService<Course> {

    /* 增 */
    boolean createCourse(Course course);
    boolean createCourseBatch(List<Course> courses);

    /* 删 */
    boolean removeCourse(Long id);
    boolean removeCourseBatch(List<Long> ids);

    /* 查 */
    Course getCourse(Long id);
    List<Course> listCourses();
}

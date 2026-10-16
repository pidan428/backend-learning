package top.zqzhang.mybatis.service.impl;

import com.baomidou.mybatisplus.spring.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import top.zqzhang.mybatis.entity.Course;
import top.zqzhang.mybatis.mapper.CourseMapper;
import top.zqzhang.mybatis.service.CourseService;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements CourseService {

    private static final int BATCH_SIZE = 100;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean createCourse(Course course) {
        applyDefaults(course);
        return save(course);
    }

    @Override
    public boolean createCourseBatch(List<Course> courses) {
        courses.forEach(this::applyDefaults);
        return saveBatch(courses, BATCH_SIZE);
    }

    @Override
    public boolean removeCourse(Long id) {
        return removeById(id);
    }

    @Override
    public boolean removeCourseBatch(List<Long> ids) {
        return removeByIds(ids);
    }

    @Override
    public Course getCourse(Long id) {
        return getById(id);
    }

    @Override
    public List<Course> listCourses() {
        return list();
    }

    private void applyDefaults(Course course) {
        if (!StringUtils.hasText(course.getName())) {
            throw new IllegalArgumentException("课程名不能为空");
        }
        if (course.getCredit() == null) {
            course.setCredit(BigDecimal.ZERO);
        }
    }
}

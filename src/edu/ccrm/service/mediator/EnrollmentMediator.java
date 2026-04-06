package edu.ccrm.service.mediator;

import edu.ccrm.domain.Course;
import edu.ccrm.domain.Student;
import edu.ccrm.service.CourseService;
import edu.ccrm.service.StudentService;

/**
 * between StudentService and CourseService. Reduces direct coupling.
 */
public class EnrollmentMediator implements ServiceMediator {
    private final StudentService studentService;
    private final CourseService courseService;

    public EnrollmentMediator(StudentService studentService, CourseService courseService) {
        this.studentService = studentService;
        this.courseService = courseService;
    }

    @Override
    public Student findStudent(String regNo) {
        return studentService.findStudentByRegNo(regNo);
    }

    @Override
    public Course findCourse(String courseCode) {
        return courseService.findCourseByCode(courseCode);
    }
}

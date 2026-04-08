package serviceTest;

import edu.ccrm.domain.*;
import edu.ccrm.exception.DuplicateEnrollmentException;
import edu.ccrm.exception.MaxCreditLimitExceededException;
import edu.ccrm.service.CourseService;
import edu.ccrm.service.DataStore;
import edu.ccrm.service.EnrollmentService;
import edu.ccrm.service.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class EnrollmentServiceTest {

    private EnrollmentService enrollmentService;
    private StudentService studentService;
    private CourseService courseService;
    private Student student;
    private Course course;
    private Instructor instructor;

    @BeforeEach
    void setUp() {
        DataStore dataStore = new DataStore();
        studentService = new StudentService(dataStore);
        courseService = new CourseService(dataStore);
        enrollmentService = new EnrollmentService(studentService, courseService);

        instructor = new Instructor(1, "Dr. Smith", "smith@example.com",
                LocalDate.of(1975, 3, 20), "CSE", "Room 101");

        student = new Student(1, "REG001", "John Doe", "john@example.com",
                LocalDate.of(2000, 1, 15));

        course = new Course.Builder(new CourseCode("CSE", 101), "Intro to CS")
                .credits(3)
                .instructor(instructor)
                .semester(Semester.FALL)
                .department("CSE")
                .build();

        studentService.addStudent(student);
        courseService.addCourse(course);
    }

    // ─────────────────────────────────────────
    // enrollStudent() Tests
    // ─────────────────────────────────────────

    @Test
    void enrollStudent_ShouldEnrollSuccessfully() throws Exception {
        enrollmentService.enrollStudent("REG001", "CSE101");
        assertEquals(1, student.getEnrolledCourses().size());
    }

    @Test
    void enrollStudent_ShouldThrowDuplicateEnrollmentException_WhenAlreadyEnrolled()
            throws Exception {
        enrollmentService.enrollStudent("REG001", "CSE101");
        assertThrows(DuplicateEnrollmentException.class, () ->
                enrollmentService.enrollStudent("REG001", "CSE101"));
    }

    @Test
    void enrollStudent_ShouldThrowMaxCreditLimitExceededException_WhenLimitExceeded()
            throws Exception {
        // Add courses totaling more than 18 credits in same semester
        for (int i = 1; i <= 6; i++) {
            Course c = new Course.Builder(new CourseCode("CSE", 100 + i), "Course " + i)
                    .credits(3)
                    .instructor(instructor)
                    .semester(Semester.FALL)
                    .department("CSE")
                    .build();
            courseService.addCourse(c);
            enrollmentService.enrollStudent("REG001", "CSE" + (100 + i));
        }
        // 7th course would exceed 18 credits
        Course extra = new Course.Builder(new CourseCode("CSE", 200), "Extra Course")
                .credits(3)
                .instructor(instructor)
                .semester(Semester.FALL)
                .department("CSE")
                .build();
        courseService.addCourse(extra);
        assertThrows(MaxCreditLimitExceededException.class, () ->
                enrollmentService.enrollStudent("REG001", "CSE200"));
    }

    @Test
    void enrollStudent_ShouldAllowEnrollment_InDifferentSemesters() throws Exception {
        Course springCourse = new Course.Builder(new CourseCode("MATH", 201), "Calculus")
                .credits(3)
                .instructor(instructor)
                .semester(Semester.SPRING)
                .department("MATH")
                .build();
        courseService.addCourse(springCourse);
        enrollmentService.enrollStudent("REG001", "CSE101");
        enrollmentService.enrollStudent("REG001", "MATH201");
        assertEquals(2, student.getEnrolledCourses().size());
    }

    // ─────────────────────────────────────────
    // recordGrade() Tests
    // ─────────────────────────────────────────

    @Test
    void recordGrade_ShouldRecordGrade_WhenEnrolled() throws Exception {
        enrollmentService.enrollStudent("REG001", "CSE101");
        enrollmentService.recordGrade("REG001", "CSE101", 85);
        Enrollment enrollment = student.getEnrolledCourses().get(0);
        assertEquals(Grade.A, enrollment.getGrade());
    }

    @Test
    void recordGrade_ShouldRecordGradeS_WhenMarksIs90() throws Exception {
        enrollmentService.enrollStudent("REG001", "CSE101");
        enrollmentService.recordGrade("REG001", "CSE101", 90);
        assertEquals(Grade.S, student.getEnrolledCourses().get(0).getGrade());
    }

    @Test
    void recordGrade_ShouldRecordGradeF_WhenMarksIsLow() throws Exception {
        enrollmentService.enrollStudent("REG001", "CSE101");
        enrollmentService.recordGrade("REG001", "CSE101", 30);
        assertEquals(Grade.F, student.getEnrolledCourses().get(0).getGrade());
    }
}

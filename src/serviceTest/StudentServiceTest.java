package serviceTest;

import edu.ccrm.domain.*;
import edu.ccrm.exception.StudentNotFoundException;
import edu.ccrm.service.DataStore;
import edu.ccrm.service.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class StudentServiceTest {

    private StudentService studentService;
    private DataStore dataStore;
    private Student student;
    private Instructor instructor;
    private Course course;

    @BeforeEach
    void setUp() {
        dataStore = new DataStore();
        studentService = new StudentService(dataStore);

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
    }

    // ─────────────────────────────────────────
    // addStudent() Tests
    // ─────────────────────────────────────────

    @Test
    void addStudent_ShouldAddStudent_Successfully() {
        studentService.addStudent(student);
        assertNotNull(studentService.findStudentByRegNo("REG001"));
    }

    @Test
    void addStudent_ShouldStoreCorrectStudent() {
        studentService.addStudent(student);
        assertEquals(student, studentService.findStudentByRegNo("REG001"));
    }

    // ─────────────────────────────────────────
    // findStudentByRegNo() Tests
    // ─────────────────────────────────────────

    @Test
    void findStudentByRegNo_ShouldReturnStudent_WhenExists() {
        studentService.addStudent(student);
        Student found = studentService.findStudentByRegNo("REG001");
        assertEquals("REG001", found.getRegNo());
    }

    @Test
    void findStudentByRegNo_ShouldThrowException_WhenNotFound() {
        assertThrows(StudentNotFoundException.class, () ->
                studentService.findStudentByRegNo("INVALID"));
    }

    @Test
    void findStudentByRegNo_ShouldThrowCorrectMessage_WhenNotFound() {
        StudentNotFoundException ex = assertThrows(StudentNotFoundException.class, () ->
                studentService.findStudentByRegNo("REG999"));
        assertTrue(ex.getMessage().contains("REG999"));
    }

    // ─────────────────────────────────────────
    // getAllStudents() Tests
    // ─────────────────────────────────────────

    @Test
    void getAllStudents_ShouldReturnEmptyList_WhenNoStudents() {
        assertTrue(studentService.getAllStudents().isEmpty());
    }

    @Test
    void getAllStudents_ShouldReturnAllStudents() {
        Student student2 = new Student(2, "REG002", "Jane Doe", "jane@example.com",
                LocalDate.of(2001, 5, 10));
        studentService.addStudent(student);
        studentService.addStudent(student2);
        assertEquals(2, studentService.getAllStudents().size());
    }

    @Test
    void getAllStudents_ShouldReturnSortedByRegNo() {
        Student student2 = new Student(2, "REG002", "Jane Doe", "jane@example.com",
                LocalDate.of(2001, 5, 10));
        studentService.addStudent(student2);
        studentService.addStudent(student);
        List<Student> students = studentService.getAllStudents();
        assertEquals("REG001", students.get(0).getRegNo());
        assertEquals("REG002", students.get(1).getRegNo());
    }

    // ─────────────────────────────────────────
    // updateStudentStatus() Tests
    // ─────────────────────────────────────────

    @Test
    void updateStudentStatus_ShouldUpdateStatus_ToINACTIVE() {
        studentService.addStudent(student);
        studentService.updateStudentStatus("REG001", Student.StudentStatus.INACTIVE);
        assertEquals(Student.StudentStatus.INACTIVE,
                studentService.findStudentByRegNo("REG001").getStatus());
    }

    @Test
    void updateStudentStatus_ShouldUpdateStatus_ToGRADUATED() {
        studentService.addStudent(student);
        studentService.updateStudentStatus("REG001", Student.StudentStatus.GRADUATED);
        assertEquals(Student.StudentStatus.GRADUATED,
                studentService.findStudentByRegNo("REG001").getStatus());
    }

    @Test
    void updateStudentStatus_ShouldThrowException_WhenStudentNotFound() {
        assertThrows(StudentNotFoundException.class, () ->
                studentService.updateStudentStatus("INVALID", Student.StudentStatus.INACTIVE));
    }

    // ─────────────────────────────────────────
    // getStudentTranscript() Tests
    // ─────────────────────────────────────────

    @Test
    void getStudentTranscript_ShouldNotReturnNull() {
        studentService.addStudent(student);
        assertNotNull(studentService.getStudentTranscript("REG001"));
    }

    @Test
    void getStudentTranscript_ShouldContainStudentName() {
        studentService.addStudent(student);
        assertTrue(studentService.getStudentTranscript("REG001")
                .contains("John Doe"));
    }

    @Test
    void getStudentTranscript_ShouldContainNoCoursesMessage_WhenNotEnrolled() {
        studentService.addStudent(student);
        assertTrue(studentService.getStudentTranscript("REG001")
                .contains("No courses enrolled yet."));
    }

    @Test
    void getStudentTranscript_ShouldContainGPA_WhenEnrolled() {
        studentService.addStudent(student);
        Enrollment enrollment = new Enrollment(student, course);
        enrollment.setGrade(Grade.A);
        student.addEnrollment(enrollment);
        assertTrue(studentService.getStudentTranscript("REG001")
                .contains("GPA"));
    }

    @Test
    void getStudentTranscript_ShouldThrowException_WhenStudentNotFound() {
        assertThrows(StudentNotFoundException.class, () ->
                studentService.getStudentTranscript("INVALID"));
    }

    // ─────────────────────────────────────────
    // calculateGpa() — indirect Tests
    // ─────────────────────────────────────────

    @Test
    void getStudentTranscript_ShouldShowZeroGpa_WhenNoGradeRecorded() {
        studentService.addStudent(student);
        String transcript = studentService.getStudentTranscript("REG001");
        assertTrue(transcript.contains("GPA: 0.00"));
    }

    @Test
    void getStudentTranscript_ShouldShowCorrectGpa_WhenGradeRecorded() {
        studentService.addStudent(student);
        Enrollment enrollment = new Enrollment(student, course);
        enrollment.setGrade(Grade.S); // 10.0 gradePoint, 3 credits
        student.addEnrollment(enrollment);
        String transcript = studentService.getStudentTranscript("REG001");
        assertTrue(transcript.contains("GPA: 10.00"));
    }
}

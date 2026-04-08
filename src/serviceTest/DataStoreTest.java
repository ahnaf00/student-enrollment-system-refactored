package serviceTest;

import edu.ccrm.domain.*;
import edu.ccrm.service.DataStore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DataStoreTest {

    private DataStore dataStore;
    private Student student;
    private Course course;
    private Instructor instructor;

    @BeforeEach
    void setUp() {
        dataStore = new DataStore();

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
    // Student Tests
    // ─────────────────────────────────────────

    @Test
    void addStudent_ShouldAddStudent_Successfully() {
        dataStore.addStudent(student);
        assertNotNull(dataStore.getStudent("REG001"));
    }

    @Test
    void getStudent_ShouldReturnCorrectStudent() {
        dataStore.addStudent(student);
        assertEquals(student, dataStore.getStudent("REG001"));
    }

    @Test
    void getStudent_ShouldReturnNull_WhenStudentNotFound() {
        assertNull(dataStore.getStudent("INVALID"));
    }

    @Test
    void getAllStudents_ShouldReturnEmptyList_WhenNoStudentsAdded() {
        assertTrue(dataStore.getAllStudents().isEmpty());
    }

    @Test
    void getAllStudents_ShouldReturnAllStudents() {
        Student student2 = new Student(2, "REG002", "Jane Doe", "jane@example.com",
                LocalDate.of(2001, 5, 10));
        dataStore.addStudent(student);
        dataStore.addStudent(student2);
        assertEquals(2, dataStore.getAllStudents().size());
    }

    @Test
    void getAllStudents_ShouldReturnDefensiveCopy() {
        dataStore.addStudent(student);
        List<Student> list = dataStore.getAllStudents();
        list.clear();
        assertEquals(1, dataStore.getAllStudents().size());
    }

    // ─────────────────────────────────────────
    // Course Tests
    // ─────────────────────────────────────────

    @Test
    void addCourse_ShouldAddCourse_Successfully() {
        dataStore.addCourse(course);
        assertNotNull(dataStore.getCourse("CSE101"));
    }

    @Test
    void getCourse_ShouldReturnCorrectCourse() {
        dataStore.addCourse(course);
        assertEquals(course, dataStore.getCourse("CSE101"));
    }

    @Test
    void getCourse_ShouldReturnNull_WhenCourseNotFound() {
        assertNull(dataStore.getCourse("INVALID"));
    }

    @Test
    void getAllCourses_ShouldReturnEmptyList_WhenNoCoursesAdded() {
        assertTrue(dataStore.getAllCourses().isEmpty());
    }

    @Test
    void getAllCourses_ShouldReturnAllCourses() {
        Course course2 = new Course.Builder(new CourseCode("MATH", 201), "Calculus")
                .credits(3)
                .instructor(instructor)
                .semester(Semester.SPRING)
                .department("MATH")
                .build();
        dataStore.addCourse(course);
        dataStore.addCourse(course2);
        assertEquals(2, dataStore.getAllCourses().size());
    }

    @Test
    void getAllCourses_ShouldReturnDefensiveCopy() {
        dataStore.addCourse(course);
        List<Course> list = dataStore.getAllCourses();
        list.clear();
        assertEquals(1, dataStore.getAllCourses().size());
    }

    // ─────────────────────────────────────────
    // Instructor Tests
    // ─────────────────────────────────────────

    @Test
    void addInstructor_ShouldAddInstructor_Successfully() {
        dataStore.addInstructor(instructor);
        assertNotNull(dataStore.getInstructor(1));
    }

    @Test
    void getInstructor_ShouldReturnCorrectInstructor() {
        dataStore.addInstructor(instructor);
        assertEquals(instructor, dataStore.getInstructor(1));
    }

    @Test
    void getInstructor_ShouldReturnNull_WhenInstructorNotFound() {
        assertNull(dataStore.getInstructor(999));
    }

    @Test
    void getAllInstructors_ShouldReturnEmptyList_WhenNoInstructorsAdded() {
        assertTrue(dataStore.getAllInstructors().isEmpty());
    }

    @Test
    void getAllInstructors_ShouldReturnAllInstructors() {
        Instructor instructor2 = new Instructor(2, "Dr. Jones", "jones@example.com",
                LocalDate.of(1980, 6, 15), "MATH", "Room 202");
        dataStore.addInstructor(instructor);
        dataStore.addInstructor(instructor2);
        assertEquals(2, dataStore.getAllInstructors().size());
    }

    @Test
    void getAllInstructors_ShouldReturnDefensiveCopy() {
        dataStore.addInstructor(instructor);
        List<Instructor> list = dataStore.getAllInstructors();
        list.clear();
        assertEquals(1, dataStore.getAllInstructors().size());
    }
}
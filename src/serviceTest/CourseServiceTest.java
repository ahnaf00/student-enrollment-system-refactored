package serviceTest;

import edu.ccrm.domain.*;
import edu.ccrm.exception.CourseNotFoundException;
import edu.ccrm.service.CourseService;
import edu.ccrm.service.DataStore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CourseServiceTest {

    private CourseService courseService;
    private DataStore dataStore;
    private Instructor instructor;
    private Course course;

    @BeforeEach
    void setUp() {
        dataStore = new DataStore();
        courseService = new CourseService(dataStore);

        instructor = new Instructor(1, "Dr. Smith", "smith@example.com",
                LocalDate.of(1975, 3, 20), "CSE", "Room 101");

        course = new Course.Builder(new CourseCode("CSE", 101), "Intro to CS")
                .credits(3)
                .instructor(instructor)
                .semester(Semester.FALL)
                .department("CSE")
                .build();
    }

    // ─────────────────────────────────────────
    // addCourse() Tests
    // ─────────────────────────────────────────

    @Test
    void addCourse_ShouldAddCourse_Successfully() {
        courseService.addCourse(course);
        assertNotNull(courseService.findCourseByCode("CSE101"));
    }

    // ─────────────────────────────────────────
    // findCourseByCode() Tests
    // ─────────────────────────────────────────

    @Test
    void findCourseByCode_ShouldReturnCourse_WhenExists() {
        courseService.addCourse(course);
        assertEquals(course, courseService.findCourseByCode("CSE101"));
    }

    @Test
    void findCourseByCode_ShouldThrowException_WhenNotFound() {
        assertThrows(CourseNotFoundException.class, () ->
                courseService.findCourseByCode("INVALID"));
    }

    @Test
    void findCourseByCode_ShouldThrowCorrectMessage_WhenNotFound() {
        CourseNotFoundException ex = assertThrows(CourseNotFoundException.class, () ->
                courseService.findCourseByCode("MATH999"));
        assertTrue(ex.getMessage().contains("MATH999"));
    }

    // ─────────────────────────────────────────
    // getAllCourses() Tests
    // ─────────────────────────────────────────

    @Test
    void getAllCourses_ShouldReturnEmptyList_WhenNoCourses() {
        assertTrue(courseService.getAllCourses().isEmpty());
    }

    @Test
    void getAllCourses_ShouldReturnAllCourses() {
        Course course2 = new Course.Builder(new CourseCode("MATH", 201), "Calculus")
                .credits(3)
                .instructor(instructor)
                .semester(Semester.SPRING)
                .department("MATH")
                .build();
        courseService.addCourse(course);
        courseService.addCourse(course2);
        assertEquals(2, courseService.getAllCourses().size());
    }

    @Test
    void getAllCourses_ShouldReturnSortedByCourseCode() {
        Course course2 = new Course.Builder(new CourseCode("MATH", 201), "Calculus")
                .credits(3)
                .instructor(instructor)
                .semester(Semester.SPRING)
                .department("MATH")
                .build();
        courseService.addCourse(course2);
        courseService.addCourse(course);
        List<Course> courses = courseService.getAllCourses();
        assertEquals("CSE101", courses.get(0).getCourseCode().getFullCode());
        assertEquals("MATH201", courses.get(1).getCourseCode().getFullCode());
    }

    // ─────────────────────────────────────────
    // filterByInstructor() Tests
    // ─────────────────────────────────────────

    @Test
    void filterByInstructor_ShouldReturnMatchingCourse() {
        courseService.addCourse(course);
        List<Course> result = courseService.searchCourses(
                CourseService.filterByInstructor("Dr. Smith"));
        assertEquals(1, result.size());
    }

    @Test
    void filterByInstructor_ShouldBeCaseInsensitive() {
        courseService.addCourse(course);
        List<Course> result = courseService.searchCourses(
                CourseService.filterByInstructor("dr. smith"));
        assertEquals(1, result.size());
    }

    @Test
    void filterByInstructor_ShouldReturnEmpty_WhenNoMatch() {
        courseService.addCourse(course);
        List<Course> result = courseService.searchCourses(
                CourseService.filterByInstructor("Dr. Jones"));
        assertTrue(result.isEmpty());
    }

    // ─────────────────────────────────────────
    // filterByDepartment() Tests
    // ─────────────────────────────────────────

    @Test
    void filterByDepartment_ShouldReturnMatchingCourse() {
        courseService.addCourse(course);
        List<Course> result = courseService.searchCourses(
                CourseService.filterByDepartment("CSE"));
        assertEquals(1, result.size());
    }

    @Test
    void filterByDepartment_ShouldBeCaseInsensitive() {
        courseService.addCourse(course);
        List<Course> result = courseService.searchCourses(
                CourseService.filterByDepartment("cse"));
        assertEquals(1, result.size());
    }

    @Test
    void filterByDepartment_ShouldReturnEmpty_WhenNoMatch() {
        courseService.addCourse(course);
        List<Course> result = courseService.searchCourses(
                CourseService.filterByDepartment("MATH"));
        assertTrue(result.isEmpty());
    }

    // ─────────────────────────────────────────
    // filterBySemester() Tests
    // ─────────────────────────────────────────

    @Test
    void filterBySemester_ShouldReturnMatchingCourse() {
        courseService.addCourse(course);
        List<Course> result = courseService.searchCourses(
                CourseService.filterBySemester(Semester.FALL));
        assertEquals(1, result.size());
    }

    @Test
    void filterBySemester_ShouldReturnEmpty_WhenNoMatch() {
        courseService.addCourse(course);
        List<Course> result = courseService.searchCourses(
                CourseService.filterBySemester(Semester.SPRING));
        assertTrue(result.isEmpty());
    }
}

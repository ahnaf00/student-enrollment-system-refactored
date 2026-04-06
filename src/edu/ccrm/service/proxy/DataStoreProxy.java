package edu.ccrm.service.proxy;

import java.util.List;

import edu.ccrm.domain.Course;
import edu.ccrm.domain.Instructor;
import edu.ccrm.domain.Student;

/**
 * The calling code doesn't know it's talking to a proxy — it sees the same interface.
 */
public class DataStoreProxy implements DataStoreInterface {
    private final DataStoreInterface realDataStore;

    public DataStoreProxy(DataStoreInterface realDataStore) {
        this.realDataStore = realDataStore;
    }

    @Override
    public void addStudent(Student student) {
        if (student == null) {
            throw new IllegalArgumentException("Student cannot be null");
        }
        if (student.getRegNo() == null || student.getRegNo().trim().isEmpty()) {
            throw new IllegalArgumentException("Student registration number cannot be empty");
        }
        System.out.println("[PROXY] Validated and adding student: " + student.getRegNo());
        realDataStore.addStudent(student);
    }

    @Override
    public Student getStudent(String regNo) {
        if (regNo == null || regNo.trim().isEmpty()) {
            throw new IllegalArgumentException("Registration number cannot be null or empty");
        }
        return realDataStore.getStudent(regNo);
    }

    @Override
    public List<Student> getAllStudents() {
        return realDataStore.getAllStudents();
    }

    @Override
    public void addCourse(Course course) {
        if (course == null) {
            throw new IllegalArgumentException("Course cannot be null");
        }
        System.out.println("[PROXY] Validated and adding course: " + course.getCourseCode().getFullCode());
        realDataStore.addCourse(course);
    }

    @Override
    public Course getCourse(String courseCode) {
        if (courseCode == null || courseCode.trim().isEmpty()) {
            throw new IllegalArgumentException("Course code cannot be null or empty");
        }
        return realDataStore.getCourse(courseCode);
    }

    @Override
    public List<Course> getAllCourses() {
        return realDataStore.getAllCourses();
    }

    @Override
    public void addInstructor(Instructor instructor) {
        if (instructor == null) {
            throw new IllegalArgumentException("Instructor cannot be null");
        }
        System.out.println("[PROXY] Validated and adding instructor: " + instructor.getFullName());
        realDataStore.addInstructor(instructor);
    }

    @Override
    public Instructor getInstructor(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Instructor ID must be positive");
        }
        return realDataStore.getInstructor(id);
    }

    @Override
    public List<Instructor> getAllInstructors() {
        return realDataStore.getAllInstructors();
    }
}

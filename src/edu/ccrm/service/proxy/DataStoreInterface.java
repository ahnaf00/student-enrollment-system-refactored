package edu.ccrm.service.proxy;

import java.util.List;

import edu.ccrm.domain.Course;
import edu.ccrm.domain.Instructor;
import edu.ccrm.domain.Student;

/**
 * Both the real DataStore and the DataStoreProxy implement this interface,
 * making the proxy transparent to the calling code.
 */
public interface DataStoreInterface {
    void addStudent(Student student);
    Student getStudent(String regNo);
    List<Student> getAllStudents();

    void addCourse(Course course);
    Course getCourse(String courseCode);
    List<Course> getAllCourses();

    void addInstructor(Instructor instructor);
    Instructor getInstructor(int id);
    List<Instructor> getAllInstructors();
}

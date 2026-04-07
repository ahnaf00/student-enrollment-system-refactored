package edu.ccrm.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.ccrm.domain.Course;
import edu.ccrm.domain.Instructor;
import edu.ccrm.domain.Student;
import edu.ccrm.service.proxy.DataStoreInterface;


public class DataStore implements DataStoreInterface {
    private final Map<String, Student> students = new HashMap<>();
    private final Map<String, Course> courses = new HashMap<>();
    private final Map<Integer, Instructor> instructors = new HashMap<>();
    
    @Override
    public void addStudent(Student student) {
        students.put(student.getRegNo(), student);
    }
    
    @Override
    public Student getStudent(String regNo) {
        return students.get(regNo);
    }
    
    @Override
    public List<Student> getAllStudents() {
        return new ArrayList<>(students.values());
    }
    
    @Override
    public void addCourse(Course course) {
        courses.put(course.getCourseCode().getFullCode(), course);
    }
    
    @Override
    public Course getCourse(String courseCode) {
        return courses.get(courseCode);
    }
    
    @Override
    public List<Course> getAllCourses() {
        return new ArrayList<>(courses.values());
    }
    
    @Override
    public void addInstructor(Instructor instructor) {
        instructors.put(instructor.getId(), instructor);
    }
    
    @Override
    public Instructor getInstructor(int id) {
        return instructors.get(id);
    }
    
    @Override
    public List<Instructor> getAllInstructors() {
        return new ArrayList<>(instructors.values());
    }
}
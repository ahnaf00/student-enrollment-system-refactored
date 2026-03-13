package edu.ccrm.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.ccrm.domain.Course;
import edu.ccrm.domain.Instructor;
import edu.ccrm.domain.Student;

public class DataStore {
    private final Map<String, Student> students = new HashMap<>();
    private final Map<String, Course> courses = new HashMap<>();
    private final Map<Integer, Instructor> instructors = new HashMap<>();
    
    public void addStudent(Student student) {
        students.put(student.getRegNo(), student);
    }
    
    public Student getStudent(String regNo) {
        return students.get(regNo);
    }
    
    public List<Student> getAllStudents() {
        return new ArrayList<>(students.values());
    }
    
    public void addCourse(Course course) {
        courses.put(course.getCourseCode().getFullCode(), course);
    }
    
    public Course getCourse(String courseCode) {
        return courses.get(courseCode);
    }
    
    public List<Course> getAllCourses() {
        return new ArrayList<>(courses.values());
    }
    
    public void addInstructor(Instructor instructor) {
        instructors.put(instructor.getId(), instructor);
    }
    
    public Instructor getInstructor(int id) {
        return instructors.get(id);
    }
    
    public List<Instructor> getAllInstructors() {
        return new ArrayList<>(instructors.values());
    }
}
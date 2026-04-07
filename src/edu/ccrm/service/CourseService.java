package edu.ccrm.service;

import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import edu.ccrm.domain.Course;
import edu.ccrm.domain.Semester;
import edu.ccrm.exception.CourseNotFoundException;
import edu.ccrm.service.proxy.DataStoreInterface;


public class CourseService {
    private final DataStoreInterface dataStore;
    
    public CourseService(DataStoreInterface dataStore) {
        this.dataStore = dataStore;
    }
    
    public void addCourse(Course course) {
        dataStore.addCourse(course);
    }
    
    public Course findCourseByCode(String courseCode) {
        Course course = dataStore.getCourse(courseCode);
        if (course == null) {
            throw new CourseNotFoundException("Course with code '" + courseCode + "' not found.");
        }
        return course;
    }
    
    public List<Course> getAllCourses() {
        return dataStore.getAllCourses().stream()
                .sorted(Comparator.comparing(
                    course -> course.getCourseCode().getFullCode()))
                .collect(Collectors.toList());
    }

    public static Predicate<Course> filterByInstructor(String instructorName) {
        return course -> course.getInstructor().getFullName().equalsIgnoreCase(instructorName);
    }
    
    public static Predicate<Course> filterByDepartment(String department) {
        return course -> course.getDepartment().equalsIgnoreCase(department);
    }
    
    public static Predicate<Course> filterBySemester(Semester semester) {
        return course -> course.getSemester() == semester;
    }
    
    public List<Course> searchCourses(Predicate<Course> filter) {
        return getAllCourses().stream()
            .filter(filter)
            .collect(Collectors.toList());
    }
}
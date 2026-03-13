package edu.ccrm.cli.helpers;

import java.util.Scanner;
import java.util.function.Predicate;

import edu.ccrm.domain.Course;
import edu.ccrm.domain.Semester;
import edu.ccrm.service.CourseService;
import edu.ccrm.util.InputValidator;

public class CourseManagementHelper {
    private final CourseService courseService;
    
    public CourseManagementHelper(CourseService courseService) {
        this.courseService = courseService;
    }
    
    public void handleCourseManagement(Scanner scanner) {
        System.out.println("\n--- Course Management ---");
        System.out.println("1. List All Courses");
        System.out.println("2. Search & Filter Courses");
        
        int choice = InputValidator.getInt(scanner, "Enter choice: ");
        
        switch (choice) {
            case 1 -> listAllCourses();
            case 2 -> filterCourses(scanner);
            default -> System.out.println("Invalid choice.");
        }
    }
    
    private void listAllCourses() {
        System.out.println("\n--- List of All Courses ---");
        courseService.getAllCourses().forEach(System.out::println);
    }
    
    private void filterCourses(Scanner scanner) {
        System.out.println("Filter by: 1. Instructor, 2. Department, 3. Semester");
        int filterChoice = InputValidator.getInt(scanner, "Enter filter type: ");
        Predicate<Course> filter = course -> true;
        
        if (filterChoice == 1) {
            String name = InputValidator.getString(scanner, "Enter Instructor Name: ");
            filter = CourseService.filterByInstructor(name);
        } else if (filterChoice == 2) {
            String dept = InputValidator.getString(scanner, "Enter Department: ");
            filter = CourseService.filterByDepartment(dept);
        } else if (filterChoice == 3) {
            String semStr = InputValidator.getString(scanner, "Enter Semester (SPRING, FALL, etc.): ");
            try {
                Semester sem = Semester.valueOf(semStr.toUpperCase());
                filter = CourseService.filterBySemester(sem);
            } catch (IllegalArgumentException e) {
                System.err.println("Invalid semester.");
                return;
            }
        }
        
        System.out.println("\n--- Filtered Courses ---");
        courseService.searchCourses(filter).forEach(System.out::println);
    }
}
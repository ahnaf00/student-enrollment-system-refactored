package edu.ccrm.service;

import edu.ccrm.config.AppConfig;
import edu.ccrm.domain.Course;
import edu.ccrm.domain.Enrollment;
import edu.ccrm.domain.Grade;
import edu.ccrm.domain.Student;
import edu.ccrm.exception.DuplicateEnrollmentException;
import edu.ccrm.exception.MaxCreditLimitExceededException;

public class EnrollmentService {
    private final StudentService studentService;
    private final CourseService courseService;
    
    public EnrollmentService(StudentService ss, CourseService cs) {
        this.studentService = ss;
        this.courseService = cs;
    }

    public void enrollStudent(String regNo, String courseCode)
        throws MaxCreditLimitExceededException, DuplicateEnrollmentException {
        Student student = studentService.findStudentByRegNo(regNo);
        Course course = courseService.findCourseByCode(courseCode);

        validateNoDuplicateEnrollment(student, courseCode);
        validateCreditLimit(student, course);

        Enrollment newEnrollment = new Enrollment(student, course);
        student.addEnrollment(newEnrollment);
        System.out.println("Enrollment successful!");
    }
    
    public void recordGrade(String regNo, String courseCode, int marks) {
        Student student = studentService.findStudentByRegNo(regNo);

        Enrollment enrollment = findEnrollment(student, courseCode);
        
        if (enrollment != null) {
            enrollment.setGrade(Grade.fromMarks(marks));
            System.out.println("Grade recorded successfully for " + regNo + " in " + courseCode);
        } else {
            System.out.println("Error: Student " + regNo + " is not enrolled in course " + courseCode);
        }
    }

    private void validateNoDuplicateEnrollment(Student student, String courseCode) 
        throws DuplicateEnrollmentException {
        boolean alreadyEnrolled = student.getEnrolledCourses().stream()
            .anyMatch(e -> e.getCourse().getCourseCode().getFullCode().equals(courseCode));
        
        if (alreadyEnrolled) {
            throw new DuplicateEnrollmentException(
                "Student " + student.getRegNo() + " is already enrolled in course " + courseCode);
        }
    }

    private void validateCreditLimit(Student student, Course course) 
        throws MaxCreditLimitExceededException {
        int currentCredits = student.getEnrolledCourses().stream()
            .filter(e -> e.getCourse().getSemester() == course.getSemester())
            .mapToInt(e -> e.getCourse().getCredits())
            .sum();
        
        int maxCredits = AppConfig.getInstance().getMaxCreditsPerSemester();
        if (currentCredits + course.getCredits() > maxCredits) {
            throw new MaxCreditLimitExceededException(
                "Cannot enroll. Exceeds max credit limit of " + maxCredits + " for the semester.");
        }
    }

    private Enrollment findEnrollment(Student student, String courseCode) {
        return student.getEnrolledCourses().stream()
            .filter(en -> en.getCourse().getCourseCode().getFullCode().equals(courseCode))
            .findFirst()
            .orElse(null);
    }
}
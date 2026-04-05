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
 
    public EnrollmentService(StudentService studentService, CourseService courseService) {  // GOOD
        this.studentService = studentService;
        this.courseService = courseService;
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
        boolean alreadyEnrolled = findEnrollment(student, courseCode) != null;
        if (alreadyEnrolled) {
            throw new DuplicateEnrollmentException(
                "Student " + student.getRegNo() + " is already enrolled in " + courseCode);
        }
    }
     
    private void validateCreditLimit(Student student, Course course)
            throws MaxCreditLimitExceededException {
        int currentCredits = student.getEnrolledCourses().stream()
                .filter(enrollment -> enrollment.getCourse().getSemester()
                    == course.getSemester())
                .mapToInt(enrollment -> enrollment.getCourse().getCredits())
                .sum();
    }
     
    private Enrollment findEnrollment(Student student, String courseCode) {
        return student.getEnrolledCourses().stream()
                .filter(enrollment ->
                    enrollment.getCourse().getCourseCode().getFullCode()
                        .equals(courseCode))
                .findFirst().orElse(null);
    }

}
package edu.ccrm.service;

import java.util.ArrayList;
import java.util.List;

import edu.ccrm.domain.Course;
import edu.ccrm.domain.Enrollment;
import edu.ccrm.domain.Student;
import edu.ccrm.exception.DuplicateEnrollmentException;
import edu.ccrm.exception.MaxCreditLimitExceededException;
import edu.ccrm.service.decorator.EnrollmentServiceInterface;
import edu.ccrm.service.mediator.ServiceMediator;
import edu.ccrm.service.observer.EnrollmentObserver;
import edu.ccrm.service.strategy.GradingStrategy;


public class EnrollmentService implements EnrollmentServiceInterface {
    private final ServiceMediator mediator;
    private final GradingStrategy gradingStrategy;
    private final List<EnrollmentObserver> observers = new ArrayList<>();

    public EnrollmentService(ServiceMediator mediator, GradingStrategy gradingStrategy) {
        this.mediator = mediator;
        this.gradingStrategy = gradingStrategy;
    }

    public void addObserver(EnrollmentObserver observer) {
        observers.add(observer);
    }

    @Override
    public void enrollStudent(String regNo, String courseCode)
            throws MaxCreditLimitExceededException, DuplicateEnrollmentException {
        Student student = mediator.findStudent(regNo);
        Course course = mediator.findCourse(courseCode);

        validateNoDuplicateEnrollment(student, courseCode);
        validateCreditLimit(student, course);

        Enrollment newEnrollment = new Enrollment(student, course);
        student.addEnrollment(newEnrollment);
        System.out.println("Enrollment successful!");
        notifyEnrollment(student, course);
    }

    @Override
    public void recordGrade(String regNo, String courseCode, int marks) {
        Student student = mediator.findStudent(regNo);
        Enrollment enrollment = findEnrollment(student, courseCode);

        if (enrollment != null) {
            enrollment.setGrade(gradingStrategy.determineGrade(marks));
            System.out.println("Grade recorded successfully for " + regNo + " in " + courseCode);
            notifyGradeRecorded(student, enrollment.getCourse(),
                    enrollment.getGrade().toString());
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

    private void notifyEnrollment(Student student, Course course) {
        for (EnrollmentObserver observer : observers) {
            observer.onEnrollment(student, course);
        }
    }

    private void notifyGradeRecorded(Student student, Course course, String grade) {
        for (EnrollmentObserver observer : observers) {
            observer.onGradeRecorded(student, course, grade);
        }
    }
}
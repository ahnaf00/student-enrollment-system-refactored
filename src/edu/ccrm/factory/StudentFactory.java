package edu.ccrm.factory;

import edu.ccrm.domain.Student;
import java.time.LocalDate;

/**
 * extras[0] = registration number (regNo)
 */
public class StudentFactory extends PersonFactory {
    @Override
    public Student createPerson(int id, String fullName, String email,
                                LocalDate dateOfBirth, String... extras) {
        String regNo = extras[0];
        return new Student(id, regNo, fullName, email, dateOfBirth);
    }
}

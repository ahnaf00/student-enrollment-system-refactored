package edu.ccrm.io;

import java.time.LocalDate;

import edu.ccrm.domain.Instructor;
import edu.ccrm.service.DataStore;

public class InstructorSetupService {
    private final DataStore dataStore;
    
    public InstructorSetupService(DataStore dataStore) {
        this.dataStore = dataStore;
    }
    
    public void initializeInstructors() {
        dataStore.addInstructor(new Instructor(1, "Dr. Alan Turing",
            "alan.t@bletchley.uk", LocalDate.of(1912, 6, 23),
            "Computer Science", "A101"));
        dataStore.addInstructor(new Instructor(2, "Dr. Grace Hopper",
            "grace.h@yale.edu", LocalDate.of(1906, 12, 9),
            "Computer Science", "B202"));
    }
}
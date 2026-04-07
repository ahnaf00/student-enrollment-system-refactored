package edu.ccrm.io;

import java.time.LocalDate;

import edu.ccrm.domain.Instructor;
import edu.ccrm.factory.InstructorFactory;
import edu.ccrm.service.proxy.DataStoreInterface;


public class InstructorSetupService {
    private final DataStoreInterface dataStore;
    private final InstructorFactory instructorFactory = new InstructorFactory();
    
    public InstructorSetupService(DataStoreInterface dataStore) {
        this.dataStore = dataStore;
    }
    
    public void initializeInstructors() {
        Instructor turing = (Instructor) instructorFactory.createPerson(
                1, "Dr. Alan Turing", "alan.t@bletchley.uk",
                LocalDate.of(1912, 6, 23),
                "Computer Science", "A101");
        dataStore.addInstructor(turing);

        Instructor hopper = (Instructor) instructorFactory.createPerson(
                2, "Dr. Grace Hopper", "grace.h@yale.edu",
                LocalDate.of(1906, 12, 9),
                "Computer Science", "B202");
        dataStore.addInstructor(hopper);
    }
}
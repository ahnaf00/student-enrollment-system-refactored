package edu.ccrm.service.strategy;

import edu.ccrm.domain.Grade;

public class RelativeGradingStrategy implements GradingStrategy {
    @Override
    public Grade determineGrade(int marks) {
        if (marks >= 85) return Grade.S;
        if (marks >= 75) return Grade.A;
        if (marks >= 65) return Grade.B;
        if (marks >= 55) return Grade.C;
        if (marks >= 45) return Grade.D;
        if (marks >= 35) return Grade.E;
        return Grade.F;
    }
}

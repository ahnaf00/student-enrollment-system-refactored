package edu.ccrm.domainTest;

import edu.ccrm.domain.Grade;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GradeTest {

    // ─────────────────────────────────────────
    // getGradePoint() Tests
    // ─────────────────────────────────────────

    @Test
    void getGradePoint_ShouldReturn10_ForS() {
        assertEquals(10.0, Grade.S.getGradePoint());
    }

    @Test
    void getGradePoint_ShouldReturn9_ForA() {
        assertEquals(9.0, Grade.A.getGradePoint());
    }

    @Test
    void getGradePoint_ShouldReturn8_ForB() {
        assertEquals(8.0, Grade.B.getGradePoint());
    }

    @Test
    void getGradePoint_ShouldReturn7_ForC() {
        assertEquals(7.0, Grade.C.getGradePoint());
    }

    @Test
    void getGradePoint_ShouldReturn6_ForD() {
        assertEquals(6.0, Grade.D.getGradePoint());
    }

    @Test
    void getGradePoint_ShouldReturn5_ForE() {
        assertEquals(5.0, Grade.E.getGradePoint());
    }

    @Test
    void getGradePoint_ShouldReturn0_ForF() {
        assertEquals(0.0, Grade.F.getGradePoint());
    }

    @Test
    void getGradePoint_ShouldReturnMinus1_ForNA() {
        assertEquals(-1.0, Grade.NA.getGradePoint());
    }

    // ─────────────────────────────────────────
    // fromMarks() — Main Cases
    // ─────────────────────────────────────────

    @Test
    void fromMarks_ShouldReturnS_WhenMarksIs90() {
        assertEquals(Grade.S, Grade.fromMarks(90));
    }

    @Test
    void fromMarks_ShouldReturnS_WhenMarksIs100() {
        assertEquals(Grade.S, Grade.fromMarks(100));
    }

    @Test
    void fromMarks_ShouldReturnA_WhenMarksIs80() {
        assertEquals(Grade.A, Grade.fromMarks(80));
    }

    @Test
    void fromMarks_ShouldReturnA_WhenMarksIs89() {
        assertEquals(Grade.A, Grade.fromMarks(89));
    }

    @Test
    void fromMarks_ShouldReturnB_WhenMarksIs70() {
        assertEquals(Grade.B, Grade.fromMarks(70));
    }

    @Test
    void fromMarks_ShouldReturnB_WhenMarksIs79() {
        assertEquals(Grade.B, Grade.fromMarks(79));
    }

    @Test
    void fromMarks_ShouldReturnC_WhenMarksIs60() {
        assertEquals(Grade.C, Grade.fromMarks(60));
    }

    @Test
    void fromMarks_ShouldReturnC_WhenMarksIs69() {
        assertEquals(Grade.C, Grade.fromMarks(69));
    }

    @Test
    void fromMarks_ShouldReturnD_WhenMarksIs50() {
        assertEquals(Grade.D, Grade.fromMarks(50));
    }

    @Test
    void fromMarks_ShouldReturnD_WhenMarksIs59() {
        assertEquals(Grade.D, Grade.fromMarks(59));
    }

    @Test
    void fromMarks_ShouldReturnE_WhenMarksIs40() {
        assertEquals(Grade.E, Grade.fromMarks(40));
    }

    @Test
    void fromMarks_ShouldReturnE_WhenMarksIs49() {
        assertEquals(Grade.E, Grade.fromMarks(49));
    }

    @Test
    void fromMarks_ShouldReturnF_WhenMarksIs39() {
        assertEquals(Grade.F, Grade.fromMarks(39));
    }

    @Test
    void fromMarks_ShouldReturnF_WhenMarksIs0() {
        assertEquals(Grade.F, Grade.fromMarks(0));
    }

    @Test
    void fromMarks_ShouldReturnF_WhenMarksIsNegative() {
        assertEquals(Grade.F, Grade.fromMarks(-1));
    }

    // ─────────────────────────────────────────
    // fromMarks() — Boundary Edge Cases
    // ─────────────────────────────────────────

    @Test
    void fromMarks_ShouldReturnA_NotS_WhenMarksIs89() {
        assertNotEquals(Grade.S, Grade.fromMarks(89));
    }

    @Test
    void fromMarks_ShouldReturnB_NotA_WhenMarksIs79() {
        assertNotEquals(Grade.A, Grade.fromMarks(79));
    }

    @Test
    void fromMarks_ShouldReturnC_NotB_WhenMarksIs69() {
        assertNotEquals(Grade.B, Grade.fromMarks(69));
    }

    @Test
    void fromMarks_ShouldReturnD_NotC_WhenMarksIs59() {
        assertNotEquals(Grade.C, Grade.fromMarks(59));
    }

    @Test
    void fromMarks_ShouldReturnE_NotD_WhenMarksIs49() {
        assertNotEquals(Grade.D, Grade.fromMarks(49));
    }

    @Test
    void fromMarks_ShouldReturnF_NotE_WhenMarksIs39() {
        assertNotEquals(Grade.E, Grade.fromMarks(39));
    }

    // ─────────────────────────────────────────
    // Enum Values Tests
    // ─────────────────────────────────────────

    @Test
    void enumValues_ShouldContainAllGrades() {
        assertEquals(8, Grade.values().length);
    }

    @Test
    void enumValueOf_ShouldReturnCorrectGrade_ForS() {
        assertEquals(Grade.S, Grade.valueOf("S"));
    }

    @Test
    void enumValueOf_ShouldReturnCorrectGrade_ForNA() {
        assertEquals(Grade.NA, Grade.valueOf("NA"));
    }

    @Test
    void enumValueOf_ShouldReturnCorrectGrade_ForF() {
        assertEquals(Grade.F, Grade.valueOf("F"));
    }
}
package mn.edu.must.sqat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class GradeCalculatorTest {

    @Test
    @DisplayName("Ердийн оноонууд A, B, C, D, F дүнтэй таарна")
    void typicalScoresMatchLetterGrades() {
        // Arrange
        GradeCalculator calc = new GradeCalculator();
        // Act
        String[] grades = {calc.letterGrade(95), calc.letterGrade(85),
                calc.letterGrade(75), calc.letterGrade(65), calc.letterGrade(30)};
        // Assert
        assertAll(
                () -> assertEquals("A", grades[0]),
                () -> assertEquals("B", grades[1]),
                () -> assertEquals("C", grades[2]),
                () -> assertEquals("D", grades[3]),
                () -> assertEquals("F", grades[4]));
    }

    @Test
    @DisplayName("Яг 90 оноо A дүн байна")
    void ninetyIsExactlyA() {
        // Arrange
        GradeCalculator calc = new GradeCalculator();
        // Act
        String grade = calc.letterGrade(90);
        // Assert
        assertEquals("A", grade);
    }

    @Test
    @DisplayName("89.99 оноог A болгож тоймлохгүй")
    void justBelowNinetyIsB() {
        // Arrange
        GradeCalculator calc = new GradeCalculator();
        // Act
        String grade = calc.letterGrade(89.99);
        // Assert
        assertEquals("B", grade);
    }

    @Test
    @DisplayName("Яг 60 оноо D дүн байна")
    void sixtyIsExactlyD() {
        // Arrange
        GradeCalculator calc = new GradeCalculator();
        // Act
        String grade = calc.letterGrade(60);
        // Assert
        assertEquals("D", grade);
    }

    @Test
    @DisplayName("59.99 оноо F дүн байна")
    void justBelowSixtyIsF() {
        // Arrange
        GradeCalculator calc = new GradeCalculator();
        // Act
        String grade = calc.letterGrade(59.99);
        // Assert
        assertEquals("F", grade);
    }

    @Test
    @DisplayName("0 болон 100 оноог зөвшөөрнө")
    void endpointScoresAreValid() {
        // Arrange
        GradeCalculator calc = new GradeCalculator();
        // Act
        String lowest = calc.letterGrade(0);
        String highest = calc.letterGrade(100);
        // Assert
        assertAll(() -> assertEquals("F", lowest), () -> assertEquals("A", highest));
    }

    @Test
    @DisplayName("Сөрөг нийт оноонд алдаа өгнө")
    void negativeLetterScoreIsRejected() {
        // Arrange
        GradeCalculator calc = new GradeCalculator();
        // Act — assertThrows дотор ажиллуулах үйлдэл
        Executable action = () -> calc.letterGrade(-1);
        // Assert
        assertThrows(IllegalArgumentException.class, action);
    }

    @Test
    @DisplayName("100-аас их нийт оноонд алдаа өгнө")
    void letterScoreAboveMaximumIsRejected() {
        // Arrange
        GradeCalculator calc = new GradeCalculator();
        // Act
        Executable action = () -> calc.letterGrade(101);
        // Assert
        assertThrows(IllegalArgumentException.class, action);
    }

    @Test
    @DisplayName("Бүх хэсэг бүтэн оноотой бол нийлбэр 100 байна")
    void fullComponentScoresTotalOneHundred() {
        // Arrange
        GradeCalculator calc = new GradeCalculator();
        // Act
        double total = calc.totalScore(10, 40, 10, 10, 30);
        // Assert
        assertEquals(100, total, 0.000001);
    }

    @Test
    @DisplayName("Ирц сөрөг бол нийлбэр тооцохгүй")
    void negativeAttendanceIsRejected() {
        // Arrange
        GradeCalculator calc = new GradeCalculator();
        // Act
        Executable action = () -> calc.totalScore(-5, 30, 8, 9, 25);
        // Assert
        assertThrows(IllegalArgumentException.class, action);
    }

    @Test
    @DisplayName("Лабораторийн оноо 40-өөс их бол алдаа өгнө")
    void labAboveMaximumIsRejected() {
        // Arrange
        GradeCalculator calc = new GradeCalculator();
        // Act
        Executable action = () -> calc.totalScore(10, 41, 8, 9, 25);
        // Assert
        assertThrows(IllegalArgumentException.class, action);
    }
}

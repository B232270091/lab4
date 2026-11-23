package mn.edu.must.sqat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class GradeCalculatorTest {

    @ParameterizedTest(name = "{0} оноо → {1}")
    @DisplayName("Үсгэн дүн солигдох бүх заагийг шалгана")
    @CsvSource({"90,A", "89.99,B", "80,B", "79.99,C", "70,C",
            "69.99,D", "60,D", "59.99,F", "0,F", "100,A"})
    void letterGradeBoundaries(double score, String expected) {
        // Arrange
        GradeCalculator calc = new GradeCalculator();
        // Act
        String actual = calc.letterGrade(score);
        // Assert
        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @DisplayName("Зөвшөөрөгдөх оноонуудын нийлбэр зөв байна")
    @CsvSource({
            "0,0,0,0,0,0",
            "10,40,10,10,30,100",
            "8,32,7,9,24,80",
            "9.5,35.5,8.5,9,27.5,90",
            "0.1,0.2,0.3,0.4,0.5,1.5"
    })
    void totalScoreForValidComponents(double att, double lab, double quiz1,
                                      double quiz2, double exam, double expected) {
        // Arrange
        GradeCalculator calc = new GradeCalculator();
        // Act
        double actual = calc.totalScore(att, lab, quiz1, quiz2, exam);
        // Assert
        assertEquals(expected, actual, 0.000001);
    }

    @ParameterizedTest
    @DisplayName("Хэсэг бүрийн сөрөг болон хэтэрсэн оноог хориглоно")
    @CsvSource({
            "-0.01,20,5,5,15", "10.01,20,5,5,15",
            "5,-0.01,5,5,15", "5,40.01,5,5,15",
            "5,20,-0.01,5,15", "5,20,10.01,5,15",
            "5,20,5,-0.01,15", "5,20,5,10.01,15",
            "5,20,5,5,-0.01", "5,20,5,5,30.01"
    })
    void invalidComponentScoresAreRejected(double att, double lab, double quiz1,
                                            double quiz2, double exam) {
        // Arrange
        GradeCalculator calc = new GradeCalculator();
        // Act
        Executable action = () -> calc.totalScore(att, lab, quiz1, quiz2, exam);
        // Assert
        assertThrows(IllegalArgumentException.class, action);
    }

    @ParameterizedTest
    @DisplayName("NaN болон хязгааргүй утга үсгэн дүн болохгүй")
    @ValueSource(doubles = {Double.NaN, Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY})
    void nonFiniteLetterScoresAreRejected(double score) {
        // Arrange
        GradeCalculator calc = new GradeCalculator();
        // Act
        Executable action = () -> calc.letterGrade(score);
        // Assert
        assertThrows(IllegalArgumentException.class, action);
    }

    @ParameterizedTest
    @DisplayName("Хэсгийн оноо NaN эсвэл хязгааргүй байж болохгүй")
    @CsvSource({
            "NaN,20,5,5,15", "5,NaN,5,5,15", "5,20,NaN,5,15",
            "5,20,5,Infinity,15", "5,20,5,5,-Infinity"
    })
    void nonFiniteComponentsAreRejected(double att, double lab, double quiz1,
                                        double quiz2, double exam) {
        // Arrange
        GradeCalculator calc = new GradeCalculator();
        // Act
        Executable action = () -> calc.totalScore(att, lab, quiz1, quiz2, exam);
        // Assert
        assertThrows(IllegalArgumentException.class, action);
    }

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

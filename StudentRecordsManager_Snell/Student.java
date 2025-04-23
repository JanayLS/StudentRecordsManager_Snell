import java.util.Arrays;

/**
 * Class representing a student record with grades.
 */
public class Student {
    private String studentId;
    private String name;
    private int[] grades;
    private double averageGrade;
    private char letterGrade;

    public Student(String studentId, String name, int[] grades) {
        this.studentId = studentId;
        this.name = name;
        this.grades = grades;
        this.averageGrade = calculateAverage();
        this.letterGrade = determineLetterGrade();
    }

    private double calculateAverage() {
        int total = 0;
        for (int grade : grades) {
            total += grade;
        }
        return grades.length > 0 ? (double) total / grades.length : 0;
    }

    private char determineLetterGrade() {
        if (averageGrade >= 90) return 'A';
        else if (averageGrade >= 80) return 'B';
        else if (averageGrade >= 70) return 'C';
        else if (averageGrade >= 60) return 'D';
        else return 'F';
    }

    public String getStudentId() {
        return studentId;
    }

    public String getName() {
        return name;
    }

    public int[] getGrades() {
        return grades;
    }

    public double getAverageGrade() {
        return averageGrade;
    }

    public char getLetterGrade() {
        return letterGrade;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Student ID: ").append(studentId);
        sb.append("\nStudent Name: ").append(name);
        sb.append("\nGrades: ").append(Arrays.toString(grades));
        sb.append("\nAverage: ").append(String.format("%.2f", averageGrade));
        sb.append("\nLetter Grade: ").append(letterGrade);
        return sb.toString();
    }
}

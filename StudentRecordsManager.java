import java.io.*;
import java.util.*;

/**
 * Main class that manages student records.
 * This class demonstrates file I/O and exception handling in Java.
 *
 * The StudentRecordsManager handles:
 * - Reading student data from CSV files
 * - Processing and validating the data
 * - Calculating statistics
 * - Writing formatted results to output files
 * - Proper exception handling throughout the process
 */
public class StudentRecordsManager {

    /**
     * Main method to run the program.
     * Handles user input and delegates processing to other methods.
     *
     * @param args Command line arguments (not used)
     */
    public static void main(String[] args) {
        StudentRecordsManager manager = new StudentRecordsManager();
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter input filename: ");
        String inputFile = scanner.nextLine();

        System.out.print("Enter output filename: ");
        String outputFile = scanner.nextLine();

        try {
            /**
             * TODO: Call the processStudentRecords method with appropriate parameters
             *
             * This should pass the inputFile and outputFile variables to the method
             */
            manager.processStudentRecords(inputFile, outputFile);
        } catch (Exception e) {
            /**
             * TODO: Handle general exceptions
             *
             * - Display a user-friendly error message
             * - Include the exception's message for debugging purposes
             * - Consider using System.err instead of System.out for error messages
             */
            System.err.println("There was an error: " + e.getMessage());
            System.err.println("No file was changed.");
        } finally {
            /**
             * The scanner is closed in the finally block to ensure resources are
             * properly released regardless of whether an exception occurred.
             * This demonstrates proper resource management.
             */
            scanner.close();
        }
    }

    /**
     * Process student records from an input file and write results to an output file.
     * This method orchestrates the entire data processing workflow.
     *
     * @param inputFile Path to the input file containing student records
     * @param outputFile Path to the output file where results will be written
     */
    public void processStudentRecords(String inputFile, String outputFile) {
        try {
            /**
             * TODO: Call readStudentRecords and writeResultsToFile methods
             *
             * 1. Call readStudentRecords to get a list of Student objects
             * 2. Call writeResultsToFile to output the processed data
             * 3. Print a success message to the console
             */
            List<Student> students = readStudentRecords(inputFile);
            writeResultsToFile(students, outputFile);
            System.out.println("Processing complete. Results written to: " + outputFile);
        } catch (FileNotFoundException e) {
            /**
             * TODO: Handle file not found exception
             *
             * Provide a clear message indicating which file couldn't be found
             * and possibly suggest solutions (check spelling, path, etc.)
             */
            System.err.println("File not found: " + inputFile);
            System.err.println("Please check the filename and path.");
        } catch (IOException e) {
            /**
             * TODO: Handle general I/O exceptions
             *
             * These could be permission issues, disk full, etc.
             * Provide helpful information about the nature of the I/O problem.
             */
            System.err.println("An I/O error occurred: " + e.getMessage());
        }
    }

    /**
     * Read student records from a file and convert them to Student objects.
     * This method demonstrates file reading operations and exception handling.
     *
     * @param filename Path to the input file
     * @return List of Student objects created from the file data
     * @throws IOException If an I/O error occurs during file reading
     */
    public List<Student> readStudentRecords(String filename) throws IOException {
        List<Student> students = new ArrayList<>();
        int lineNumber = 0;

        /**
         * TODO: Implement using try-with-resources to read the file
         *
         * 1. Create a BufferedReader wrapped around a FileReader
         * 2. Read each line of the file
         * 3. For each line, parse the student data:
         *    - Split the line by commas
         *    - Extract studentId (parts[0]) and name (parts[1])
         *    - Parse the four grade values (parts[2] through parts[5])
         * 4. Create a Student object with the parsed data
         * 5. Add the Student object to the students list
         *
         * Remember to handle these specific exceptions:
         * - NumberFormatException: When a grade cannot be parsed as an integer
         * - InvalidGradeException: When a grade is outside the valid range (0-100)
         * - ArrayIndexOutOfBoundsException: When a line has too few fields
         *
         * For each exception, provide clear error messages that include the line number
         * where the error occurred for easier debugging.
         */
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                try {
                    String[] parts = line.split(",");
                    if (parts.length < 6) throw new ArrayIndexOutOfBoundsException();

                    String studentId = parts[0].trim();
                    String name = parts[1].trim();
                    int[] grades = new int[4];

                    for (int i = 0; i < 4; i++) {
                        int grade = Integer.parseInt(parts[i + 2].trim());
                        if (grade < 0 || grade > 100) {
                            throw new InvalidGradeException("Grade out of range on line " + lineNumber);
                        }
                        grades[i] = grade;
                    }

                    students.add(new Student(studentId, name, grades));
                } catch (NumberFormatException e) {
                    System.err.println("Invalid number format on line " + lineNumber + ": " + e.getMessage());
                } catch (ArrayIndexOutOfBoundsException e) {
                    System.err.println("Missing fields on line " + lineNumber);
                } catch (InvalidGradeException e) {
                    System.err.println(e.getMessage());
                }
            }
        }

        return students;
    }

    /**
     * Write processed student results to an output file.
     * This method demonstrates file writing operations.
     *
     * @param students List of Student objects to be written to the file
     * @param filename Path to the output file
     * @throws IOException If an I/O error occurs during file writing
     */
    public void writeResultsToFile(List<Student> students, String filename) throws IOException {
        /**
         * TODO: Implement using try-with-resources to write to the file
         *
         * 1. Create a PrintWriter wrapped around a FileWriter
         * 2. Write a header section with title and separator
         * 3. Write each student's information
         * 4. Calculate and write class statistics:
         *    - Total number of students
         *    - Class average grade
         *    - Distribution of letter grades (how many A's, B's, etc.)
         *
         * For calculating statistics:
         * 1. Initialize counters for each letter grade (A, B, C, D, F)
         * 2. Initialize a sum for calculating the average
         * 3. Loop through all students, incrementing counters and adding to sum
         * 4. Calculate the class average and format it to 2 decimal places
         * 5. Write all statistics to the file
         *
         * Consider using a StringBuilder for building complex strings
         * before writing them to the file.
         */
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            writer.println("Student Records Report");
            writer.println("======================");

            int totalStudents = students.size();
            double sum = 0;
            int[] gradeDistribution = new int[5]; // A, B, C, D, F

            for (Student student : students) {
                writer.println(student);
                writer.println();
                double avg = student.getAverageGrade();
                sum += avg;

                switch (student.getLetterGrade()) {
                    case 'A': gradeDistribution[0]++; break;
                    case 'B': gradeDistribution[1]++; break;
                    case 'C': gradeDistribution[2]++; break;
                    case 'D': gradeDistribution[3]++; break;
                    case 'F': gradeDistribution[4]++; break;
                }
            }

            writer.println("Class Statistics");
            writer.println("----------------");
            writer.printf("Total Students: %d%n", totalStudents);
            writer.printf("Class Average: %.2f%n", totalStudents > 0 ? sum / totalStudents : 0.0);
            writer.printf("A's: %d%n", gradeDistribution[0]);
            writer.printf("B's: %d%n", gradeDistribution[1]);
            writer.printf("C's: %d%n", gradeDistribution[2]);
            writer.printf("D's: %d%n", gradeDistribution[3]);
            writer.printf("F's: %d%n", gradeDistribution[4]);
        }
    }
}

import java.io.*;
import java.util.*;

class InvalidScoreException extends Exception {
    public InvalidScoreException(String message) {
        super(message);
    }
}

class Student {
    private String name;
    private int score;

    public Student(String name, int score) {
        this.name = name;
        this.score = score;
    }

    public String getName() { return name; }
    public int getScore() { return score; }

    @Override
    public String toString() {
        return name + " - " + score;
    }
}

public class StudentRecordProcessor {
    // Поля для хранения данных
    private final List<Student> students = new ArrayList<>();
    private double averageScore;
    private Student highestStudent;

    /**
     * Task 1 + Task 2 + Task 5 + Task 6
     */
    public void readFile() {
        String filePath = "input/student.txt";

        try(BufferedReader b = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = b.readLine()) != null) {
                try {
                    String[] parts = line.split(",");

                    if (parts.length != 2) {
                        throw new NumberFormatException();
                    }

                    String name = parts[0];
                    int score = Integer.parseInt(parts[1]);

                    if (score < 0 || score > 100) {
                        throw new InvalidScoreException("Invalid score: " + line);
                    }

                    students.add(new Student(name, score));
                    System.out.println("Valid: " + line);

                } catch (NumberFormatException e) {
                    System.out.println("Invalid data: " + line);
                } catch (InvalidScoreException e) {
                    System.out.println(e.getMessage());
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            System.out.println("Error reading file");
        }
    }

    /**
     * Task 3 + Task 8
     */
    public void processData() {
        if (students.isEmpty()) {
            System.out.println("No valid data");
            return;
        }

        double sum = 0;
        highestStudent = students.get(0);

        for (Student s : students) {
            sum += s.getScore();

            if (s.getScore() > highestStudent.getScore()) {
                highestStudent = s;
            }
        }

        averageScore = sum / students.size();
        students.sort((a,b) -> b.getScore() - a.getScore());
    }

    /**
     * Task 4 + Task 5 + Task 8
     */
    public void writeFile() {
        String filePath = "output/report.txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {

            writer.write("Average: " + averageScore);
            writer.newLine();

            writer.write("Highest: " + highestStudent);
            writer.newLine();

            writer.newLine();
            writer.write("Sorted Students");
            writer.newLine();

            for (Student s : students) {
                writer.write(s.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error writing file");
        }
    }

    public static void main(String[] args) {
        StudentRecordProcessor processor = new StudentRecordProcessor();

        try {
            processor.readFile();
            processor.processData();
            processor.writeFile();
            System.out.println("Processing completed. Check output/report.txt");
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
        }
    }
}

// class InvalidScoreException реализуйте меня
// class Student (name, score)
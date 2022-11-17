package gridu.gd;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class App
{
    public static void main(String[] args) throws ParseException, NegativeHoursInCourseException {
        String dateString = "01-06-2020";
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        Student firstStudent = new Student("John Doe", "Java Developer", formatter.parse(dateString));

        List<Course> firstStudentCourses = new ArrayList<>();
        firstStudentCourses.add(new Course("Java", 16));
        firstStudentCourses.add(new Course("JDBC", 24));
        firstStudentCourses.add(new Course("Spring", 16));
        firstStudent.setTrainingProgram(firstStudentCourses);


        Student secondStudent = new Student("Joan Doe", "AQE", formatter.parse(dateString));
        List<Course> secondStudentCourses = new ArrayList<>();
        secondStudentCourses.add(new Course("Test design", 10));
        secondStudentCourses.add(new Course("Page Object", 16));
        secondStudentCourses.add(new Course("Selenium", 16));
        secondStudent.setTrainingProgram(secondStudentCourses);

        Scanner scanner = new Scanner(System.in);
        try
        {
            System.out.println("Enter the date of report creation (dd-MM-yyyy): ");
            String dateInput = scanner.next();
            System.out.println("Enter the time of report creation (HH:mm): ");
            String timeInput = scanner.next();
            String dateTimeInput = dateInput + "T" + timeInput;
            SimpleDateFormat inputFormatter = new SimpleDateFormat("dd-MM-yyyy'T'HH:mm");
            Date reportDate = inputFormatter.parse(dateTimeInput);

            StudentReport report = new StudentReport(reportDate, System.out);

            if (args.length > 0) {
                    if (args[0].equals("0")) {
                        report.getShortReport(firstStudent);
                        report.getShortReport(secondStudent);
                    } else {
                        report.getFullReport(firstStudent);
                        report.getFullReport(secondStudent);
                    }
                } else {
                report.getShortReport(firstStudent);
                report.getShortReport(secondStudent);
                }
        }
        catch (ParseException e)
        {
            System.out.println("Incorrect input format.");
        }


    }


}

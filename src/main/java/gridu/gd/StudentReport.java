package gridu.gd;

import java.io.PrintStream;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class StudentReport {

    private PrintStream output;
    private Date reportDate;

    public StudentReport(Date reportDate, PrintStream output) {
        this.reportDate = reportDate;
        this.output = output;
    }


    public void getShortReport(Student student) throws NegativeHoursInCourseException {
        StringBuilder sb = new StringBuilder();
        sb.append("----- Short Report -----\n");
        sb.append(String.format("Generating report date (%tc)%n", reportDate));
        long hours = student.getDifferenceBetweenReportAndEndDatesInHours(reportDate);
        String timeString = student.getRemainingDaysAndHours(reportDate, hours);
        if (hours > 0) {
            sb.append(String.format("%s (%s) - Training is not finished. %s are left until the end.%n", student.getName(),
                    student.getCurriculum(), timeString));
        } else {
            sb.append(String.format("%s (%s) - Training completed. %s have passed since the end.%n",
                    student.getName(), student.getCurriculum(), timeString));
        }
        sb.append("-----------------------\n");
        output.format(sb.toString());
    }

    public void getFullReport(Student student) throws NegativeHoursInCourseException {
        StringBuilder sb = new StringBuilder();

        sb.append("----- Full Report -----\n");
        sb.append(String.format("Generating report date (%tc)%n", reportDate));

        long hours = student.getDifferenceBetweenReportAndEndDatesInHours(reportDate);
        sb.append("Student name: ").append(student.getName()).append("\n");
        sb.append("Working time: from 10 to 18\n");
        sb.append("Program name: ").append(student.getCurriculum()).append("\n");
        sb.append("Program duration: ").append(student.getTrainingProgram().stream().mapToInt(o -> o.getDuration()).sum()).append(" hours\n");
        sb.append("Start date: ").append(student.getStartDate()).append("\n");
        sb.append("End date: ").append(student.getCourseEndDate().toString()).append("\n");
        if (hours > 0) {
            sb.append(String.format("%s are left until the end.%n", student.getRemainingDaysAndHours(reportDate, hours)));
        } else {
            sb.append(String.format("%s have passed since the end.%n", student.getRemainingDaysAndHours(reportDate, hours)));
        }
        sb.append("-----------------------\n");
        output.format(sb.toString());
    }
}

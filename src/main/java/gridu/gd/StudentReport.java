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


    public void getShortReport(Student student) {
        StringBuilder sb = new StringBuilder();
        sb.append("----- Short Report -----\n");
        sb.append(String.format("Generating report date (%tc)\n", reportDate));
        long diffInMillies = student.getCourseEndDate().getTime() - reportDate.getTime();
        long hours = TimeUnit.HOURS.convert(diffInMillies,TimeUnit.MILLISECONDS);
        String timeString = student.getRemainingDaysAndHours(reportDate, hours);
        if (hours > 0) {
            sb.append(String.format("%s (%s) - Training is not finished. %s are left until the end.\n", student.getName(),
                    student.getCurriculum(), timeString));
        } else {
            sb.append(String.format("%s (%s) - Training completed. %s have passed since the end.\n",
                    student.getName(), student.getCurriculum(), timeString));
        }
        sb.append("-----------------------\n");
        output.format(sb.toString());
    }

    public void getFullReport(Student student) {
        StringBuilder sb = new StringBuilder();

        sb.append("----- Full Report -----\n");
        sb.append(String.format("Generating report date (%tc)\n", reportDate));

        long diffInMillies = student.getCourseEndDate().getTime() - reportDate.getTime();
        long hours = TimeUnit.HOURS.convert(diffInMillies, TimeUnit.MILLISECONDS);
        sb.append("Student name: " + student.getName()+"\n");
        sb.append("Working time: from 10 to 18\n");
        sb.append("Program name: " + student.getCurriculum()+"\n");
        sb.append("Program duration: " + student.getTrainingProgram().stream().mapToInt(o -> o.getDuration()).sum()+" hours\n");
        sb.append("Start date: " + student.getStartDate()+"\n");
        sb.append("End date: " + student.getCourseEndDate().toString()+"\n");
        if (hours > 0) {
            sb.append(String.format("%s are left until the end.", student.getRemainingDaysAndHours(reportDate, hours)));
        } else {
            sb.append(String.format("%s have passed since the end.", student.getRemainingDaysAndHours(reportDate, hours)));
        }
        sb.append("-----------------------\n");
        output.format(sb.toString());
    }
}

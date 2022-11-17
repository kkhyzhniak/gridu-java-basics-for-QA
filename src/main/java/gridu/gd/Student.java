package gridu.gd;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Student {

    private String name;
    private String curriculum;
    private Date startDate;
    private static final int START_WORKING_DAY = 10;
    private static final int END_WORKING_DAY = 18;
    private static final int DAY_WORKING_HOURS = END_WORKING_DAY - START_WORKING_DAY;
    private static final int WEEK_WORKING_HOURS = DAY_WORKING_HOURS * 5;


    private List<Course> trainingProgram;

    public Student (String name, String curriculum, Date startDate) {
        this.name = name;
        this.curriculum = curriculum;
        this.startDate = startDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCurriculum() {
        return curriculum;
    }

    public void setCurriculum(String curriculum) {
        this.curriculum = curriculum;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public List<Course> getTrainingProgram() {
        return trainingProgram;
    }

    public void setTrainingProgram(List<Course> trainingProgram) {
        this.trainingProgram = trainingProgram;
    }

    Date getCourseEndDate() throws NegativeHoursInCourseException {
        Calendar c = Calendar.getInstance();
        c.setTime(this.startDate);
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
        if (dayOfWeek == 0) {
            dayOfWeek = 7;  // Fixing sunday as first day of week
        }

        int remainingCoursesDuration = 0;
        for(Course course : trainingProgram) {
            if (course.getDuration() < 0) {
                throw new NegativeHoursInCourseException();
            }
            remainingCoursesDuration += course.getDuration();
        }

        int daysOfFirstWeekLeft = 6 - dayOfWeek;
        if (remainingCoursesDuration > daysOfFirstWeekLeft * DAY_WORKING_HOURS) {
            remainingCoursesDuration -= daysOfFirstWeekLeft * DAY_WORKING_HOURS;
            c.add(Calendar.DATE, daysOfFirstWeekLeft + 2);
            c.add(Calendar.DATE, 7 * (remainingCoursesDuration / WEEK_WORKING_HOURS));
            remainingCoursesDuration = remainingCoursesDuration % WEEK_WORKING_HOURS;
        }
        c.add(Calendar.DATE, remainingCoursesDuration / DAY_WORKING_HOURS);
        remainingCoursesDuration = remainingCoursesDuration % DAY_WORKING_HOURS;
        if (remainingCoursesDuration == 0) {
            if (c.get(Calendar.DAY_OF_WEEK) - 1 == 1) {
                c.add(Calendar.DATE, -3);
            } else {
                c.add(Calendar.DATE, -1);
            }
            c.set(Calendar.HOUR_OF_DAY, END_WORKING_DAY);
            return c.getTime();
        }
        c.set(Calendar.HOUR_OF_DAY, START_WORKING_DAY + remainingCoursesDuration);
        return c.getTime();
    }

    String getRemainingDaysAndHours(Date reportDate, long hours) {
        if (Math.abs(hours)/24 == 0) {
           return String.format("%d hours", Math.abs(hours)%24);
        }
       return String.format("%d d %d hours", Math.abs(hours)/24, Math.abs(hours)%24);
    }

    public long getDifferenceBetweenReportAndEndDatesInHours(Date reportDate) throws NegativeHoursInCourseException {
        long diffInMillies = this.getCourseEndDate().getTime() - reportDate.getTime();
        return TimeUnit.HOURS.convert(diffInMillies, TimeUnit.MILLISECONDS);
    }
}

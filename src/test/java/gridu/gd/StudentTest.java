package gridu.gd;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class StudentTest {

    private Student student;

    @Before
    public void setUpStudent() throws ParseException {
        student = new Student("Candela Carme", "Go Developer",
                new SimpleDateFormat("dd-MM-yyyy").parse("12-11-2022"));
    }

    @Test
    public void shouldCountEndDateWithStartDateAtWeekend() throws ParseException, NegativeHoursInCourseException {
        List<Course> courseList = new ArrayList<>();
        courseList.add(new Course("Go Basics", 8));
        String dateTimeInput = "14-11-2022" + "T" + "18:00";
        SimpleDateFormat inputFormatter = new SimpleDateFormat("dd-MM-yyyy'T'HH:mm");
        student.setTrainingProgram(courseList);
        Date actualDate = student.getCourseEndDate();
        Assert.assertEquals(inputFormatter.parse(dateTimeInput), actualDate);
    }

    @Test
        public void shouldCountEndDateWhichWillBeTheNextWeek() throws ParseException, NegativeHoursInCourseException {
            student.setStartDate( new SimpleDateFormat("dd-MM-yyyy").parse("17-11-2022"));
            List<Course> courseList = new ArrayList<>();
            courseList.add(new Course("Go Basics", 17));
            String dateTimeInput = "21-11-2022" + "T" + "11:00";
            SimpleDateFormat inputFormatter = new SimpleDateFormat("dd-MM-yyyy'T'HH:mm");
            student.setTrainingProgram(courseList);
            Date actualDate = student.getCourseEndDate();
            Assert.assertEquals(inputFormatter.parse(dateTimeInput), actualDate);
        }

    @Test
        public void shouldCountHoursBeforeCurrentTime() throws ParseException, NegativeHoursInCourseException {
            student.setStartDate( new SimpleDateFormat("dd-MM-yyyy").parse("16-11-2022"));
            List<Course> courseList = new ArrayList<>();
            courseList.add(new Course("Go Basics", 10));
            student.setTrainingProgram(courseList);
            String currentDate = "17-11-2022" + "T" + "13:00";
            SimpleDateFormat inputFormatter = new SimpleDateFormat("dd-MM-yyyy'T'HH:mm");
            long remainingHours = student.getDifferenceBetweenReportAndEndDatesInHours(inputFormatter.parse(currentDate));
            long expectedRemainingHours = -1;
            Assert.assertEquals(remainingHours, expectedRemainingHours);
        }

    @Test
    public void shouldCountHoursAfterCurrentTime() throws ParseException, NegativeHoursInCourseException {
        student.setStartDate( new SimpleDateFormat("dd-MM-yyyy").parse("16-11-2022"));
        List<Course> courseList = new ArrayList<>();
        courseList.add(new Course("Go Basics", 12));
        student.setTrainingProgram(courseList);
        String currentDate = "17-11-2022" + "T" + "13:00";
        SimpleDateFormat inputFormatter = new SimpleDateFormat("dd-MM-yyyy'T'HH:mm");
        long actualRemainingHours = student.getDifferenceBetweenReportAndEndDatesInHours(inputFormatter.parse(currentDate));
        long expectedRemainingHours = 1;
        Assert.assertEquals(expectedRemainingHours, actualRemainingHours);
    }

    @Test(expected = NegativeHoursInCourseException.class)
    public void shouldReturnExceptionNegativeHours() throws NegativeHoursInCourseException {
        List<Course> courseList = new ArrayList<>();
        courseList.add(new Course("Go Basics", -1));
        student.setTrainingProgram(courseList);
        Date actualDate = student.getCourseEndDate();
    }

    @Test(expected = NullPointerException.class)
    public void shouldReturnExceptionWithoutProgram() throws NegativeHoursInCourseException {
        Date expectedDate = student.getCourseEndDate(); }

}

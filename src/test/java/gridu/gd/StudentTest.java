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
    public void shouldReturnEndDate() throws ParseException {
        List<Course> courseList = new ArrayList<>();
        courseList.add(new Course("Java", 8));
        String dateTimeInput = "14-11-2022" + "T" + "18:00";
        SimpleDateFormat inputFormatter = new SimpleDateFormat("dd-MM-yyyy'T'HH:mm");
        student.setTrainingProgram(courseList);
        Date actualDate = student.getCourseEndDate();
        Assert.assertEquals(inputFormatter.parse(dateTimeInput), actualDate);
    }

    @Test(expected = NullPointerException.class)
    public void shouldReturnExceptionWithoutProgram() {
        Date expectedDate = student.getCourseEndDate(); }

}

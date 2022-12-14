package gridu.gd;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.mockito.Mockito.*;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.PrintStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RunWith(MockitoJUnitRunner.class)
public class StudentReportTest {

    private Student mockStudent;
    private PrintStream mockOutput;
    private Date reportDate;
    private StudentReport report;

    @Before
    public void setUp() throws ParseException {
        this.mockStudent = mock(Student.class);
        this.mockOutput = mock(PrintStream.class);
        this.reportDate = new SimpleDateFormat("dd-MM-yyyy").parse("12-11-2022");
        //this.mockOutput = new PrintStream(System.out);
        this.report = new StudentReport(reportDate, mockOutput);
    }

    @Test
    public void shouldReturnShortReportUncompletedTraining() throws NegativeHoursInCourseException {
        when(mockStudent.getDifferenceBetweenReportAndEndDatesInHours(reportDate)).thenReturn((long) 10);
        report.getShortReport(mockStudent);
        verify(mockOutput, atLeast(1)).
                format("----- Short Report -----\n" +
                        "Generating report date (Sat Nov 12 00:00:00 CET 2022)\n" +
                        "null (null) - Training is not finished. null are left until the end.\n" +
                        "-----------------------\n");
    }

    @Test
    public void shouldReturnShortReportCompletedTraining() throws NegativeHoursInCourseException {
        when(mockStudent.getDifferenceBetweenReportAndEndDatesInHours(reportDate)).thenReturn((long) -10);
        report.getShortReport(mockStudent);
        verify(mockOutput, atLeast(1)).
                format("----- Short Report -----\n" +
                        "Generating report date (Sat Nov 12 00:00:00 CET 2022)\n" +
                        "null (null) - Training completed. null have passed since the end.\n" +
                        "-----------------------\n");
    }

    @Test
    public void shouldReturnFullReportCompletedTraining() throws ParseException, NegativeHoursInCourseException {
        Date courseStartDate = new SimpleDateFormat("dd-MM-yyyy").parse("10-11-2022");
        Date courseEndDate = new SimpleDateFormat("dd-MM-yyyy'T'HH:mm").parse("11-11-2022T18:00");
        when(mockStudent.getCourseEndDate()).thenReturn(courseEndDate);
        when(mockStudent.getStartDate()).thenReturn(courseStartDate);
        when(mockStudent.getDifferenceBetweenReportAndEndDatesInHours(reportDate)).thenReturn((long) -10);
        report.getFullReport(mockStudent);
        verify(mockOutput, atLeast(1)).
                format("----- Full Report -----\n" +
                        "Generating report date (Sat Nov 12 00:00:00 CET 2022)\n" +
                        "Student name: null\n" +
                        "Working time: from 10 to 18\n" +
                        "Program name: null\n" +
                        "Program duration: 0 hours\n" +
                        "Start date: Thu Nov 10 00:00:00 CET 2022\n" +
                        "End date: Fri Nov 11 18:00:00 CET 2022\n" +
                        "null have passed since the end.\n" +
                        "-----------------------\n");
    }

    @Test
    public void shouldReturnFullReportUncompletedTraining() throws ParseException, NegativeHoursInCourseException {
        Date courseStartDate = new SimpleDateFormat("dd-MM-yyyy").parse("10-11-2022");
        Date courseEndDate = new SimpleDateFormat("dd-MM-yyyy'T'HH:mm").parse("11-11-2022T18:00");
        when(mockStudent.getCourseEndDate()).thenReturn(courseEndDate);
        when(mockStudent.getStartDate()).thenReturn(courseStartDate);
        when(mockStudent.getDifferenceBetweenReportAndEndDatesInHours(reportDate)).thenReturn((long) 10);
        report.getFullReport(mockStudent);
        verify(mockOutput, atLeast(1)).
                format("----- Full Report -----\n" +
                        "Generating report date (Sat Nov 12 00:00:00 CET 2022)\n" +
                        "Student name: null\n" +
                        "Working time: from 10 to 18\n" +
                        "Program name: null\n" +
                        "Program duration: 0 hours\n" +
                        "Start date: Thu Nov 10 00:00:00 CET 2022\n" +
                        "End date: Fri Nov 11 18:00:00 CET 2022\n" +
                        "null are left until the end.\n" +
                        "-----------------------\n");
    }
}

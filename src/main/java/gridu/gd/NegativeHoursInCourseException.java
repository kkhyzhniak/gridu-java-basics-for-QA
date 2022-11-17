package gridu.gd;

public class NegativeHoursInCourseException extends Exception {

    public NegativeHoursInCourseException () {
        super("Course hours cannot be negative!");
    }
}

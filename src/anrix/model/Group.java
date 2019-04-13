package anrix.model;

import java.util.ArrayList;

public class Group {
    public Integer course;
    public Integer number;
    public ArrayList<Student> students = new ArrayList<>();

    @Override
    public String toString() {
        return number.toString();
    }

}

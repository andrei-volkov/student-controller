package anrix.model.bean;

import java.io.Serializable;
import java.util.ArrayList;

public class Group implements Serializable {
    public Integer course;
    public String number;
    public ArrayList<Student> students = new ArrayList<>();

    public Group() {}

    public Integer getCourse() {
        return course;
    }

    public void setCourse(Integer course) {
        this.course = course;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public ArrayList<Student> getStudents() {
        return students;
    }

    public void setStudents(ArrayList<Student> students) {
        this.students = students;
    }

    @Override
    public String toString() {
        return number.toString();
    }
}

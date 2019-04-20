package anrix.model.bean;

import java.io.Serializable;
import java.util.ArrayList;

public class Group implements Serializable {
    public Integer course;
    public String id;
    public ArrayList<Student> students = new ArrayList<>();

    public Group() {}

    public Integer getCourse() {
        return course;
    }

    public void setCourse(Integer course) {
        this.course = course;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ArrayList<Student> getStudents() {
        return students;
    }

    public void setStudents(ArrayList<Student> students) {
        this.students = students;
    }

    @Override
    public String toString() {
        return id;
    }
}

package anrix.model.bean;

import java.io.Serializable;
import java.util.Objects;

public class Student implements Serializable {
    public String name;
    public String surname;
    public String group;
    public String course;
    public String faculty;
    public Double averageMark;
    public GENDER gender;

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Double getAverageMark() {
        return averageMark;
    }

    public void setAverageMark(Double averageMark) {
        this.averageMark = averageMark;
    }

    public GENDER getGender() {
        return gender;
    }

    public void setGender(GENDER gender) {
        this.gender = gender;
    }

    public enum GENDER {
        MALE,
        FEMALE
    }

    public Student() {}

    public Student(String name, String surname, String group, String course, String faculty, Double averageMark, GENDER gender) {
        this.name = name;
        this.surname = surname;
        this.group = group;
        this.course = course;
        this.faculty = faculty;
        this.averageMark = averageMark;
        this.gender = gender;
    }

    public Student(String name, String surname, double averageMark) {
        this.name = name;
        this.surname = surname;
        this.averageMark = averageMark;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return Double.compare(student.averageMark, averageMark) == 0 &&
                Objects.equals(name, student.name) &&
                Objects.equals(surname, student.surname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, surname, averageMark);
    }
}

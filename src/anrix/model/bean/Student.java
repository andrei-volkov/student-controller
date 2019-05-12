package anrix.model.bean;

import java.io.Serializable;
import java.util.Objects;

public class Student implements Serializable {
    public String name;
    public String surname;
    public String group;
    public String faculty;
    public Double averageMark;
    public GENDER gender;
    public String id;

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

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public enum GENDER {
        MALE,
        FEMALE
    }

    public Student() {}

    public Student(String name, String surname, String group, String faculty, Double averageMark, GENDER gender, String id) {
        this.name = name;
        this.surname = surname;
        this.group = group;
        this.faculty = faculty;
        this.averageMark = averageMark;
        this.gender = gender;
        this.id = id;
    }

    @Override
    public String toString() {
        return String.format("VALUES ('%s', '%s', '%s', '%s', %f, '%s', '%s')",
                getName(),
                getSurname(),
                getGroup(),
                getFaculty(),
                getAverageMark(),
                getGender().toString(),
                getId());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return Objects.equals(getName(), student.getName()) &&
                Objects.equals(getSurname(), student.getSurname()) &&
                Objects.equals(getGroup(), student.getGroup()) &&
                Objects.equals(getFaculty(), student.getFaculty()) &&
                Objects.equals(getAverageMark(), student.getAverageMark()) &&
                getGender() == student.getGender() &&
                Objects.equals(getId(), student.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getSurname(), getGroup(), getFaculty(), getAverageMark(), getGender(), getId());
    }
}

package anrix.model.bean;

import java.io.Serializable;
import java.util.Formatter;
import java.util.Objects;

public class Student implements Serializable {
    public String name;
    public String surname;
    public String group;
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

    public String getId() {return getName() + getSurname() + getGroup() + getFaculty()
            + getAverageMark() + getAverageMark() + getGender().toString();}

    public enum GENDER {
        MALE,
        FEMALE

    }

    public Student() {}

    @Override
    public String toString() {
        return String.format("VALUES ('%s', '%s', '%s', '%s', %f, '%s', '%s'",
                    getName(),
                    getSurname(),
                    getGroup(),
                    getFaculty(),
                    getAverageMark(),
                    getGender().toString(),
                    getId());
     }

    public Student(String name, String surname, String group, String faculty, Double averageMark, GENDER gender) {
        this.name = name;
        this.surname = surname;
        this.group = group;
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
        return Objects.equals(getName(), student.getName()) &&
                Objects.equals(getSurname(), student.getSurname()) &&
                Objects.equals(getGroup(), student.getGroup()) &&
                Objects.equals(getFaculty(), student.getFaculty()) &&
                Objects.equals(getAverageMark(), student.getAverageMark()) &&
                getGender() == student.getGender();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getSurname(), getGroup(), getFaculty(), getAverageMark(), getGender());
    }
}

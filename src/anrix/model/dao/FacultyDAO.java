package anrix.model.dao;

import anrix.model.bean.Faculty;
import anrix.model.bean.Group;
import anrix.model.bean.Student;
import javafx.collections.ObservableList;

public interface FacultyDAO {
    ObservableList<Faculty> getFaculties();
    ObservableList<Student> toStudentList();

    void remove(Student student);
    void removeGroup(String id);
    void removeFaculty(String name);

    void add(Student student);
    void add(Group group, String facultyName);
    void add(Faculty faculty);


    boolean contains(Student student);
    boolean contains(Group group);
    boolean contains(Faculty faculty);

    void update(Student student, String name, String surname, String group, String faculty, Double mark, Student.GENDER gender);
}

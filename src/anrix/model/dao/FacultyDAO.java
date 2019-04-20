package anrix.model.dao;

import anrix.model.bean.Faculty;
import anrix.model.bean.Group;
import anrix.model.bean.Student;
import javafx.collections.ObservableList;

public interface FacultyDAO {
    ObservableList<Faculty> getFaculties();
    ObservableList<Student> toStudentList();

    void setFaculties(ObservableList<Faculty> faculties);

    void remove(Student student);
    void remove(Group group);
    void remove(Faculty faculty);

    void add(Student student);
    void add(Group group, String facultyName);
    void add(Faculty faculty);
}

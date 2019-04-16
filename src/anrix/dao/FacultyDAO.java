package anrix.dao;

import anrix.model.Faculty;
import anrix.model.Group;
import anrix.model.Student;
import javafx.collections.ObservableList;

import java.util.List;

public interface FacultyDAO {
    ObservableList<Faculty> getFaculties();
    ObservableList<Student> toStudentList();

    void setFaculties(ObservableList<Faculty> faculties);

    void remove(Student student);
    void remove(Group group);
    void remove(Faculty faculty);

}

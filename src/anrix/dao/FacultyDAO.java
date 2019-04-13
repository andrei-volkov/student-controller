package anrix.dao;

import anrix.model.Faculty;
import anrix.model.Group;
import anrix.model.Student;

import java.util.List;

public interface FacultyDAO {
    List<Faculty> getFaculties();
    void setFaculties(List<Faculty> faculties);

    boolean remove(Student student);
    boolean remove(Group group);
    boolean remove(Faculty faculty);

}

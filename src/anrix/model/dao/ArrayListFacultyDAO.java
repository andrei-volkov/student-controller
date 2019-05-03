package anrix.model.dao;

import anrix.model.bean.Faculty;
import anrix.model.bean.Group;
import anrix.model.bean.Student;
import anrix.model.service.DatabaseService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.Random;
import java.util.stream.Collectors;

public class ArrayListFacultyDAO implements FacultyDAO {

    private static volatile FacultyDAO instance;
    private ObservableList<Faculty> faculties = FXCollections.observableArrayList();
    private ObservableList<Student> students = FXCollections.observableArrayList();

    private DatabaseService databaseService = DatabaseService.getInstance();

    private ArrayListFacultyDAO() { }

    @Override
    public synchronized ObservableList<Faculty> getFaculties() {
        return faculties;
    }

    @Override
    public synchronized void remove(Student student) {
        boolean checker = false;
        for (Faculty f : faculties) {
            for (Group g : f.groups) {
                if (g.students.contains(student)) {
                    g.students.remove(student);
                    checker = true;
                    break;
                }
                if (checker)
                    break;
            }
        }
        students.remove(student);
    }

    @Override
    public synchronized void removeGroup(String id) {
        boolean checker = false;

        for (Faculty faculty : faculties) {
            for (Group group : faculty.getGroups()) {
                if (id.equals(group.id)) {
                    faculty.getGroups().remove(group);
                    checker = true;
                    break;
                }
            }
            if (checker)
                break;
        }

        students = students.stream()
                           .filter(s -> !s.getGroup().equals(id))
                           .collect(Collectors.toCollection(FXCollections::observableArrayList));
    }

    @Override
    public synchronized void removeFaculty(String name) {
        Faculty temp = null;
        for (Faculty faculty : faculties) {
            if (name.equals(faculty.getName())) {
                temp = faculty;
            }
        }
        faculties.remove(temp);

        students = students.stream()
                .filter(s -> !s.getFaculty().equals(name))
                .collect(Collectors.toCollection(FXCollections::observableArrayList));

    }

    @Override
    public void add(Student student) {
        String groupNumber = student.group;

        for (Faculty  f : faculties) {
            for (Group group : f.groups) {
                if (group.id.equals(groupNumber)) {
                    group.getStudents().add(student);
                    if (DatabaseService.isAvailable) {
                        databaseService.add(student);
                    }
                }
            }
        }

        students.add(student);
    }

    @Override
    public void add(Group group, String facultyName) {
        for (Faculty  f : faculties) {
            if (f.getName().equals(facultyName)) {
                f.getGroups().add(group);
                return;
            }
        }
        throw new IllegalArgumentException("Illegal name of faculty");
    }

    @Override
    public void add(Faculty faculty) {
        faculties.add(faculty);
    }

    @Override
    public boolean contains(Student student) {
        return false;
    }

    @Override
    public boolean contains(Group group) {
        return false;
    }

    @Override
    public boolean contains(Faculty faculty) {
        return false;
    }

    @Override
    public void update(Student student, String name, String surname, String group, String faculty, Double mark, Student.GENDER gender) {
        remove(student);

        student.setName(name);
        student.setSurname(surname);
        student.setFaculty(faculty);
        student.setGender(gender);
        student.setGroup(group);
        student.setAverageMark(mark);

        add(student);
    }

    public static synchronized FacultyDAO getInstance() {
        if (instance == null) {
            instance = new ArrayListFacultyDAO();
        }
        return instance;
    }

    public ObservableList<Student> toStudentList() {
        return students;
    }
}
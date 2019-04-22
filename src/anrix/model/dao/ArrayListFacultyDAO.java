package anrix.model.dao;

import anrix.model.bean.Faculty;
import anrix.model.bean.Group;
import anrix.model.bean.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.Random;

public class ArrayListFacultyDAO implements FacultyDAO {

    private static volatile FacultyDAO instance;
    private ObservableList<Faculty> faculties = FXCollections.observableArrayList();
    private ObservableList<Student> students = FXCollections.observableArrayList();

    private ArrayListFacultyDAO() {
        //TODO implement db after debug

        for (int e = 0; e < 2; e++) {
            Faculty faculty = FacultyService.getSample(e);
            faculties.add(faculty);
        }

        getFaculties()
                .forEach(e -> e.groups
                        .forEach(a -> students.addAll(a.students)));

    }

    @Override
    public synchronized ObservableList<Faculty> getFaculties() {
        return faculties;
    }

    @Override
    public synchronized void setFaculties(ObservableList<Faculty> faculties) {
        this.faculties = faculties;
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
    public synchronized void remove(Group group) {

        throw new UnsupportedOperationException();
    }

    @Override
    public synchronized void remove(Faculty faculty) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void add(Student student) {
        String groupNumber = student.group;

        for (Faculty  f : faculties) {
            for (Group group : f.groups) {
                if (group.id.equals(groupNumber)) {
                    group.getStudents().add(student);
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

    private static class FacultyService {
        public static Faculty getSample(int e) {
            Faculty faculty = new Faculty();
            faculty.name = "Fkis" + e;

            faculty.groups = new ArrayList<>();

            for (char a : "abcdef".toCharArray()) {
                Group group = new Group();
                group.course =  a - '0';
                group.id =  Long.toString(Math.round(Math.random() * 1000000));
                group.students = new ArrayList<>();

                for (int b = 0; b < 10; b++) {
                    group.students.add(
                            new Student(getSaltString(),
                                        getSaltString(),
                                        group.getId(),
                                        faculty.getName(),
                                        (Math.random() * 10),
                                        Student.GENDER.MALE));
                }
                faculty.groups.add(group);
            }
            return faculty;
        }

        @Deprecated
        private static String getSaltString() {
            String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
            StringBuilder salt = new StringBuilder();
            Random rnd = new Random();
            while (salt.length() < 18) {
                int index = (int) (rnd.nextFloat() * SALTCHARS.length());
                salt.append(SALTCHARS.charAt(index));
            }
            return salt.toString();

        }
    }

}
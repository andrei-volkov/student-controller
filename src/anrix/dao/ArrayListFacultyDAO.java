package anrix.dao;

import anrix.model.Faculty;
import anrix.model.Group;
import anrix.model.Student;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ArrayListFacultyDAO implements FacultyDAO {

    private static volatile FacultyDAO instance;
    private List<Faculty> faculties = new ArrayList<>();

    private ArrayListFacultyDAO() {
        //TODO implement db after debug

        for (int e = 0; e < 2; e++) {
            Faculty faculty = new Faculty();
            faculty.nameOfFaculty = "Fkis" + e;

            faculty.groups = new ArrayList<>();

            for (char a : "abcdef".toCharArray()) {
                Group group = new Group();
                group.course = a - '0';
                group.number = (int) (Math.random() * 100);
                group.students = new ArrayList<>();

                for (int b = 0; b < 10; b++) {
                    group.students.add(
                            new Student(FacultyService.getSaltString(),
                                    FacultyService.getSaltString(),
                                    2d));
                }
                faculty.groups.add(group);
            }
            System.out.println(faculty);
            faculties.add(faculty);
        }
    }


    @Override
    public synchronized List<Faculty> getFaculties() {
        return faculties;
    }

    @Override
    public synchronized void setFaculties(List<Faculty> faculties) {
        this.faculties = faculties;
    }

    @Override
    public synchronized boolean remove(Student student) {
        throw new UnsupportedOperationException();
    }

    @Override
    public synchronized boolean remove(Group group) {
        throw new UnsupportedOperationException();
    }

    @Override
    public synchronized boolean remove(Faculty faculty) {
        throw new UnsupportedOperationException();
    }

    public static synchronized FacultyDAO getInstance() {
        if (instance == null) {
            instance = new ArrayListFacultyDAO();
        }
        return instance;
    }

    private static class FacultyService {
        public static Faculty getSample() {
            Faculty faculty = new Faculty();
            faculty.nameOfFaculty = "Fkis";

            faculty.groups = new ArrayList<>();

            for (char a : "abcdef".toCharArray()) {
                Group group = new Group();
                group.course =  a - '0';
                group.number = (int) (Math.random() * 100);
                group.students = new ArrayList<>();

                for (int b = 0; b < 10; b++) {
                    group.students.add(new Student(getSaltString(), getSaltString(), 2d));
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
            while (salt.length() < 18) { // length of the random string.
                int index = (int) (rnd.nextFloat() * SALTCHARS.length());
                salt.append(SALTCHARS.charAt(index));
            }
            String saltStr = salt.toString();
            return saltStr;

        }
    }

}
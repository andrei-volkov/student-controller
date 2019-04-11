package anrix.dao;

import anrix.model.Faculty;
import anrix.model.Group;
import anrix.model.Student;

import java.util.ArrayList;
import java.util.Random;

public class FacultyService {
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

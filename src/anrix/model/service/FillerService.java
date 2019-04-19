package anrix.model.service;

import anrix.model.bean.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Comparator;
import java.util.stream.Collectors;

public class FillerService {
    private FillerService() {}

    private static volatile FillerService instance;

    public static synchronized FillerService getInstance() {
        if (instance == null) {
            instance = new FillerService();
        }
        return instance;
    }

    public ObservableList<Student> find(ObservableList<Student> students, String key) {
        ObservableList<Student> results = FXCollections.observableArrayList();

        for (Student student : students) {
            if (student.name.toLowerCase().contains(key.toLowerCase()) ||
                student.surname.toLowerCase().contains(key.toLowerCase()) ||
                student.averageMark.toString().contains(key))
                results.add(student);
        }

        return results;
    }

    public ObservableList<Student> sort(ObservableList<Student> students, String key) {
        switch (key) {
            case "A-Z":
                return students
                        .stream()
                        .sorted(Comparator.comparing(Student::getName))
                        .collect(Collectors.toCollection(FXCollections::observableArrayList));
            case "Z-A":
                return students
                        .stream()
                        .sorted(Comparator.comparing(Student::getName).reversed())
                        .collect(Collectors.toCollection(FXCollections::observableArrayList));
            case "0-9":
                return students
                        .stream()
                        .sorted(Comparator.comparing(Student::getGroup))
                        .collect(Collectors.toCollection(FXCollections::observableArrayList));
            case "9-0":
                return students
                        .stream()
                        .sorted(Comparator.comparing(Student::getGroup).reversed())
                        .collect(Collectors.toCollection(FXCollections::observableArrayList));
            default:
                throw new IllegalArgumentException("There are no such cases");
        }
    }
}

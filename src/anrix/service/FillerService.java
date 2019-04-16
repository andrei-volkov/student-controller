package anrix.service;

import anrix.model.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

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
}

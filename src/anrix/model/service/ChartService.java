package anrix.model.service;

import anrix.model.bean.Student;
import javafx.collections.ObservableList;

import java.util.HashMap;
import java.util.Map;

public class ChartService {
    private static volatile ChartService instance;

    private ChartService() {}

    public static synchronized ChartService getInstance() {
        if (instance == null) {
            instance = new ChartService();
        }
        return instance;
    }

    public Map<String, Double> createChartPoints(ObservableList<Student> students) {
        HashMap<String, Double> result = new HashMap<>();

        for (Student student : students) {
            if (result.containsKey(student.group)) {
                result.put(student.group, (result.get(student.group) + student.averageMark) / 2);
            } else {
                result.put(student.group, student.averageMark);
            }
        }

        return result;
    }
}
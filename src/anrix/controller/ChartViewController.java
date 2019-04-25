package anrix.controller;

import anrix.model.bean.Student;
import anrix.model.service.ChartService;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

import java.util.HashMap;
import java.util.Map;

public class ChartViewController {
    @FXML
    private LineChart<String, Double> lineChart;

    @FXML
    private final CategoryAxis xAxis = new CategoryAxis();

    @FXML
    private final NumberAxis yAxis = new NumberAxis();

    private XYChart.Series series;

    private ChartService chartService = ChartService.getInstance();

    @FXML
    public void initialize() {
        series = new XYChart.Series();
        series.setName("Average mark");


        lineChart.getXAxis().setAnimated(false);
    }


    public void setContent(ObservableList<Student> students) {
        Map<String, Double> values = chartService.createChartPoints(students);

        for (Map.Entry<String, Double> entry : values.entrySet()) {
            series.getData().add(new XYChart.Data<>(entry.getKey(),entry.getValue()));
        }

        lineChart.getData().add(series);
    }
}

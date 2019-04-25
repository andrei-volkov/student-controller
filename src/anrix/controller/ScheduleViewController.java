package anrix.controller;

import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.scene.control.ProgressBar;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class ScheduleViewController {
    @FXML
    private ProgressBar progressBar;

    @FXML
    private WebView browser;

    private WebEngine webEngine;
    private Worker<Void> worker;

    @FXML
    private void initialize() {
        webEngine = browser.getEngine();
        worker = webEngine.getLoadWorker();

        progressBar.progressProperty().bind(worker.progressProperty());
    }

    public void setURL(String url) {

        webEngine.load(url);
        webEngine.setJavaScriptEnabled(true);
    }
}

package anrix.model.service;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.control.TextField;
import javafx.util.Duration;

public class AnimationService {
    private static volatile AnimationService instance;

    private AnimationService() {}

    public static synchronized AnimationService getInstance() {
        if (instance == null) {
            instance = new AnimationService();
        }
        return instance;
    }

    public Timeline getIncorrectInputAnimation(TextField field) {
        Timeline timeline = new Timeline();

        timeline.getKeyFrames().addAll(
                new KeyFrame(Duration.ZERO,
                        new KeyValue(field.translateXProperty(),0),
                        new KeyValue(field.translateYProperty(), 0)),

                new KeyFrame(new Duration(50),
                        new KeyValue(field.translateXProperty(),-15),
                        new KeyValue(field.translateYProperty(), 0)),

                new KeyFrame(new Duration(100),
                        new KeyValue(field.translateXProperty(), 15),
                        new KeyValue(field.translateYProperty(), 0)),

                new KeyFrame(new Duration(150),
                        new KeyValue(field.translateXProperty(), -10),
                        new KeyValue(field.translateYProperty(), 0)),

                new KeyFrame(new Duration(200),
                        new KeyValue(field.translateXProperty(), 10),
                        new KeyValue(field.translateYProperty(), 0)),

                new KeyFrame(new Duration(250),
                        new KeyValue(field.translateXProperty(), -5),
                        new KeyValue(field.translateYProperty(), 0)),

                new KeyFrame(new Duration(300),
                        new KeyValue(field.translateXProperty(), 5),
                        new KeyValue(field.translateYProperty(), 0)),

                new KeyFrame(new Duration(310),
                        new KeyValue(field.translateXProperty(), 0),
                        new KeyValue(field.translateYProperty(), 0))

        );
        return timeline;
    }
}

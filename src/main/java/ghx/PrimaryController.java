package ghx;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import nu.pattern.OpenCV;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.videoio.VideoCapture;

public class PrimaryController {

    private static VideoCapture videoCapture;

    public PrimaryController() {
        if (videoCapture == null) {
            OpenCV.loadLocally();
            videoCapture = new VideoCapture();
            videoCapture.open(0);
        }
//        Timeline fiveSecondsWonder = new Timeline(
//                new KeyFrame(Duration.millis(1000.0 / 5),
//                        new EventHandler<ActionEvent>() {
//
//                            @Override
//                            public void handle(ActionEvent event) {
//                                showImg();
//                            }
//                        }));
//        fiveSecondsWonder.setCycleCount(Timeline.INDEFINITE);
//        fiveSecondsWonder.play();

        ScheduledService<Void> svc = new ScheduledService<Void>() {
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    protected Void call() {
                        showImg();
                        return null;
                    }
                };
            }
        };
        svc.setPeriod(Duration.millis(1000.0/30));
        svc.start();
    }

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }

    @FXML
    ImageView image;


    void showImg() {
        Mat m = new Mat();
        videoCapture.read(m);
        MatOfByte byteMat = new MatOfByte();
        Imgcodecs.imencode(".bmp", m, byteMat);
        image.setImage(new Image(new ByteArrayInputStream(byteMat.toArray())));
    }
}

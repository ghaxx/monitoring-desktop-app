package ghx;

import javafx.application.Platform;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class PrimaryController implements MotionDetector.Listener {

    @FXML
    ImageView image;

    private OpenCvCamera openCvCamera;

    public PrimaryController() {
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
    }

    public void startShowingLiveImage() {
        ScheduledService<Void> svc = new ScheduledService<Void>() {
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    protected Void call() {
                        try {
                            image.setImage(new Image(openCvCamera.getBitmapBytes()));
                        } catch (Throwable t) {
                            t.printStackTrace();
                        }
                        return null;
                    }
                };
            }
        };
        svc.setPeriod(Duration.millis(1000.0 / 25));
        svc.start();
    }

    public void setOpenCvCamera(OpenCvCamera openCvCamera) {
        this.openCvCamera = openCvCamera;
        image.setFitWidth(this.openCvCamera.getImageWidth());
        image.setFitHeight(this.openCvCamera.getImageHeight());
    }

    public OpenCvCamera getOpenCvCamera() {
        return openCvCamera;
    }

    @Override
    public void onMotionDetection() {

    }
}

package ghx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import nu.pattern.OpenCV;
import org.opencv.videoio.VideoCapture;

import java.io.IOException;
import java.util.Optional;

/**
 * JavaFX App
 */
public class App extends Application {

    static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        OpenCV.loadLocally();
        OpenCvCameraLoader cameraLoader = new OpenCvCameraLoader();
        Optional<OpenCvCamera> nextCamera = cameraLoader.findFirstCamera();
        if (nextCamera.isPresent()) {
            MotionDetector detector = new MotionDetector(nextCamera.get());
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("primary.fxml"));
            Parent parent = fxmlLoader.load();
            PrimaryController controller = fxmlLoader.<PrimaryController>getController();
            detector.addListener(controller);
            controller.setOpenCvCamera(nextCamera.get());
            controller.startShowingLiveImage();
            scene = new Scene(parent);
            stage.setScene(scene);
            stage.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Couldn't find a camera", ButtonType.CLOSE);
            alert.showAndWait();
        }
    }

    public static void main(String[] args) {
        launch();
    }

}
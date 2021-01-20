package ghx

import java.io.IOException
import java.util.Optional

import javafx.application.Application
import javafx.application.Application.launch
import javafx.fxml.FXMLLoader
import javafx.scene.control.{Alert, ButtonType}
import javafx.scene.{Parent, Scene}
import javafx.stage.Stage
import nu.pattern.OpenCV

class Boot extends Application {
  private[ghx] var scene: Scene = null

  @throws[IOException]
  override def start(stage: Stage): Unit = {
    OpenCV.loadLocally()
    val cameraLoader: OpenCvCameraLoader = new OpenCvCameraLoader
    val nextCamera: Option[OpenCvCamera] = cameraLoader.findFirstCamera
    if (nextCamera.nonEmpty) {
      val detector: MotionDetector = new MotionDetector(nextCamera.get)
      val fxmlLoader: FXMLLoader = new FXMLLoader(classOf[Boot].getResource("primary.fxml"))
      val parent: Parent = fxmlLoader.load()
      val controller: PrimaryController = fxmlLoader.getController[PrimaryController]
      detector.addListener(controller)
      controller.setOpenCvCamera(new FilteredCamera(nextCamera.get))
      controller.startShowingLiveImage()
      scene = new Scene(parent)
      stage.setScene(scene)
      stage.show()
    }
    else {
      val alert: Alert = new Alert(Alert.AlertType.ERROR, "Couldn't find a camera", ButtonType.CLOSE)
      alert.showAndWait
    }
  }

  def __launch(args: Array[String]) = {
    Application.launch(args: _*)
  }
}

object Boot {
  def main(args: Array[String]): Unit = {
    new Boot().__launch(args: Array[String])
  }
}


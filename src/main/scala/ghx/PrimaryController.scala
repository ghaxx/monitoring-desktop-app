package ghx

import javafx.beans.value.{ChangeListener, ObservableValue}
import javafx.concurrent.{ScheduledService, Task}
import javafx.fxml.FXML
import javafx.scene.control.Slider
import javafx.scene.image.{Image, ImageView}
import javafx.util.Duration

class PrimaryController extends MotionDetector.Listener {
  @FXML
  var image: ImageView = null

  @FXML
  var hBlurSlider: Slider = null
  @FXML
  var vBlurSlider: Slider = null

  private var openCvCamera: FilteredCamera = null

  def startShowingLiveImage(): Unit = {
    val svc: ScheduledService[Void] = new ScheduledService[Void]() {
      override protected def createTask: Task[Void] = {
        return new Task[Void]() {
          override protected def call: Void = {
            try image.setImage(new Image(openCvCamera.getBitmapBytes))
            catch {
              case t: Throwable =>
                t.printStackTrace()
            }
            return null
          }
        }
      }
    }
    svc.setPeriod(Duration.millis(1000.0 / 25))
    svc.start()
  }

  def initialize() = {
    vBlurSlider.valueProperty().addListener(new ChangeListener[Number]() {
      def changed(observableValue: ObservableValue[_ <: Number], t: Number, t1: Number): Unit = {
        openCvCamera.vBlurSize = t1.doubleValue()
      }
    })
    hBlurSlider.valueProperty().addListener(new ChangeListener[Number]() {
      def changed(observableValue: ObservableValue[_ <: Number], t: Number, t1: Number): Unit = {
        openCvCamera.hBlurSize = t1.doubleValue()
      }
    })
  }

  def setOpenCvCamera(openCvCamera: FilteredCamera): Unit = {
    this.openCvCamera = openCvCamera
//    image.setFitWidth(500)
//    image.setFitHeight(500)
  }

  override def onMotionDetection(): Unit = {
  }
}

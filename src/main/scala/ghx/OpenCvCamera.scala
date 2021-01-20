package ghx

import java.io.ByteArrayInputStream

import org.opencv.core.{Mat, MatOfByte, Scalar}
import org.opencv.imgcodecs.Imgcodecs
import org.opencv.videoio.{VideoCapture, Videoio}
import org.opencv.videoio.Videoio.CAP_PROP_GUID

class OpenCvCamera extends ImageSource {
  private var videoCapture: VideoCapture = null
  private var reloader: OpenCvCameraLoader.VideoCaptureReloader = null
  private[ghx] val buffer = new Mat

  def this(videoCapture: VideoCapture, reloader: OpenCvCameraLoader.VideoCaptureReloader) {
    this()
    this.videoCapture = videoCapture
    this.reloader = reloader
    videoCapture.set(Videoio.CAP_PROP_FRAME_WIDTH, 1280)
    videoCapture.set(Videoio.CAP_PROP_FRAME_HEIGHT, 720)
    System.out.println(videoCapture.get(CAP_PROP_GUID))
  }


  def getMat: Mat = {
    var frame = new Mat
    if (!videoCapture.read(frame)) {
      videoCapture.release()
      val red = new Mat(buffer.size, buffer.`type`, new Scalar(0, 0, 1.1))
      reloader.findFirstCamera.map((newVideoCapture: VideoCapture) => {
        def foo(newVideoCapture: VideoCapture) = {
          videoCapture = newVideoCapture
          null
        }

        foo(newVideoCapture)
      })
      frame = red.mul(buffer)
    }
    frame.copyTo(buffer)
    frame
  }

  override def getImageWidth: Double = videoCapture.get(Videoio.CAP_PROP_FRAME_WIDTH)

  override def getImageHeight: Double = videoCapture.get(Videoio.CAP_PROP_FRAME_HEIGHT)


}

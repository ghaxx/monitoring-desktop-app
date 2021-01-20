package ghx

import java.io.ByteArrayInputStream

import org.opencv.core.{Mat, Size}
import org.opencv.imgproc.Imgproc.GaussianBlur

class FilteredCamera(camera: OpenCvCamera) extends ImageSource {
  var hBlurSize = 0.0

  var vBlurSize = 0.0

  def getMat: Mat = {
    if (vBlurSize > 0) {
      val m = camera.getMat
      val b = new Mat()
      GaussianBlur(m, b, new Size(0.0, 0.0), vBlurSize, hBlurSize)
      b
    } else {
      camera.getMat
    }
  }

  def getImageWidth: Double = {
    camera.getImageWidth
  }
  def getImageHeight: Double = {
    camera.getImageHeight
  }
}

package ghx

import java.io.ByteArrayInputStream

import org.opencv.core.{Mat, MatOfByte}
import org.opencv.imgcodecs.Imgcodecs

trait ImageSource {
  def getMat: Mat

  def getBitmapBytes: ByteArrayInputStream = {
    val byteMat = new MatOfByte
    Imgcodecs.imencode(".bmp", getMat, byteMat)
    try new ByteArrayInputStream(byteMat.toArray)
    finally byteMat.release()
  }

  def getImageWidth: Double
  def getImageHeight: Double
}

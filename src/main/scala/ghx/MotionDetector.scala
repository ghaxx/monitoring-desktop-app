package ghx

import java.util

import scala.collection.mutable

class MotionDetector {
  private var imageSource: ImageSource = null
  private val listeners: mutable.ListBuffer[MotionDetector.Listener] = new mutable.ListBuffer[MotionDetector.Listener].empty

  def this(imageSource: ImageSource) = {
    this()
    this.imageSource = imageSource
  }

  def start(): Unit = {
  }

  private[ghx] def addListener(l: MotionDetector.Listener): Unit = {
    listeners.addOne(l)
  }

}

object MotionDetector {
  trait Listener {
    def onMotionDetection(): Unit
  }
}

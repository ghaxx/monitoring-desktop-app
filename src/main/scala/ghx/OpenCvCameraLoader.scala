package ghx

import org.opencv.videoio.VideoCapture

class OpenCvCameraLoader {
  private val reloader: OpenCvCameraLoader.VideoCaptureReloader = new OpenCvCameraLoader.VideoCaptureReloader

  def findFirstCamera: Option[OpenCvCamera] = {
    return reloader.findFirstCamera.map((videoCapture: VideoCapture) => {
      def foo(videoCapture: VideoCapture) = {
        new OpenCvCamera(videoCapture, reloader)
      }

      foo(videoCapture)
    })
  }

}

object OpenCvCameraLoader {
  class VideoCaptureReloader {
    private val MaxHandlesToCheck: Int = 10
    def findFirstCamera: Option[VideoCapture] = {
      var cameraHandle: Int = 0
      val videoCapture: VideoCapture = new VideoCapture
      var cameraLoaded: Boolean = false
      while ( {
        !(cameraLoaded) && cameraHandle < MaxHandlesToCheck
      }) {
        cameraLoaded = videoCapture.open({
          cameraHandle += 1; cameraHandle - 1
        })
      }
      if (cameraLoaded) {
        return Option(videoCapture)
      }
      else {
        return Option.empty
      }
    }
  }
}
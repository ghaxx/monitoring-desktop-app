package ghx;

import org.opencv.videoio.VideoCapture;

import java.util.Optional;

public class OpenCvCameraLoader {
    private VideoCaptureReloader reloader = new VideoCaptureReloader();

    public Optional<OpenCvCamera> findFirstCamera() {
        return reloader.findFirstCamera().map(videoCapture -> {
           return new OpenCvCamera(videoCapture, reloader);
        });
    }

    static class VideoCaptureReloader {
        private int MaxHandlesToCheck = 10;
        public Optional<VideoCapture> findFirstCamera() {
            int cameraHandle = 0;
            VideoCapture videoCapture = new VideoCapture();
            boolean cameraLoaded = false;
            while (!cameraLoaded && cameraHandle < MaxHandlesToCheck) {
                cameraLoaded = videoCapture.open(cameraHandle++);
            }
            if (cameraLoaded) {
                return Optional.of(videoCapture);
            } else {
                return Optional.empty();
            }
        }
    }
}

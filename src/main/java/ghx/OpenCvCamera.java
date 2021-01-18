package ghx;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.Videoio;

import java.io.ByteArrayInputStream;
import java.util.ArrayDeque;
import java.util.Queue;

import static org.opencv.videoio.Videoio.CAP_PROP_GUID;

public class OpenCvCamera implements ImageSource {

    private VideoCapture videoCapture;
    private OpenCvCameraLoader.VideoCaptureReloader reloader;
    Mat buffer = new Mat();

    public OpenCvCamera(VideoCapture videoCapture, OpenCvCameraLoader.VideoCaptureReloader reloader) {
        this.videoCapture = videoCapture;
        this.reloader = reloader;
        System.out.println(videoCapture.get(CAP_PROP_GUID));
    }

    @Override
    public ByteArrayInputStream getBitmapBytes() {
        Mat frame = new Mat();
        if (!videoCapture.read(frame)) {
            videoCapture.release();
            Mat red = new Mat(buffer.size(), buffer.type(), new Scalar(0, 0, 1.1));
            reloader.findFirstCamera().map(newVideoCapture -> {
               videoCapture = newVideoCapture;
               return null;
            });
            frame = red.mul(buffer);
        }
        frame.copyTo(buffer);
        MatOfByte byteMat = new MatOfByte();
        Imgcodecs.imencode(".bmp", buffer, byteMat);
        try {
            return new ByteArrayInputStream(byteMat.toArray());
        } finally {
            byteMat.release();
        }
    }

    @Override
    public double getImageWidth() {
        return videoCapture.get(Videoio.CAP_PROP_FRAME_WIDTH);
    }

    @Override
    public double getImageHeight() {
        return videoCapture.get(Videoio.CAP_PROP_FRAME_HEIGHT);
    }

    ;
}

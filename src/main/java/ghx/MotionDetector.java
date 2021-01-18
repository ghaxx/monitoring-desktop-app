package ghx;

import java.util.ArrayList;
import java.util.List;

public class MotionDetector {
    private ImageSource imageSource;
    private final List<Listener> listeners = new ArrayList<>();

    public MotionDetector(ImageSource imageSource) {
        this.imageSource = imageSource;
    }

    public void start() {

    }

    void addListener(Listener l) {
        listeners.add(l);
    }

    interface Listener {
        void onMotionDetection();
    }
}

package ghx;

import java.io.ByteArrayInputStream;

public interface ImageSource {
    ByteArrayInputStream getBitmapBytes();
    double getImageWidth();
    double getImageHeight();
}

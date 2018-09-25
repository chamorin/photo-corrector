import java.awt.image.BufferedImage;

public class HistogramBlue extends Histogram{

    public HistogramBlue(BufferedImage image) {
        setPixels(readImage(image));
        createTable();
        getComponent();
        findZones();
    }

    @Override
    public void getComponent() {
        for (int pixel : getPixels()) {
            int blue = (pixel) & 0xff;
            stats.set(blue, stats.get(blue) + 1);
        }
    }
}

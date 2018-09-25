import java.awt.image.BufferedImage;

public class HistogramGreen extends Histogram {

    public HistogramGreen(BufferedImage image) {
        setPixels(readImage(image));
        createTable();
        getComponent();
        findZones();
    }

    @Override
    public void getComponent() {
        for (int pixel : getPixels()) {
            int green = (pixel >> 8) & 0xff;
            stats.set(green, stats.get(green) + 1);
        }
    }
}

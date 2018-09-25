import java.awt.image.BufferedImage;

public class HistogramRed extends Histogram {

    public HistogramRed(BufferedImage image) {
        setPixels(readImage(image));
        createTable();
        getComponent();
        findZones();
    }

    @Override
    public void getComponent() {
        for (int pixel : getPixels()) {
            int red = (pixel >> 16) & 0xff;
            stats.set(red, stats.get(red) + 1);
        }
    }
}

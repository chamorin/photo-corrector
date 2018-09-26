import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Corrector {

    private ArrayList<Integer> redList = new ArrayList<>();
    private ArrayList<Integer> greenList = new ArrayList<>();
    private ArrayList<Integer> blueList = new ArrayList<>();

    Corrector(BufferedImage image) {
        createList(redList);
        createList(greenList);
        createList(blueList);

        getComponents(image);

        Histogram histogramRed = new Histogram(redList);
        Histogram histogramGreen = new Histogram(greenList);
        Histogram histogramBlue = new Histogram(blueList);
    }

    private void createList(ArrayList<Integer> list) {
        for (int i = 0; i <= 256; ++i)
            list.add(i,0);
    }

    public void getComponents(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        int rgb, red, green, blue;
        for (int col = 0; col < width; ++col) {
            for (int row = 0; row < height; ++row) {
                rgb = image.getRGB(col, row);
                red = (rgb >> 16) & 0xff;
                green = (rgb >> 8) & 0xff;
                blue = (rgb >> 0) & 0xff;

                redList.set(red, redList.get(red) + 1);
                greenList.set(green, greenList.get(green) + 1);
                blueList.set(blue, blueList.get(blue) + 1);
            }
        }
    }

    public void firstAlgo() {

    }

    public void secondAlgo() {

    }
}

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * - Corrector class -
 * Creates three histograms containing RGB components of a selected image
 * and modified them individually creating two modified images
 */
public class Corrector {

    private String _imageName;

    private Histogram _histogramRed;
    private Histogram _histogramGreen;
    private Histogram _histogramBlue;

    Corrector() {
        BufferedImage image = setImage();

        _histogramRed = new Histogram();
        _histogramGreen = new Histogram();
        _histogramBlue = new Histogram();

        getComponents(image);

        createImageCls(image);
        createImageClc(image);
    }

    /**
     * Set the current working image to the user input
     *
     * @return the selected image
     */
    private BufferedImage setImage() {
        BufferedImage image = null;
        try {
            System.out.print("Enter image name: ");
            Scanner sc = new Scanner(System.in);
            _imageName = sc.nextLine();
            image = ImageIO.read(new File(_imageName));
        } catch (IOException e) {
            System.err.println("Error: unable to find " + _imageName);
            System.exit(-1);
        }
        _imageName = _imageName.split("\\.")[0];
        return image;
    }


    /**
     * Get each RGB components of the image
     *
     * @param image the initial selected image
     */
    private void getComponents(BufferedImage image) {
        try {
            int width = image.getWidth();
            int height = image.getHeight();
            int red, green, blue;

            for (int col = 0; col < width; ++col) {
                for (int row = 0; row < height; ++row) {
                    ColorM color = new ColorM(image.getRGB(col, row));
                    red = color.getRed();
                    green = color.getGreen();
                    blue = color.getBlue();

                    _histogramRed._componentsList.set(red, _histogramRed._componentsList.get(red) + 1);
                    _histogramGreen._componentsList.set(green, _histogramGreen._componentsList.get(green) + 1);
                    _histogramBlue._componentsList.set(blue, _histogramBlue._componentsList.get(blue) + 1);
                }
            }

            _histogramRed.findZones();
            _histogramGreen.findZones();
            _histogramBlue.findZones();

        } catch (Exception e) {
            System.err.println("Error: unable to get image components");
            System.exit(-1);
        }
    }

    /**
     * Create the first image using the first algorithm
     *
     * @param image the initial selected image
     */
    private void createImageCls(BufferedImage image) {
        try {
            int width = image.getWidth();
            int height = image.getHeight();

            BufferedImage newImage = new BufferedImage(width, height, image.getType());

            int red, green, blue;
            for (int col = 0; col < width; ++col) {
                for (int row = 0; row < height; ++row) {
                    ColorM color = new ColorM(image.getRGB(col, row));
                    red = color.getRed();
                    green = color.getGreen();
                    blue = color.getBlue();

                    color.setRed(firstAlgo(red, _histogramRed));
                    color.setGreen(firstAlgo(green, _histogramGreen));
                    color.setBlue(firstAlgo(blue, _histogramBlue));

                    newImage.setRGB(col, row, color.getArgb());
                }
            }
            ImageIO.write(newImage, "jpg", new File(_imageName + "cls.jpg"));
        } catch (IOException e) {
            System.err.println("Error: unable to create cls image");
            System.exit(-1);
        }
    }

    /**
     * Create the second image using the second algorithm
     *
     * @param image the initial selected image
     */
    private void createImageClc(BufferedImage image) {
        try {
            int width = image.getWidth();
            int height = image.getHeight();

            BufferedImage newImage = new BufferedImage(width, height, image.getType());

            int red, green, blue;
            for (int col = 0; col < width; ++col) {
                for (int row = 0; row < height; ++row) {
                    ColorM color = new ColorM(image.getRGB(col, row));
                    red = color.getRed();
                    green = color.getGreen();
                    blue = color.getBlue();

                    color.setRed(secondAlgo(red, _histogramRed));
                    color.setGreen(secondAlgo(green, _histogramGreen));
                    color.setBlue(secondAlgo(blue, _histogramBlue));

                    newImage.setRGB(col, row, color.getArgb());
                }
            }

            ImageIO.write(newImage, "jpg", new File(_imageName + "clc.jpg"));
        } catch (IOException e) {
            System.err.println("Error: unable to create clc image");
            System.exit(-1);
        }
    }

    /**
     * @param value     the component value to modify
     * @param histogram the histogram of the selected value
     * @return the modified value
     */
    private int firstAlgo(int value, Histogram histogram) {
        value = 256 * (value - histogram.getZones().get(0).begin) /
                (histogram.getZones().get(histogram.getZones().size() - 1).end - histogram.getZones().get(0).begin + 1);
        return value;
    }

    /**
     * Find the center values of each zones
     *
     * @param histogram the current selected histogram
     * @return list of all center values of each zones of the histogram
     */
    private ArrayList<Integer> findCenterValues(Histogram histogram) {
        ArrayList<Integer> center = new ArrayList<>();

        center.add(0, 0);
        for (int i = 1; i < histogram.getZones().size(); ++i) {
            int temp = (histogram.getZones().get(i - 1).end + histogram.getZones().get(i).begin) / 2;
            center.add(i, temp);
        }
        center.add(255);

        return center;
    }

    /**
     * Find the zones in wich the value can be found
     *
     * @param value     the value that needs to find to wich zone it belongs to
     * @param histogram the current selected histogram
     * @return the zone containing the value
     */
    private int findBelongingZone(int value, Histogram histogram) {
        int zone = 0;
        for (int i = 1; i < histogram.getZones().size(); ++i) {
            if (histogram.getZones().get(i).begin <= value && value <= histogram.getZones().get(i).end)
                zone = i;
        }
        return zone;
    }

    /**
     * @param value     the component value to modify
     * @param histogram the current selected histogram
     * @return the modified value
     */
    private int secondAlgo(int value, Histogram histogram) {
        ArrayList<Integer> center = findCenterValues(histogram);
        int zone = findBelongingZone(value, histogram);

        value = center.get(zone) + (center.get(zone + 1) - center.get(zone) + 1) * (value - histogram.getZones().get(zone).begin) /
                (histogram.getZones().get(zone).end - histogram.getZones().get(zone).begin + 1);

        return value;
    }
}

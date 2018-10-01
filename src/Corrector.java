import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Corrector {

    private ArrayList<Integer> redList = new ArrayList<>();
    private ArrayList<Integer> greenList = new ArrayList<>();
    private ArrayList<Integer> blueList = new ArrayList<>();

    private BufferedImage image;
    private String imageName;

    Corrector() {

        image = getImage();

        createList(redList);
        createList(greenList);
        createList(blueList);

        getComponents(image);

        Histogram histogramRed = new Histogram(redList);
        Histogram histogramGreen = new Histogram(greenList);
        Histogram histogramBlue = new Histogram(blueList);

        createImageCls(image, histogramRed, histogramGreen, histogramBlue);
        createImageClc(image, histogramRed, histogramGreen, histogramBlue);
    }

    private BufferedImage getImage() {
        BufferedImage image = null;
        try {
            System.out.print("Enter image name: ");
            Scanner sc = new Scanner(System.in);
            imageName = sc.nextLine();
            image = ImageIO.read(new File(imageName));
        } catch (IOException e) {
            System.err.println("Error: unable to find " + imageName);
            System.exit(-1);
        }
        imageName = imageName.split("\\.")[0];
        return image;
    }

    private void createList(ArrayList<Integer> list) {
        for (int i = 0; i <= 256; ++i)
            list.add(i, 0);
    }

    public void getComponents(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        int red, green, blue;
        for (int col = 0; col < width; ++col) {
            for (int row = 0; row < height; ++row) {
                Couleur color = new Couleur(image.getRGB(col, row));
                red = color.getRouge();
                green = color.getVert();
                blue = color.getBleu();

                redList.set(red, redList.get(red) + 1);
                greenList.set(green, greenList.get(green) + 1);
                blueList.set(blue, blueList.get(blue) + 1);
            }
        }
    }

    public void createImageCls(BufferedImage image, Histogram histogramRed, Histogram histogramGreen, Histogram histogramBlue) {
        int width = image.getWidth();
        int height = image.getHeight();

        BufferedImage newImage = new BufferedImage(width, height, image.getType());

        int red, green, blue;
        for (int col = 0; col < width; ++col) {
            for (int row = 0; row < height; ++row) {
                Couleur color = new Couleur(image.getRGB(col, row));
                red = color.getRouge();
                green = color.getVert();
                blue = color.getBleu();

                color.setRouge(firstAlgo(red, histogramRed));
                color.setVert(firstAlgo(green, histogramGreen));
                color.setBleu(firstAlgo(blue, histogramBlue));

                newImage.setRGB(col, row, color.getArgb());
            }
        }
        try {
            ImageIO.write(newImage, "jpg", new File(imageName + "cls.jpg"));
        } catch (IOException e) {
            System.err.println("Error: unable to create cls image");
            System.exit(-1);
        }
    }

    public void createImageClc(BufferedImage image, Histogram histogramRed, Histogram histogramGreen, Histogram histogramBlue) {
        int width = image.getWidth();
        int height = image.getHeight();

        BufferedImage newImage = new BufferedImage(width, height, image.getType());

        int red, green, blue;
        for (int col = 0; col < width; ++col) {
            for (int row = 0; row < height; ++row) {
                Couleur color = new Couleur(image.getRGB(col, row));
                red = color.getRouge();
                green = color.getVert();
                blue = color.getBleu();

                color.setRouge(secondAlgo(red, histogramRed));
                color.setVert(secondAlgo(green, histogramGreen));
                color.setBleu(secondAlgo(blue, histogramBlue));

                newImage.setRGB(col, row, color.getArgb());
            }
        }
        try {
            ImageIO.write(newImage, "jpg", new File(imageName + "clc.jpg"));
        } catch (IOException e) {
            System.err.println("Error: unable to create clc image");
            System.exit(-1);
        }
    }

    public int firstAlgo(int value, Histogram histogram) {
        value = 256 * (value - histogram.getZones().get(0).begin) /
                (histogram.getZones().get(histogram.getZones().size() - 1).end - histogram.getZones().get(0).begin + 1);
        return value;
    }

    public ArrayList<Integer> findCenterValues(Histogram histogram) {
        ArrayList<Integer> center = new ArrayList<>();

        center.add(0, 0);
        for (int i = 1; i < histogram.getZones().size(); ++i) {
            int temp = (histogram.getZones().get(i - 1).end + histogram.getZones().get(i).begin) / 2;
            center.add(i, temp);
        }
        center.add(255);

        return center;
    }

    public int findBelongingZone(int value, Histogram histogram) {
        int zone = 0;
        for (int i = 1; i < histogram.getZones().size(); ++i) {
            if (histogram.getZones().get(i).begin <= value && value <= histogram.getZones().get(i).end)
                zone = i;
//            else
//                zone = histogram.getZones().size() - 1;
        }
        return zone;
    }

    public int secondAlgo(int value, Histogram histogram) {
        ArrayList<Integer> center = findCenterValues(histogram);
        int zone = findBelongingZone(value, histogram);

        value = center.get(zone) + (center.get(zone + 1) - center.get(zone) + 1) * (value - histogram.getZones().get(zone).begin) /
                (histogram.getZones().get(zone).end - histogram.getZones().get(zone).begin + 1);

        return value;
    }
}

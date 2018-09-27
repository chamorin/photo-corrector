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
    }

    private BufferedImage getImage() {
        BufferedImage image = null;
        String fileName = "";
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
        int rgb, red, green, blue;
        for (int col = 0; col < width; ++col) {
            for (int row = 0; row < height; ++row) {
                Couleur color = new Couleur(image.getRGB(col, row));
                red = color.getRouge();
                green = color.getVert();
                blue = color.getBleu();

//                rgb = image.getRGB(col, row);
//                red = (rgb >> 16) & 0xff;
//                green = (rgb >> 8) & 0xff;
//                blue = (rgb >> 0) & 0xff;

                redList.set(red, redList.get(red) + 1);
                greenList.set(green, greenList.get(green) + 1);
                blueList.set(blue, blueList.get(blue) + 1);
            }
        }
    }

    public void createImageCls(BufferedImage image, Histogram histogramRed, Histogram histogramGreen, Histogram histogramBlue) {
        int width = image.getWidth();
        int height = image.getHeight();
        int rgb, red, green, blue;
        for (int col = 0; col < width; ++col) {
            for (int row = 0; row < height; ++row) {
                Couleur color = new Couleur(image.getRGB(col, row));
                red = color.getRouge();
                green = color.getVert();
                blue = color.getBleu();

//                rgb = image.getRGB(col, row);
//                red = (rgb >> 16) & 0xff;
//                green = (rgb >> 8) & 0xff;
//                blue = (rgb >> 0) & 0xff;

                color.setRouge(firstAlgo(red, histogramRed));
                color.setVert(firstAlgo(green, histogramGreen));
                color.setBleu(firstAlgo(blue, histogramBlue));

                image.setRGB(col, row, color.getArgb());
            }
        }
        try {
            ImageIO.write(image, "jpg", new File("cls_" + imageName));
        } catch (IOException e) {
            // Erreur
        }
    }

    public void createImageClc(BufferedImage image, Histogram histogramRed, Histogram histogramGreen, Histogram histogramBlue) {
        int width = image.getWidth();
        int height = image.getHeight();
        int rgb, red, green, blue;
        for (int col = 0; col < width; ++col) {
            for (int row = 0; row < height; ++row) {
                rgb = image.getRGB(col, row);

                red = (rgb >> 16) & 0xff;
                green = (rgb >> 8) & 0xff;
                blue = (rgb >> 0) & 0xff;

                image.setRGB(col, row, secondAlgo(red, histogramRed) + secondAlgo(green, histogramGreen) + secondAlgo(blue, histogramBlue));

            }
        }
        try {
            ImageIO.write(image, "jpg", new File("clc_" + imageName));
        } catch (IOException e) {
            // Erreur
        }
    }

    public int firstAlgo(int value, Histogram histogram) {

        value = 256 * (value - histogram.getZones().get(0).begin) /
                histogram.getZones().get(histogram.getZones().size() - 1).end - histogram.getZones().get(0).begin + 1;
        return value;
    }

    public int secondAlgo(int value, Histogram histogram) {
        ArrayList<Integer> m = new ArrayList<>();

        for (int i = 0; i < histogram.getZones().size(); ++i) {
            if (i == 0)
                m.add(i, 0);
            else if (histogram.getZones().get(i) == histogram.getZones().get(histogram.getZones().size() - 1)) {
                m.add(i, 255);
            } else {
                int temp = (histogram.getZones().get(i - 1).end + histogram.getZones().get(i).begin) / 2;
                m.add(i, temp);
            }
        }


        return 0;
    }
}

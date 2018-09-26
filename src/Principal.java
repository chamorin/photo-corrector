import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Principal {

    public static BufferedImage getImage() {
        BufferedImage image = null;
        String fileName = "";
        try {
            System.out.print("Enter image name: ");
            Scanner sc = new Scanner( System.in );
            fileName = sc.nextLine();
            image = ImageIO.read(new File( fileName));
        } catch (IOException e) {
            System.err.println( "Error: unable to find " + fileName);
            System.exit(-1);
        }
        return image;
    }

    public static void writeImage() {

    }

    public static void main(String args[]) {
        Corrector corrector = new Corrector(getImage());

    }
}

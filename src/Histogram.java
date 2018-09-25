import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.util.ArrayList;

abstract public class Histogram {

    class Zone {
        int begin;
        int end;
    }

    private ArrayList<Zone> zoneList = new ArrayList<>();
    protected ArrayList<Integer> stats = new ArrayList<>();
    protected byte[] pixels;

    public void setStats(ArrayList<Integer> stats) {
        this.stats = stats;
    }

    public ArrayList<Integer> getStats() {
        return stats;
    }

    public void setPixels(byte[] pixels) {
        this.pixels = pixels;
    }

    public byte[] getPixels() {
        return pixels;
    }

    public byte[] readImage(BufferedImage image) {
        return ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
    }

    public void createTable() {
        for(int i = 0; i < 255; ++i)
            stats.add(0);
    }

    abstract protected void getComponent();

    public void findZones() {

    }

}
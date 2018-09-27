import java.util.ArrayList;

public class Histogram {

    class Zone {
        int begin;
        int end;

        Zone(int b, int e) {
            begin = b;
            end = e;
        }
    }

    private ArrayList<Zone> zones = new ArrayList<>();

    public ArrayList<Zone> getZones() {
        return zones;
    }

    public void setZones(ArrayList<Zone> zoneList) {
        this.zones = zoneList;
    }

    Histogram(ArrayList<Integer> colors) {
        setZones(findZones(colors));
    }

    public ArrayList<Zone> findZones(ArrayList<Integer> colors) {
        ArrayList<Zone> zones = new ArrayList<>();
        int i = 0, begin = 0, end = 0;
        while (i < 256) {
            if (!colors.get(i).equals(0)) {
                begin = i;
                while (!colors.get(i).equals(0))
                    ++i;
                end = i;
                zones.add(new Zone(begin, end));
            }
            ++i;
        }
        return zones;
    }

}
import java.util.ArrayList;

public class Histogram {

    class Zone {
         int begin;
         int end;
    }

    private ArrayList<Zone> zonelist = new ArrayList<>();

    public ArrayList<Zone> getZonelist() {
        return zonelist;
    }

    public void setZonelist(ArrayList<Zone> zones) {
        zonelist = zones;
    }

    Histogram(ArrayList<Integer> colors) {
        setZonelist(findZones(colors));
    }

    public ArrayList<Zone> findZones(ArrayList<Integer> colors) {
        ArrayList<Zone> zones = new ArrayList<>();
        Zone temp = new Zone();

        for(int i = 0; i < 256; ++i) {
            if(!colors.get(i).equals(0)) {
                temp.begin = i;
                while (!colors.get(i).equals(0))
                    ++i;
                temp.end = i - 1;
            }
            zones.add(temp);
        }
        return zones;
    }

}
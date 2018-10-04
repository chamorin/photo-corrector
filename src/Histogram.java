import java.util.ArrayList;

/**
 * - Histogram class -
 * Contains one of the three color components of the selected image
 */
public class Histogram {

    class Zone {
        int begin;
        int end;

        Zone(int b, int e) {
            begin = b;
            end = e;
        }
    }

    private ArrayList<Zone> _zones = new ArrayList<>();
    public ArrayList<Integer> _componentsList;

    public ArrayList<Zone> getZones() {
        return _zones;
    }

    private void setZones(ArrayList<Zone> zoneList) {
        this._zones = zoneList;
    }

    Histogram() {
        _componentsList = createList();
    }

    /**
     * Creates empty Integer list of size 256
     *
     * @return the new empty list
     */
    private ArrayList<Integer> createList() {
        ArrayList<Integer> colorList = new ArrayList<>();
        try {
            for (int i = 0; i <= 256; ++i)
                colorList.add(i, 0);
        } catch (Exception e) {
            System.err.println("Error: unable to create list");
            System.exit(-1);
        }
        return colorList;
    }

    /**
     * Finds every zones where the components value are not zero
     */
    public void findZones() {
        ArrayList<Zone> zones = new ArrayList<>();
        int i = 0, begin, end;
        while (i < 256) {
            if (!_componentsList.get(i).equals(0)) {
                begin = i;
                while (!_componentsList.get(i).equals(0))
                    ++i;
                end = i;
                zones.add(new Zone(begin, end));
            }
            ++i;
        }
        setZones(zones);
    }

}
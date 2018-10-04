
public class ColorM {
    private static final int BYTE_PER_COMPONENT = 8;
    private static final int MASK_COMPONENT;
    public static final int MAX_COMPONENT_VALUE;
    public static final int MIN_COMPONENT_VALUE = 0;
    public static final int NUMBER_COMPONENT_VALUE;

    static {
        int i = 0;
        int mask = 0;

        for (i = 0; i < BYTE_PER_COMPONENT; ++i) {
            mask <<= 1;
            mask |= 1;
        }

        MASK_COMPONENT = mask;
        MAX_COMPONENT_VALUE = mask;
        NUMBER_COMPONENT_VALUE = MAX_COMPONENT_VALUE - MIN_COMPONENT_VALUE + 1;
    }

    protected int _alpha;
    protected int _red;
    protected int _green;
    protected int _blue;

    public ColorM(int argb) {
        setArgb(argb);
    }

    public ColorM(int red, int green, int blue) {
        assert MIN_COMPONENT_VALUE <= red && red <= MAX_COMPONENT_VALUE;
        assert MIN_COMPONENT_VALUE <= green && green <= MAX_COMPONENT_VALUE;
        assert MIN_COMPONENT_VALUE <= blue && blue <= MAX_COMPONENT_VALUE;

        _red = red;
        _green = green;
        _blue = blue;
    }

    public ColorM(int alpha, int red, int green, int blue) {
        assert MIN_COMPONENT_VALUE <= alpha && alpha <= MAX_COMPONENT_VALUE;
        assert MIN_COMPONENT_VALUE <= red && red <= MAX_COMPONENT_VALUE;
        assert MIN_COMPONENT_VALUE <= green && green <= MAX_COMPONENT_VALUE;
        assert MIN_COMPONENT_VALUE <= blue && blue <= MAX_COMPONENT_VALUE;

        _alpha = alpha;
        _red = red;
        _green = green;
        _blue = blue;
    }

    public int getArgb() {
        return (((((_alpha << BYTE_PER_COMPONENT) | _red) << BYTE_PER_COMPONENT) | _green) << BYTE_PER_COMPONENT) | _blue;
    }

    public int getAlpha() {
        return _alpha;
    }

    public int getBlue() {
        return _blue;
    }

    public int getRed() {
        return _red;
    }

    public int getGreen() {
        return _green;
    }

    public void setArgb(int argb) {
        _blue = argb & MASK_COMPONENT;
        argb >>= BYTE_PER_COMPONENT;
        _green = argb & MASK_COMPONENT;
        argb >>= BYTE_PER_COMPONENT;
        _red = argb & MASK_COMPONENT;
        argb >>= BYTE_PER_COMPONENT;
        _alpha = argb & MASK_COMPONENT;
    }

    public void setAlpha(int alpha) {
        assert MIN_COMPONENT_VALUE <= alpha && alpha <= MAX_COMPONENT_VALUE;

        _alpha = alpha;
    }

    public void setBlue(int blue) {
        assert MIN_COMPONENT_VALUE <= blue && blue <= MAX_COMPONENT_VALUE;

        _blue = blue;
    }

    public void setRed(int red) {
        assert MIN_COMPONENT_VALUE <= red && red <= MAX_COMPONENT_VALUE;

        _red = red;
    }

    public void setGreen(int green) {
        assert MIN_COMPONENT_VALUE <= green && green <= MAX_COMPONENT_VALUE;

        _green = green;
    }
}
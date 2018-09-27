
public class Couleur {
    private static final int BIT_PAR_COMPOSANTE = 8;
    private static final int MASQUE_COMPOSANTE;
    public static final int MAX_VALEUR_COMPOSANTE;
    public static final int MIN_VALEUR_COMPOSANTE = 0;
    public static final int NOMBRE_VALEUR_COMPOSANTE;

    static {
        int i = 0;
        int masque = 0;

        for (i = 0; i < BIT_PAR_COMPOSANTE; ++i) {
            masque <<= 1;
            masque |= 1;
        }

        MASQUE_COMPOSANTE = masque;
        MAX_VALEUR_COMPOSANTE = masque;
        NOMBRE_VALEUR_COMPOSANTE = MAX_VALEUR_COMPOSANTE - MIN_VALEUR_COMPOSANTE + 1;
    }

    protected int _alpha;
    protected int _rouge;
    protected int _vert;
    protected int _bleu;

    public Couleur(int argb) {
        setArgb(argb);
    }

    public Couleur(int rouge, int vert, int bleu) {
        assert MIN_VALEUR_COMPOSANTE <= rouge && rouge <= MAX_VALEUR_COMPOSANTE;
        assert MIN_VALEUR_COMPOSANTE <= vert && vert <= MAX_VALEUR_COMPOSANTE;
        assert MIN_VALEUR_COMPOSANTE <= bleu && bleu <= MAX_VALEUR_COMPOSANTE;

        _rouge = rouge;
        _vert = vert;
        _bleu = bleu;
    }

    public Couleur(int alpha, int rouge, int vert, int bleu) {
        assert MIN_VALEUR_COMPOSANTE <= alpha && alpha <= MAX_VALEUR_COMPOSANTE;
        assert MIN_VALEUR_COMPOSANTE <= rouge && rouge <= MAX_VALEUR_COMPOSANTE;
        assert MIN_VALEUR_COMPOSANTE <= vert && vert <= MAX_VALEUR_COMPOSANTE;
        assert MIN_VALEUR_COMPOSANTE <= bleu && bleu <= MAX_VALEUR_COMPOSANTE;

        _alpha = alpha;
        _rouge = rouge;
        _vert = vert;
        _bleu = bleu;
    }

    public int getArgb() {
        return (((((_alpha << BIT_PAR_COMPOSANTE) | _rouge) << BIT_PAR_COMPOSANTE) | _vert) << BIT_PAR_COMPOSANTE) | _bleu;
    }

    public int getAlpha() {
        return _alpha;
    }

    public int getBleu() {
        return _bleu;
    }

    public int getRouge() {
        return _rouge;
    }

    public int getVert() {
        return _vert;
    }

    public void setArgb(int argb) {
        _bleu = argb & MASQUE_COMPOSANTE;
        argb >>= BIT_PAR_COMPOSANTE;
        _vert = argb & MASQUE_COMPOSANTE;
        argb >>= BIT_PAR_COMPOSANTE;
        _rouge = argb & MASQUE_COMPOSANTE;
        argb >>= BIT_PAR_COMPOSANTE;
        _alpha = argb & MASQUE_COMPOSANTE;
    }

    public void setAlpha(int alpha) {
        assert MIN_VALEUR_COMPOSANTE <= alpha && alpha <= MAX_VALEUR_COMPOSANTE;

        _alpha = alpha;
    }

    public void setBleu(int bleu) {
        assert MIN_VALEUR_COMPOSANTE <= bleu && bleu <= MAX_VALEUR_COMPOSANTE;

        _bleu = bleu;
    }

    public void setRouge(int rouge) {
        assert MIN_VALEUR_COMPOSANTE <= rouge && rouge <= MAX_VALEUR_COMPOSANTE;

        _rouge = rouge;
    }

    public void setVert(int vert) {
        assert MIN_VALEUR_COMPOSANTE <= vert && vert <= MAX_VALEUR_COMPOSANTE;

        _vert = vert;
    }
}
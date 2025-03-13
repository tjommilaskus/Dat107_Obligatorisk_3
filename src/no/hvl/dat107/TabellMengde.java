package no.hvl.dat107;

public class TabellMengde <T> implements MengdeADT {
    private static final int DEFAULT_CAPACITY = 10;

    private T[] tabell;
    private int antall;


    public TabellMengde() {
        this(DEFAULT_CAPACITY);
    }

    @SuppressWarnings("unchecked")
    public TabellMengde(int kapasitet) {
        tabell = (T[]) new Object[kapasitet];
        antall = 0;
    }

    @Override
    public boolean erTom() {
        return antall == 0;
    }

    @Override
    public boolean inneholder(Object element) {
        for (T t : tabell) {
            if (t.equals(element)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean erDelmengdeAv(MengdeADT annenMengde) {
        for (int i = 0; i < antall; i++) {
            if (!annenMengde.inneholder(tabell[i])){
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean erLik(MengdeADT annenMengde) {
        return erDelmengdeAv(annenMengde) && annenMengde.erDelmengdeAv(this);
    }

    @Override
    public boolean erDisjunkt(MengdeADT annenMengde) {
        for (int i = 0; i < antall; i++) {
            if (annenMengde.inneholder(tabell[i])){
                return false;
            }
        }
        return false;
    }

    @Override
    public MengdeADT snitt(MengdeADT annenMengde) {
        return null;
    }

    @Override
    public MengdeADT union(MengdeADT annenMengde) {
        return null;
    }

    @Override
    public MengdeADT minus(MengdeADT annenMengde) {
        return null;
    }

    @Override
    public void leggTil(Object element) {

    }

    @Override
    public void leggTilAlleFra(MengdeADT annenMengde) {

    }

    @Override
    public Object fjern(Object element) {
        return null;
    }

    @Override
    public Object[] tilTabell() {
        return new Object[0];
    }

    @Override
    public int antallElementer() {
        return 0;
    }
}

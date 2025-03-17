package no.hvl.dat107;

public class TabellMengde<T> implements MengdeADT<T> {
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
    public boolean inneholder(T element) {
        if (element == null) {
            return false;
        }
        for (int i = 0; i < antall; i++) {
            if (element.equals(tabell[i])) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean erDelmengdeAv(MengdeADT<T> annenMengde) {
        for (int i = 0; i < antall; i++) {
            if (!annenMengde.inneholder(tabell[i])){
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean erLik(MengdeADT<T> annenMengde) {
        return erDelmengdeAv(annenMengde) && annenMengde.erDelmengdeAv(this);
    }

    @Override
    public boolean erDisjunkt(MengdeADT<T> annenMengde) {
        for (int i = 0; i < antall; i++) {
            if (annenMengde.inneholder(tabell[i])){
                return false;
            }
        }
        return true;
    }

    @Override
    public MengdeADT<T> snitt(MengdeADT<T> annenMengde) {
        MengdeADT<T> snittMengde = new TabellMengde<T>();

        for (int i = 0; i < antall; i++){
            if (annenMengde.inneholder(tabell[i])) {
                snittMengde.leggTil(tabell[i]);
            }
        }
        return snittMengde;
    }

    @Override
    public MengdeADT<T> union(MengdeADT<T> annenMengde) {
        MengdeADT<T> unionMengde = new TabellMengde<T>();

        for (int i = 0; i < antall; i++) {
            unionMengde.leggTil(tabell[i]);
        }
        unionMengde.leggTilAlleFra(annenMengde);
        return unionMengde;
    }

    @Override
    public MengdeADT<T> minus(MengdeADT<T> annenMengde) {
        MengdeADT<T> differanseMengde = new TabellMengde<T>();

        for (int i = 0; i < antall; i++) {
            if (!annenMengde.inneholder(tabell[i])) {
                differanseMengde.leggTil(tabell[i]);
            }
        }
        return differanseMengde;
    }

    @Override
    public void leggTil(T element) {
        if (element == null) {
            return;
        }
        if (inneholder(element)){
            return;
        }
        if (antall == tabell.length) {
            utvidKapasitet();
        }
        tabell[antall] = element;
        antall++;

    }

    @Override
    public void leggTilAlleFra(MengdeADT<T> annenMengde) {
        T[] annenTabell = annenMengde.tilTabell();

        for (T element : annenTabell) {
            leggTil(element);
        }
    }

    @Override
    public T fjern(T element) {
        if (element == null || antall == 0) {
            return null;
        }
        for (int i = 0; i < antall; i++) {
            if (element.equals(tabell[i])) {
                T fjernElement = tabell[i];

                tabell[i] = tabell[antall - 1];
                tabell[antall - 1] = null;
                antall--;

                return fjernElement;
            }
        }
        return null;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T[] tilTabell() {
        T[] resultat = (T[]) new Object[antall];
        for (int i = 0; i < antall; i++) {
            resultat[i] = tabell[i];
        }
        return resultat;

    }

    @Override
    public int antallElementer() {
        return antall;
    }

    @SuppressWarnings("unchecked")
    private void utvidKapasitet() {
        T[] nyTabell = (T[]) new Object()[tabell.length * 2];

        for (int i = 0; i < antall; i++){
            nyTabell[i] = tabell[i];
        }
        tabell = nyTabell;
    }

    @Override
    public String toString() {
        if (antall == 0) {
            return "[]";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("[");

        for (int i = 0; i < antall - 1; i++) {
            sb.append(tabell[i]).append(", ");
        }

        sb.append(tabell[antall - 1]).append("]");
        return sb.toString();
    }
}

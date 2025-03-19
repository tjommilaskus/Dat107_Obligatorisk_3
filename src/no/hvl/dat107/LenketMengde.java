package no.hvl.dat107;


public class LenketMengde<T> implements MengdeADT<T> {

    private Node<T> forste;
    private int antall;


    public LenketMengde() {
        forste = null;
        antall = 0;
    }

    @Override
    public boolean erTom() {
        return antall == 0;
    }

    @Override
    public boolean inneholder(T element) {
        Node<T> current = forste;
        while (current != null) {
            if (current.data.equals(element)) {
                return true;
            }
            current = current.neste;
        }
        return false;
    }

    @Override
    public boolean erDelmengdeAv(MengdeADT<T> annenMengde) {
        if (this.erTom()) {
            return true;
        }

        T[] elementer = this.tilTabell();
        for (T element : elementer) {
            if (!annenMengde.inneholder(element)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean erLik(MengdeADT<T> annenMengde) {
        if (this.antallElementer() != annenMengde.antallElementer()) {
            return false;
        }

        return this.erDelmengdeAv(annenMengde);
    }

    @Override
    public boolean erDisjunkt(MengdeADT<T> annenMengde) {
        if (this.erTom() || annenMengde.erTom()) {
            return true;
        }

        T[] elementer = this.tilTabell();
        for (T element : elementer) {
            if (annenMengde.inneholder(element)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public MengdeADT<T> snitt(MengdeADT<T> annenMengde) {
        MengdeADT<T> resultat = new LenketMengde<>();

        if (this.erTom() || annenMengde.erTom()) {
            return resultat;
        }

        T[] elementer = this.tilTabell();
        for (T element : elementer) {
            if (annenMengde.inneholder(element)) {
                resultat.leggTil(element);
            }
        }

        return resultat;
    }

    @Override
    public MengdeADT<T> union(MengdeADT<T> annenMengde) {
        MengdeADT<T> resultat = new LenketMengde<>();

        resultat.leggTilAlleFra(this);

        resultat.leggTilAlleFra(annenMengde);

        return resultat;
    }

    @Override
    public MengdeADT<T> minus(MengdeADT<T> annenMengde) {
        MengdeADT<T> resultat = new LenketMengde<>();

        if (this.erTom()) {
            return resultat;
        }

        T[] elementer = this.tilTabell();
        for (T element : elementer) {
            if (!annenMengde.inneholder(element)) {
                resultat.leggTil(element);
            }
        }

        return resultat;
    }

    @Override
    public void leggTil(T element) {
        if (inneholder(element)) {
            return;
        }

        Node<T> nyNode = new Node<>(element);
        nyNode.neste = forste;
        forste = nyNode;
        antall++;
    }

    @Override
    public void leggTilAlleFra(MengdeADT<T> annenMengde) {
        if (annenMengde.erTom()) {
            return;
        }

        T[] elementer = annenMengde.tilTabell();
        for (T element : elementer) {
            this.leggTil(element);
        }
    }

    @Override
    public T fjern(T element) {
        if (erTom()) {
            return null;
        }

        if (forste.data.equals(element)) {
            T resultat = forste.data;
            forste = forste.neste;
            antall--;
            return resultat;
        }

        Node<T> current = forste;
        while (current.neste != null) {
            if (current.neste.data.equals(element)) {
                T resultat = current.neste.data;
                current.neste = current.neste.neste;
                antall--;
                return resultat;
            }
            current = current.neste;
        }

        return null;
    }

    @Override
    public T[] tilTabell() {
        @SuppressWarnings("unchecked")
        T[] tabell = (T[]) new Object[antall];

        Node<T> current = forste;
        for (int i = 0; i < antall && current != null; i++) {
            tabell[i] = current.data;
            current = current.neste;
        }

        return tabell;
    }

    @Override
    public int antallElementer() {
        return antall;
    }


    private static class Node<E> {
        private E data;
        private Node<E> neste;

        public Node(E data) {
            this.data = data;
            this.neste = null;
        }
    }
    @Override
    public String toString() {
        if (erTom()) {
            return "[]";
        }

        StringBuilder sb = new StringBuilder("[");
        Node<T> current = forste;

        while (current != null) {
            sb.append(current.data);
            if (current.neste != null) {
                sb.append(", ");
            }
            current = current.neste;
        }

        sb.append("]");
        return sb.toString();
    }
    }
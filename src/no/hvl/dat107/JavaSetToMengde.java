package no.hvl.dat107;

import java.util.HashSet;
import java.util.Set;


public class JavaSetToMengde<T> implements MengdeADT<T> {

    private Set<T> elementer;


    public JavaSetToMengde() {
        elementer = new HashSet<>();
    }


    public JavaSetToMengde(Set<T> set) {
        elementer = new HashSet<>(set);
    }

    @Override
    public boolean erTom() {
        return elementer.isEmpty();
    }

    @Override
    public boolean inneholder(T element) {
        return elementer.contains(element);
    }

    @Override
    public boolean erDelmengdeAv(MengdeADT<T> annenMengde) {
        if (this.erTom()) {
            return true;
        }

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

        for (T element : elementer) {
            if (annenMengde.inneholder(element)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public MengdeADT<T> snitt(MengdeADT<T> annenMengde) {
        JavaSetToMengde<T> resultat = new JavaSetToMengde<>();

        if (this.erTom() || annenMengde.erTom()) {
            return resultat;
        }

        for (T element : elementer) {
            if (annenMengde.inneholder(element)) {
                resultat.leggTil(element);
            }
        }

        return resultat;
    }

    @Override
    public MengdeADT<T> union(MengdeADT<T> annenMengde) {
        JavaSetToMengde<T> resultat = new JavaSetToMengde<>();


        resultat.leggTilAlleFra(this);


        resultat.leggTilAlleFra(annenMengde);

        return resultat;
    }

    @Override
    public MengdeADT<T> minus(MengdeADT<T> annenMengde) {
        JavaSetToMengde<T> resultat = new JavaSetToMengde<>();

        for (T element : elementer) {
            if (!annenMengde.inneholder(element)) {
                resultat.leggTil(element);
            }
        }

        return resultat;
    }

    @Override
    public void leggTil(T element) {
        elementer.add(element);
    }

    @Override
    public void leggTilAlleFra(MengdeADT<T> annenMengde) {
        T[] elementerArray = annenMengde.tilTabell();
        for (T element : elementerArray) {
            this.leggTil(element);
        }
    }

    @Override
    public T fjern(T element) {
        if (elementer.contains(element)) {
            elementer.remove(element);
            return element;
        }
        return null;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T[] tilTabell() {
        T[] resultat = (T[]) new Object[elementer.size()];
        return elementer.toArray(resultat);
    }

    @Override
    public int antallElementer() {
        return elementer.size();
    }

    @Override
    public String toString() {
        return elementer.toString();
    }
}
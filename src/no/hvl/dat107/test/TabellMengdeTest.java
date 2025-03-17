package no.hvl.dat107.test;

import static org.junit.jupiter.api.Assertions.*;

import no.hvl.dat107.MengdeADT;
import no.hvl.dat107.TabellMengde;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TabellMengdeTest {

    private MengdeADT<String> tom;
    private MengdeADT<String> m1;
    private MengdeADT<String> m2;
    private MengdeADT<String> m3;
    private MengdeADT<String> m4;

    @BeforeEach
    public void setUp() {
        // Tom mengde
        tom = new TabellMengde<>();

        // m1 = {"A", "B", "C"}
        m1 = new TabellMengde<>();
        m1.leggTil("A");
        m1.leggTil("B");
        m1.leggTil("C");

        // m2 = {"B", "C", "D"}
        m2 = new TabellMengde<>();
        m2.leggTil("B");
        m2.leggTil("C");
        m2.leggTil("D");

        // m3 = {"E", "F"} - disjunkt med m1
        m3 = new TabellMengde<>();
        m3.leggTil("E");
        m3.leggTil("F");

        // m4 = {"A", "B", "C"} - lik m1
        m4 = new TabellMengde<>();
        m4.leggTil("A");
        m4.leggTil("B");
        m4.leggTil("C");
    }

    @Test
    public void testErTom() {
        assertTrue(tom.erTom());
        assertFalse(m1.erTom());

        // Fjern alle elementer fra m1
        m1.fjern("A");
        m1.fjern("B");
        m1.fjern("C");
        assertTrue(m1.erTom());
    }

    @Test
    public void testInneholder() {
        assertTrue(m1.inneholder("A"));
        assertTrue(m1.inneholder("B"));
        assertTrue(m1.inneholder("C"));
        assertFalse(m1.inneholder("D"));
        assertFalse(m1.inneholder("E"));
        assertFalse(m1.inneholder(null));
    }

    @Test
    public void testErDelmengdeAv() {
        // Tom mengde er delmengde av alle mengder
        assertTrue(tom.erDelmengdeAv(m1));

        // m1 = {"A", "B", "C"} er ikke delmengde av m2 = {"B", "C", "D"}
        assertFalse(m1.erDelmengdeAv(m2));

        // Legg til "A" i m2, så m1 burde være en delmengde av m2 nå
        m2.leggTil("A");
        assertTrue(m1.erDelmengdeAv(m2));

        // En mengde er alltid delmengde av seg selv
        assertTrue(m1.erDelmengdeAv(m1));
    }

    @Test
    public void testErLik() {
        // m1 og m4 inneholder samme elementer
        assertTrue(m1.erLik(m4));
        assertTrue(m4.erLik(m1));

        // m1 og m2 er ikke like
        assertFalse(m1.erLik(m2));

        // En mengde er alltid lik seg selv
        assertTrue(m1.erLik(m1));

        // To tomme mengder er like
        MengdeADT<String> tomAnnen = new TabellMengde<>();
        assertTrue(tom.erLik(tomAnnen));
    }

    @Test
    public void testErDisjunkt() {
        // m1 = {"A", "B", "C"} og m3 = {"E", "F"} er disjunkte
        assertTrue(m1.erDisjunkt(m3));
        assertTrue(m3.erDisjunkt(m1));

        // m1 og m2 er ikke disjunkte (deler B og C)
        assertFalse(m1.erDisjunkt(m2));

        // Tom mengde er disjunkt med alle mengder
        assertTrue(tom.erDisjunkt(m1));
    }

    @Test
    public void testSnitt() {
        // Snitt av m1 = {"A", "B", "C"} og m2 = {"B", "C", "D"} skal være {"B", "C"}
        MengdeADT<String> snitt = m1.snitt(m2);
        assertEquals(2, snitt.antallElementer());
        assertTrue(snitt.inneholder("B"));
        assertTrue(snitt.inneholder("C"));
        assertFalse(snitt.inneholder("A"));
        assertFalse(snitt.inneholder("D"));

        // Snitt med tom mengde skal være tom mengde
        snitt = m1.snitt(tom);
        assertTrue(snitt.erTom());

        // Snitt av disjunkte mengder skal være tom mengde
        snitt = m1.snitt(m3);
        assertTrue(snitt.erTom());
    }

    @Test
    public void testUnion() {
        // Union av m1 = {"A", "B", "C"} og m2 = {"B", "C", "D"} skal være {"A", "B", "C", "D"}
        MengdeADT<String> union = m1.union(m2);
        assertEquals(4, union.antallElementer());
        assertTrue(union.inneholder("A"));
        assertTrue(union.inneholder("B"));
        assertTrue(union.inneholder("C"));
        assertTrue(union.inneholder("D"));

        // Union med tom mengde skal være den opprinnelige mengden
        union = m1.union(tom);
        assertTrue(union.erLik(m1));

        // Union av disjunkte mengder skal ha antall elementer lik summen av antall elementer
        union = m1.union(m3);
        assertEquals(m1.antallElementer() + m3.antallElementer(), union.antallElementer());
    }

    @Test
    public void testMinus() {
        // m1 = {"A", "B", "C"} minus m2 = {"B", "C", "D"} skal være {"A"}
        MengdeADT<String> differanse = m1.minus(m2);
        assertEquals(1, differanse.antallElementer());
        assertTrue(differanse.inneholder("A"));

        // Minus tom mengde skal gi den opprinnelige mengden
        differanse = m1.minus(tom);
        assertTrue(differanse.erLik(m1));

        // Tom mengde minus en annen mengde skal gi tom mengde
        differanse = tom.minus(m1);
        assertTrue(differanse.erTom());

        // Minus disjunkt mengde skal gi den opprinnelige mengden
        differanse = m1.minus(m3);
        assertTrue(differanse.erLik(m1));

        // Mengde minus seg selv skal gi tom mengde
        differanse = m1.minus(m1);
        assertTrue(differanse.erTom());
    }

    @Test
    public void testLeggTil() {
        MengdeADT<String> mengde = new TabellMengde<>();

        // Legg til et element
        mengde.leggTil("X");
        assertEquals(1, mengde.antallElementer());
        assertTrue(mengde.inneholder("X"));

        // Legg til samme element igjen - skal ikke endres
        mengde.leggTil("X");
        assertEquals(1, mengde.antallElementer());

        // Legg til null - skal ignoreres
       // mengde.leggT
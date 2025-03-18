package no.hvl.dat107;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.util.Arrays;
import java.util.HashSet;

public class JavaSetToMengdeTest {

    private MengdeADT<String> mengde1;
    private MengdeADT<String> mengde2;
    private MengdeADT<String> tomMengde;

    @Before
    public void setUp() {
        mengde1 = new JavaSetToMengde<>();
        mengde1.leggTil("A");
        mengde1.leggTil("B");
        mengde1.leggTil("C");

        mengde2 = new JavaSetToMengde<>();
        mengde2.leggTil("C");
        mengde2.leggTil("D");
        mengde2.leggTil("E");

        tomMengde = new JavaSetToMengde<>();
    }

    @Test
    public void testKonstruktor() {
        // Test konstruktør med eksisterende Set
        HashSet<String> set = new HashSet<>(Arrays.asList("X", "Y", "Z"));
        JavaSetToMengde<String> fraMengde = new JavaSetToMengde<>(set);

        assertEquals(3, fraMengde.antallElementer());
        assertTrue(fraMengde.inneholder("X"));
        assertTrue(fraMengde.inneholder("Y"));
        assertTrue(fraMengde.inneholder("Z"));
    }

    @Test
    public void testErTom() {
        assertFalse(mengde1.erTom());
        assertTrue(tomMengde.erTom());

        // Test at fjerne alle elementer gjør mengden tom
        mengde1.fjern("A");
        mengde1.fjern("B");
        mengde1.fjern("C");
        assertTrue(mengde1.erTom());
    }

    @Test
    public void testInneholder() {
        assertTrue(mengde1.inneholder("A"));
        assertTrue(mengde1.inneholder("B"));
        assertTrue(mengde1.inneholder("C"));
        assertFalse(mengde1.inneholder("D"));
        assertFalse(tomMengde.inneholder("A"));
    }

    @Test
    public void testErDelmengdeAv() {
        MengdeADT<String> superMengde = new JavaSetToMengde<>();
        superMengde.leggTil("A");
        superMengde.leggTil("B");
        superMengde.leggTil("C");
        superMengde.leggTil("D");

        assertTrue(mengde1.erDelmengdeAv(superMengde));
        assertFalse(superMengde.erDelmengdeAv(mengde1));
        assertTrue(tomMengde.erDelmengdeAv(mengde1));
        assertTrue(mengde1.erDelmengdeAv(mengde1)); // En mengde er alltid delmengde av seg selv
    }

    @Test
    public void testErLik() {
        MengdeADT<String> kopimengde = new JavaSetToMengde<>();
        kopimengde.leggTil("A");
        kopimengde.leggTil("B");
        kopimengde.leggTil("C");

        assertTrue(mengde1.erLik(kopimengde));
        assertTrue(kopimengde.erLik(mengde1));
        assertFalse(mengde1.erLik(mengde2));
        assertTrue(tomMengde.erLik(new JavaSetToMengde<>()));

        // Test at rekkefølgen ikke betyr noe
        MengdeADT<String> annenRekkefølge = new JavaSetToMengde<>();
        annenRekkefølge.leggTil("C");
        annenRekkefølge.leggTil("A");
        annenRekkefølge.leggTil("B");
        assertTrue(mengde1.erLik(annenRekkefølge));
    }

    @Test
    public void testErDisjunkt() {
        MengdeADT<String> disjunktMengde = new JavaSetToMengde<>();
        disjunktMengde.leggTil("X");
        disjunktMengde.leggTil("Y");
        disjunktMengde.leggTil("Z");

        assertTrue(disjunktMengde.erDisjunkt(mengde1));
        assertTrue(mengde1.erDisjunkt(disjunktMengde));
        assertFalse(mengde1.erDisjunkt(mengde2)); // De har "C" felles
        assertTrue(tomMengde.erDisjunkt(mengde1));
        assertTrue(tomMengde.erDisjunkt(tomMengde));
    }

    @Test
    public void testSnitt() {
        MengdeADT<String> snitt = mengde1.snitt(mengde2);

        assertEquals(1, snitt.antallElementer());
        assertTrue(snitt.inneholder("C"));
        assertFalse(snitt.inneholder("A"));
        assertFalse(snitt.inneholder("D"));

        MengdeADT<String> tomtSnitt = mengde1.snitt(tomMengde);
        assertTrue(tomtSnitt.erTom());
    }

    @Test
    public void testUnion() {
        MengdeADT<String> union = mengde1.union(mengde2);

        assertEquals(5, union.antallElementer());
        assertTrue(union.inneholder("A"));
        assertTrue(union.inneholder("B"));
        assertTrue(union.inneholder("C"));
        assertTrue(union.inneholder("D"));
        assertTrue(union.inneholder("E"));

        MengdeADT<String> unionMedTom = mengde1.union(tomMengde);
        assertTrue(unionMedTom.erLik(mengde1));
    }

    @Test
    public void testMinus() {
        MengdeADT<String> differens = mengde1.minus(mengde2);

        assertEquals(2, differens.antallElementer());
        assertTrue(differens.inneholder("A"));
        assertTrue(differens.inneholder("B"));
        assertFalse(differens.inneholder("C")); // C er i begge mengder og skal være borte

        MengdeADT<String> minusTom = mengde1.minus(tomMengde);
        assertTrue(minusTom.erLik(mengde1));

        MengdeADT<String> tomMinus = tomMengde.minus(mengde1);
        assertTrue(tomMinus.erTom());
    }

    @Test
    public void testLeggTil() {
        // Test at duplikater ikke legges til
        int antallFør = mengde1.antallElementer();
        mengde1.leggTil("A"); // A finnes allerede
        assertEquals(antallFør, mengde1.antallElementer());

        // Test at nye elementer legges til
        mengde1.leggTil("X");
        assertEquals(antallFør + 1, mengde1.antallElementer());
        assertTrue(mengde1.inneholder("X"));
    }

    @Test
    public void testLeggTilAlleFra() {
        MengdeADT<String> målMengde = new JavaSetToMengde<>();
        målMengde.leggTil("K");

        målMengde.leggTilAlleFra(mengde1);
        assertEquals(4, målMengde.antallElementer());
        assertTrue(målMengde.inneholder("K"));
        assertTrue(målMengde.inneholder("A"));
        assertTrue(målMengde.inneholder("B"));
        assertTrue(målMengde.inneholder("C"));

        // Test at duplikater ikke legges til
        int antallFør = målMengde.antallElementer();
        målMengde.leggTilAlleFra(mengde1);
        assertEquals(antallFør, målMengde.antallElementer());
    }

    @Test
    public void testFjern() {
        assertEquals("A", mengde1.fjern("A"));
        assertEquals(2, mengde1.antallElementer());
        assertFalse(mengde1.inneholder("A"));

        // Test å fjerne element som ikke finnes
        assertNull(mengde1.fjern("X"));
        assertEquals(2, mengde1.antallElementer());

        // Test å fjerne fra tom mengde
        assertNull(tomMengde.fjern("A"));
    }

    @Test
    public void testTilTabell() {
        Object[] tabell = mengde1.tilTabell();

        assertEquals(3, tabell.length);
        // Sjekk at alle elementer er med (uavhengig av rekkefølge)
        boolean harA = false, harB = false, harC = false;
        for (Object element : tabell) {
            if ("A".equals(element)) harA = true;
            if ("B".equals(element)) harB = true;
            if ("C".equals(element)) harC = true;
        }
        assertTrue(harA && harB && harC);

        Object[] tomTabell = tomMengde.tilTabell();
        assertEquals(0, tomTabell.length);
    }

    @Test
    public void testAntallElementer() {
        assertEquals(3, mengde1.antallElementer());
        assertEquals(3, mengde2.antallElementer());
        assertEquals(0, tomMengde.antallElementer());

        mengde1.leggTil("X");
        assertEquals(4, mengde1.antallElementer());

        mengde1.fjern("X");
        assertEquals(3, mengde1.antallElementer());
    }

    @Test
    public void testToString() {
        // Test at toString gir en string-representasjon
        String str = mengde1.toString();
        assertTrue(str.contains("A"));
        assertTrue(str.contains("B"));
        assertTrue(str.contains("C"));
    }

    @Test
    public void testInteroperabilitet() {
        // Test at ulike implementasjoner fungerer sammen
        MengdeADT<String> lenketMengde = new LenketMengde<>();
        lenketMengde.leggTil("A");
        lenketMengde.leggTil("B");
        lenketMengde.leggTil("C");

        assertTrue(mengde1.erLik(lenketMengde));
        assertTrue(lenketMengde.erLik(mengde1));

        MengdeADT<String> snitt = mengde1.snitt(lenketMengde);
        assertEquals(3, snitt.antallElementer());

        MengdeADT<String> union = mengde2.union(lenketMengde);
        assertEquals(5, union.antallElementer());
    }
}
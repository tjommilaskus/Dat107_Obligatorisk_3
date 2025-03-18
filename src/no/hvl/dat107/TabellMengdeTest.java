package no.hvl.dat107;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TabellMengdeTest {

    private TabellMengde<Integer> mengde;
    private TabellMengde<Integer> annenMengde;

    @BeforeEach
    public void setUp() {
        mengde = new TabellMengde<>();
        annenMengde = new TabellMengde<>();
    }

    @Test
    public void testConstructor() {
        assertEquals(0, mengde.antallElementer());
        assertTrue(mengde.erTom());
    }

    @Test
    public void testLeggTil() {
        mengde.leggTil(1);
        assertEquals(1, mengde.antallElementer());
        assertTrue(mengde.inneholder(1));

        // Test adding null
        mengde.leggTil(null);
        assertEquals(1, mengde.antallElementer());

        // Test adding duplicate
        mengde.leggTil(1);
        assertEquals(1, mengde.antallElementer());
    }

    @Test
    public void testLeggTilAlleFra() {
        mengde.leggTil(1);
        mengde.leggTil(2);

        annenMengde.leggTil(3);
        annenMengde.leggTil(4);

        mengde.leggTilAlleFra(annenMengde);

        assertEquals(4, mengde.antallElementer());
        assertTrue(mengde.inneholder(1));
        assertTrue(mengde.inneholder(2));
        assertTrue(mengde.inneholder(3));
        assertTrue(mengde.inneholder(4));
    }

    @Test
    public void testFjern() {
        mengde.leggTil(1);
        mengde.leggTil(2);
        mengde.leggTil(3);

        Integer removed = mengde.fjern(2);

        assertEquals(Integer.valueOf(2), removed);
        assertEquals(2, mengde.antallElementer());
        assertFalse(mengde.inneholder(2));
        assertTrue(mengde.inneholder(1));
        assertTrue(mengde.inneholder(3));

        // Test removing null
        assertNull(mengde.fjern(null));

        // Test removing non-existent element
        assertNull(mengde.fjern(4));
    }

    @Test
    public void testErTom() {
        assertTrue(mengde.erTom());

        mengde.leggTil(1);
        assertFalse(mengde.erTom());

        mengde.fjern(1);
        assertTrue(mengde.erTom());
    }

    @Test
    public void testInneholder() {
        assertFalse(mengde.inneholder(1));

        mengde.leggTil(1);
        assertTrue(mengde.inneholder(1));

        mengde.fjern(1);
        assertFalse(mengde.inneholder(1));


        assertFalse(mengde.inneholder(null));
    }

    @Test
    public void testErDelmengdeAv() {
        mengde.leggTil(1);
        mengde.leggTil(2);

        annenMengde.leggTil(1);
        annenMengde.leggTil(2);
        annenMengde.leggTil(3);

        assertTrue(mengde.erDelmengdeAv(annenMengde));
        assertFalse(annenMengde.erDelmengdeAv(mengde));


        TabellMengde<Integer> tomMengde = new TabellMengde<>();
        assertTrue(tomMengde.erDelmengdeAv(mengde));
    }

    @Test
    public void testErLik() {
        mengde.leggTil(1);
        mengde.leggTil(2);

        annenMengde.leggTil(1);
        annenMengde.leggTil(2);

        assertTrue(mengde.erLik(annenMengde));
        assertTrue(annenMengde.erLik(mengde));


        annenMengde.leggTil(3);
        assertFalse(mengde.erLik(annenMengde));
        assertFalse(annenMengde.erLik(mengde));
    }

    @Test
    public void testErDisjunkt() {
        mengde.leggTil(1);
        mengde.leggTil(2);

        annenMengde.leggTil(3);
        annenMengde.leggTil(4);

        assertTrue(mengde.erDisjunkt(annenMengde));


        annenMengde.leggTil(1);
        assertFalse(mengde.erDisjunkt(annenMengde));
    }

    @Test
    public void testSnitt() {
        mengde.leggTil(1);
        mengde.leggTil(2);
        mengde.leggTil(3);

        annenMengde.leggTil(2);
        annenMengde.leggTil(3);
        annenMengde.leggTil(4);

        MengdeADT<Integer> snittMengde = mengde.snitt(annenMengde);

        assertEquals(2, snittMengde.antallElementer());
        assertTrue(snittMengde.inneholder(2));
        assertTrue(snittMengde.inneholder(3));
        assertFalse(snittMengde.inneholder(1));
        assertFalse(snittMengde.inneholder(4));
    }

    @Test
    public void testUnion() {
        mengde.leggTil(1);
        mengde.leggTil(2);

        annenMengde.leggTil(2);
        annenMengde.leggTil(3);

        MengdeADT<Integer> unionMengde = mengde.union(annenMengde);

        assertEquals(3, unionMengde.antallElementer());
        assertTrue(unionMengde.inneholder(1));
        assertTrue(unionMengde.inneholder(2));
        assertTrue(unionMengde.inneholder(3));
    }

    @Test
    public void testMinus() {
        mengde.leggTil(1);
        mengde.leggTil(2);
        mengde.leggTil(3);

        annenMengde.leggTil(2);
        annenMengde.leggTil(4);

        MengdeADT<Integer> differanseMengde = mengde.minus(annenMengde);

        assertEquals(2, differanseMengde.antallElementer());
        assertTrue(differanseMengde.inneholder(1));
        assertTrue(differanseMengde.inneholder(3));
        assertFalse(differanseMengde.inneholder(2));
    }

    @Test
    public void testTilTabell() {
        mengde.leggTil(1);
        mengde.leggTil(2);
        mengde.leggTil(3);

        Object[] tabell = mengde.tilTabell();

        assertEquals(3, tabell.length);



        boolean found1 = false, found2 = false, found3 = false;
        for (Object element : tabell) {
            if (Integer.valueOf(1).equals(element)) found1 = true;
            if (Integer.valueOf(2).equals(element)) found2 = true;
            if (Integer.valueOf(3).equals(element)) found3 = true;
        }

        assertTrue(found1 && found2 && found3);
    }

    @Test
    public void testAntallElementer() {
        assertEquals(0, mengde.antallElementer());

        mengde.leggTil(1);
        assertEquals(1, mengde.antallElementer());

        mengde.leggTil(2);
        assertEquals(2, mengde.antallElementer());

        mengde.fjern(1);
        assertEquals(1, mengde.antallElementer());
    }

    @Test
    public void testUtvidKapasitet() {

        TabellMengde<Integer> litenMengde = new TabellMengde<>(3);

        litenMengde.leggTil(1);
        litenMengde.leggTil(2);
        litenMengde.leggTil(3);


        litenMengde.leggTil(4);

        assertEquals(4, litenMengde.antallElementer());
        assertTrue(litenMengde.inneholder(4));
    }

    @Test
    public void testToString() {
        assertEquals("[]", mengde.toString());

        mengde.leggTil(1);
        assertEquals("[1]", mengde.toString());

        mengde.leggTil(2);


        String result = mengde.toString();
        assertTrue("[1, 2]".equals(result) || "[2, 1]".equals(result));
    }

    @Test
    public void testWithString() {

        TabellMengde<String> stringMengde = new TabellMengde<>();

        stringMengde.leggTil("A");
        stringMengde.leggTil("B");

        assertEquals(2, stringMengde.antallElementer());
        assertTrue(stringMengde.inneholder("A"));
        assertTrue(stringMengde.inneholder("B"));
    }
}
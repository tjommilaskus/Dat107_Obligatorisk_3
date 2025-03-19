package no.hvl.dat107;

public class Main {
    private static final int ANTALL_MÅLINGER = 10;

    public static void main(String[] args) {
        TabellMengde<Integer> tabellMengde = new TabellMengde<>();
        tabellMengde.leggTil(1);
        tabellMengde.leggTil(2);
        tabellMengde.leggTil(3);
        tabellMengde.leggTil(4);

        LenketMengde<Integer> lenketMengde = new LenketMengde<>();
        lenketMengde.leggTil(5);
        lenketMengde.leggTil(6);
        lenketMengde.leggTil(7);
        lenketMengde.leggTil(8);

        // Skriver ut mengdene
        System.out.println("TabellMengde: " + tabellMengde);
        System.out.println("LenketMengde: " + lenketMengde);

        // Måler kjøretid for inneholder-metoden på TabellMengde
        double tidInneholderTabell = målGjennomsnittstid(() -> tabellMengde.inneholder(2));
        System.out.println("TabellMengde inneholder(2): " + tidInneholderTabell + " ns");

        // Måler kjøretid for inneholder-metoden på LenketMengde
        double tidInneholderLenket = målGjennomsnittstid(() -> lenketMengde.inneholder(6));
        System.out.println("LenketMengde inneholder(6): " + tidInneholderLenket + " ns");

        // Måler kjøretid for erDelmengdeAv-metoden (tabellMengde delmengde av lenketMengde)
        double tidDelmengdeTabell = målGjennomsnittstid(() -> tabellMengde.erDelmengdeAv(lenketMengde));
        System.out.println("TabellMengde er delmengde av LenketMengde: " + tidDelmengdeTabell + " ns");

        // Måler kjøretid for erDelmengdeAv-metoden (lenketMengde delmengde av tabellMengde)
        double tidDelmengdeLenket = målGjennomsnittstid(() -> lenketMengde.erDelmengdeAv(tabellMengde));
        System.out.println("LenketMengde er delmengde av TabellMengde: " + tidDelmengdeLenket + " ns");
    }


    private static double målGjennomsnittstid(Runnable metode) {
        long totalTid = 0;

        // Kjører målingen flere ganger
        for (int i = 0; i < ANTALL_MÅLINGER; i++) {
            long start = System.nanoTime();
            metode.run();
            long end = System.nanoTime();
            totalTid += (end - start);
        }

        // Returner gjennomsnittlig kjøretid
        return (double) totalTid / ANTALL_MÅLINGER;
    }
}

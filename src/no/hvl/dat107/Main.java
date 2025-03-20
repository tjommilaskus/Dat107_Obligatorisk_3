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


        System.out.println("TabellMengde: " + tabellMengde);
        System.out.println("LenketMengde: " + lenketMengde);

        double tidInneholderTabell = målGjennomsnittstid(() -> tabellMengde.inneholder(2));
        System.out.println("TabellMengde inneholder(2): " + tidInneholderTabell + " ns");

        double tidInneholderLenket = målGjennomsnittstid(() -> lenketMengde.inneholder(6));
        System.out.println("LenketMengde inneholder(6): " + tidInneholderLenket + " ns");

        double tidLikTabell = målGjennomsnittstid(() -> tabellMengde.erLik(lenketMengde));
        System.out.println("TabellMengde er Lik(lenketMengde): " + tidLikTabell + " ns");

        double tidLikLenket = målGjennomsnittstid(() -> lenketMengde.erLik(tabellMengde));
        System.out.println("LenketMengde er Lik(tabellMengde): " + tidLikLenket + " ns");

        double tidDelmengdeTabell = målGjennomsnittstid(() -> tabellMengde.erDelmengdeAv(lenketMengde));
        System.out.println("TabellMengde er delmengde av LenketMengde: " + tidDelmengdeTabell + " ns");

        double tidDelmengdeLenket = målGjennomsnittstid(() -> lenketMengde.erDelmengdeAv(tabellMengde));
        System.out.println("LenketMengde er delmengde av TabellMengde: " + tidDelmengdeLenket + " ns");

        double tidFjernTabell = målGjennomsnittstid(() -> tabellMengde.fjern(3));
        System.out.println("Fjern fra TabellMengde: " + tidFjernTabell + " ns");

        double tidFjernLenket = målGjennomsnittstid(() -> lenketMengde.fjern(6));
        System.out.println("Fjern fra LenketMengde: " + tidFjernLenket + " ns");
    }


    private static double målGjennomsnittstid(Runnable metode) {
        long totalTid = 0;


        for (int i = 0; i < ANTALL_MÅLINGER; i++) {
            long start = System.nanoTime();
            metode.run();
            long end = System.nanoTime();
            totalTid += (end - start);
        }


        return (double) totalTid / ANTALL_MÅLINGER;
    }
}

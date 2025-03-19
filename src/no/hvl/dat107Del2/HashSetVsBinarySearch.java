package no.hvl.dat107Del2;

import java.util.HashSet;
import java.util.Arrays;
import java.util.Random;

public class HashSetVsBinarySearch {
    private static final int antallElement = 100_000;
    private static final int søkAntall = 10_000;
    private static final int maxVerdi = 1_000_000;

   public static void main(String[] args) {
       Integer[] tabell = new Integer[antallElement];
       HashSet<Integer> hs = new HashSet<>(antallElement);


       int tall = 376;
       int steg = 45713;
        for (int i = 0; i < antallElement; i++) {
            tabell[i] = tall;
            hs.add(tall);
            tall = (tall + steg) % maxVerdi;
        }
       System.out.println("HashSet: " + hs.size());

       Arrays.sort(tabell);

       Random rand = new Random();
       Integer[] søketall = new Integer[søkAntall];
       for(int i = 0; i < søkAntall; i++){
           søketall[i] = rand.nextInt(maxVerdi);
       }
       long startTid = System.nanoTime();
       int funnetIHs = 0;
       for (int i = 0; i < søkAntall; i++) {
           if (hs.contains(søketall[i])) {
               funnetIHs++;
           }
       }
       long hashSetTid = System.nanoTime() - startTid;

       startTid = System.nanoTime();
       int funnetIArray = 0;
       for (int i = 0; i < søkAntall; i++) {
           if (Arrays.binarySearch(tabell, søketall[i]) >= 0) {
               funnetIArray++;
           }
       }
       long binerySearchTid = System.nanoTime() - startTid;

       System.out.println("funnetIHs: " + funnetIHs);
       System.out.println("HashSetTid: " + hashSetTid + "ns / " + hashSetTid / 1_000_000 + "ms");

       System.out.println("funnetIArray: " + funnetIArray);
       System.out.println("binerySearchTid: " + binerySearchTid + "ns / " + binerySearchTid / 1_000_000 + "ms");
    }
}

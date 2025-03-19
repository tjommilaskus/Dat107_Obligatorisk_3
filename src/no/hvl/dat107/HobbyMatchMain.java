package no.hvl.dat107;

import java.util.HashSet;

public class HobbyMatchMain {
    public static double match(Person<String> a, Person<String> b) {
        HashSet<String> felles = new HashSet<>(a.getHobbyer());
        felles.retainAll(b.getHobbyer());

        HashSet<String> KunHosA = new HashSet<>(a.getHobbyer());
        KunHosA.removeAll(b.getHobbyer());

        HashSet<String> KunHosB = new HashSet<>(b.getHobbyer());
        KunHosB.removeAll(a.getHobbyer());

        int antallFelles = felles.size();
        int antallKunHosEn = KunHosA.size() + KunHosB.size();
        int antallTotalt = antallFelles + antallKunHosEn;

        return (double) (antallFelles - antallKunHosEn) / antallTotalt;
    }

    public static void main(String[] args) {
        Person<String> John = new Person<>("John", "Sykling", "Fotball");
        Person<String> Per = new Person<>("Per", "Matlaging", "Fotball", "løping");
        Person<String> Morten = new Person<>("Morten", "Fridrett", "Maling", "Gaming");

        double matchJohnPer = match(John, Per);
        double matchJohnMorten = match(John, Morten);
        double matchMortenPer = match(Morten, Per);


        System.out.println("Match John og Per: " + matchJohnPer);
        System.out.println("Match John og Morten: " + matchJohnMorten);
        System.out.println("Match Morten og Per: " + matchMortenPer);

        double bestMatch = Math.max(matchJohnPer, Math.max(matchJohnMorten, matchMortenPer));

        if (bestMatch == matchJohnPer) {
            System.out.println("Best match er John og Per: " + bestMatch);
        } else if (bestMatch == matchJohnMorten) {
            System.out.println("Best match er John og Morten: " + bestMatch);
        } else {
            System.out.println("Best match er Morten og Per: " + bestMatch);
        }
    }
}
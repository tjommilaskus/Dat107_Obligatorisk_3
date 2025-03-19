package no.hvl.dat107;

import java.util.Set;
import java.util.HashSet;

public class Person<T> {
    private String navn;
    private Set<T> hobbyer;

    public Person(String navn, T... hobbyer) {
        this.navn = navn;
        this.hobbyer = new HashSet<>();
        for(T hobby :  hobbyer) {
            this.hobbyer.add(hobby);
        }
    }

    public Set<T> getHobbyer() {
        return hobbyer;
    }

    public String getNavn() {
        return navn;
    }
}
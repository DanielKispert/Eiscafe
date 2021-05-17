package eiscafe.objects;

import java.util.concurrent.ThreadLocalRandom;

public class Kellner {

    private boolean istBeschaeftigt;

    private final String name;

    public Kellner(String name) {
        this.istBeschaeftigt = false;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public boolean istBeschaeftigt() {
        return istBeschaeftigt;
    }

    /**
     * der Kellner bearbeitet eine Bestellung, dies dauert 3 bis 8 Minuten
     */
    public void bestellungBearbeiten(int gastID) {
        long startTime = System.currentTimeMillis();
        System.out.println(name + " bearbeitet die Bestellung von Gast " + gastID);
        this.istBeschaeftigt = true;
        // warte zwischen 3000 und 8000 Millisekunden (3 bis 8 Minuten in der Simulation)
        try {
            Thread.sleep(ThreadLocalRandom.current().nextInt(3000, 8001));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.istBeschaeftigt = false;
        System.out.println(name + " ist fertig mit der Bestellung von Gast " + gastID + ". Es dauerte " + (System.currentTimeMillis() - startTime) / 1000.0 + " Minuten");
    }
}

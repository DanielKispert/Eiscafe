package eiscafe.objects;

import java.util.concurrent.ThreadLocalRandom;

public class Gast extends Thread {

    private final int id;

    // speichert, welches Cafe der Gast besuchen will
    private Eiscafe cafe;

    public Gast(Eiscafe cafe, int id) {
        this.id = id;
        this.cafe = cafe;
    }

    @Override
    public void run() {
        long startTime = System.currentTimeMillis();
        // Cafe betreten
        cafe.eintreten(id);
        // Gast studiert die Karte, warte zwischen 1000 und 2000 Millisekunden (1 bis 2 Minuten in der Simulation)
        try {
            Thread.sleep(ThreadLocalRandom.current().nextInt(1000, 2001));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        cafe.kellnerRufen(id).bestellungBearbeiten(id);
        cafe.eisNehmen(id);
        // Gast isst Eis
        try {
            // warte 3 Sekunden (3 Minuten in der Simulation)
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        cafe.aufstehen();
        System.out.println("Gast " + id + " hat fertig gegessen und geht nun. Aufenthaltsdauer (inkl. warten): " + (System.currentTimeMillis() - startTime) / 1000.0 + " Minuten");
    }
}

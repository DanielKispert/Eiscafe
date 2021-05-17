package eiscafe;

import eiscafe.objects.Eiscafe;
import eiscafe.objects.Gast;

import java.util.concurrent.ThreadLocalRandom;

// Grundgedanke: Gäste sind die Threads, die versuchen auf Kellner und Sitzplätze (=Ressourcen) zuzugreifen
public class Simulation {

    public static void main(String[] args) {

        // die Simulation läuft für 8 Ticks, es werden also 8 Gruppen aus 3 bis 5 Besuchern im Abstand von 5 Sekunden
        // = 5 Minuten in der Simulation erzeugt
        int simulationsDauer = 8;
        int gastID = 0;

        Eiscafe cafe = new Eiscafe();

        for (int zeit = 0; zeit < simulationsDauer; zeit++) {
            // zwischen 3 und 5 Gäste betreten das Eiscafe
            int anzahlBesucher = ThreadLocalRandom.current().nextInt(3, 6);
            for (int i = 0; i < anzahlBesucher; i++) {
                // ein neuer Gast wird erzeugt und versucht, das Eiscafe zu betreten
                new Gast(cafe, ++gastID).start();
            }
            // warte 5 Sekunden (5 Minuten in der Simulation)
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }



}

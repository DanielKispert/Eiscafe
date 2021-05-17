package eiscafe.objects;

import java.util.HashSet;
import java.util.Set;

public class Eiscafe {

    private Set<Integer> aufSitzplatzWartendeGaeste;

    private Set<Integer> aufKellnerWartendeGaeste;

    private Kellner[] kellner;

    private int freieSitze;

    public Eiscafe() {
        this.aufSitzplatzWartendeGaeste = new HashSet<>();
        this.aufKellnerWartendeGaeste = new HashSet<>();
        this.freieSitze = 6;
        this.kellner = new Kellner[3];
        this.kellner[0] = new Kellner("Tom");
        this.kellner[1] = new Kellner("Susi");
        this.kellner[2] = new Kellner("Alex");
    }

    /**
     * ein Gast versucht, das Eiscafe zu betreten und sich zu setzen. Falls kein Platz ist, wartet der Gast.
     * Das Warten erfolgt nicht in einer Schlange, sondern alle Wartenden stürzen sich auf verfügbare Plätze.
     */
    public synchronized void eintreten(int gastID) {
        long startTime = System.currentTimeMillis();
        System.out.println("Gast " + gastID + " möchte das Cafe betreten. Aktuell warten " + aufSitzplatzWartendeGaeste.size() + " Gäste auf einen Platz.");
        while(freieSitze == 0) {
            try {
                aufSitzplatzWartendeGaeste.add(gastID);
                wait();
            } catch (InterruptedException e) {

            }
        }
        aufSitzplatzWartendeGaeste.remove(gastID);
        freieSitze--;
        System.out.println("Gast " + gastID + " hat einen Sitzplatz nach " + (System.currentTimeMillis() - startTime) / 1000.0 + " Minuten bekommen. Noch " + freieSitze + " Sitze frei.");
    }

    public synchronized Kellner kellnerRufen(int gastID) {
        long startTime = System.currentTimeMillis();
        System.out.println("Gast " + gastID + " hat die Karte fertig studiert und möchte nun bestellen. Aktuell warten " + aufKellnerWartendeGaeste.size() + " Gäste auf einen Kellner.");
        while(getNaechstenFreienKellner() == null) {
            try {
                aufKellnerWartendeGaeste.add(gastID);
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        aufKellnerWartendeGaeste.remove(gastID);
        System.out.println("Gast " + gastID + " konnte nach " + (System.currentTimeMillis() - startTime) / 1000.0 + " Minuten bestellen.");
        return getNaechstenFreienKellner();
    }

    public synchronized void eisNehmen(int gastID) {
        notify();
        System.out.println("Gast " + gastID + " beginnt, Eis zu essen");
    }

    /**
     * Hilfsmethode, die den nächsten freien Kellner zurückgibt, oder null, wenn kein Kellner frei ist.
     */
    private Kellner getNaechstenFreienKellner() {
        for (Kellner kellner: kellner) {
            if (!kellner.istBeschaeftigt()) {
                return kellner;
            }
        }
        return null;
    }

    /**
     * ein Gast verlässt das Eiscafe, ein Sitz wird frei
     */
    public synchronized void aufstehen() {
        notify();
        freieSitze++;
        System.out.println("wieder " + freieSitze + " Sitze frei.");
    }





}

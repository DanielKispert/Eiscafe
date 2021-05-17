package eiscafe.objects;

import java.util.ArrayList;
import java.util.List;

public class Eiscafe {

    private List<Gast> warteschlange;

    private Kellner[] kellner;

    private int freieSitze;

    public Eiscafe() {
        this.warteschlange = new ArrayList<>();
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
    public synchronized void eintreten() {
        while(freieSitze == 0) {
            try {
                wait();
            } catch (InterruptedException e) {

            }
        }
        freieSitze--;
        System.out.println("noch " + freieSitze + " Sitze frei.");
    }

    public synchronized Kellner kellnerRufen() {
        while(getNaechstenFreienKellner() == null) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return getNaechstenFreienKellner();
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
        freieSitze++;
        notify();
        System.out.println("wieder " + freieSitze + " Sitze frei.");
    }





}

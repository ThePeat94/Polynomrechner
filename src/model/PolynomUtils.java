package model;
import java.util.ArrayList;

/**
 * Eine Hilfsklasse, die Methoden zur Arbeit mit Polynomen zur Verfügung stellt
 */
public class PolynomUtils {

    /**
     * Sortiert alle Glieder einer gegebenen Arraylist nach ihrem Exponenten
     * @param gliederZumSortieren Die zu sortierenden Glieder
     * @param groessterExponent Der größte Exponent, der im Array vorkommt
     * @return Ein zweidimensionales Array, in welchem die Glieder nach ihrem Grad sortiert sind
     */
    public static ArrayList<ArrayList<Glied>> sortiereGlieder(ArrayList<Glied> gliederZumSortieren, int groessterExponent)
    {
        ArrayList<ArrayList<Glied>> sortiert = new ArrayList<ArrayList<Glied>>();

        // Jeder Dimension eine Liste hinzufügen
        for(int i = 0; i <= groessterExponent; i++)
        {
            sortiert.add(new ArrayList<Glied>());
        }

        // Einsortieren der Glieder in die entsprechende Dimension
        for (Glied aktuellesGlied : gliederZumSortieren) {
            sortiert.get(aktuellesGlied.getExponent()).add(aktuellesGlied);
        }
        return sortiert;
    }

    /**
     * Fasst alle Glieder einer zweidimensionalen Liste zusammen
     * @param zusammenfassen Die zweidimensionale Liste, mit zu zusammenfassenden Gliedern
     * @return Eindimensionales Array mit den zusammengefassten Gliedern
     */
    public static Glied[] gliederZusammenfassen(ArrayList<ArrayList<Glied>> zusammenfassen)
    {
        Glied[] zusammengefassteGlieder = new Glied[zusammenfassen.size()];


        for(int i = 0; i < zusammenfassen.size(); i++)
        {
            int koeffizient = 0;

            // Berechnen des Koeffizienten
            for(int j = 0; j < zusammenfassen.get(i).size(); j++)
            {
                koeffizient += zusammenfassen.get(i).get(j).getKoeffizient();
            }

            zusammengefassteGlieder[i] = new Glied(koeffizient, i);
        }
        return zusammengefassteGlieder;
    }
}

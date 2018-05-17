package model;

/**
 * Ein {@link Polynom} besteht aus mehreren Gliedern. Das Glied mit dem höchsten Exponent legt den Grad eines Polynoms fest. <br>
 * f(x) = 3x^5 + 2x² + 1 (Polynom des 5. Grads)<br>
 *
 * @author 563176 - Hendrik Rakemann
 */
public class Glied
{

    /**
     * Ein Nullglied, welches den Koeffizienten 0 und den Exponenten 0 hat. Wird zum Auffüllen von arithmetischen Operationen genutzt oder wenn ein Glied eines Polynoms abgefragt wird, zu dessen Grad kein Glied existiert.
     */
    public final static Glied NULLGLIED = new Glied(0,0);


    /**
     * Der Exponent/Grad des Glieds
     */
    private int exponent;

    /**
     * Der Faktor der x-Variable
     */
    private double koeffizient;

    /**
     * Initialisiert ein Glied
     * @param koeffizient Der Koeffizient
     * @param exponent Der Exponent
     */
    public Glied(double koeffizient, int exponent)
    {
        this.exponent = exponent;
        this.koeffizient = koeffizient;
    }

    @Override
    public String toString()
    {
        if(exponent == 0)
            return String.format("%+.2f", koeffizient);

        if(exponent == 1)
            return String.format("%+.2fx", koeffizient);

        return String.format("%+.2fx", koeffizient) + Utils.numberToSuperscriptedString(exponent);
    }

    /**
     * Getter für den Koeffizienten
     * @return Koeffizienten des Gliedes
     */
    public double getKoeffizient()
    {
        return koeffizient;
    }

    /**
     * Setter für den den Koeffizienten
     * @param koeffizient Der neue Wert des Koeffizienten
     */
    public void setKoeffizient(float koeffizient)
    {
        this.koeffizient = koeffizient;
    }

    /**
     * Getter für den Exponenten
     * @return Exponent des Glieds
     */
    public int getExponent()
    {
        return exponent;
    }

    /**
     * Setter für den Exponenten
     * @param exponent Der neue Wert des Exponenten
     */
    public void setExponent(int exponent)
    {
        this.exponent = exponent;
    }

    /**
     * Berechnet den Wert für ein gegebenes X
     * @param x x-Stelle
     * @return Wert an der gegebenen x-Stelle
     */
    public double wertFuerX(double x)
    {
        return koeffizient * Math.pow(x, exponent);
    }

    /**
     * Bildet die Ableitung dieses Glieds
     * @return Ableitung
     */
    public Glied ersteAbleitung()
    {
        return new Glied(koeffizient * exponent, exponent - 1);
    }
}

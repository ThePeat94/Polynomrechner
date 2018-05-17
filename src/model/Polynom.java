package model;

import java.util.ArrayList;

/**
 * Ein Polynom besteht aus mehreren Gliedern. Das {@link Glied} mit dem höchsten Exponent legt den Grad eines Polynoms fest.
 */
public class Polynom
{

    /**
     * Die einzelnen Glieder des Polynoms.
     * Die Anzahl der Elemente des Arrays entspricht dem Grad + 1.
     * Der Grad des Glieds entspricht dem Index des Elements im Array.
     * So ist ein Glied des 0. Grades das Element mit dem Index 0 und ein Glied des 23. Grades das Element mit dem Index 23.
     */
    private Glied[] glieder;

    /**
     * Der Grad des Polynoms.
     */
    private int grad;

    /**
     * Initialisiert ein Polynom mithilfe eines Grades. Initialisiert anhand dessen das Glieder-Array
     * @param grad Der Grad des Polynoms
     */
    public Polynom(int grad)
    {
        glieder = new Glied[grad+1];
        this.grad = grad;
    }

    /**
     * Initialisiert ein Polynom mit gegebenen sortierten und zusammengefassten Gliedern
     * @param glieder Die zu setzenden GLieder
     */
    public Polynom(Glied[] glieder)
    {
        setGlieder(glieder);
    }

    /**
     * Gibt das Polynom auf der Konsole aus.
     */
    public void Ausgabe()
    {
        System.out.println("f(x) = " + toString());
    }

    /**
     * Wandelt das Polynom in eine lesbare Form um.
     * @return String, welcher das Polynom darstellt.
     */
    @Override
    public String toString()
    {
        String s = new String();
        for(int i = glieder.length - 1; i >= 0; i--)
        {
            if(glieder[i].getKoeffizient() != 0)
            {
                s += glieder[i].toString();
            }
        }

        if(s.length() > 0)
            return s;
        else
            return "0 (Nullfunktion)";
    }

    /**
     * Berechnet den Funktionswert an Stelle x.
     * @param x x-Stelle
     * @return Den Funktionswert an Stelle X
     */
    public double wertAnStelle(double x)
    {
        double wert = 0;
        for(Glied glied : glieder)
        {
            if(glied.getKoeffizient() != 0)
                wert += glied.wertFuerX(x);
        }
        return wert;
    }

    /**
     * Bildet die erste Ableitung des Polynoms.
     * @return Erste Ableitung
     */
    public Polynom ersteAbleitung()
    {
        Polynom ableitung = new Polynom(grad - 1);
        for(Glied glied : glieder)
        {
            if(glied.getExponent() == 0)
                continue;

            ableitung.setGlied(glied.ersteAbleitung());
        }
        return ableitung;

    }

    /**
     * Setzt ein Glied an der entsprechenden Stelle des Polynoms.
     * @param neuesGlied Das Glied, welches gesetzt werden soll.
     */
    public void setGlied(Glied neuesGlied)
    {
        if(neuesGlied.getExponent() <= glieder.length)
            glieder[neuesGlied.getExponent()] = neuesGlied;
    }

    /**
     * Setzt alle Glieder und ermittelt den entsprechenden Grad
     * @param neueGlieder Die zu setzenden Glieder
     */
    public void setGlieder(Glied[] neueGlieder)
    {
        grad = 0;
        for(int i = 0; i < neueGlieder.length; i++)
        {
            if(neueGlieder[i].getKoeffizient() != 0)
            {
                grad = i;
            }
        }

        glieder = new Glied[grad + 1];
        System.arraycopy(neueGlieder, 0, glieder, 0, grad + 1);
    }

    /**
     * Gibt das Glied des angegebenen Grads zurück.
     * @param gliedGrad Der Grad des zu ermittelnden Glieds
     * @return Das Glied mit dem angegebenen Grad
     */
    public Glied getGlied(int gliedGrad)
    {
        if(gliedGrad <= grad)
            return glieder[gliedGrad];
        else
            return Glied.NULLGLIED;
    }

    /**
     * Getter für die Glieder des Polynoms
     * @return Die Glieder des Polynoms
     */
    public Glied[] getGlieder() {
        return glieder;
    }

    /**
     * Getter für den Grad
     * @return Den Grad des Polynoms
     */
    public int getGrad()
    {
        return grad;
    }

    /**
     * Bildet die Summe von zwei Polynomen.
     * @param b Summand
     * @return Summe aus a + b
     */
    public Polynom summeMit(Polynom b)
    {
        // Hat b keine Elemente, muss nicht gerechnet werden, es ändert nichts
        if(b.glieder.length == 0)
        {
            return this;
        }

        Glied[] a;

        // Wenn b mehr Glieder als dieses Polynom hat, so müssen noch Nullglieder hinzugefügt werden
        if(b.glieder.length > glieder.length)
        {
            a = new Glied[b.grad + 1];
            System.arraycopy(glieder, 0, a, 0, glieder.length);
            for(int i = grad + 1; i < a.length; i++)
            {
                a[i] = Glied.NULLGLIED;
            }
        }
        else
        {
            a = glieder;
        }

        Polynom summe = new Polynom(a.length - 1);

        for(int aktuellerGrad = 0; aktuellerGrad < a.length; aktuellerGrad++)
        {
            Glied aGlied = a[aktuellerGrad];
            Glied bGlied;

            // Sollte der aktuelle Grad größer sein, als b Elemente hat, so muss mit einem Nullglied gerechnet werden
            if(aktuellerGrad >= b.glieder.length)
            {
                bGlied = Glied.NULLGLIED;
            }
            else
            {
                bGlied = b.glieder[aktuellerGrad];
            }

            double aKoeffizient = aGlied.getKoeffizient();
            double bKoeffizient = bGlied.getKoeffizient();


            Glied gliedErgebnis = new Glied(aKoeffizient + bKoeffizient, aktuellerGrad);
            summe.setGlied(gliedErgebnis);
        }


        return summe;
    }

    /**
     * Bildet die Differenz zum angegebenen Polynom.
     * @param b Subtrahend
     * @return Das Ergebnispolynom aus a - b
     */
    public Polynom differenzMit(Polynom b)
    {
        // Hat b keine Elemente, muss nicht gerechnet werden, es ändert nichts
        if(b.glieder.length == 0)
        {
            return this;
        }

        Glied[] a;

        // Wenn b mehr Glieder als dieses Polynom hat, so müssen noch Nullglieder hinzugefügt werden
        if(b.glieder.length > glieder.length)
        {
            a = new Glied[b.grad + 1];
            System.arraycopy(glieder, 0, a, 0, glieder.length);
            for(int i = grad + 1; i < a.length; i++)
            {
                a[i] = Glied.NULLGLIED;
            }
        }
        else
        {
            a = glieder;
        }

        Polynom differenz = new Polynom(a.length - 1);

        for(int aktuellerGrad = 0; aktuellerGrad < a.length; aktuellerGrad++)
        {
            Glied aGlied = a[aktuellerGrad];
            Glied bGlied;

            // Sollte der aktuelle Grad größer sein, als b Elemente hat, so muss mit einem Nullglied gerechnet werden
            if(aktuellerGrad >= b.glieder.length)
            {
                bGlied = Glied.NULLGLIED;
            }
            else
            {
                bGlied = b.glieder[aktuellerGrad];
            }

            double aKoeffizient = aGlied.getKoeffizient();
            double bKoeffizient = bGlied.getKoeffizient();


            Glied gliedErgebnis = new Glied(aKoeffizient - bKoeffizient, aktuellerGrad);
            differenz.setGlied(gliedErgebnis);
        }

        return differenz;
    }

    /**
     * Multipliziert dieses Polynom mit einem anderen Polynom
     * @param bPolynom Der Multiplikand
     * @return Das Ergebnispolynom aus a * b
     */
    public Polynom multipliziereMit(Polynom bPolynom)
    {
        ArrayList<Glied> neueGlieder = new ArrayList<>();
        int groessterExponent = 0;

        if(bPolynom.glieder.length == 0)
        {
            return new Polynom(0);
        }

        // Jedes Glied von a muss mit jedem Glied von b multipliziert werden
        for(Glied aGlied : glieder)
        {
            for(Glied bGlied : bPolynom.glieder)
            {
                double neuerKoeffizient = aGlied.getKoeffizient() * bGlied.getKoeffizient();

                // Wenn das Ergebnis der Koeffizienten null ist, so können wir dieses Glied ignorieren
                if(neuerKoeffizient != 0)
                {
                    int neuerExponent = aGlied.getExponent() + bGlied.getExponent();

                    groessterExponent = (neuerExponent > groessterExponent ? neuerExponent : groessterExponent);

                    Glied neuesGlied = new Glied(neuerKoeffizient, neuerExponent);
                    neueGlieder.add(neuesGlied);
                }
            }
        }

        // Sortieren der Glieder nach ihrem Exponenten
        ArrayList<ArrayList<Glied>> sortiert = PolynomUtils.sortiereGlieder(neueGlieder, groessterExponent);

        // Zusammenfassen der sortierten Glieder
        Glied[] zusammengefasst = PolynomUtils.gliederZusammenfassen(sortiert);
        return new Polynom(zusammengefasst);
    }

    /**
     * Wendet die Funktionswertberechnung nach Horner auf das Polynom an
     * @param x Die x-Stelle für die der Wert berechnet werden soll
     * @return Den Funktionswert an der x-Stelle
     */
    public double hornerschemaFuerX(double x)
    {
        return hornerTabelle(x)[0];
    }

    /**
     * Führt nach dem Hornerschema eine Polynomdivision aus
     * @param x Der x-Wert für die Polynomdivision
     * @return Die Funktion als String
     */
    public String HornerDivision(double x)
    {
        double[] hornerTabelle = hornerTabelle(x);
        Glied[]  hornerGlieder = new Glied[hornerTabelle.length - 1];

        for(int i = 1; i < hornerTabelle.length; i++)
        {
            hornerGlieder[i - 1] = new Glied(hornerTabelle[i], i - 1);
        }

        Polynom resultPolynom = new Polynom(hornerGlieder);
        String restString = String.format("%+.2f/(x%+.2f)", hornerTabelle[0], -x);


        if(hornerTabelle[0] == 0)
            return resultPolynom.toString();
        else
            return resultPolynom.toString() + restString;
    }

    /**
     * Erstellt die "Hornertabelle" für einen x-Wert nach dem Hornerschema
     * @param x Der x-Wert für den die Hornertabelle berechnet werden soll
     * @return Die Werte der Hornertabelle (Index = Grad des Wertes)
     */
    private double[] hornerTabelle(double x)
    {
        double[] hornerTabelle = new double[glieder.length];
        for(int i = glieder.length - 1; i >= 0; i--)
        {
            if(i == glieder.length - 1)
            {
                hornerTabelle[i] = glieder[i].getKoeffizient();
            }
            else
            {
                double multiplikationVorher = hornerTabelle[i+1] * x;
                hornerTabelle[i] = glieder[i].getKoeffizient() + multiplikationVorher;
            }
        }
        return hornerTabelle;
    }
}

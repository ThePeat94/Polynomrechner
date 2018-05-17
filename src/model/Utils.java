package model;

/**
 * Eine Hilfsklasse, die nützliche Methoden zur Verfügung stellt
 */
public class Utils {

    /**
     * Enthält alle hochgestellten Zahlen.
     */
    private static char[] superscriptNumbers = new char [] {'⁰', '¹', '²', '³', '⁴', '⁵', '⁶', '⁷', '⁸', '⁹'};

    /**
     * Konvertiert eine gegebene Zahl in einen String, der die Zahl als hochgestellt darstellt
     * @param number Die zu konvertierende Zahl
     * @return Die gegebene Zahl mit hochgestellten Zeichen
     */
    public static String numberToSuperscriptedString(int number)
    {
        if(number < 10 && number >= 0)
            return String.valueOf(superscriptNumbers[number]);

        if(number > -10 && number <= 0)
            return "⁻" + String.valueOf(superscriptNumbers[Math.abs(number)]);

        String superscriptSequence = new String();

        String numberString = String.valueOf(number);
        for(int i = 0; i < numberString.length(); i++)
        {
            if(number < 0)
            {
                superscriptSequence += '⁻';
                continue;
            }

            superscriptSequence += superscriptNumbers[Integer.parseInt(String.valueOf(numberString.charAt(i)))];
        }


        return superscriptSequence;
    }
}

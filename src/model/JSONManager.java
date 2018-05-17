package model;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Verwaltet die Polynome.json <br>
 * Stellt Funktionen bereit, um die JSON auszulesen, zu leeren und um Elemente einzuspeichern.
 */
public class JSONManager {

    /**
     * Dateipfad zur JSON mit den Polynomen
     */
    private static final String JSONPATH = "./Resources/Polynome.json";

    /**
     * Liest die JSON aus und gibt alle enthaltenen Polynome zurück
     * @return Die gespeicherten Polynome in der JSON
     */
    public static Polynom[] getPolynomialsFromJSON() throws IOException
    {
        File jsonFile = new File(JSONPATH);

        if(!checkAndCreateFile(jsonFile))
        {
            return null;
        }

        Gson gson = new Gson();
        try (JsonReader jsonReader = new JsonReader(new FileReader(jsonFile))) {

            // Die Wurzel
            JsonObject object = gson.fromJson(jsonReader, JsonObject.class);

            // Wenn die Datei leer ist, ist object null
            if(object != null)
            {
                // Die unter Polynome gespeicherten Elemente abrufen
                JsonArray polynomeJSON = object.get("polynome").getAsJsonArray();

                if(polynomeJSON != null)
                {
                    // Aus der JSON das Polynom-Array erstellen
                    return gson.fromJson(polynomeJSON, Polynom[].class);
                }
            }
        }

        return null;
    }

    /**
     * Speichert ein Polynom in der JSON
     * @param polynomToSave Das zu speichernde Polynom
     */
    public static void savePolynomialToJSON(Polynom polynomToSave) throws IOException
    {
        Gson gson = new Gson();
        JsonObject root = null;
        File jsonFile = new File(JSONPATH);

        // Wenn die JSON Datei existiert, können wir diese auch auslesen
        if(checkAndCreateFile(jsonFile))
        {
            try (JsonReader jsonReader = new JsonReader(new FileReader(jsonFile))) {
                root = gson.fromJson(jsonReader, JsonObject.class);
            }
        }

        // Erstellt den zu speichernden JSON-String für das Objekt
        String toSave = gson.toJson(polynomToSave);

        // Wenn es eine "Wurzel" gibt, so wird dieser das Polynom hinzugefügt, anderenfalls wird diese erstellt
        if(root != null)
        {
            root.get("polynome").getAsJsonArray().add(gson.fromJson(toSave, JsonObject.class));
        }
        else
        {
            Polynom[] polynome = new Polynom [] { polynomToSave };
            root = new JsonObject();
            root.add("polynome", gson.toJsonTree(polynome));
        }

        // Schreiben in die Datei
        try (JsonWriter jsonWriter = new JsonWriter(new FileWriter(JSONPATH))) {
            gson.toJson(root, jsonWriter);
        }
    }

    /**
     * Lösht die Inhalte der Polynome.json
     * @throws IOException Bei Fehlern, die Lese-/Schreibeprozesse auf der Datei beenden oder unterbrechen
     */
    public static void clearFile() throws IOException
    {
        File jsonFile = new File(JSONPATH) ;
        if(checkAndCreateFile(jsonFile))
        {
            FileWriter fileWriter = new FileWriter(jsonFile);
            fileWriter.close();
        }
    }

    /**
     * Speichert die angegebenen Polynome in der JSON
     * @param polynomialsToSave Die zuspeichernden Polynome
     * @throws IOException Bei Fehlern, die Lese-/Schreibeprozesse auf der Datei beenden oder unterbrechen
     */
    public static void writePolynomialsToJson(Polynom[] polynomialsToSave) throws IOException
    {

        Gson gson = new Gson();
        JsonObject root = null;
        File jsonFile = new File(JSONPATH);

        // Wenn die JSON Datei existiert, können wir diese auch auslesen
        if(checkAndCreateFile(jsonFile))
        {
            try (JsonReader jsonReader = new JsonReader(new FileReader(jsonFile))) {
                root = gson.fromJson(jsonReader, JsonObject.class);
            }
        }

        // Erstellt den zu speichernden JSON-String für das Objekt
        String toSave = gson.toJson(polynomialsToSave);

        // Wenn es eine "Wurzel" gibt, so wird dieser das Polynom hinzugefügt, anderenfalls wird diese erstellt
        if(root != null)
        {
            root.get("polynome").getAsJsonArray().add(gson.fromJson(toSave, JsonObject.class));
        }
        else
        {
            Polynom[] polynome = polynomialsToSave;
            root = new JsonObject();
            root.add("polynome", gson.toJsonTree(polynome));
        }

        // Schreiben in die Datei
        try (JsonWriter jsonWriter = new JsonWriter(new FileWriter(JSONPATH))) {
            gson.toJson(root, jsonWriter);
        }
    }

    /**
     * Prüft ob die Datei existiert und erstellt sie ggf.
     * @param jsonFile Die zu überprüfende Datei
     * @return true, wenn die Datei bereits existierte, false, wenn sie erst erstellt werden musste
     * @throws IOException Bei Fehlern, die Lese-/Schreibeprozesse auf der Datei beenden oder unterbrechen
     */
    private static boolean checkAndCreateFile(File jsonFile) throws IOException
    {
        // Wenn die Datei nicht existiert, Datei erst anlegen und dann abbrechen.
        if(!jsonFile.exists() && !jsonFile.isDirectory())
        {
            try(FileWriter fw = new FileWriter(jsonFile))
            {
                return false;
            }

        }
        return true;
    }
}

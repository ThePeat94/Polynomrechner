package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import model.*;

/**
 * Controller-Klasse für das UI des Polynomrechners
 */
public class PolynomialCalculatorController {

    public TextField tbFPSixthMember;
    public TextField tbFPFifthMember;
    public TextField tbFPFourthMember;
    public TextField tbFPThirdMember;
    public TextField tbFPSecondMember;
    public TextField tbFPFirstMember;
    public TextField tbFPConstant;
    public Button btnCalculateValueForFirstPolynomial;
    public Button btnSaveFirstPolynomial;
    public Label lbValueForFirstPolynomial;
    public TextField tbFPXValue;
    public Label lbFPDeriviation;
    public TextArea taFPInformations;

    public TextField tbSPSixthMember;
    public TextField tbSPFifthMember;
    public TextField tbSPFourthMember;
    public TextField tbSPThirdMember;
    public TextField tbSPSecondMember;
    public TextField tbSPFirstMember;
    public TextField tbSPConstant;
    public Label lbSPDeriviation;
    public TextField tbSPXValue;
    public Label lbValueForSecondPolynomial;
    public TextArea taSPInformations;
    public Label lbResultPolynomial;

    public TitledPane tpFirstPolynomial;
    public TitledPane tpSecondPolynomial;
    public Label lbFPHornerResult;
    public TextField tbFPHornerX;
    public TextField tbFPDivisionValue;
    public Label lbFPDivisionResult;
    public Label lbSPHornerResult;
    public TextField tbSPHornerX;
    public TextField tbSPDivisionValue;
    public Label lbSPDivisionResult;
    public ListView lvSavedPolynomials;

    private Polynom firstPolynomial;
    private Polynom secondPolynomial;


    /**
     * Initialisiert das Fenster. Wird von JavaFX automatisch aufgerufen
     */
    public void initialize()
    {
        reloadSavedPolynomials();
    }

    /**
     * Lädt die gespeicherten Polynome neu in das Listview
     */
    private void reloadSavedPolynomials()
    {
        try
        {
            Polynom[] gespeichertePolynome = JSONManager.getPolynomialsFromJSON();

            // Wenn es keine Polynome in der JSON gibt, dann dennoch eine Liste initialisieren
            if(gespeichertePolynome != null)
            {
                lvSavedPolynomials.setItems(FXCollections.observableArrayList(gespeichertePolynome));
            }
            else
            {
                lvSavedPolynomials.setItems(FXCollections.observableArrayList());
            }
        }
        catch(Exception ex)
        {
            displayMessage(Alert.AlertType.ERROR, "Unerwarteter Fehler", "Es ist ein unerwarteter Fehler beim Lesen der Polynomdatei aufgetreten.",
                    ex.getMessage());
        }
    }

    /**
     * onClick-Event zum Speichern des ersten Polynoms.
     * @param actionEvent
     */
    public void saveFirstPolynomial(ActionEvent actionEvent)
    {
        if(firstPolynomial == null)
            firstPolynomial = new Polynom(6);

        try
        {
            if(checkIfTextfieldIsValid(tbFPConstant) && checkIfTextfieldIsValid(tbFPFirstMember) &&
                    checkIfTextfieldIsValid(tbFPSecondMember) && checkIfTextfieldIsValid(tbFPThirdMember) &&
                    checkIfTextfieldIsValid(tbFPFourthMember) && checkIfTextfieldIsValid(tbFPFifthMember) &&
                    checkIfTextfieldIsValid(tbFPSixthMember))
            {
                // Auslesen der Textfelder
                Glied konstante = new Glied(getValueFromTextfield(tbFPConstant), 0);
                Glied erstesGlied = new Glied(getValueFromTextfield(tbFPFirstMember), 1);
                Glied zweitesGlied = new Glied(getValueFromTextfield(tbFPSecondMember), 2);
                Glied drittesGlied = new Glied(getValueFromTextfield(tbFPThirdMember), 3);
                Glied viertesGlied = new Glied(getValueFromTextfield(tbFPFourthMember), 4);
                Glied fuenftesGlied = new Glied(getValueFromTextfield(tbFPFifthMember), 5);
                Glied sechstesGlied = new Glied(getValueFromTextfield(tbFPSixthMember), 6);

                Glied[] glieder = new Glied[] { konstante, erstesGlied, zweitesGlied, drittesGlied, viertesGlied, fuenftesGlied, sechstesGlied};

                firstPolynomial.setGlieder(glieder);

                tpFirstPolynomial.setText("1. Polynom ("+ firstPolynomial.getGrad() +". Grad) - f(x) = " + firstPolynomial.toString());

                clearFirstPolynomialTexts();
            }
        }
        catch (Exception ex)
        {
            displayMessage(Alert.AlertType.ERROR, "Unerwarteter Fehler beim Speichern des Polynoms",
                "Es ist ein Fehler beim Speichern des 1. Polynoms aufgetreten!", ex.getMessage());
        }

    }

    /**
     * onClick-Event für Berechnung des Funktionswertes an Stelle x des 1. Polynoms
     * @param actionEvent
     */
    public void calculateValueForFirstPolynomial(ActionEvent actionEvent) {
        try
        {
            double xValue = getValueFromTextfield(tbFPXValue);
            lbValueForFirstPolynomial.setText(String.valueOf(firstPolynomial.wertAnStelle(xValue)));
        }
        catch(Exception ex)
        {
            displayMessage(Alert.AlertType.ERROR, "Unerwarteter Fehler beim Berechnen des Funktionwertes",
                    "Es ist ein Fehler bei der Berechnung des Funktionwertes des 1. Polynoms aufgetreten!", ex.getMessage());
        }

    }

    /**
     * onClick-Event für die 1. Ableitung des 1. Polynoms
     * @param actionEvent
     */
    public void derivateFirstPolynomial(ActionEvent actionEvent) {
        try
        {
            lbFPDeriviation.setText(firstPolynomial.ersteAbleitung().toString());
        }
        catch(Exception ex)
        {
            displayMessage(Alert.AlertType.ERROR, "Unerwarteter Fehler beim Ableiten des Polynoms",
                    "Es ist ein Fehler bei der Ableitung des 1. Polynoms aufgetreten!", ex.getMessage());
        }

    }

    /**
     * onClick-Event zur Berechnung des Funktionswertes des 1. Polynoms mithilfe des Hornerschema
     * @param actionEvent
     */
    public void hornerFPforX(ActionEvent actionEvent) {
        try
        {
            double xValue = getValueFromTextfield(tbFPHornerX);
            lbFPHornerResult.setText(String.valueOf(firstPolynomial.hornerschemaFuerX(xValue)));
        }
        catch(Exception ex)
        {
            displayMessage(Alert.AlertType.ERROR, "Unerwarteter Fehler beim Hornerschema des Polynoms",
                    "Es ist ein Fehler beim Hornerschema des 1. Polynoms aufgetreten!", ex.getMessage());
        }

    }

    /**
     * onClick-Event zur Polynomdivision des 1. Polynoms mithilfe des Hornerschema
     * @param actionEvent
     */
    public void hornerDivisionForFirstPolynomial(ActionEvent actionEvent) {

        try
        {
            double x = getValueFromTextfield(tbFPDivisionValue);
            lbFPDivisionResult.setText(firstPolynomial.HornerDivision(x));
        }
        catch(Exception ex)
        {
            displayMessage(Alert.AlertType.ERROR, "Unerwarteter Fehler bei Polynomdivision nach Horner",
                    "Es ist ein unerwateter Fehler bei der Division mittels Horner beim 1. Polynom aufgetreten!",
                    ex.getMessage());
        }
    }

    /**
     * onClick-Event zum Speichern des 1. Polynoms in die Datei
     * @param actionEvent
     */
    public void saveFirstPolynomialToFile(ActionEvent actionEvent) {
        try
        {
            saveFirstPolynomial(null);
            JSONManager.savePolynomialToJSON(firstPolynomial);
            reloadSavedPolynomials();
        }
        catch(Exception ex)
        {
            displayMessage(Alert.AlertType.ERROR, "Unerwarteter Fehler", "Es ist ein unerwateter Fehler geim Speichern des 1. Polynoms aufgetreten!",
                    ex.getMessage());
        }

    }

    /**
     * onClick-Event zum Speichern des zweiten Polynoms.
     * @param actionEvent
     */
    public void saveSecondPolynomial(ActionEvent actionEvent) {

        if(secondPolynomial == null)
            secondPolynomial = new Polynom(6);

        try
        {
            if(checkIfTextfieldIsValid(tbSPConstant) && checkIfTextfieldIsValid(tbSPFirstMember) &&
                    checkIfTextfieldIsValid(tbSPSecondMember) && checkIfTextfieldIsValid(tbSPThirdMember) &&
                    checkIfTextfieldIsValid(tbSPFourthMember) && checkIfTextfieldIsValid(tbSPFifthMember) &&
                    checkIfTextfieldIsValid(tbSPSixthMember))
            {
                Glied konstante = new Glied(getValueFromTextfield(tbSPConstant), 0);
                Glied erstesGlied = new Glied(getValueFromTextfield(tbSPFirstMember), 1);
                Glied zweitesGlied = new Glied(getValueFromTextfield(tbSPSecondMember), 2);
                Glied drittesGlied = new Glied(getValueFromTextfield(tbSPThirdMember), 3);
                Glied viertesGlied = new Glied(getValueFromTextfield(tbSPFourthMember), 4);
                Glied fuenftesGlied = new Glied(getValueFromTextfield(tbSPFifthMember), 5);
                Glied sechstesGlied = new Glied(getValueFromTextfield(tbSPSixthMember), 6);

                Glied[] glieder = new Glied[] { konstante, erstesGlied, zweitesGlied, drittesGlied, viertesGlied, fuenftesGlied, sechstesGlied};

                secondPolynomial.setGlieder(glieder);

                tpSecondPolynomial.setText("2. Polynom ("+ secondPolynomial.getGrad() +". Grad) - g(x) = " + secondPolynomial.toString());

            }
        }
        catch(Exception ex)
        {
            displayMessage(Alert.AlertType.ERROR, "Unerwarteter Fehler beim Speichern des Polynoms",
                    "Es ist ein Fehler beim Speichern des 2. Polynoms aufgetreten!", ex.getMessage());
        }
    }

    /**
     * onClick-Event für Berechnung des Funktionswertes an Stelle x des 2. Polynoms
     * @param actionEvent
     */
    public void calculateValueForSecondPolynomial(ActionEvent actionEvent) {
        try
        {
            double xValue = getValueFromTextfield(tbSPXValue);
            lbValueForSecondPolynomial.setText(String.valueOf(secondPolynomial.wertAnStelle(xValue)));
        }
        catch(Exception ex)
        {
            displayMessage(Alert.AlertType.ERROR, "Unerwarteter Fehler bei Funktionswertberechnung",
                    "Es ist ein unerwateter Fehler bei der Funktionswertberechnung beim 2. Polynom aufgetreten!",
                    ex.getMessage());
        }

    }

    /**
     * onClick-Event zur Bildung der ersten Ableitung des zweiten Polynoms
     * @param actionEvent
     */
    public void derivateSecondPolynomial(ActionEvent actionEvent)
    {
        try
        {
            lbSPDeriviation.setText(secondPolynomial.ersteAbleitung().toString());
        }
        catch(Exception ex)
        {
            displayMessage(Alert.AlertType.ERROR, "Unerwarteter Fehler bei Ableitung",
                    "Es ist ein unerwateter Fehler bei der Ableitung des 2. Polynom aufgetreten!",
                    ex.getMessage());
        }

    }

    /**
     * onClick-Event zur Berechnung des Funktionswertes des 2. Polynoms mithilfe des Hornerschema
     * @param actionEvent
     */
    public void hornerSPForX(ActionEvent actionEvent)
    {
        try
        {
            double xValue = getValueFromTextfield(tbSPHornerX);
            lbSPHornerResult.setText(String.valueOf(secondPolynomial.hornerschemaFuerX(xValue)));
        }
        catch(Exception ex)
        {
            displayMessage(Alert.AlertType.ERROR, "Unerwarteter Fehler bei Funktionswertberechnung nach Horner",
                    "Es ist ein unerwateter Fehler bei der Funktionswertberechnung nach Horner beim 2. Polynom aufgetreten!",
                    ex.getMessage());
        }

    }

    /**
     * onClick-Event zur Polynomdivision des 2. Polynoms mithilfe des Hornerschema
     * @param actionEvent
     */
    public void hornerDivisionForSecondPolynomial(ActionEvent actionEvent) {
        try
        {
            double x = getValueFromTextfield(tbSPDivisionValue);
            lbSPDivisionResult.setText(secondPolynomial.HornerDivision(x));
        }
        catch(Exception ex)
        {
            displayMessage(Alert.AlertType.ERROR, "Unerwarteter Fehler bei Polynomdivision nach Horner",
                    "Es ist ein unerwateter Fehler bei der Division mittels Horner beim 2. Polynom aufgetreten!",
                    ex.getMessage());
        }
    }


    /**
     * onClick-Event zum Speichern des 2. Polynoms in die Datei
     * @param actionEvent
     */
    public void saveSecondPolynomialToFIle(ActionEvent actionEvent) {
        try
        {
            saveSecondPolynomial(null);
            JSONManager.savePolynomialToJSON(secondPolynomial);
            reloadSavedPolynomials();
        }
        catch(Exception ex)
        {
            displayMessage(Alert.AlertType.ERROR, "Unerwarteter Fehler beim Speichern", "Es ist ein unerwateter Fehler beim Speichern des 2. Polynoms aufgetreten!",
                    ex.getMessage());
        }

    }

    /**
     * onClick-Event zur Berechnung der Summe aus dem 1. und 2. Polynom
     * @param actionEvent
     */
    public void sumFromFandG(ActionEvent actionEvent)
    {
        try
        {
            if(canOperate())
            {
                Polynom ergebnisPolynom = firstPolynomial.summeMit(secondPolynomial);
                lbResultPolynomial.setText(ergebnisPolynom.toString());
            }
            else
            {
                displayCalculationHint();
            }
        }
        catch(Exception exception)
        {
            displayMessage(Alert.AlertType.ERROR, "Fehler aufgetrten", "Es ist ein unerwarteter Fehler aufgetrten!",
                    exception.getMessage());
        }

    }

    /**
     * onClick-Event zur Berechnung der Summe aus dem 1. und 1. Polynom
     * @param actionEvent
     */
    public void sumFromFandF(ActionEvent actionEvent)
    {
        try
        {
            if(canOperate())
            {
                Polynom ergebnisPolynom = firstPolynomial.summeMit(firstPolynomial);
                lbResultPolynomial.setText(ergebnisPolynom.toString());
            }
            else
            {
                displayCalculationHint();
            }
        }
        catch(Exception exception)
        {
            displayMessage(Alert.AlertType.ERROR, "Fehler aufgetrten", "Es ist ein unerwarteter Fehler aufgetrten!",
                    exception.getMessage());
        }

    }

    /**
     * onClick-Event zur Berechnung der Summe aus dem 2. und 2. Polynom
     * @param actionEvent
     */
    public void sumFromGandG(ActionEvent actionEvent)
    {
        try{
            if(canOperate())
            {
                Polynom ergebnisPolynom = secondPolynomial.summeMit(secondPolynomial);
                lbResultPolynomial.setText(ergebnisPolynom.toString());
            }
            else
            {
                displayCalculationHint();
            }
        }
        catch(Exception exception)
        {
            displayMessage(Alert.AlertType.ERROR, "Fehler aufgetrten", "Es ist ein unerwarteter Fehler aufgetrten!",
                    exception.getMessage());
        }
    }

    /**
     * onClick-Event zur Berechnung der Differenz zwischen dem 1. und 2. Polynom
     * @param actionEvent
     */
    public void differenceBetweenFandG(ActionEvent actionEvent)
    {
        try
        {
            if (canOperate())
            {
                Polynom ergebnisPolynom = firstPolynomial.differenzMit(secondPolynomial);
                lbResultPolynomial.setText(ergebnisPolynom.toString());
            }
            else
            {
                displayCalculationHint();
            }
        }
        catch(Exception exception)
        {
            displayMessage(Alert.AlertType.ERROR, "Fehler aufgetrten", "Es ist ein unerwarteter Fehler aufgetrten!",
                    exception.getMessage());
        }
    }

    /**
     * onClick-Event zur Berechnung der Differenz zwischen dem 2. und 1. Polynom
     * @param actionEvent
     */
    public void differenceBetweenGandF(ActionEvent actionEvent)
    {
        try
        {
            if(canOperate())
            {
                Polynom ergebnisPolynom = secondPolynomial.differenzMit(firstPolynomial);
                lbResultPolynomial.setText(ergebnisPolynom.toString());
            }
            else
            {
                displayCalculationHint();
            }
        }
        catch(Exception exception)
        {
            displayMessage(Alert.AlertType.ERROR, "Fehler aufgetrten", "Es ist ein unerwarteter Fehler aufgetrten!",
                          exception.getMessage());
        }

    }

    /**
     * onClick-Event zur Berechnung des Produkts aus dem 1. und 2. Polynom
     * @param actionEvent
     */
    public void multiplyFwithG(ActionEvent actionEvent)
    {
        if(canOperate())
        {
            Polynom neuesPolynom = firstPolynomial.multipliziereMit(secondPolynomial);
            lbResultPolynomial.setText(neuesPolynom.toString());
        }
        else
        {
            displayCalculationHint();
        }
    }

    /**
     * onClick-Event zur Berechnung des Produkts aus dem 1. und 1. Polynom
     * @param actionEvent
     */
    public void multiplyFwithF(ActionEvent actionEvent)
    {
        if(canOperate())
        {
            Polynom neuesPolynom = firstPolynomial.multipliziereMit(firstPolynomial);
            lbResultPolynomial.setText(neuesPolynom.toString());
        }
        else
        {
            displayCalculationHint();
        }
    }

    /**
     * onClick-Event zur Berechnung des Produkts aus dem 2. und 2. Polynom
     * @param actionEvent
     */
    public void multiplyGwithG(ActionEvent actionEvent)
    {
        if(canOperate())
        {
            Polynom neuesPolynom = secondPolynomial.multipliziereMit(secondPolynomial);
            lbResultPolynomial.setText(neuesPolynom.toString());
        }
        else
        {
            displayCalculationHint();
        }
    }

    /**
     * Überprüft, ob beide Polynome existieren. Wird bei den arithmetischen Operationen aufgerufen, um zu prüfen, ob gerechnet werden kann.
     * @return true, wenn beide Polynome initialisiert wurden, sonst false
     */
    private boolean canOperate()
    {
        if(secondPolynomial == null || firstPolynomial == null)
            return false;

        return true;
    }

    /**
     * Stellt eine Messagebox dar, welche Informationen zu fehlenden Polynomen bei arithmetischen Operationen beinhaltet
     */
    private void displayCalculationHint()
    {
        String messageBoxContent =  "Um eine Operation durchzuführen, müssen beide Polynome initialisiert sein! \r\n" +
                                    "Folgende Polynome sind nicht initialisiert:\r\n";

        if(firstPolynomial == null)
            messageBoxContent += "1. Polynom - f(x)\r\n";

        if(secondPolynomial == null)
            messageBoxContent += "2. Polynom - g(x)\r\n";

        displayMessage(Alert.AlertType.WARNING, "Kann keine Berechnung durchführen - Polynome fehlen",
            "Hinweis zur Durchführung einer Berechnung zwischen zwei Polynomen:", messageBoxContent);
    }

    /**
     * Stellt eine Messagebox dar
     * @param alertType Der Alerttype (Warning, Information, Error, ...)
     * @param title Der Titel der Messagebox
     * @param headerText Der Kopftext der Messagebox
     * @param contentText Der Inhalt der Messagebox
     */
    private void displayMessage(Alert.AlertType alertType, String title, String headerText, String contentText)
    {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }

    /**
     * Überprüft, ob ein gegebenes Textfeld einen validen Wert enthält
     * @param textField Das zu überprüfende Textfeld
     * @return true, wenn der Inhalt gültig ist
     */
    private boolean checkIfTextfieldIsValid(TextField textField)
    {
        String value = textField.getText().trim();

        // Wenn Textfeld leer ist, dann wird 0 gesetzt
        if(value.isEmpty())
            return true;

        value = value.replace(',', '.');
        float testForValidNumber = Float.parseFloat(value);


        return true;
    }

    /**
     * Extrahiert den Wert aus dem gegebenen Textfeld
     * @param textField Das Textfeld mit dem enhaltenen numerischen Wert
     * @return Den numerischen Wert im Textfeld
     */
    private double getValueFromTextfield(TextField textField)
    {
        String value = textField.getText().trim();
        // Wenn Textfeld leer ist, dann wird 0 gesetzt
        if(value.isEmpty())
        {
            return 0.0;
        }
        else
        {
            value = value.replace(',', '.');
            return Double.parseDouble(value);
        }

    }

    /**
     * onClick-Event zum Laden des gewählten Polynoms in das 1. Polynom
     * @param actionEvent
     */
    public void loadIntoFirstPolynomial(ActionEvent actionEvent)
    {
        try
        {
            firstPolynomial = (Polynom) lvSavedPolynomials.getSelectionModel().getSelectedItem();
            if(firstPolynomial != null)
            {
                tbFPConstant.setText(String.valueOf(firstPolynomial.getGlied(0).getKoeffizient()));
                tbFPFirstMember.setText(String.valueOf(firstPolynomial.getGlied(1).getKoeffizient()));
                tbFPSecondMember.setText(String.valueOf(firstPolynomial.getGlied(2).getKoeffizient()));
                tbFPThirdMember.setText(String.valueOf(firstPolynomial.getGlied(3).getKoeffizient()));
                tbFPFourthMember.setText(String.valueOf(firstPolynomial.getGlied(4).getKoeffizient()));
                tbFPFifthMember.setText(String.valueOf(firstPolynomial.getGlied(5).getKoeffizient()));
                tbFPSixthMember.setText(String.valueOf(firstPolynomial.getGlied(6).getKoeffizient()));

                tpFirstPolynomial.setText("1. Polynom ("+ firstPolynomial.getGrad() +". Grad) - f(x) = " + firstPolynomial.toString());
                clearFirstPolynomialTexts();
            }
        }
        catch(Exception ex)
        {
            displayMessage(Alert.AlertType.ERROR, "Unerwateter Fehler", "Es ist ein unerwateter Fehler aufgetreten!",
                    ex.getMessage());
        }
    }

    /**
     * onClick-Event zum Laden des gewählten Polynoms in das 2. Polynom
     * @param actionEvent
     */
    public void loadIntoSecondPolynomial(ActionEvent actionEvent)
    {
        try
        {
            secondPolynomial = (Polynom) lvSavedPolynomials.getSelectionModel().getSelectedItem();
            if(secondPolynomial != null)
            {
                tbSPConstant.setText(String.valueOf(secondPolynomial.getGlied(0).getKoeffizient()));
                tbSPFirstMember.setText(String.valueOf(secondPolynomial.getGlied(1).getKoeffizient()));
                tbSPSecondMember.setText(String.valueOf(secondPolynomial.getGlied(2).getKoeffizient()));
                tbSPThirdMember.setText(String.valueOf(secondPolynomial.getGlied(3).getKoeffizient()));
                tbSPFourthMember.setText(String.valueOf(secondPolynomial.getGlied(4).getKoeffizient()));
                tbSPFifthMember.setText(String.valueOf(secondPolynomial.getGlied(5).getKoeffizient()));
                tbSPSixthMember.setText(String.valueOf(secondPolynomial.getGlied(6).getKoeffizient()));

                tpSecondPolynomial.setText("2. Polynom ("+ secondPolynomial.getGrad() +". Grad) - f(x) = " + secondPolynomial.toString());
                clearSecondPolynomialTexts();
            }
        }
        catch(Exception ex)
        {
            displayMessage(Alert.AlertType.ERROR, "Unerwateter Fehler", "Es ist ein unerwateter Fehler aufgetreten!",
                    ex.getMessage());
        }
    }

    /**
     * onClick-Event zum Löschen des gewählten Polynoms in der Liste
     * @param actionEvent
     */
    public void deleteSelectedPolynomial(ActionEvent actionEvent)
    {
        ObservableList polynomials = lvSavedPolynomials.getItems();

        if(polynomials != null && !polynomials.isEmpty())
        {
            try
            {
                polynomials.remove(lvSavedPolynomials.getSelectionModel().getSelectedItem());
                Polynom[] polynomialsToSave = new Polynom[polynomials.size()];
                for(int i = 0; i < polynomials.size(); i++)
                {
                    polynomialsToSave[i] = (Polynom) polynomials.get(i);
                }
                JSONManager.clearFile();
                JSONManager.writePolynomialsToJson(polynomialsToSave);
                reloadSavedPolynomials();

            }
            catch(Exception ex)
            {
                displayMessage(Alert.AlertType.ERROR, "Unerwarteter Fehler", "Es ist ein unerwateter Fehler aufgetreten!",
                        ex.getMessage());
            }

        }
    }

    /**
     * Setzt die Texte zur Darstellung von Information und Werten für das erste Polynom zurück
     */
    private void clearFirstPolynomialTexts()
    {
        String empty = "";
        tbFPXValue.setText(empty);
        tbFPHornerX.setText(empty);
        tbFPDivisionValue.setText(empty);

        lbValueForFirstPolynomial.setText(empty);
        lbFPDeriviation.setText(empty);
        lbFPHornerResult.setText(empty);
        lbFPDivisionResult.setText(empty);
    }

    /**
     * Setzt die Texte zur Darstellung von Information und Werten für das zweite Polynom zurück
     */
    private void clearSecondPolynomialTexts()
    {
        String empty = "";
        tbSPXValue.setText(empty);
        tbSPHornerX.setText(empty);
        tbSPDivisionValue.setText(empty);

        lbValueForSecondPolynomial.setText(empty);
        lbSPDeriviation.setText(empty);
        lbSPHornerResult.setText(empty);
        lbSPDivisionResult.setText(empty);
    }
}

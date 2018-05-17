package controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    /**
     * Initialsiert das Hauptfenster
     * @param primaryStage Das Hauptfenster
     * @throws Exception Jegliche nicht gefangene Exceptions
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("PolynomialCalculatorMain.fxml"));
        primaryStage.setTitle("Polynomrechner");
        primaryStage.setScene(new Scene(root));
        primaryStage.setMinHeight(250);
        primaryStage.setMinWidth(740);
        primaryStage.getIcons().add(new Image("file:.\\Resources\\icons8-taschenrechner-40.png"));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}

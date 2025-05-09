package com.elams.demo_morpion;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;

public class Morpion extends Application {
    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/elams/demo_morpion/accueil.fxml"));
            Scene scene = new Scene(loader.load());

            stage.setTitle("Morpion");
            stage.getIcons().getClass().getResourceAsStream("/img/icon.png");
            stage.setScene(scene);
            stage.show();
        } catch (IOException _) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Impossible de charger l'interface");
            alert.setContentText("Veuillez contacter l'administrateur");
            alert.showAndWait();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}

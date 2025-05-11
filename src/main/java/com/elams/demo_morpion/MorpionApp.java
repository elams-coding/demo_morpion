package com.elams.demo_morpion;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class MorpionApp extends Application {
    public static final Image ICON = new Image(Objects.requireNonNull(MorpionApp.class.getResourceAsStream("/img/icon.png")));

    public static void impossibleOuvrirInterface(IOException e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText("Impossible de charger l'interface");
        alert.setContentText("Détail de l'erreur : " + e.getMessage());
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/elams/demo_morpion/accueil.fxml"));
            Scene scene = new Scene(loader.load());

            stage.setTitle("Morpion");
            stage.getIcons().add(ICON);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            impossibleOuvrirInterface(e);
        }
    }
}

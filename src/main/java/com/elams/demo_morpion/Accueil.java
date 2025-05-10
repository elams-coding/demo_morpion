package com.elams.demo_morpion;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public final class Accueil {
    @FXML
    private ImageView image;
    @FXML
    private VBox reglesTxt;
    @FXML
    private VBox creditsTxt;

    @FXML
    private void jouer() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/elams/demo_morpion/param-joueur.fxml"));
            Scene scene = new Scene(loader.load());

            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Mode de jeu");
            stage.getIcons().add(MorpionApp.ICON);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText("Impossible de charger l'interface");
            alert.setContentText("Détail de l'erreur :" + e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    private void regles() {
        image.setVisible(false); // Permet l'affichage du panneau des règles à la place
        if (!reglesTxt.isVisible()) { // Vérifie que le panneau des règles est masqué avant affichage
            reglesTxt.setVisible(true);
            creditsTxt.setVisible(false);
        } else {
            image.setVisible(true);
            reglesTxt.setVisible(false);
        }
    }

    @FXML
    private void credits() {
        image.setVisible(false);
        if (!creditsTxt.isVisible()) { // Vérifie que le panneau des crédits est masqué avant affichage
            reglesTxt.setVisible(false);
            creditsTxt.setVisible(true);
        } else {
            image.setVisible(true);
            creditsTxt.setVisible(false);
        }
    }
}
package com.elams.demo_morpion;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
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
    private void initialize() {
        reglesTxt.setVisible(false);
        creditsTxt.setVisible(false);
    }

    @FXML
    private void jouer() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/elams/demo_morpion/param-joueur.fxml"));
            Scene scene = new Scene(loader.load());

            Stage stage = (Stage) image.getScene().getWindow();
            stage.setScene(scene);
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
        image.setVisible(false); // cacher l'image
        if (!reglesTxt.isVisible()) { // verifier que les règles ne soient pas (déjà) affichées
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
        if (!creditsTxt.isVisible()) { // vérifier que les crédits ne soient pas (déjà) affichés
            reglesTxt.setVisible(false);
            creditsTxt.setVisible(true);
        } else {
            image.setVisible(true);
            creditsTxt.setVisible(false);
        }
    }
}

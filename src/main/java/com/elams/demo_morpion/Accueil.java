package com.elams.demo_morpion;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

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
        System.out.println("Bientôt");
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

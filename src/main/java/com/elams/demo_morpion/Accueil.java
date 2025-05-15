package com.elams.demo_morpion;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public final class Accueil {
    @FXML
    private ImageView image;
    @FXML
    private VBox reglesTxt;
    @FXML
    private VBox creditsTxt;

    @FXML
    public void jouer() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/elams/demo_morpion/param-joueur.fxml"));
            Scene scene = new Scene(loader.load());
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/css/param-joueur.css")).toExternalForm());

            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Mode de jeu");
            stage.getIcons().add(MorpionApp.ICON);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();

            if (ParamJoueur.p1 != null && ParamJoueur.p2 != null) {
                // Fermer la page d'accueil
                stage = (Stage) image.getScene().getWindow();
                stage.close();

                // Ouvrir la page de jeu
                loader = new FXMLLoader(getClass().getResource("/com/elams/demo_morpion/jeu.fxml"));

                scene = new Scene(loader.load());
                scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/css/jeu.css")).toExternalForm());

                Jeu gererJeu = loader.getController();
                stage.setScene(scene);
                stage.setTitle("Morpion - " + ParamJoueur.p1.getName() + " vs " + ParamJoueur.p2.getName());
                stage.setOnCloseRequest(event -> {
                    gererJeu.quitter();
                    event.consume(); // Empêche la fermeture automatique
                });
                stage.show();
            }
        } catch (IOException e) {
            MorpionApp.impossibleOuvrirInterface(e);
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

    @FXML
    private void versDiscord() {
        new Regles().versDiscord();
    }

    @FXML
    private void versGitHub() {
        new Regles().versGitHub();
    }
}
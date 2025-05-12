package com.elams.demo_morpion;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.util.Optional;

public class Jeu {
    public static final String X = "X";
    public static final String O = "O";
    private String premierJoueur;
    private String secondJoueur;

    @FXML
    private CheckMenuItem activerHistorique;
    @FXML
    private CheckMenuItem activerScore;
    @FXML
    private ScrollPane conteneurHistorique;
    @FXML
    private ScrollPane conteneurScore;
    @FXML
    private VBox voletHistorique;
    @FXML
    private VBox voletScore;
    @FXML
    private Label nom;
    @FXML
    private MenuItem nouvellePartie;
    @FXML
    private GridPane plateau;
    @FXML
    private MenuItem reinitialiser;
    @FXML
    private Label signe;

    @FXML
    public void initialize() {
        activerHistorique.setSelected(true);
        activerScore.setSelected(true);
        signe.setText(X);
        initialiserPlateau();
        initialiserVolets();
        initialiserNomJoueurs();
    }

    private void changerSigne() {
        if (signe.getText().equals(X)) {
            signe.setText(O);
        } else {
            signe.setText(X);
        }
    }

    @FXML
    void boutonCliquer(ActionEvent event) {
        // TODO Gérer le clic sur un bouton du plateau de jeu
    }

    @FXML
    private void aPropos() {
        // TODO Nouvelle modale pour les crédits
    }

    @FXML
    private void regles() {
        // TODO Nouvelle modale pour les règles
    }

    @FXML
    public void quitter() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Quitter");
        alert.setHeaderText(null);
        alert.setContentText("Voulez-vous vraiment quitter ?");
        Optional<ButtonType> resultat = alert.showAndWait();
        if (resultat.isPresent() && resultat.get() == ButtonType.OK) {
            Platform.exit();
        }
    }

    private void initialiserPlateau() {
        for (Node node : plateau.getChildren()) {
            if (node instanceof Button button) {
                button.setText(null);
            }
        }
    }

    private void initialiserVolets() {
        // Supprimer le contenu des volets gauche et droite, tout en conservant leur en-tête
        voletHistorique.getChildren().remove(1, voletHistorique.getChildren().size());
        voletScore.getChildren().remove(1, voletHistorique.getChildren().size());
    }

    private void initialiserNomJoueurs() {
        assert premierJoueur != null;
        premierJoueur = ParamJoueur.premierJoueur;
        assert secondJoueur != null;
        secondJoueur = (premierJoueur.equals(ParamJoueur.p1.getName())) ? ParamJoueur.p2.getName() : ParamJoueur.p1.getName();
        nom.setText(premierJoueur);
    }
}

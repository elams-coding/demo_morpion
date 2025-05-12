package com.elams.demo_morpion;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class Jeu {
    public static final String X = "X";
    public static final String O = "O";
    private String premierJoueur;
    private String secondJoueur;
    private static int nbParties;

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
    private GridPane plateau;
    @FXML
    private Label signe;

    @FXML
    public void initialize() {
        signe.setText(X);
        nbParties = 1;
        initialiserPlateau();
        initialiserVolets();
        initialiserNomJoueurs();
        ajouterAuxVolets("Début de la partie !");
        ajouterAuxVolets("Partie " + nbParties);
    }

    private void changerNom() {
        if (nom.getText().equals(premierJoueur)) {
            nom.setText(secondJoueur);
        } else {
            nom.setText(premierJoueur);
        }
        changerSigne();
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
        Button bouton = (Button) event.getSource();
        if (bouton.getText() != null) {
            return;
        }
        Integer ligne = GridPane.getRowIndex(bouton);
        Integer colonne = GridPane.getColumnIndex(bouton);

        if (ligne == null) {
            ligne = 0;
        }
        if (colonne == null) {
            colonne = 0;
        }

        bouton.setText(signe.getText());
        ajouterHistorique(nom.getText() + " : (" + ligne + "," + colonne + ")" + " -> " + signe.getText());
        changerNom();
    }

    @FXML
    private void afficherHistorique() {
        if (conteneurHistorique.isVisible() && conteneurHistorique.isManaged()) {
            conteneurHistorique.setVisible(false);
            conteneurHistorique.setManaged(false);
        } else {
            conteneurHistorique.setVisible(true);
            conteneurHistorique.setManaged(true);
        }
    }

    @FXML
    private void afficherScore() {
        if (conteneurScore.isVisible() && conteneurScore.isManaged()) {
            conteneurScore.setVisible(false);
            conteneurScore.setManaged(false);
        } else {
            conteneurScore.setVisible(true);
            conteneurScore.setManaged(true);
        }
    }

    @FXML
    private void aPropos() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/elams/demo_morpion/a-propos.fxml"));
            Scene scene = new Scene(loader.load());

            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("À propos");
            stage.getIcons().add(MorpionApp.ICON);
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (IOException e) {
            MorpionApp.impossibleOuvrirInterface(e);
        }
    }

    @FXML
    private void regles() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/elams/demo_morpion/regles.fxml"));
            Scene scene = new Scene(loader.load());

            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Règles du jeu");
            stage.getIcons().add(MorpionApp.ICON);
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (IOException e) {
            MorpionApp.impossibleOuvrirInterface(e);
        }
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

    @FXML
    private void reinitialiser() {

    }

    @FXML
    private void nouvellePartie() {

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

    private void ajouterAuxVolets(String message) {
        ajouterHistorique(message);
        ajouterScore(message);
    }

    private void ajouterHistorique(String message) {
        voletHistorique.getChildren().add(new Label(message));
    }

    private void ajouterScore(String message) {
        voletScore.getChildren().add(new Label(message));
    }
}

package com.elams.demo_morpion;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

public class Jeu {
    @FXML
    private MenuItem aPropos;
    @FXML
    private CheckMenuItem activerHistorique;
    @FXML
    private CheckMenuItem activerScore;
    @FXML
    private ScrollPane conteneurHistorique;
    @FXML
    private ScrollPane conteneurScore;
    @FXML
    private Label nom;
    @FXML
    private MenuItem nouvellePartie;
    @FXML
    private GridPane plateau;
    @FXML
    private MenuItem regles;
    @FXML
    private MenuItem reinitialiser;
    @FXML
    private Label signe;

    @FXML
    public void initialize() {
    }

    @FXML
    void boutonCliquer(ActionEvent event) {
        // Gérer le clic sur un bouton du plateau de jeu
    }

    @FXML
    public void quitter() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Quitter");
        alert.setHeaderText(null);
        alert.setContentText("Voulez-vous vraiment quitter ?");
        alert.showAndWait();

        alert.setOnCloseRequest(Event::consume);

        if (alert.getResult() == ButtonType.CANCEL) {
            alert.close();
        } else if (alert.getResult() == ButtonType.OK) {
            Platform.exit();
        }
    }
}

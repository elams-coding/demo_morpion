package com.elams.demo_morpion;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
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
    private MenuItem quitter;
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
}

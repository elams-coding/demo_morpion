package com.elams.demo_morpion;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ParamJoueur {
    private boolean modeDeuxJoueurs;
    @FXML
    private ToggleGroup nbJoueurs;
    @FXML
    private VBox vBox1;
    @FXML
    private HBox hBoxSpecial;
    @FXML
    private VBox vBox2;
    @FXML
    private TextField entree2;
    @FXML
    private Label erreurMessage;
    @FXML
    private ChoiceBox<String> choix;

    @FXML
    private void initialize() {
        erreurMessage.setManaged(false);

        // TODO gérer le cas où c'est un seul jour et donc créer MBot
        if (modeDeuxJoueurs) {
            entree2.setText("MBot");
        }
    }

    @FXML
    private void modeUnJoueur() {
        modeDeuxJoueurs = false;
        vBox1.setDisable(false);
        hBoxSpecial.setDisable(true);
    }

    @FXML
    private void modeDeuxJoueurs() {
        modeDeuxJoueurs = true;
        vBox1.setDisable(false);
        if (hBoxSpecial.isDisable()) {
            hBoxSpecial.setDisable(false);
        }
    }

    @FXML
    private void creerJoueur1(ActionEvent event) {

    }

    @FXML
    private void creerJoueur2(ActionEvent event) {

    }
}

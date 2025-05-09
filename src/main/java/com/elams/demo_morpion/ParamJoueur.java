package com.elams.demo_morpion;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class ParamJoueur {
    private boolean modeDeuxJoueurs;
    @FXML
    private VBox vbox1;
    @FXML
    private VBox vbox2;
    @FXML
    private TextField entree1;
    @FXML
    private TextField entree2;
    @FXML
    private Label erreurMessage;
    @FXML
    private ChoiceBox<String> choix;

    @FXML
    private void initialize() {
        erreurMessage.setManaged(false);

        if (entree2.isDisable()) {
            entree2.setText("MBot");
        }
    }

    @FXML
    private void modeUnJoueur() {
        modeDeuxJoueurs = false;
        vbox1.setDisable(false);
        entree1.setDisable(false);
        entree2.setDisable(true);
    }

    @FXML
    private void modeDeuxJoueurs() {
        modeDeuxJoueurs = true;
        vbox1.setDisable(false);
    }
}

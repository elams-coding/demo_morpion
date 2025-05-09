package com.elams.demo_morpion;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class ParamJoueur {
    @FXML
    private VBox vbox1;
    @FXML
    private VBox vbox2;
    @FXML
    private RadioButton solo;
    @FXML
    private RadioButton duo;
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
    }
}

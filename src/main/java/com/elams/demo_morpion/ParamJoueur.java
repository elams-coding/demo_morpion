package com.elams.demo_morpion;

import javafx.application.Platform;
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
    private Player p1;
    private Player p2;

    @FXML
    private ToggleGroup nbJoueurs; // avoir seulement 1 choix
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
    }

    @FXML
    private void modeUnJoueur() {
        modeDeuxJoueurs = false;
        vBox1.setDisable(false);
        entree2.setText("MBot");
        hBoxSpecial.setDisable(true);
    }

    @FXML
    private void modeDeuxJoueurs() {
        modeDeuxJoueurs = true;
        vBox1.setDisable(false);
        if (hBoxSpecial.isDisable()) {
            hBoxSpecial.setDisable(false);
            entree2.setText(null);
        }
    }

    @FXML
    private void creerJoueur1(ActionEvent event) {
        TextField tf = (TextField) event.getSource();
        String nom = tf.getText();
        p1 = new Player(nom, "X");
        System.out.println("Joueur 1: " + p1);
    }

    @FXML
    private void creerJoueur2(ActionEvent event) {
        String nom = entree2.getText();
        p2 = new Player(nom, "O");
        System.out.println("Joueur 2: " + p2);
    }
}

package com.elams.demo_morpion;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class ParamJoueur {
    private boolean modeDeuxJoueurs;
    private Player p1;
    private Player p2;

    @FXML
    private ToggleGroup nbJoueurs; // Gestion de la sélection exclusive du mode de jeu (1 ou 2 joueurs)
    @FXML
    private VBox vBox1;
    @FXML
    private HBox hBoxSpecial;
    @FXML
    private VBox vBox2;
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
        p1 = null;
        p2 = null;
    }

    @FXML
    private void modeUnJoueur() {
        modeDeuxJoueurs = false;
        vBox1.setDisable(false); // Réactive les contrôles après la sélection du mode de jeu
        entree2.setText("MBot");
        hBoxSpecial.setDisable(true);
        // Création du joueur IA (MBot) pour le mode solo
        // Si vous changez le nom du bot, veillez à ce que celui-ci soit valide. cf : nomValide()
        String nom = entree2.getText();
        p2 = new Player(nom, "O");
        System.out.println("Joueur 2 : " + p2);
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
        String nom = entree1.getText();
        String erreur = nomValide(nom);
        if (erreur != null) {
            afficherMessageErreur(erreur);
        } else {
            erreurMessage.setManaged(false);
            p1 = new Player(nom, "X");
            System.out.println("Joueur 1 : " + p1);
        }
    }

    @FXML
    private void creerJoueur2(ActionEvent event) {
        String nom = entree2.getText();
        String erreur = nomValide(nom);
        if (erreur != null) {
            afficherMessageErreur(erreur);
        } else {
            erreurMessage.setManaged(false);
            p2 = new Player(nom, "O");
            System.out.println("Joueur 2 : " + p2);
        }
    }

    private String nomValide(String nom) {
        if (nom == null || nom.trim().isEmpty()) {
            return "Le nom du joueur est obligatoire.";
        }

        String nomFormatee = nom.trim();

        for (int i = 0; i < nomFormatee.length(); i++) {
            char c = nomFormatee.charAt(i);
            if (c == '?' || c == '!' || c == ':' || c == ',' || c == '/' || c == '\\'
                    || c == ';' || c == '<' || c == '>' || c == '~' || c == '#'
                    || c == '{' || c == '}' || c == '[' || c == ']' || c == '('
                    || c == ')' || c == '|' || c == '\'' || c == '\"') {
                return "Le nom ne doit pas contenir \"?!:,;/\\<>~#{}[]()|'\"@\".";
            }
        }

        if (nomFormatee.length() < 3) {
            return "Le nom doit contenir au moins trois caractères.";
        }

        if (nomFormatee.length() >= 16) {
            return "Le nom doit contenir au plus quinze caractères.";
        }

        return null;
    }

    private void afficherMessageErreur(String erreur) {
        erreurMessage.setText(erreur);
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, _ -> {
                    erreurMessage.setManaged(true);
                    erreurMessage.setVisible(true);
                }),
                new KeyFrame(Duration.seconds(2), _ -> {
                    erreurMessage.setManaged(false);
                    erreurMessage.setVisible(false);
                })
        );
        timeline.setCycleCount(1);  // Une seule exécution
        timeline.play();
    }
}
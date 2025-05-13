package com.elams.demo_morpion;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Random;

public class ParamJoueur {
    public static final String RANDOM = "Random";

    public static boolean modeDeuxJoueurs;
    public static Player p1;
    public static Player p2;
    public static String premierJoueur;
    public static boolean difficile;

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
    private CheckBox modeDifficile;
    @FXML
    private ChoiceBox<String> selection;

    @FXML
    private void initialize() {
        erreurMessage.setManaged(false);
        p1 = null;
        p2 = null;
        vBox2.setDisable(true);
        entree1.clear();
        entree2.clear();
        modeDifficile.setManaged(false);
        modeDifficile.setVisible(false);
        difficile = false;
    }

    @FXML
    private void modeUnJoueur() {
        initialize();
        modeDeuxJoueurs = false;
        System.out.println("mode deux joueurs : " + false);
        vBox1.setDisable(false); // Réactive les contrôles après la sélection du mode de jeu
        entree2.setText("MBot");
        hBoxSpecial.setDisable(true);
        // Création du joueur IA (MBot) pour le mode solo
        // Si vous changez le nom du bot, veillez à ce que celui-ci soit valide. cf : nomValide()
        String nom = entree2.getText();
        p2 = new Player(nom, "O");
        System.out.println("Joueur 2 : " + p2);
        entree1.requestFocus();
        modeDifficile.setManaged(true);
        modeDifficile.setVisible(true);
        modeDifficile.setDisable(true);
    }

    @FXML
    private void modeDeuxJoueurs() {
        initialize();
        modeDeuxJoueurs = true;
        System.out.println("mode deux joueurs : " + true);
        vBox1.setDisable(false);
        if (hBoxSpecial.isDisable()) {
            hBoxSpecial.setDisable(false);
            entree2.setText(null);
        }
        entree1.requestFocus();
        modeDifficile.setManaged(false);
        modeDifficile.setVisible(false);
    }

    @FXML
    private void creerJoueur1() {
        String nom = entree1.getText().trim();
        nom = nom.replaceAll("\\s+", "_");
        String erreur = nomValide(nom);
        if (erreur != null) {
            afficherMessageErreur(erreur);
            entree1.clear();
        } else {
            erreurMessage.setManaged(false);
            nom = Character.toUpperCase(nom.charAt(0)) + nom.substring(1);
            p1 = new Player(nom, "X");
            System.out.println("Joueur 1 : " + p1);

            if (modeDeuxJoueurs) {
                entree2.requestFocus();
            }
        }

        if (p1 != null && p2 != null) {
            modeDifficile.setDisable(false);
            modeDifficile.requestFocus();
            quiCommence();
        }
    }

    @FXML
    private void creerJoueur2() {
        String nom = entree2.getText().trim();
        nom = nom.replaceAll("\\s+", "_");
        String erreur = nomValide(nom);
        if (erreur != null) {
            afficherMessageErreur(erreur);
            entree2.clear();
        } else {
            erreurMessage.setManaged(false);
            nom = Character.toUpperCase(nom.charAt(0)) + nom.substring(1);
            p2 = new Player(nom, "O");
            System.out.println("Joueur 2 : " + p2);
        }

        if (p2 != null && p1 != null) {
            quiCommence();
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
                    || c == ')' || c == '|' || c == '\'' || c == '\"' || c == '%'
                    || c == '@' || c == '¨' || c == '^' || c == '¤' || c == 'µ'
                    || c == '$' || c == '&' || c == '*' || c == '£') {
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

    private void quiCommence() {
        vBox2.setDisable(false);
        ObservableList<String> choix = FXCollections.observableArrayList();
        choix.addAll(p1.getName(), p2.getName());
        choix.add(RANDOM);
        selection.setValue("Choisissez un joueur");
        selection.setItems(choix);

        selection.setOnAction(_ -> {
            premierJoueur = selection.getSelectionModel().getSelectedItem();

            if (premierJoueur.equals(RANDOM)) { // Sélection aléatoire du premier joueur
                Random random = new Random();
                if (random.nextBoolean()) {
                    premierJoueur = p1.getName();
                } else {
                    premierJoueur = p2.getName();
                }
            }

            System.out.println("Premier joueur : " + premierJoueur);
            ouvrirPageJeu();
        });
    }

    private void ouvrirPageJeu() {
        // Fermer la fenêtre modale de configuration des joueurs pour passer à l'écran de jeu
        Stage fenetreModale = (Stage) vBox1.getScene().getWindow();
        fenetreModale.close();
    }

    @FXML
    private void activerModeDifficile() {
        difficile = modeDifficile.isSelected();
        selection.requestFocus();
    }
}
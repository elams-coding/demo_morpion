package com.elams.demo_morpion;

import javafx.animation.PauseTransition;
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
import javafx.util.Duration;

import java.io.IOException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class Jeu {
    public static final String X = "X";
    public static final String O = "O";
    private static int nbParties;
    private static String[][] plateauJeu;
    private static boolean egalite;
    private String premierJoueur;
    private String secondJoueur;
    private Player joueur1;
    private Player joueur2;
    private boolean contreIA;
    private boolean imbattable;
    @FXML
    private CheckMenuItem mBotImbattable;
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
    private Label nomPremier;
    @FXML
    private GridPane plateau;
    @FXML
    private Label signe;

    @FXML
    public void initialize() {
        signe.setText(X);
        nbParties = 1;
        contreIA = !ParamJoueur.modeDeuxJoueurs;
        initialiserPlateau();
        initialiserVolets();
        initialiserNomJoueurs();
        nomPremier.setText(premierJoueur);
        ajouterAuxVolets("Partie " + nbParties);
        joueur1 = ParamJoueur.p1;
        joueur2 = ParamJoueur.p2;
        imbattable = ParamJoueur.imbattable;

        if (!contreIA) {
            mBotImbattable.setDisable(true);
        } else {
            mBotImbattable.setSelected(imbattable);
        }

        // Faire jouer l'IA si c'est son tour en premier
        if (contreIA && nom.getText().equals("MBot")) {
            PauseTransition pause = new PauseTransition(Duration.millis(500));
            pause.setOnFinished(_ -> jouerCoupIA(imbattable));
            pause.play();
        }
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

    private void jouerCoupIA(boolean niveauImbattable) {
        if (!contreIA || !nom.getText().equals("MBot")) {
            return;
        }

        PauseTransition pause = new PauseTransition(Duration.millis(500));
        pause.setOnFinished(_ -> {
            int[] coup = niveauImbattable ? trouverMeilleurCoup() : trouverCoupAleatoire();
            Button bouton = trouverBouton(coup[0], coup[1]);
            if (bouton != null) {
                bouton.fire();
            }
        });
        pause.play();
    }

    private int[] trouverCoupAleatoire() {
        // Créer une liste des coups possibles
        java.util.List<int[]> coupsPossibles = new java.util.ArrayList<>();

        // Parcourir le plateau pour trouver toutes les cases vides
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (plateauJeu[i][j] == null) {
                    coupsPossibles.add(new int[]{i, j});
                }
            }
        }

        // Choisir un coup au hasard parmi les coups possibles
        if (!coupsPossibles.isEmpty()) {
            int indexAleatoire = (int) (Math.random() * coupsPossibles.size());
            return coupsPossibles.get(indexAleatoire);
        }

        return new int[]{0, 0}; // Ne dois jamais arriver
    }

    private int[] trouverMeilleurCoup() {
        String symboleIA = signe.getText();
        String symboleJoueur = symboleIA.equals(X) ? O : X;

        // Vérifier si l'IA peut gagner
        int[] coupGagnant = verifierCoup(symboleIA);
        if (coupGagnant != null) {
            return coupGagnant;
        }

        // Vérifier si le joueur peut gagner pour le bloquer
        int[] coupBloquant = verifierCoup(symboleJoueur);
        if (coupBloquant != null) {
            return coupBloquant;
        }

        // Jouer au centre si possible
        if (plateauJeu[1][1] == null) {
            return new int[]{1, 1};
        }

        // Jouer dans un coin
        int[][] coins = {{0, 0}, {0, 2}, {2, 0}, {2, 2}};
        for (int[] coin : coins) {
            if (plateauJeu[coin[0]][coin[1]] == null) {
                return coin;
            }
        }

        // Jouer sur un côté
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (plateauJeu[i][j] == null) {
                    return new int[]{i, j};
                }
            }
        }

        return new int[]{0, 0}; // Ne dois jamais arriver
    }

    private int[] verifierCoup(String symbole) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                assert plateauJeu[i] != null;
                if (plateauJeu[i][j] == null) {
                    plateauJeu[i][j] = symbole;
                    if (!avoirGagnant(plateauJeu).isEmpty()) {
                        plateauJeu[i][j] = null;
                        return new int[]{i, j};
                    }
                    plateauJeu[i][j] = null;
                }
            }
        }
        return null;
    }

    @FXML
    void boutonCliquer(ActionEvent event) {
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
        plateauJeu[ligne][colonne] = signe.getText();
        ajouterHistorique(nom.getText() + " : (" + ligne + "," + colonne + ")" + " [" + signe.getText() + "]");

        Set<String> positionsGagnantes = avoirGagnant(plateauJeu);
        if (!egalite && !positionsGagnantes.isEmpty()) {
            for (Node node : plateau.getChildren()) {
                if (node instanceof Button btn) {
                    String pos = GridPane.getRowIndex(btn) + "," + GridPane.getColumnIndex(btn);
                    if (positionsGagnantes.contains(pos)) {
                        btn.setStyle("-fx-background-color: lightgreen;" + "-fx-background-radius: 5;" +
                                "-fx-border-style: solid;" + "-fx-border-color: darkgreen;" +
                                "-fx-border-width: 2;" + "-fx-border-radius: 5");
                    }
                }
            }
            ajouterScore("Gagnant : " + nom.getText() + " !");
            augmenterScore(nom.getText());
            plateau.setDisable(true);
        } else if (egalite) {
            ajouterScore("Match nul !");
            augmenterScore(null);
            plateau.setDisable(true);
        } else {
            changerNom();
            if (contreIA) {
                jouerCoupIA(imbattable);
            }
        }
    }

    private Set<String> avoirGagnant(String[][] plateau) {
        Set<String> positionsGagnantes = new HashSet<>();

        // Vérifier les lignes
        for (int i = 0; i < 3; i++) {
            if (plateau[i][0] != null && plateau[i][0].equals(plateau[i][1]) && plateau[i][1].equals(plateau[i][2])) {
                positionsGagnantes.add(i + ",0");
                positionsGagnantes.add(i + ",1");
                positionsGagnantes.add(i + ",2");
                return positionsGagnantes;
            }
        }

        // Vérifier les colonnes
        for (int j = 0; j < 3; j++) {
            if (plateau[0][j] != null && plateau[0][j].equals(plateau[1][j]) && plateau[1][j].equals(plateau[2][j])) {
                positionsGagnantes.add("0," + j);
                positionsGagnantes.add("1," + j);
                positionsGagnantes.add("2," + j);
                return positionsGagnantes;
            }
        }

        // Vérifier la diagonale principale
        if (plateau[0][0] != null && plateau[0][0].equals(plateau[1][1]) && plateau[1][1].equals(plateau[2][2])) {
            positionsGagnantes.add("0,0");
            positionsGagnantes.add("1,1");
            positionsGagnantes.add("2,2");
            return positionsGagnantes;
        }

        // Vérifier la diagonale secondaire
        if (plateau[0][2] != null && plateau[0][2].equals(plateau[1][1]) && plateau[1][1].equals(plateau[2][0])) {
            positionsGagnantes.add("0,2");
            positionsGagnantes.add("1,1");
            positionsGagnantes.add("2,0");
            return positionsGagnantes;
        }

        // Vérifier s'il y a match nul (plus de cases vides)
        boolean plateauPlein = true;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (plateau[i][j] == null) {
                    plateauPlein = false;
                    break;
                }
            }
            if (!plateauPlein) {
                break;
            }
        }

        if (plateauPlein) {
            egalite = true;
        }

        return positionsGagnantes; // Retourne un ensemble vide s'il n'y a pas de gagnant
    }

    private void augmenterScore(String joueur) {
        if (joueur != null && joueur.equals(premierJoueur)) {
            joueur1.incrScore();
        } else if (joueur != null && joueur.equals(secondJoueur)) {
            joueur2.incrScore();
        }
        System.out.println("p1 score: " + joueur1.getScore());
        System.out.println("p2 score: " + joueur2.getScore());

        reinitialiserDansSeconds(2);
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
        if (reinitialisationInutile()) {
            return;
        }

        initialiserPlateau();
        nom.setText(premierJoueur);
        signe.setText(X);
        ajouterScore(joueur1.getName() + " : " + joueur1.getScore());
        ajouterScore(joueur2.getName() + " : " + joueur2.getScore());
        ajouterAuxVolets("Partie " + ++nbParties);

        if (plateau.isDisable()) {
            plateau.setDisable(false);
        }

        if (contreIA && nom.getText().equals("MBot")) {
            jouerCoupIA(imbattable);
        }
    }

    @FXML
    private void nouvellePartie() {
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.setTitle("Nouvelle partie");
        confirmation.setHeaderText(null);
        confirmation.setContentText("Voulez-vous vraiment commencer une nouvelle partie ?" + System.lineSeparator() + "Cela réinitialisera les joueurs actuels.");

        Optional<ButtonType> resultat = confirmation.showAndWait();
        if (resultat.isPresent() && resultat.get() == ButtonType.OK) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/elams/demo_morpion/accueil.fxml"));
                Scene sceneAccueil = new Scene(loader.load());

                Stage stageActuel = (Stage) plateau.getScene().getWindow();
                stageActuel.setScene(sceneAccueil);
                stageActuel.setTitle("Morpion - Accueil");

                Accueil accueil = loader.getController();
                accueil.jouer();

            } catch (IOException e) {
                MorpionApp.impossibleOuvrirInterface(e);
            }
        }
    }

    @FXML
    private void choisirImbattable() {
        if (contreIA) {
            imbattable = !imbattable;
        }
    }

    private void initialiserPlateau() {
        plateauJeu = new String[plateau.getRowCount()][plateau.getColumnCount()];
        egalite = false;
        for (Node node : plateau.getChildren()) {
            if (node instanceof Button button) {
                button.setText(null);
                button.setStyle("");
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
        Platform.runLater(() -> conteneurHistorique.setVvalue(1.0));

    }

    private void ajouterScore(String message) {
        voletScore.getChildren().add(new Label(message));
        Platform.runLater(() -> conteneurScore.setVvalue(1.0));
    }

    private boolean reinitialisationInutile() {
        for (String[] strings : plateauJeu) {
            for (String string : strings) {
                if (string != null) {
                    return false;
                }
            }
        }

        return true;
    }

    private void reinitialiserDansSeconds(@SuppressWarnings("SameParameterValue") double seconds) {
        reinitialiserDansMillis(seconds * 1000);
    }

    private void reinitialiserDansMillis(double millis) {
        PauseTransition pause = new PauseTransition(Duration.millis(millis));
        pause.setOnFinished(_ -> reinitialiser());
        pause.play();
    }

    private Button trouverBouton(int ligne, int colonne) {
        for (Node node : plateau.getChildren()) {
            if (node instanceof Button button) {
                Integer rowIndex = GridPane.getRowIndex(button);
                Integer colIndex = GridPane.getColumnIndex(button);

                if (rowIndex != null && colIndex != null && rowIndex == ligne && colIndex == colonne) {
                    return button;
                }
            }
        }
        return null;
    }
}
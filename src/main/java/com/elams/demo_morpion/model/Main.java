package com.elams.demo_morpion.model;

import java.util.Random;

public class Main {
    public static void main(String[] args) {
        Player p1 = new Player("Elams", "X");
        Player p2 = new Player("Noah", "O");

        String[][] plateau = new String[3][3];

        // Initialisation du plateau
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                plateau[i][j] = "-";
            }
        }

        Random random = new Random();
        boolean partieFini = false;
        boolean tourJoueur1 = true;

        while (!partieFini) {
            // Trouver une case vide
            int ligne, colonne;
            do {
                ligne = random.nextInt(3);
                colonne = random.nextInt(3);
            } while (!plateau[ligne][colonne].equals("-"));

            // Placer le symbole du joueur actuel
            if (tourJoueur1) {
                plateau[ligne][colonne] = p1.signe();
                System.out.println("Tour de " + p1.nom() + " (" + p1.signe() + ")");
            } else {
                plateau[ligne][colonne] = p2.signe();
                System.out.println("Tour de " + p2.nom() + " (" + p2.signe() + ")");
            }

            // Afficher le plateau après chaque coup
            afficherPlateau(plateau);
            System.out.println();

            // Vérifier s'il y a un gagnant ou match nul
            partieFini = verifierGagnant(plateau, p1, p2);

            // Changer de joueur
            tourJoueur1 = !tourJoueur1;
        }
    }

    private static void afficherPlateau(String[][] plateau) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print(plateau[i][j] + " ");
            }
            System.out.println();
        }
    }

    private static boolean verifierGagnant(String[][] plateau, Player p1, Player p2) {
        // Vérifier les lignes
        for (int i = 0; i < 3; i++) {
            if (!plateau[i][0].equals("-") &&
                    plateau[i][0].equals(plateau[i][1]) &&
                    plateau[i][1].equals(plateau[i][2])) {
                String gagnant = plateau[i][0].equals(p1.signe()) ? p1.nom() : p2.nom();
                System.out.println(gagnant + " a gagné !");
                return true;
            }
        }

        // Vérifier les colonnes
        for (int j = 0; j < 3; j++) {
            if (!plateau[0][j].equals("-") &&
                    plateau[0][j].equals(plateau[1][j]) &&
                    plateau[1][j].equals(plateau[2][j])) {
                String gagnant = plateau[0][j].equals(p1.signe()) ? p1.nom() : p2.nom();
                System.out.println(gagnant + " a gagné !");
                return true;
            }
        }

        // Vérifier la diagonale principale
        if (!plateau[0][0].equals("-") &&
                plateau[0][0].equals(plateau[1][1]) &&
                plateau[1][1].equals(plateau[2][2])) {
            String gagnant = plateau[0][0].equals(p1.signe()) ? p1.nom() : p2.nom();
            System.out.println(gagnant + " a gagné !");
            return true;
        }

        // Vérifier la diagonale secondaire
        if (!plateau[0][2].equals("-") &&
                plateau[0][2].equals(plateau[1][1]) &&
                plateau[1][1].equals(plateau[2][0])) {
            String gagnant = plateau[0][2].equals(p1.signe()) ? p1.nom() : p2.nom();
            System.out.println(gagnant + " a gagné !");
            return true;
        }

        // Vérifier s'il y a match nul (plus de cases vides)
        boolean plateauPlein = true;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (plateau[i][j].equals("-")) {
                    plateauPlein = false;
                    break;
                }
            }
            if (!plateauPlein) break;
        }

        if (plateauPlein) {
            System.out.println("Match nul !");
            return true;
        }

        return false;
    }
}
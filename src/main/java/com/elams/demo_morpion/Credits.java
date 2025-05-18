package com.elams.demo_morpion;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class Credits {

    public static final String HTTPS_GITHUB_COM_ELAMS_CODING = "https://github.com/elams-coding";
    public static final String HTTPS_DISCORD_COM_USERS = "https://discord.com/users/1280435655817691189";

    @FXML
    public void versDiscord() {
        try {
            Desktop.getDesktop().browse(new URI(HTTPS_DISCORD_COM_USERS));
        } catch (IOException | URISyntaxException e) {
            erreurOuvertureLien(HTTPS_DISCORD_COM_USERS, e.getCause().getMessage());
        }
    }

    @FXML
    public void versGitHub() {
        try {
            Desktop.getDesktop().browse(new URI(HTTPS_GITHUB_COM_ELAMS_CODING));
        } catch (IOException | URISyntaxException e) {
            erreurOuvertureLien(HTTPS_GITHUB_COM_ELAMS_CODING, e.getCause().getMessage());
        }
    }

    private void erreurOuvertureLien(String lien, String erreur) {
        Alert alerte = new Alert(Alert.AlertType.ERROR);
        alerte.setTitle("Erreur d'ouverture du lien");
        alerte.setHeaderText(null);
        alerte.setContentText("Impossible d'ouvrir le lien " + lien + System.lineSeparator() + erreur);
        alerte.showAndWait();
    }
}

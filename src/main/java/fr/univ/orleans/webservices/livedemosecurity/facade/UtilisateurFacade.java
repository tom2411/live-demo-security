package fr.univ.orleans.webservices.livedemosecurity.facade;

import fr.univ.orleans.webservices.livedemosecurity.modele.Utilisateur;

import java.util.Map;

public interface UtilisateurFacade {

    Utilisateur getUtilisateur(String username);
    Map<String,Utilisateur> getUtilisateurs();
    Utilisateur newUtilisateur(String username, String password, boolean isAdmin);
    boolean utilisateurExist(String username);
    void addUtilisateur(String username, Utilisateur utilisateur);
}
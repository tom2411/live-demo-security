package fr.univ.orleans.webservices.livedemosecurity.facade;

import fr.univ.orleans.webservices.livedemosecurity.modele.Utilisateur;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component("UtilisateurFacade")
public class UtilisateurFacadeImpl implements UtilisateurFacade {

    private final Map<String, Utilisateur> utilisateurs = new HashMap<>();

    public UtilisateurFacadeImpl() {
        Utilisateur tom = new Utilisateur("tom", "tom", false);
        Utilisateur admin = new Utilisateur("admin", "admin", true);
        utilisateurs.put(tom.getLogin(), tom);
        utilisateurs.put(admin.getPassword(), admin);
    }

    @Override
    public Utilisateur getUtilisateur(String username) {
        return this.utilisateurs.get(username);
    }

    @Override
    public Map<String, Utilisateur> getUtilisateurs() {
        return utilisateurs;
    }

    @Override
    public boolean utilisateurExist(String username) {
        return utilisateurs.containsKey(username);
    }

    @Override
    public void addUtilisateur(String username, Utilisateur utilisateur) {
        this.utilisateurs.put(username, utilisateur);
    }
}

package fr.univ.orleans.webservices.livedemosecurity.config;

import fr.univ.orleans.webservices.livedemosecurity.facade.UtilisateurFacade;
import fr.univ.orleans.webservices.livedemosecurity.modele.Utilisateur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

public class CustomUserDetailsService implements UserDetailsService {
    private static final String[] ROLES_ADMIN = {"USER", "ADMIN"};
    private static final String[] ROLES_USER = {"USER"};

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UtilisateurFacade utilisateurFacade;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Utilisateur utilisateur = utilisateurFacade.getUtilisateurs().get(s);
        if (utilisateur == null) {
            throw new UsernameNotFoundException("User" + s + " not found");
        }
        String[] roles = utilisateur.isAdmin() ? ROLES_ADMIN : ROLES_USER;
        UserDetails userDetails = User.builder()
                .username(utilisateur.getLogin())
                .password(passwordEncoder.encode(utilisateur.getPassword()))
                .roles(roles)
                .build();
        return userDetails;
    }
}

package fr.univ.orleans.webservices.livedemosecurity.controller;

import fr.univ.orleans.webservices.livedemosecurity.facade.MessageFacade;
import fr.univ.orleans.webservices.livedemosecurity.facade.MessageFacadeImpl;
import fr.univ.orleans.webservices.livedemosecurity.facade.UtilisateurFacade;
import fr.univ.orleans.webservices.livedemosecurity.modele.Message;
import fr.univ.orleans.webservices.livedemosecurity.modele.Utilisateur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.security.Principal;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Predicate;

@RestController
@RequestMapping("/api")
public class MessageController {
    /*private static List<Message> messages = new ArrayList<>();
    private final AtomicLong counter = new AtomicLong(1L);*/
    @Autowired
    MessageFacade messageFacade;

    @Autowired
    UtilisateurFacade utilisateurFacade;

    @PostMapping("/messages")
    public ResponseEntity<Message> create(Principal principal, @RequestBody Message message) {
        String login = principal.getName();
        Message messageRec = messageFacade.newMessage(login + ": " + message.getTexte());

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(messageRec.getId()).toUri();

        return ResponseEntity.created(location).body(messageRec);
    }

    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getAll() {
        return ResponseEntity.ok().body(messageFacade.getMessages());
    }

    @GetMapping("/messages/{id}")
    public ResponseEntity<Message> findById(@PathVariable("id") Long id) {
        Optional<Message> message = messageFacade.getMessage(id);
        if (message.isPresent()) {
            return ResponseEntity.ok().body(message.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/messages/{id}")
    public ResponseEntity<Message> deleteById(@PathVariable("id") Long id) {
        boolean removeSuccess = messageFacade.removeMessage(id);
        if (removeSuccess){
            return ResponseEntity.noContent().build();
        }else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/utilisateurs")
    public ResponseEntity<Utilisateur> enregistreUtilisateur(@RequestBody Utilisateur utilisateur) {
        Predicate<String> isOk = s -> (s != null) && (s.length() >= 2);
        if (!isOk.test(utilisateur.getLogin()) || !isOk.test(utilisateur.getPassword())) {
            return ResponseEntity.badRequest().build();
        }
        if (utilisateurFacade.utilisateurExist(utilisateur.getLogin())) {
            return ResponseEntity.badRequest().build();
        }
        utilisateurFacade.addUtilisateur(utilisateur.getLogin(), utilisateur);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(utilisateur.getLogin()).toUri();

        return ResponseEntity.created(location).body(utilisateur);
    }

    @GetMapping("/utilisateurs/{login}")
    public ResponseEntity<Utilisateur> findUtilisateurById(Principal principal, @PathVariable("login") String login) {
        if (!principal.getName().equals(login)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        if (utilisateurFacade.utilisateurExist(login)) {
            return ResponseEntity.ok().body(utilisateurFacade.getUtilisateur(login));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/utilisateurs2/{login}")
    @PreAuthorize("#login == authentication.principal.username")
    public ResponseEntity<Utilisateur> findUtilisateurById2(@PathVariable("login") String login) {
        if (utilisateurFacade.utilisateurExist(login)) {
            return ResponseEntity.ok().body(utilisateurFacade.getUtilisateur(login));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}

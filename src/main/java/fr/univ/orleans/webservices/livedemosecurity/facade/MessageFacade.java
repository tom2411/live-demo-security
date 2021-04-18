package fr.univ.orleans.webservices.livedemosecurity.facade;

import fr.univ.orleans.webservices.livedemosecurity.modele.Message;

import java.util.List;
import java.util.Optional;

public interface MessageFacade {

    Message newMessage(String texte);
    Optional<Message> getMessage(long id);
    List<Message> getMessages();
    Boolean removeMessage(long id);
}

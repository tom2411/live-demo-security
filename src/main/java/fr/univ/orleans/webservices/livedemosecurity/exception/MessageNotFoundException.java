package fr.univ.orleans.webservices.livedemosecurity.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class MessageNotFoundException extends RuntimeException {
    public MessageNotFoundException() {
        super("L'id du message n'a pas été trouvé.");

    }
}

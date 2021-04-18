package fr.univ.orleans.webservices.livedemosecurity.facade;

import fr.univ.orleans.webservices.livedemosecurity.exception.MessageNotFoundException;
import fr.univ.orleans.webservices.livedemosecurity.modele.Message;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Component("MessageFacade")
public class MessageFacadeImpl implements MessageFacade {

    private final List<Message> messages = new ArrayList<>();
    private final AtomicLong counter = new AtomicLong(1L);

    @Override
    public Message newMessage(String texte) {
        Message message = new Message(counter.getAndIncrement(), texte);
        this.messages.add(message);
        return message;
    }

    @Override
    public Optional<Message> getMessage(long id) {
        Optional<Message> message = this.messages.stream().filter(m -> m.getId() == id).findAny();
        if (message.isEmpty()) {
            throw new MessageNotFoundException();
        }
        return message;
    }

    @Override
    public List<Message> getMessages() {
        return this.messages;
    }

    @Override
    public Boolean removeMessage(long id) {
        boolean res = false;
        for (int index = 0; index < this.messages.size(); index++) {
            if (this.messages.get(index).getId() == id) {
                this.messages.remove(index);
                res = true;
            }
        }
        return res;
    }
}

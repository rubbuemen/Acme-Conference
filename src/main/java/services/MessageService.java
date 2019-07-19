
package services;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.MessageRepository;
import domain.Actor;
import domain.Message;

@Service
@Transactional
public class MessageService {

	// Managed repository
	@Autowired
	private MessageRepository	messageRepository;

	// Supporting services
	@Autowired
	private ActorService		actorService;


	// Simple CRUD methods
	public Message create() {
		Message result;

		final Actor sender = this.actorService.findActorLogged();
		Assert.notNull(sender);

		result = new Message();
		final Collection<Actor> recipients = new HashSet<>();
		final Date moment = new Date(System.currentTimeMillis() - 1);

		result.setRecipients(recipients);
		result.setSender(sender);
		result.setMoment(moment);

		return result;
	}

	public Message createAuxiliar() {
		Message result;

		result = new Message();
		final Collection<Actor> recipients = new HashSet<>();
		final Date moment = new Date(System.currentTimeMillis() - 1);

		result.setRecipients(recipients);
		result.setMoment(moment);

		return result;
	}

	public Collection<Message> findAll() {
		Collection<Message> result;

		result = this.messageRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Message findOne(final int messageId) {
		Assert.isTrue(messageId != 0);
		Message result;

		result = this.messageRepository.findOne(messageId);
		Assert.notNull(result);

		return result;
	}

	public void save(final Message message, final boolean notification) {
		Assert.notNull(message);
		Assert.isTrue(message.getId() == 0); //Un mensaje no tiene sentido que se edite, por lo que sólo vendrá del create

		Message result;

		final Date moment = new Date(System.currentTimeMillis() - 1);
		message.setMoment(moment);

		for (final Actor recipient : message.getRecipients()) {
			final Message copyMessage = this.create();
			copyMessage.setMoment(message.getMoment());
			copyMessage.setBody(message.getBody());
			copyMessage.setSubject(message.getSubject());
			copyMessage.setSender(message.getSender());
			copyMessage.setRecipients(message.getRecipients());
			result = this.messageRepository.save(copyMessage);
			recipient.getMessages().add(result);
		}
	}

	public void delete(final Message message) {
		Assert.notNull(message);
		Assert.isTrue(message.getId() != 0);
		Assert.isTrue(this.messageRepository.exists(message.getId()));

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);

		final Collection<Message> messagesActorLogged = actorLogged.getMessages();
		Assert.isTrue(messagesActorLogged.contains(message), "The logged actor is not the owner of this entity");

		messagesActorLogged.remove(message);
		actorLogged.setMessages(messagesActorLogged);
		this.actorService.saveAuxiliar(actorLogged);
		this.messageRepository.delete(message);
	}

	// Other business methods
	public Collection<Message> findMessagesOrderByTagByActorLogged() {
		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);

		Collection<Message> result;

		//result = this.messageRepository.findMessagesOrderByTagByActorId(actorLogged.getId());
		result = null;
		return result;
	}

	public Message findMessageActorLogged(final int messageId) {
		Assert.isTrue(messageId != 0);

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);

		final Actor actorOwner = this.actorService.findActorByMessageId(messageId);
		Assert.isTrue(actorLogged.equals(actorOwner), "The logged actor is not the owner of this entity");

		final Message result;

		result = this.messageRepository.findOne(messageId);
		Assert.notNull(result);

		return result;
	}

	public Collection<Message> findMessagesSentByActorId(final int actorId) {
		Assert.isTrue(actorId != 0);

		Collection<Message> result;

		result = this.messageRepository.findMessagesSentByActorId(actorId);
		return result;
	}

}


package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.TopicRepository;
import domain.Actor;
import domain.Topic;

@Service
@Transactional
public class TopicService {

	// Managed repository
	@Autowired
	private TopicRepository	topicRepository;

	// Supporting services
	@Autowired
	private ActorService	actorService;


	// Simple CRUD methods
	public Topic create() {
		Topic result;

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);

		result = new Topic();

		return result;
	}

	public Collection<Topic> findAll() {
		Collection<Topic> result;

		result = this.topicRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Topic findOne(final int topicId) {
		Assert.isTrue(topicId != 0);

		Topic result;

		result = this.topicRepository.findOne(topicId);
		Assert.notNull(result);

		return result;
	}

	public Topic save(final Topic topic) {
		Assert.notNull(topic);

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);

		Topic result;

		if (topic.getId() == 0)
			result = this.topicRepository.save(topic);
		else
			result = this.topicRepository.save(topic);

		return result;
	}

	public void delete(final Topic topic) {
		Assert.notNull(topic);
		Assert.isTrue(topic.getId() != 0);
		Assert.isTrue(this.topicRepository.exists(topic.getId()));

		this.topicRepository.delete(topic);
	}

	public Topic findTopicOther() {
		Topic result;

		result = this.topicRepository.findTopicOther();

		return result;
	}

	// Other business methods

}


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

	//Managed repository
	@Autowired
	private TopicRepository	topicRepository;

	//Supporting services
	@Autowired
	private ActorService	actorService;


	//Simple CRUD methods
	//R17
	public Topic create() {
		Topic result;

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		this.actorService.checkUserLoginAdministrator(actorLogged);

		result = new Topic();

		return result;
	}

	//R17
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

	//R17
	public Topic save(final Topic topic) {
		Assert.notNull(topic);

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		this.actorService.checkUserLoginAdministrator(actorLogged);

		Topic result;

		result = this.topicRepository.save(topic);

		return result;
	}

	//R17
	public void delete(final Topic topic) {
		Assert.notNull(topic);
		Assert.isTrue(topic.getId() != 0);
		Assert.isTrue(this.topicRepository.exists(topic.getId()));

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		this.actorService.checkUserLoginAdministrator(actorLogged);

		final Collection<Topic> topicsUsed = this.topicRepository.findTopicsUsed();
		Assert.isTrue(!topicsUsed.contains(topic), "This topic can not be deleted because it is in use");

		this.topicRepository.delete(topic);
	}

	// Other business methods
	public Collection<Topic> findTopicsUsed() {
		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		this.actorService.checkUserLoginAdministrator(actorLogged);

		final Collection<Topic> result = this.topicRepository.findTopicsUsed();

		return result;
	}

	public Topic findTopicOther() {
		Topic result;

		result = this.topicRepository.findTopicOther();

		return result;
	}

}

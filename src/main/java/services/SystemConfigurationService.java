
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.SystemConfigurationRepository;
import domain.Actor;
import domain.Author;
import domain.Message;
import domain.Submission;
import domain.SystemConfiguration;

@Service
@Transactional
public class SystemConfigurationService {

	// Managed repository
	@Autowired
	private SystemConfigurationRepository	systemConfigurationRepository;

	// Supporting services
	@Autowired
	private ActorService					actorService;

	@Autowired
	private SubmissionService				submissionService;

	@Autowired
	private MessageService					messageService;

	@Autowired
	private AuthorService					authorService;

	@Autowired
	private TopicService					topicService;


	// Simple CRUD methods
	public SystemConfiguration create() {
		SystemConfiguration result;

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);

		result = new SystemConfiguration();

		return result;
	}

	public Collection<SystemConfiguration> findAll() {
		Collection<SystemConfiguration> result;

		result = this.systemConfigurationRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public SystemConfiguration findOne(final int systemConfigurationId) {
		Assert.isTrue(systemConfigurationId != 0);

		SystemConfiguration result;

		result = this.systemConfigurationRepository.findOne(systemConfigurationId);
		Assert.notNull(result);

		return result;
	}

	public SystemConfiguration save(final SystemConfiguration systemConfiguration) {
		Assert.notNull(systemConfiguration);

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		this.actorService.checkUserLoginAdministrator(actorLogged);

		SystemConfiguration result;

		result = this.systemConfigurationRepository.save(systemConfiguration);

		return result;
	}

	public void delete(final SystemConfiguration systemConfiguration) {
		Assert.notNull(systemConfiguration);
		Assert.isTrue(systemConfiguration.getId() != 0);
		Assert.isTrue(this.systemConfigurationRepository.exists(systemConfiguration.getId()));

		this.systemConfigurationRepository.delete(systemConfiguration);
	}

	// Other business methods
	public SystemConfiguration getConfiguration() {
		SystemConfiguration result;

		result = this.systemConfigurationRepository.getConfiguration();
		Assert.notNull(result);

		return result;
	}

	//R14.5
	public void notificationProcedure() {
		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		this.actorService.checkUserLoginAdministrator(actorLogged);

		final Collection<Submission> submissionsAcceptedOrRejectedNotNotifiedNoDeadline = this.submissionService.findSubmissionsAcceptedOrRejectedNotNotifiedNoDeadline();

		for (final Submission s : submissionsAcceptedOrRejectedNotNotifiedNoDeadline) {
			final Author author = this.authorService.findAuthorBySubmissionId(s.getId());
			final Message result = this.messageService.create();
			final Actor system = this.actorService.getSystemActor();
			result.setSubject("Your submission has been reviewed");
			result.setBody("We inform you that the submission with ticker " + s.getTicker() + " has been reviewed and its status has been '" + s.getStatus() + "'. You can see the reports in the corresponding section.");
			result.setTopic(this.topicService.findTopicOther());
			result.setSender(system);
			result.getRecipients().add(author);
			this.messageService.save(result);
			s.setIsNotified(true);
			this.submissionService.saveAuxiliar(s);
		}

	}

}

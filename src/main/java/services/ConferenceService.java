
package services;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ConferenceRepository;
import domain.Activity;
import domain.Actor;
import domain.Comment;
import domain.Conference;
import domain.Registration;

@Service
@Transactional
public class ConferenceService {

	// Managed repository
	@Autowired
	private ConferenceRepository	conferenceRepository;

	// Supporting services
	@Autowired
	private ActorService			actorService;


	// Simple CRUD methods
	//R14.2
	public Conference create() {
		Conference result;

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		this.actorService.checkUserLoginAdministrator(actorLogged);

		final Collection<Activity> activities = new HashSet<>();
		final Collection<Registration> registrations = new HashSet<>();
		final Collection<Comment> comments = new HashSet<>();

		result = new Conference();
		result.setIsFinalMode(false);
		result.setActivities(activities);
		result.setRegistrations(registrations);
		result.setComments(comments);

		return result;
	}
	public Collection<Conference> findAll() {
		Collection<Conference> result;

		result = this.conferenceRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Conference findOne(final int conferenceId) {
		Assert.isTrue(conferenceId != 0);

		Conference result;

		result = this.conferenceRepository.findOne(conferenceId);
		Assert.notNull(result);

		return result;
	}

	//R14.2
	public Conference save(final Conference conference) {
		Assert.notNull(conference);

		Conference result;

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		this.actorService.checkUserLoginAdministrator(actorLogged);

		Assert.isTrue(!conference.getIsFinalMode(), "You can only save conferences that are not in final mode");

		final Date currentMoment = new Date(System.currentTimeMillis());
		final Date submissionDeadline = conference.getSubmissionDeadline();
		final Date notificationDeadline = conference.getNotificationDeadline();
		final Date cameraReadyDeadline = conference.getCameraReadyDeadline();
		final Date startDate = conference.getStartDate();
		final Date endDate = conference.getEndDate();

		Assert.isTrue(submissionDeadline.compareTo(currentMoment) > 0, "The submission deadline must be future");
		Assert.isTrue(notificationDeadline.compareTo(currentMoment) > 0, "The notification deadline must be future");
		Assert.isTrue(cameraReadyDeadline.compareTo(currentMoment) > 0, "The camera-ready deadline must be future");
		Assert.isTrue(startDate.compareTo(currentMoment) > 0, "The start date must be future");
		Assert.isTrue(endDate.compareTo(currentMoment) > 0, "The end date must be future");

		Assert.isTrue(submissionDeadline.compareTo(notificationDeadline) < 0, "The submission deadline must be before the notification deadline");
		Assert.isTrue(notificationDeadline.compareTo(cameraReadyDeadline) < 0, "The notification deadline must be before the camera-ready deadline");
		Assert.isTrue(cameraReadyDeadline.compareTo(startDate) < 0, "The camera-ready deadline must be before the start date");
		Assert.isTrue(startDate.compareTo(endDate) < 0, "The start date must be before the end date");

		result = this.conferenceRepository.save(conference);

		return result;
	}

	public Conference saveAuxiliar(final Conference conference) {
		Assert.notNull(conference);

		Conference result;
		result = this.conferenceRepository.save(conference);

		return result;
	}

	//R14.2
	public void delete(final Conference conference) {
		Assert.notNull(conference);
		Assert.isTrue(conference.getId() != 0);
		Assert.isTrue(this.conferenceRepository.exists(conference.getId()));

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		this.actorService.checkUserLoginAdministrator(actorLogged);

		Assert.isTrue(!conference.getIsFinalMode(), "You can only delete conferences that are not in final mode");

		this.conferenceRepository.delete(conference);
	}

	//R14.2
	public Conference changeFinalMode(final Conference conference) {
		Assert.notNull(conference);
		Assert.isTrue(conference.getId() != 0);
		Assert.isTrue(this.conferenceRepository.exists(conference.getId()));

		Conference result;

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		this.actorService.checkUserLoginAdministrator(actorLogged);

		Assert.isTrue(!conference.getIsFinalMode(), "This conference is already in final mode");
		conference.setIsFinalMode(true);

		result = this.conferenceRepository.save(conference);

		return result;
	}

	// Other business methods
	//R11.1
	public Collection<Conference> findForthcomingConferencesFinalMode() {
		Collection<Conference> result;

		result = this.conferenceRepository.findForthcomingConferencesFinalMode();
		Assert.notNull(result);

		return result;
	}

	//R11.2
	public Collection<Conference> findPastConferencesFinalMode() {
		Collection<Conference> result;

		result = this.conferenceRepository.findPastConferencesFinalMode();
		Assert.notNull(result);

		return result;
	}

	//R11.3
	public Collection<Conference> findRunningConferencesFinalMode() {
		Collection<Conference> result;

		result = this.conferenceRepository.findRunningConferencesFinalMode();
		Assert.notNull(result);

		return result;
	}

	//R11.4
	public Collection<Conference> findConferencesFinalModeBySingleKeyWord(final String singleKeyWord) {
		Collection<Conference> result;

		result = this.conferenceRepository.findConferencesFinalModeBySingleKeyWord(singleKeyWord);
		Assert.notNull(result);

		return result;
	}

	public Collection<Conference> findConferencesToSubmit() {
		Collection<Conference> result;

		result = this.conferenceRepository.findConferencesToSubmit();
		Assert.notNull(result);

		return result;
	}

	public Collection<Conference> findConferencesFinalModeNotStartDateDeadlineNotRegistrated() {
		Collection<Conference> result;

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		this.actorService.checkUserLoginAuthor(actorLogged);

		result = this.conferenceRepository.findConferencesFinalModeNotStartDateDeadlineNotRegistrated();
		Assert.notNull(result);

		return result;
	}

	public Collection<Conference> findConferencesRegistratedByAuthorId() {
		Collection<Conference> result;

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		this.actorService.checkUserLoginAuthor(actorLogged);

		result = this.conferenceRepository.findConferencesRegistratedByAuthorId(actorLogged.getId());
		Assert.notNull(result);

		return result;
	}

	//R14.1
	public Collection<Conference> findConferencesSubmissionDeadlineLastFiveDays() {
		Collection<Conference> result;

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		this.actorService.checkUserLoginAdministrator(actorLogged);

		result = this.conferenceRepository.findConferencesSubmissionDeadlineLastFiveDays();
		Assert.notNull(result);

		return result;
	}

	//R14.1
	public Collection<Conference> findConferencesNotificationDeadlineInLessFiveDays() {
		Collection<Conference> result;

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		this.actorService.checkUserLoginAdministrator(actorLogged);

		result = this.conferenceRepository.findConferencesNotificationDeadlineInLessFiveDays();
		Assert.notNull(result);

		return result;
	}

	//R14.1
	public Collection<Conference> findConferencesCameraReadyDeadlineInLessFiveDays() {
		Collection<Conference> result;

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		this.actorService.checkUserLoginAdministrator(actorLogged);

		result = this.conferenceRepository.findConferencesCameraReadyDeadlineInLessFiveDays();
		Assert.notNull(result);

		return result;
	}

	//R14.1
	public Collection<Conference> findConferencesStartDateInLessFiveDays() {
		Collection<Conference> result;

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		this.actorService.checkUserLoginAdministrator(actorLogged);

		result = this.conferenceRepository.findConferencesStartDateInLessFiveDays();
		Assert.notNull(result);

		return result;
	}

}

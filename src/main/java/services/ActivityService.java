
package services;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ActivityRepository;
import domain.Activity;
import domain.Actor;
import domain.Conference;

@Service
@Transactional
public class ActivityService {

	// Managed repository
	@Autowired
	private ActivityRepository	activityRepository;

	// Supporting services
	@Autowired
	private ActorService		actorService;

	@Autowired
	private ConferenceService	conferenceService;


	// Simple CRUD methods
	public Collection<Activity> findAll() {
		Collection<Activity> result;

		result = this.activityRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Activity findOne(final int activityId) {
		Assert.isTrue(activityId != 0);

		Activity result;

		result = this.activityRepository.findOne(activityId);
		Assert.notNull(result);

		return result;
	}

	//R14.6
	public Activity save(final Activity activity, final Conference conference) {
		Assert.notNull(activity);

		Activity result;

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		this.actorService.checkUserLoginAdministrator(actorLogged);

		final Calendar endDate = Calendar.getInstance();
		endDate.setTime(conference.getEndDate());
		endDate.add(Calendar.DATE, 1);

		Assert.isTrue(conference.getIsFinalMode(), "Activities can only be managed if the conference is in final mode");
		Assert.isTrue(conference.getStartDate().compareTo(new Date(System.currentTimeMillis())) > 0, "Activities can only be managed if the start date of the conference has not passed");
		Assert.isTrue(activity.getStartMoment().compareTo(conference.getStartDate()) >= 0 && activity.getStartMoment().compareTo(endDate.getTime()) < 0,
			"The start moment of an activity must be equal to or later than the start date of the conference and lower than the end date of the conference");

		if (activity.getId() == 0) {
			result = this.activityRepository.save(activity);
			conference.getActivities().add(result);
			this.conferenceService.saveAuxiliar(conference);
		} else
			result = this.activityRepository.save(activity);

		return result;
	}

	//R14.6
	public void delete(final Activity activity, final Conference conference) {
		Assert.notNull(activity);
		Assert.isTrue(activity.getId() != 0);
		Assert.isTrue(this.activityRepository.exists(activity.getId()));

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		this.actorService.checkUserLoginAdministrator(actorLogged);

		conference.getActivities().remove(activity);
		this.conferenceService.saveAuxiliar(conference);
		this.activityRepository.delete(activity);
	}

	// Other business methods

}

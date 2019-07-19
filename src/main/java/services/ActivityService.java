
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ActivityRepository;
import domain.Activity;
import domain.Actor;

@Service
@Transactional
public class ActivityService {

	// Managed repository
	@Autowired
	private ActivityRepository	activityRepository;

	// Supporting services
	@Autowired
	private ActorService		actorService;


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

	public Activity save(final Activity activity) {
		Assert.notNull(activity);

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);

		Activity result;

		if (activity.getId() == 0)
			result = this.activityRepository.save(activity);
		else
			result = this.activityRepository.save(activity);

		return result;
	}

	public void delete(final Activity activity) {
		Assert.notNull(activity);
		Assert.isTrue(activity.getId() != 0);
		Assert.isTrue(this.activityRepository.exists(activity.getId()));

		this.activityRepository.delete(activity);
	}

	// Other business methods

}

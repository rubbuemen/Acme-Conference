
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.PanelRepository;
import domain.Actor;
import domain.Conference;
import domain.Panel;

@Service
@Transactional
public class PanelService {

	// Managed repository
	@Autowired
	private PanelRepository	panelRepository;

	// Supporting services
	@Autowired
	private ActorService	actorService;

	@Autowired
	private ActivityService	activityService;


	// Simple CRUD methods
	//R14.6
	public Panel create() {
		Panel result;

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		this.actorService.checkUserLoginAdministrator(actorLogged);

		result = new Panel();

		return result;
	}

	public Collection<Panel> findAll() {
		Collection<Panel> result;

		result = this.panelRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Panel findOne(final int panelId) {
		Assert.isTrue(panelId != 0);

		Panel result;

		result = this.panelRepository.findOne(panelId);
		Assert.notNull(result);

		return result;
	}

	//R14.6
	public Panel save(final Panel panel, final Conference conference) {
		Assert.notNull(panel);

		Panel result;

		result = (Panel) this.activityService.save(panel, conference);
		result = this.panelRepository.save(result);

		return result;
	}

	public void delete(final Panel panel) {
		Assert.notNull(panel);
		Assert.isTrue(panel.getId() != 0);
		Assert.isTrue(this.panelRepository.exists(panel.getId()));

		this.panelRepository.delete(panel);
	}

	// Other business methods
	//R14.6
	public Collection<Panel> findPanelsByConference(final Conference conference) {
		Collection<Panel> result;

		Assert.isTrue(conference.getIsFinalMode(), "Activities can only be managed if the conference is in final mode");

		result = this.panelRepository.findPanelsByConferenceId(conference.getId());

		return result;

	}

}

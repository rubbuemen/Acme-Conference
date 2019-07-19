
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.PanelRepository;
import domain.Actor;
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


	// Simple CRUD methods
	public Panel create() {
		Panel result;

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);

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

	public Panel save(final Panel panel) {
		Assert.notNull(panel);

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);

		Panel result;

		if (panel.getId() == 0)
			result = this.panelRepository.save(panel);
		else
			result = this.panelRepository.save(panel);

		return result;
	}

	public void delete(final Panel panel) {
		Assert.notNull(panel);
		Assert.isTrue(panel.getId() != 0);
		Assert.isTrue(this.panelRepository.exists(panel.getId()));

		this.panelRepository.delete(panel);
	}

	// Other business methods

}

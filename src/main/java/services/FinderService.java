
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.FinderRepository;
import domain.Actor;
import domain.Finder;

@Service
@Transactional
public class FinderService {

	// Managed repository
	@Autowired
	private FinderRepository	finderRepository;

	// Supporting services
	@Autowired
	private ActorService		actorService;


	// Simple CRUD methods
	public Finder create() {
		Finder result;

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);

		result = new Finder();

		return result;
	}

	public Collection<Finder> findAll() {
		Collection<Finder> result;

		result = this.finderRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Finder findOne(final int finderId) {
		Assert.isTrue(finderId != 0);

		Finder result;

		result = this.finderRepository.findOne(finderId);
		Assert.notNull(result);

		return result;
	}

	public Finder save(final Finder finder) {
		Assert.notNull(finder);

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);

		Finder result;

		if (finder.getId() == 0)
			result = this.finderRepository.save(finder);
		else
			result = this.finderRepository.save(finder);

		return result;
	}

	public void delete(final Finder finder) {
		Assert.notNull(finder);
		Assert.isTrue(finder.getId() != 0);
		Assert.isTrue(this.finderRepository.exists(finder.getId()));

		this.finderRepository.delete(finder);
	}

	// Other business methods

}


package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ActorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Actor;

@Service
@Transactional
public class ActorService {

	// Managed repository
	@Autowired
	private ActorRepository				actorRepository;

	// Supporting services
	@Autowired
	private UserAccountService			userAccountService;

	@Autowired
	private SystemConfigurationService	systemConfigurationService;


	// Simple CRUD methods
	public Collection<Actor> findAll() {
		Collection<Actor> result;

		result = this.actorRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Actor findOne(final int actorId) {
		Assert.isTrue(actorId != 0);
		Actor result;

		result = this.actorRepository.findOne(actorId);
		Assert.notNull(result);

		return result;
	}

	public Actor save(final Actor actor) {
		Assert.notNull(actor);

		Actor result;

		Assert.isTrue(actor.getUserAccount().getStatusAccount());

		if (actor.getId() == 0) {
			final UserAccount userAccount = this.userAccountService.save(actor.getUserAccount());
			actor.setUserAccount(userAccount);
		} else {
			final Actor actorLogged = this.findActorLogged();
			Assert.notNull(actorLogged);
			Assert.isTrue(actor.equals(actorLogged));
		}

		if (actor.getPhoneNumber() != null) {
			String phoneNumber = actor.getPhoneNumber();
			final String phoneCountryCode = this.systemConfigurationService.getConfiguration().getPhoneCountryCode();
			if (!actor.getPhoneNumber().isEmpty() && !actor.getPhoneNumber().startsWith("+"))
				phoneNumber = phoneCountryCode + " " + phoneNumber;
			actor.setPhoneNumber(phoneNumber);
		}

		result = this.actorRepository.save(actor);

		return result;
	}

	public Actor saveAuxiliar(final Actor actor) {
		Assert.notNull(actor);

		Actor result;

		result = this.actorRepository.save(actor);

		return result;
	}

	// Other business methods
	public Actor findActorLogged() {
		Actor result;
		UserAccount userAccount;

		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);

		result = this.actorRepository.findActorByUserAccountId(userAccount.getId());
		Assert.notNull(result);

		return result;
	}

	public Actor getSystemActor() {
		Actor result;

		result = this.actorRepository.getSystemActor();
		Assert.notNull(result);

		return result;
	}

	public void checkUserLoginAuthor(final Actor actor) {
		final Authority auth = new Authority();
		auth.setAuthority(Authority.AUTHOR);
		final Collection<Authority> authorities = actor.getUserAccount().getAuthorities();
		Assert.isTrue(authorities.contains(auth), "The logged actor is not an author");
	}

	public void checkUserLoginReviewer(final Actor actor) {
		final Authority auth = new Authority();
		auth.setAuthority(Authority.REVIEWER);
		final Collection<Authority> authorities = actor.getUserAccount().getAuthorities();
		Assert.isTrue(authorities.contains(auth), "The logged actor is not a reviewer");
	}

	public void checkUserLoginAdministrator(final Actor actor) {
		final Authority auth = new Authority();
		auth.setAuthority(Authority.ADMIN);
		final Collection<Authority> authorities = actor.getUserAccount().getAuthorities();
		Assert.isTrue(authorities.contains(auth), "The logged actor is not an administrator");
	}

	public void checkUserLoginSponsor(final Actor actor) {
		final Authority auth = new Authority();
		auth.setAuthority(Authority.SPONSOR);
		final Collection<Authority> authorities = actor.getUserAccount().getAuthorities();
		Assert.isTrue(authorities.contains(auth), "The logged actor is not a sponsor");
	}

	public Actor findActorByMessageId(final int messageId) {
		Assert.isTrue(messageId != 0);

		Actor result;

		result = this.actorRepository.findActorByMessageId(messageId);
		return result;
	}

	public Collection<Actor> findAllActorsExceptLogged() {
		Collection<Actor> result;

		final Actor actorLogged = this.findActorLogged();
		final Actor systemActor = this.actorRepository.getSystemActor();
		result = this.actorRepository.findAll();
		result.remove(actorLogged);
		result.remove(systemActor);

		return result;
	}

}

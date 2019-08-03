
package services;

import java.util.Collection;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.SponsorRepository;
import security.Authority;
import security.UserAccount;
import domain.Message;
import domain.Sponsor;
import domain.Sponsorship;

@Service
@Transactional
public class SponsorService {

	// Managed repository
	@Autowired
	private SponsorRepository	sponsorRepository;

	// Supporting services
	@Autowired
	private ActorService		actorService;

	@Autowired
	private UserAccountService	userAccountService;


	// Simple CRUD methods
	//R29.1
	public Sponsor create() {
		Sponsor result;

		result = new Sponsor();
		final Collection<Message> messages = new HashSet<>();
		final Collection<Sponsorship> sponsorships = new HashSet<>();
		final UserAccount userAccount = this.userAccountService.create();
		final Authority auth = new Authority();

		auth.setAuthority(Authority.SPONSOR);
		result.setMessages(messages);
		userAccount.addAuthority(auth);
		result.setSponsorships(sponsorships);
		result.setUserAccount(userAccount);

		return result;
	}

	public Collection<Sponsor> findAll() {
		Collection<Sponsor> result;

		result = this.sponsorRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Sponsor findOne(final int sponsorId) {
		Assert.isTrue(sponsorId != 0);

		Sponsor result;

		result = this.sponsorRepository.findOne(sponsorId);
		Assert.notNull(result);

		return result;
	}

	//R29.1
	public Sponsor save(final Sponsor sponsor) {
		Assert.notNull(sponsor);

		Sponsor result;

		result = (Sponsor) this.actorService.save(sponsor);
		result = this.sponsorRepository.save(result);

		return result;
	}

	public void delete(final Sponsor sponsor) {
		Assert.notNull(sponsor);
		Assert.isTrue(sponsor.getId() != 0);
		Assert.isTrue(this.sponsorRepository.exists(sponsor.getId()));

		this.sponsorRepository.delete(sponsor);
	}

	// Other business methods
	public Sponsor findSponsorBySponsorshipId(final int sponsorshipId) {
		Assert.isTrue(sponsorshipId != 0);

		Sponsor result;

		result = this.sponsorRepository.findSponsorBySponsorshipId(sponsorshipId);
		return result;
	}

}

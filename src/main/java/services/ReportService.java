
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ReportRepository;
import domain.Actor;
import domain.Report;

@Service
@Transactional
public class ReportService {

	// Managed repository
	@Autowired
	private ReportRepository	reportRepository;

	// Supporting services
	@Autowired
	private ActorService		actorService;


	// Simple CRUD methods
	public Report create() {
		Report result;

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);

		result = new Report();

		return result;
	}

	public Collection<Report> findAll() {
		Collection<Report> result;

		result = this.reportRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Report findOne(final int reportId) {
		Assert.isTrue(reportId != 0);

		Report result;

		result = this.reportRepository.findOne(reportId);
		Assert.notNull(result);

		return result;
	}

	public Report save(final Report report) {
		Assert.notNull(report);

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);

		Report result;

		if (report.getId() == 0)
			result = this.reportRepository.save(report);
		else
			result = this.reportRepository.save(report);

		return result;
	}

	public void delete(final Report report) {
		Assert.notNull(report);
		Assert.isTrue(report.getId() != 0);
		Assert.isTrue(this.reportRepository.exists(report.getId()));

		this.reportRepository.delete(report);
	}

	// Other business methods

}

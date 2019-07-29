
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ReportRepository;
import domain.Actor;
import domain.Report;
import domain.Reviewer;
import domain.Submission;

@Service
@Transactional
public class ReportService {

	// Managed repository
	@Autowired
	private ReportRepository	reportRepository;

	// Supporting services
	@Autowired
	private ActorService		actorService;

	@Autowired
	private SubmissionService	submissionService;

	@Autowired
	private ReviewerService		reviewerService;


	// Simple CRUD methods
	//R5
	public Report create() {
		Report result;

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		this.actorService.checkUserLoginReviewer(actorLogged);

		result = new Report();
		result.setStatus("BORDER-LINE");

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

	//R5
	public Report save(final Report report) {
		Assert.notNull(report);

		Report result;

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		this.actorService.checkUserLoginReviewer(actorLogged);

		final Reviewer reviewerLogged = (Reviewer) actorLogged;

		final Collection<Submission> findSubmissionsToReportReviewerLogged = this.submissionService.findSubmissionsToReportReviewerLogged();
		Assert.isTrue(findSubmissionsToReportReviewerLogged.contains(report.getSubmission()), "This submission is not available to report");

		result = this.reportRepository.save(report);
		final Collection<Report> reportsReviewerLogged = reviewerLogged.getReports();
		reportsReviewerLogged.add(result);
		reviewerLogged.setReports(reportsReviewerLogged);
		this.reviewerService.save(reviewerLogged);

		return result;
	}

	public void delete(final Report report) {
		Assert.notNull(report);
		Assert.isTrue(report.getId() != 0);
		Assert.isTrue(this.reportRepository.exists(report.getId()));

		this.reportRepository.delete(report);
	}

	// Other business methods
	public Collection<Report> findReportsByReviewerLogged() {
		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		this.actorService.checkUserLoginReviewer(actorLogged);

		Collection<Report> result;

		final Reviewer reviewerLogged = (Reviewer) actorLogged;

		result = this.reportRepository.findReportsByReviewerId(reviewerLogged.getId());
		Assert.notNull(result);

		return result;
	}

	public Report findReportReviewerLogged(final int reportId) {
		Assert.isTrue(reportId != 0);

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		this.actorService.checkUserLoginReviewer(actorLogged);

		final Reviewer reviewerOwner = this.reviewerService.findReviewerByReportId(reportId);
		Assert.isTrue(actorLogged.equals(reviewerOwner), "The logged actor is not the owner of this entity");

		Report result;

		result = this.reportRepository.findOne(reportId);
		Assert.notNull(result);

		return result;
	}

	public Report acceptReport(final Report report) {
		Assert.notNull(report);
		Assert.isTrue(report.getId() != 0);
		Assert.isTrue(this.reportRepository.exists(report.getId()));

		Report result;

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		this.actorService.checkUserLoginReviewer(actorLogged);

		final Reviewer reviewerOwner = this.reviewerService.findReviewerByReportId(report.getId());
		Assert.isTrue(actorLogged.equals(reviewerOwner), "The logged actor is not the owner of this entity");

		Assert.isTrue(report.getStatus().equals("BORDER-LINE"), "The status of this report is not 'BORDER-LINE'");
		report.setStatus("ACCEPT");

		result = this.reportRepository.save(report);

		return result;
	}

	public Report rejectReport(final Report report) {
		Assert.notNull(report);
		Assert.isTrue(report.getId() != 0);
		Assert.isTrue(this.reportRepository.exists(report.getId()));

		Report result;

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		this.actorService.checkUserLoginReviewer(actorLogged);

		final Reviewer reviewerOwner = this.reviewerService.findReviewerByReportId(report.getId());
		Assert.isTrue(actorLogged.equals(reviewerOwner), "The logged actor is not the owner of this entity");

		Assert.isTrue(report.getStatus().equals("BORDER-LINE"), "The status of this report is not 'BORDER-LINE'");
		report.setStatus("REJECT");

		result = this.reportRepository.save(report);

		return result;
	}

	public Collection<Report> findReportsBySubmissionId(final int submissionId) {
		Collection<Report> result;

		result = this.reportRepository.findReportsBySubmissionId(submissionId);
		Assert.notNull(result);

		return result;
	}

}


package services;

import java.util.Collection;
import java.util.Date;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.SubmissionRepository;
import domain.Actor;
import domain.Author;
import domain.Conference;
import domain.Paper;
import domain.Submission;

@Service
@Transactional
public class SubmissionService {

	// Managed repository
	@Autowired
	private SubmissionRepository	submissionRepository;

	// Supporting services
	@Autowired
	private ActorService			actorService;

	@Autowired
	private PaperService			paperService;

	@Autowired
	private ConferenceService		conferenceService;

	@Autowired
	private AuthorService			authorService;


	// Simple CRUD methods
	//R13.1
	public Submission create() {
		Submission result;

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		this.actorService.checkUserLoginAuthor(actorLogged);

		result = new Submission();
		final Paper paper = this.paperService.create();
		final String ticker = this.generateTicker();
		final Date moment = new Date(System.currentTimeMillis() - 1);

		result.setPaper(paper);
		result.setTicker(ticker);
		result.setMoment(moment);
		result.setStatus("UNDER-REVIEW");
		result.setIsAssigned(false);
		result.setIsNotified(false);

		return result;
	}

	public Collection<Submission> findAll() {
		Collection<Submission> result;

		result = this.submissionRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Submission findOne(final int submissionId) {
		Assert.isTrue(submissionId != 0);

		Submission result;

		result = this.submissionRepository.findOne(submissionId);
		Assert.notNull(result);

		return result;
	}

	//R13.1
	public Submission save(final Submission submission) {
		Assert.notNull(submission);

		Submission result;

		if (submission.getId() == 0) {
			final Actor actorLogged = this.actorService.findActorLogged();
			Assert.notNull(actorLogged);
			this.actorService.checkUserLoginAuthor(actorLogged);
			final Author authorLogged = (Author) actorLogged;

			Assert.isTrue(!submission.getIsAssigned(), "When creating a submission, it has not yet been possible to make an assignment");
			Assert.isTrue(!submission.getIsNotified(), "When creating a submission it has not yet been possible to notify");
			Assert.isTrue(!submission.getPaper().getIsCameraReadyVersion(), "The submission must include a paper to review only, not a camera-ready version");
			final Collection<Conference> findConferencesToSubmit = this.conferenceService.findConferencesToSubmit();
			Assert.isTrue(findConferencesToSubmit.contains(submission.getConference()), "This conference is not available to submit");

			Paper paper = submission.getPaper();
			paper = this.paperService.save(paper);
			submission.setPaper(paper);

			result = this.submissionRepository.save(submission);
			final Collection<Submission> submissionsAuthorLogged = authorLogged.getSubmissions();
			submissionsAuthorLogged.add(result);
			authorLogged.setSubmissions(submissionsAuthorLogged);
			this.authorService.save(authorLogged);
		} else
			//Se harán cosas como cambiar el isAssigned o el isNotified
			result = this.submissionRepository.save(submission);

		return result;
	}

	public void delete(final Submission submission) {
		Assert.notNull(submission);
		Assert.isTrue(submission.getId() != 0);
		Assert.isTrue(this.submissionRepository.exists(submission.getId()));

		this.submissionRepository.delete(submission);
	}

	// Other business methods
	//R4
	public String generateTicker() {
		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);

		final String letterName = actorLogged.getName().substring(0, 1).toUpperCase();
		String letterMiddleName = "X";
		if (actorLogged.getMiddleName() != null || !actorLogged.getMiddleName().isEmpty())
			letterMiddleName = actorLogged.getMiddleName().substring(0, 1).toUpperCase();
		final String letterSurname = actorLogged.getSurname().substring(0, 1).toUpperCase();

		String result = letterName + letterMiddleName + letterSurname;
		result = result + "-" + RandomStringUtils.randomAlphanumeric(4).toUpperCase();

		return result;
	}

	//R13.1
	public Collection<Submission> findSubmissionsByAuthorLogged() {
		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		this.actorService.checkUserLoginAuthor(actorLogged);

		Collection<Submission> result;

		final Author auditorLogged = (Author) actorLogged;

		result = this.submissionRepository.findSubmissionsByAuthorId(auditorLogged.getId());
		Assert.notNull(result);

		return result;
	}

	public Submission findSubmissionAuthorLogged(final int submissionId) {
		Assert.isTrue(submissionId != 0);

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		this.actorService.checkUserLoginAuthor(actorLogged);

		final Author authorOwner = this.authorService.findAuthorBySubmissionId(submissionId);
		Assert.isTrue(actorLogged.equals(authorOwner), "The logged actor is not the owner of this entity");

		Submission result;

		result = this.submissionRepository.findOne(submissionId);
		Assert.notNull(result);

		return result;
	}

	//R13.2
	public Submission uploadCameraReadyVersion(final Submission submission) {
		Submission result;
		Assert.notNull(submission);
		Assert.isTrue(submission.getId() != 0);
		Assert.isTrue(this.submissionRepository.exists(submission.getId()));

		Assert.isTrue(!submission.getPaper().getIsCameraReadyVersion(), "The submission paper already has a camera-ready version");
		Assert.isTrue(submission.getStatus().equals("ACCEPTED"), "To upload the camera-ready version the submission must be accepted");
		Assert.isTrue(submission.getConference().getCameraReadyDeadline().compareTo(new Date(System.currentTimeMillis())) >= 0, "You can't upload the camera-ready version because the camera-ready deadline of the conference has elipsed");
		submission.getPaper().setIsCameraReadyVersion(true);
		this.paperService.save(submission.getPaper());

		result = this.submissionRepository.save(submission);

		return result;
	}

}

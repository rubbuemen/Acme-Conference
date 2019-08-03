
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.SubmissionRepository;
import utilities.internal.MapSortedByEntries;
import domain.Actor;
import domain.Author;
import domain.Conference;
import domain.Paper;
import domain.Reviewer;
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

	@Autowired
	private ReviewerService			reviewerService;


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
		Assert.isTrue(submission.getId() == 0); //Sólo se crearán submissions, no se editarán

		Submission result;

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

		return result;
	}

	public Submission saveAuxiliar(final Submission submission) {
		Assert.notNull(submission);

		Submission result;

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
	public void uploadCameraReadyVersion(final Submission submission) {
		Assert.notNull(submission);
		Assert.isTrue(submission.getId() != 0);
		Assert.isTrue(this.submissionRepository.exists(submission.getId()));

		Assert.isTrue(!submission.getPaper().getIsCameraReadyVersion(), "The submission paper already has a camera-ready version");
		Assert.isTrue(submission.getStatus().equals("ACCEPTED"), "To upload the camera-ready version the submission must be accepted");
		Assert.isTrue(submission.getConference().getCameraReadyDeadline().compareTo(new Date(System.currentTimeMillis())) > 0, "You can't upload the camera-ready version because the camera-ready deadline of the conference has elipsed");
		submission.getPaper().setIsCameraReadyVersion(true);
		this.paperService.save(submission.getPaper());

		this.submissionRepository.save(submission);
	}

	//R14.3
	public Collection<Submission> findSubmissionsByStatus() {
		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		this.actorService.checkUserLoginAdministrator(actorLogged);

		Collection<Submission> result;

		result = this.submissionRepository.findSubmissionsByStatus();
		Assert.notNull(result);

		return result;
	}

	private Pattern patternKeywords(final Reviewer reviewer) {
		final Collection<String> keywords = reviewer.getKeywords();
		String pattern = "";
		for (final String kw : keywords)
			pattern = pattern + (kw + "|");
		pattern = pattern.substring(0, pattern.length() - 1);
		final Pattern result = Pattern.compile(pattern);
		return result;
	}

	//R14.3
	public void assignSubmission(final Submission submission) {
		Assert.notNull(submission);
		Assert.isTrue(submission.getId() != 0);
		Assert.isTrue(this.submissionRepository.exists(submission.getId()));

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		this.actorService.checkUserLoginAdministrator(actorLogged);

		Assert.isTrue(!submission.getIsAssigned(), "The submission is already assigned");
		Assert.isTrue(submission.getConference().getStartDate().compareTo(new Date(System.currentTimeMillis())) > 0, "You cannot assign the submission because the conference start date has passed");

		Matcher keywordsTitle, keywordsSummary;
		final Map<Reviewer, Integer> matchesByReviewer = new HashMap<>();
		final Collection<Reviewer> reviewers = this.reviewerService.findAll();
		final Conference conferenceSubmission = submission.getConference();

		for (final Reviewer r : reviewers) {
			Integer countWordMatches = 0;
			keywordsTitle = this.patternKeywords(r).matcher(conferenceSubmission.getTitle().toLowerCase());
			keywordsSummary = this.patternKeywords(r).matcher(conferenceSubmission.getSummary().toLowerCase());
			while (keywordsTitle.find())
				countWordMatches++;
			while (keywordsSummary.find())
				countWordMatches++;
			if (countWordMatches > 0)
				matchesByReviewer.put(r, countWordMatches);
		}

		final List<Entry<Reviewer, Integer>> matchesByReviewerSorted = new ArrayList<>(MapSortedByEntries.entriesSortedByValues(matchesByReviewer));
		Collections.reverse(matchesByReviewerSorted);

		if (matchesByReviewerSorted.size() > 3)
			for (int i = 0; i < 3; i++) {
				final Reviewer r = matchesByReviewerSorted.get(i).getKey();
				submission.getReviewers().add(r);
			}
		else
			for (int i = 0; i < matchesByReviewerSorted.size(); i++) {
				final Reviewer r = matchesByReviewerSorted.get(i).getKey();
				submission.getReviewers().add(r);
			}

		submission.setIsAssigned(true);
		this.submissionRepository.save(submission);
	}

	public Map<Submission, Author> getMapSubmissionAuthor(final Collection<Submission> submissions) {
		final Map<Submission, Author> result = new HashMap<>();

		if (submissions != null)
			for (final Submission s : submissions) {
				final Author author = this.authorService.findAuthorBySubmissionId(s.getId());
				result.put(s, author);
			}
		return result;
	}

	public Collection<Submission> findSubmissionsToReportReviewerLogged() {
		Collection<Submission> result, submissionsAlreadyReported;

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		this.actorService.checkUserLoginReviewer(actorLogged);

		final Reviewer reviewerLogged = (Reviewer) actorLogged;

		result = this.submissionRepository.findSubmissionsToReportByReviewerId(reviewerLogged.getId());
		submissionsAlreadyReported = this.submissionRepository.findSubmissionsAlreadyReportedByReviewerId(reviewerLogged.getId());
		result.removeAll(submissionsAlreadyReported);

		Assert.notNull(result);

		return result;
	}

	public Collection<Submission> findSubmissionsUnderReviewByConferenceId(final int conferenceId) {
		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		this.actorService.checkUserLoginAdministrator(actorLogged);

		Collection<Submission> result;

		result = this.submissionRepository.findSubmissionsUnderReviewByConferenceId(conferenceId);
		Assert.notNull(result);

		return result;
	}

	public Collection<Submission> findSubmissionsAcceptedOrRejectedNotNotifiedNoDeadline() {
		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		this.actorService.checkUserLoginAdministrator(actorLogged);

		Collection<Submission> result;

		result = this.submissionRepository.findSubmissionsAcceptedOrRejectedNotNotifiedNoDeadline();
		Assert.notNull(result);

		return result;
	}

	public Collection<Submission> findSubmissionsAcceptedByConferenceId(final int conferenceId) {
		Collection<Submission> result;

		result = this.submissionRepository.findSubmissionsAcceptedByConferenceId(conferenceId);
		Assert.notNull(result);

		return result;
	}

}

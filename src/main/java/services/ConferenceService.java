
package services;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ConferenceRepository;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;

import domain.Activity;
import domain.Actor;
import domain.Category;
import domain.Comment;
import domain.Conference;
import domain.Registration;
import domain.Report;
import domain.Submission;

@Service
@Transactional
public class ConferenceService {

	// Managed repository
	@Autowired
	private ConferenceRepository	conferenceRepository;

	// Supporting services
	@Autowired
	private ActorService			actorService;

	@Autowired
	private SubmissionService		submissionService;

	@Autowired
	private ReportService			reportService;


	// Simple CRUD methods
	//R14.2
	public Conference create() {
		Conference result;

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		this.actorService.checkUserLoginAdministrator(actorLogged);

		final Collection<Activity> activities = new HashSet<>();
		final Collection<Registration> registrations = new HashSet<>();
		final Collection<Comment> comments = new HashSet<>();

		result = new Conference();
		result.setIsFinalMode(false);
		result.setActivities(activities);
		result.setRegistrations(registrations);
		result.setComments(comments);

		return result;
	}
	public Collection<Conference> findAll() {
		Collection<Conference> result;

		result = this.conferenceRepository.findAll();
		Assert.notNull(result);

		return result;
	}

	public Conference findOne(final int conferenceId) {
		Assert.isTrue(conferenceId != 0);

		Conference result;

		result = this.conferenceRepository.findOne(conferenceId);
		Assert.notNull(result);

		return result;
	}

	//R14.2
	public Conference save(final Conference conference) {
		Assert.notNull(conference);

		Conference result;

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		this.actorService.checkUserLoginAdministrator(actorLogged);

		Assert.isTrue(!conference.getIsFinalMode(), "You can only save conferences that are not in final mode");

		final Date currentMoment = new Date(System.currentTimeMillis());
		final Date submissionDeadline = conference.getSubmissionDeadline();
		final Date notificationDeadline = conference.getNotificationDeadline();
		final Date cameraReadyDeadline = conference.getCameraReadyDeadline();
		final Date startDate = conference.getStartDate();
		final Date endDate = conference.getEndDate();

		Assert.isTrue(submissionDeadline.compareTo(currentMoment) > 0, "The submission deadline must be future");
		Assert.isTrue(notificationDeadline.compareTo(currentMoment) > 0, "The notification deadline must be future");
		Assert.isTrue(cameraReadyDeadline.compareTo(currentMoment) > 0, "The camera-ready deadline must be future");
		Assert.isTrue(startDate.compareTo(currentMoment) > 0, "The start date must be future");
		Assert.isTrue(endDate.compareTo(currentMoment) > 0, "The end date must be future");

		Assert.isTrue(submissionDeadline.compareTo(notificationDeadline) < 0, "The submission deadline must be before the notification deadline");
		Assert.isTrue(notificationDeadline.compareTo(cameraReadyDeadline) < 0, "The notification deadline must be before the camera-ready deadline");
		Assert.isTrue(cameraReadyDeadline.compareTo(startDate) < 0, "The camera-ready deadline must be before the start date");
		Assert.isTrue(startDate.compareTo(endDate) < 0, "The start date must be before the end date");

		result = this.conferenceRepository.save(conference);

		return result;
	}

	public Conference saveAuxiliar(final Conference conference) {
		Assert.notNull(conference);

		Conference result;
		result = this.conferenceRepository.save(conference);

		return result;
	}

	//R14.2
	public void delete(final Conference conference) {
		Assert.notNull(conference);
		Assert.isTrue(conference.getId() != 0);
		Assert.isTrue(this.conferenceRepository.exists(conference.getId()));

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		this.actorService.checkUserLoginAdministrator(actorLogged);

		Assert.isTrue(!conference.getIsFinalMode(), "You can only delete conferences that are not in final mode");

		this.conferenceRepository.delete(conference);
	}

	//R14.2
	public void changeFinalMode(final Conference conference) {
		Assert.notNull(conference);
		Assert.isTrue(conference.getId() != 0);
		Assert.isTrue(this.conferenceRepository.exists(conference.getId()));

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		this.actorService.checkUserLoginAdministrator(actorLogged);

		Assert.isTrue(!conference.getIsFinalMode(), "This conference is already in final mode");
		conference.setIsFinalMode(true);

		this.conferenceRepository.save(conference);
	}

	// Other business methods
	//R11.1
	public Collection<Conference> findForthcomingConferencesFinalMode() {
		Collection<Conference> result;

		result = this.conferenceRepository.findForthcomingConferencesFinalMode();
		Assert.notNull(result);

		return result;
	}

	//R11.2
	public Collection<Conference> findPastConferencesFinalMode() {
		Collection<Conference> result;

		result = this.conferenceRepository.findPastConferencesFinalMode();
		Assert.notNull(result);

		return result;
	}

	//R11.3
	public Collection<Conference> findRunningConferencesFinalMode() {
		Collection<Conference> result;

		result = this.conferenceRepository.findRunningConferencesFinalMode();
		Assert.notNull(result);

		return result;
	}

	//R11.4
	public Collection<Conference> findConferencesFinalModeBySingleKeyWord(final String singleKeyWord) {
		Collection<Conference> result;

		result = this.conferenceRepository.findConferencesFinalModeBySingleKeyWord(singleKeyWord);
		Assert.notNull(result);

		return result;
	}

	public Collection<Conference> findConferencesToSubmit() {
		Collection<Conference> result;

		result = this.conferenceRepository.findConferencesToSubmit();
		Assert.notNull(result);

		return result;
	}

	public Collection<Conference> findConferencesFinalModeNotStartDateDeadlineNotRegistrated() {
		Collection<Conference> result;

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		this.actorService.checkUserLoginAuthor(actorLogged);

		result = this.conferenceRepository.findConferencesFinalModeNotStartDateDeadlineNotRegistrated();
		Assert.notNull(result);

		return result;
	}

	public Collection<Conference> findConferencesRegistratedByAuthorId() {
		Collection<Conference> result;

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		this.actorService.checkUserLoginAuthor(actorLogged);

		result = this.conferenceRepository.findConferencesRegistratedByAuthorId(actorLogged.getId());
		Assert.notNull(result);

		return result;
	}

	//R14.1
	public Collection<Conference> findConferencesSubmissionDeadlineLastFiveDays() {
		Collection<Conference> result;

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		this.actorService.checkUserLoginAdministrator(actorLogged);

		result = this.conferenceRepository.findConferencesSubmissionDeadlineLastFiveDays();
		Assert.notNull(result);

		return result;
	}

	//R14.1
	public Collection<Conference> findConferencesNotificationDeadlineInLessFiveDays() {
		Collection<Conference> result;

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		this.actorService.checkUserLoginAdministrator(actorLogged);

		result = this.conferenceRepository.findConferencesNotificationDeadlineInLessFiveDays();
		Assert.notNull(result);

		return result;
	}

	//R14.1
	public Collection<Conference> findConferencesCameraReadyDeadlineInLessFiveDays() {
		Collection<Conference> result;

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		this.actorService.checkUserLoginAdministrator(actorLogged);

		result = this.conferenceRepository.findConferencesCameraReadyDeadlineInLessFiveDays();
		Assert.notNull(result);

		return result;
	}

	//R14.1
	public Collection<Conference> findConferencesStartDateInLessFiveDays() {
		Collection<Conference> result;

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		this.actorService.checkUserLoginAdministrator(actorLogged);

		result = this.conferenceRepository.findConferencesStartDateInLessFiveDays();
		Assert.notNull(result);

		return result;
	}

	//R14.4
	public void decisionMakingProcedure(final Conference conference) {
		Assert.notNull(conference);
		Assert.isTrue(conference.getId() != 0);
		Assert.isTrue(this.conferenceRepository.exists(conference.getId()));

		final Actor actorLogged = this.actorService.findActorLogged();
		Assert.notNull(actorLogged);
		this.actorService.checkUserLoginAdministrator(actorLogged);

		Assert.isTrue(!conference.getIsDecisionProcedureDone(), "You cannot run a decision-make procedure because already is done");
		Assert.isTrue(conference.getIsFinalMode(), "You cannot run a decision-make procedure because the conference is not in final mode");
		Assert.isTrue(conference.getSubmissionDeadline().compareTo(new Date(System.currentTimeMillis())) < 0, "You cannot run a decision-make procedure on this conference until the submission deadline has elapsed");
		Assert.isTrue(conference.getStartDate().compareTo(new Date(System.currentTimeMillis())) > 0, "You cannot run a decision-make procedure on this conference because the conference start date has passed");

		final Collection<Submission> submissionsUnderReviewConference = this.submissionService.findSubmissionsUnderReviewByConferenceId(conference.getId());

		for (final Submission s : submissionsUnderReviewConference) {
			final Collection<Report> reportsSubmission = this.reportService.findReportsBySubmissionId(s.getId());
			int countAccept = 0;
			int countReject = 0;
			for (final Report r : reportsSubmission)
				if (r.getStatus().equals("ACCEPT"))
					countAccept++;
				else if (r.getStatus().equals("REJECT"))
					countReject++;
			if (countAccept >= countReject)
				s.setStatus("ACCEPTED");
			else
				s.setStatus("REJECTED");
			this.submissionService.saveAuxiliar(s);
		}

		conference.setIsDecisionProcedureDone(true);
		this.conferenceRepository.save(conference);
	}

	public Conference findConferenceByActivityId(final int activityId) {
		Conference result;

		result = this.conferenceRepository.findConferenceByActivityId(activityId);

		return result;
	}

	public Collection<Conference> findConferencesFinalMode() {
		Collection<Conference> result;

		result = this.conferenceRepository.findConferencesFinalMode();
		Assert.notNull(result);

		return result;
	}

	public Collection<Conference> findConferencesFromFinder(final String keyWord, final Date minDate, final Date maxDate, final Double maxFee, final int categoryId) {
		final Collection<Conference> result = new HashSet<>();
		Collection<Conference> findConferencesFilterByKeyWord = new HashSet<>();
		Collection<Conference> findConferencesFilterByDate = new HashSet<>();
		Collection<Conference> findConferencesFilterByMaxFee = new HashSet<>();
		Collection<Conference> findConferencesFilterByCategoryId = new HashSet<>();
		final Calendar cal1 = Calendar.getInstance();
		final Calendar cal2 = Calendar.getInstance();
		cal1.setTime(minDate);
		cal2.setTime(maxDate);

		if (!keyWord.isEmpty())
			findConferencesFilterByKeyWord = this.conferenceRepository.findConferencesFilterByKeyWord(keyWord);
		if (cal1.get(Calendar.YEAR) != 1000 || cal2.get(Calendar.YEAR) != 3000)
			findConferencesFilterByDate = this.conferenceRepository.findConferencesFilterByDate(minDate, maxDate);
		if (maxFee != 0.0)
			findConferencesFilterByMaxFee = this.conferenceRepository.findConferencesFilterByMaxFee(maxFee);
		if (categoryId != 0)
			findConferencesFilterByCategoryId = this.conferenceRepository.findConferencesFilterByCategoryId(categoryId);

		result.addAll(findConferencesFilterByKeyWord);
		result.addAll(findConferencesFilterByDate);
		result.addAll(findConferencesFilterByMaxFee);
		result.addAll(findConferencesFilterByCategoryId);

		if (!keyWord.isEmpty())
			result.retainAll(findConferencesFilterByKeyWord);
		if (cal1.get(Calendar.YEAR) != 1000 || cal2.get(Calendar.YEAR) != 3000)
			result.retainAll(findConferencesFilterByDate);
		if (maxFee != 0.0)
			result.retainAll(findConferencesFilterByMaxFee);
		if (categoryId != 0)
			result.retainAll(findConferencesFilterByCategoryId);

		return result;
	}

	public Collection<Conference> findConferencesByCategory(final Category category) {
		Collection<Conference> result;

		result = this.conferenceRepository.findConferencesByCategoryId(category.getId());
		Assert.notNull(result);

		return result;
	}

	public Collection<Conference> findConferencesLastYearAndFuture() {
		Collection<Conference> result;

		result = this.conferenceRepository.findConferencesLastYearAndFuture();
		Assert.notNull(result);

		return result;
	}

	public void downloadPDF(final Document document, final Conference conference) throws DocumentException, IOException {
		final Font normalFont = new Font(FontFamily.COURIER, 10, Font.NORMAL, BaseColor.BLACK);
		final Font underlineFont = new Font(FontFamily.COURIER, 11, Font.UNDERLINE, BaseColor.BLACK);
		final Font boldFont = new Font(FontFamily.COURIER, 11, Font.BOLD, BaseColor.BLACK);
		final Font header1Font = new Font(FontFamily.COURIER, 18, Font.BOLD, BaseColor.BLACK);
		final Font header2Font = new Font(FontFamily.COURIER, 16, Font.BOLD, BaseColor.BLACK);

		document.open();
		final Paragraph content = new Paragraph();
		Chunk attribute, contentAttribute;

		//Información de la conferencia
		content.add(new Paragraph("Conference Information:", header1Font));
		content.add(new Paragraph(" "));
		final SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		attribute = new Chunk("Title", underlineFont);
		contentAttribute = new Chunk(": " + conference.getTitle(), normalFont);
		content.add(attribute);
		content.add(contentAttribute);
		content.add(new Paragraph(" "));
		attribute = new Chunk("Acronym", underlineFont);
		contentAttribute = new Chunk(": " + conference.getAcronym(), normalFont);
		content.add(attribute);
		content.add(contentAttribute);
		content.add(new Paragraph(" "));
		attribute = new Chunk("Submission deadline", underlineFont);
		contentAttribute = new Chunk(": " + format.format(conference.getSubmissionDeadline()), normalFont);
		content.add(attribute);
		content.add(contentAttribute);
		content.add(new Paragraph(" "));
		attribute = new Chunk("Notification deadline", underlineFont);
		contentAttribute = new Chunk(": " + format.format(conference.getNotificationDeadline()), normalFont);
		content.add(attribute);
		content.add(contentAttribute);
		content.add(new Paragraph(" "));
		attribute = new Chunk("Camera-ready deadline", underlineFont);
		contentAttribute = new Chunk(": " + format.format(conference.getCameraReadyDeadline()), normalFont);
		content.add(attribute);
		content.add(contentAttribute);
		content.add(new Paragraph(" "));
		attribute = new Chunk("Start date", underlineFont);
		contentAttribute = new Chunk(": " + format.format(conference.getStartDate()), normalFont);
		content.add(attribute);
		content.add(contentAttribute);
		content.add(new Paragraph(" "));
		attribute = new Chunk("End date", underlineFont);
		contentAttribute = new Chunk(": " + format.format(conference.getEndDate()), normalFont);
		content.add(attribute);
		content.add(contentAttribute);
		content.add(new Paragraph(" "));
		attribute = new Chunk("Summary", underlineFont);
		contentAttribute = new Chunk(": " + conference.getSummary(), normalFont);
		content.add(attribute);
		content.add(contentAttribute);
		content.add(new Paragraph(" "));
		attribute = new Chunk("Fee", underlineFont);
		contentAttribute = new Chunk(": " + conference.getFee(), normalFont);
		content.add(attribute);
		content.add(contentAttribute);
		content.add(new Paragraph(" "));

		//Información de cada camera-ready version de submissions aceptadas
		content.add(new Paragraph("Camera-ready version papers:", header2Font));
		content.add(new Paragraph(" "));

		final Collection<Submission> submissions = this.submissionService.findSubmissionsAcceptedByConferenceId(conference.getId());
		final PdfPTable table = new PdfPTable(5);
		table.setHorizontalAlignment(Element.ALIGN_LEFT);
		table.setWidthPercentage(100);

		final BaseColor colorTh = new BaseColor(143, 195, 255);
		final BaseColor colorTd = new BaseColor(214, 233, 255);

		final PdfPCell th1 = new PdfPCell(new Phrase("Submission ticker", boldFont));
		final PdfPCell th2 = new PdfPCell(new Phrase("Title", boldFont));
		final PdfPCell th3 = new PdfPCell(new Phrase("Alias of the authors", boldFont));
		final PdfPCell th4 = new PdfPCell(new Phrase("Summary", boldFont));
		final PdfPCell th5 = new PdfPCell(new Phrase("Document", boldFont));
		th1.setHorizontalAlignment(Element.ALIGN_CENTER);
		th2.setHorizontalAlignment(Element.ALIGN_CENTER);
		th3.setHorizontalAlignment(Element.ALIGN_CENTER);
		th4.setHorizontalAlignment(Element.ALIGN_CENTER);
		th5.setHorizontalAlignment(Element.ALIGN_CENTER);
		th1.setVerticalAlignment(Element.ALIGN_MIDDLE);
		th2.setVerticalAlignment(Element.ALIGN_MIDDLE);
		th3.setVerticalAlignment(Element.ALIGN_MIDDLE);
		th4.setVerticalAlignment(Element.ALIGN_MIDDLE);
		th5.setVerticalAlignment(Element.ALIGN_MIDDLE);
		th1.setBackgroundColor(colorTh);
		th2.setBackgroundColor(colorTh);
		th3.setBackgroundColor(colorTh);
		th4.setBackgroundColor(colorTh);
		th5.setBackgroundColor(colorTh);
		table.addCell(th1);
		table.addCell(th2);
		table.addCell(th3);
		table.addCell(th4);
		table.addCell(th5);
		table.setHeaderRows(1);

		int cont = 0;
		for (final Submission s : submissions)
			if (s.getPaper().getIsCameraReadyVersion()) {
				cont++;
				final PdfPCell td1 = new PdfPCell(new Phrase(s.getTicker(), normalFont));
				final PdfPCell td2 = new PdfPCell(new Phrase(s.getPaper().getTitle(), normalFont));
				final PdfPCell td3 = new PdfPCell(new Phrase(s.getPaper().getAliasAuthors().toString(), normalFont));
				final PdfPCell td4 = new PdfPCell(new Phrase(s.getPaper().getSummary(), normalFont));
				final PdfPCell td5 = new PdfPCell(new Phrase(s.getPaper().getDocument(), normalFont));
				td1.setHorizontalAlignment(Element.ALIGN_CENTER);
				td2.setHorizontalAlignment(Element.ALIGN_CENTER);
				td3.setHorizontalAlignment(Element.ALIGN_CENTER);
				td4.setHorizontalAlignment(Element.ALIGN_CENTER);
				td5.setHorizontalAlignment(Element.ALIGN_CENTER);
				td1.setVerticalAlignment(Element.ALIGN_MIDDLE);
				td2.setVerticalAlignment(Element.ALIGN_MIDDLE);
				td3.setVerticalAlignment(Element.ALIGN_MIDDLE);
				td4.setVerticalAlignment(Element.ALIGN_MIDDLE);
				td5.setVerticalAlignment(Element.ALIGN_MIDDLE);
				td1.setBackgroundColor(colorTd);
				td2.setBackgroundColor(colorTd);
				td3.setBackgroundColor(colorTd);
				td4.setBackgroundColor(colorTd);
				td5.setBackgroundColor(colorTd);
				table.addCell(td1);
				table.addCell(td2);
				table.addCell(td3);
				table.addCell(td4);
				table.addCell(td5);
			}

		if (cont == 0)
			content.add(new Paragraph("There are no camera-ready versions uploaded in any of the accepted submissions from this conference.", normalFont));

		content.add(table);

		document.add(content);
		document.close();
	}

}

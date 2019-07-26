
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import cz.jirutka.validator.collection.constraints.EachNotNull;

@Entity
@Access(AccessType.PROPERTY)
public class Conference extends Commentable {

	// Attributes
	private String	title;
	private String	acronym;
	private String	venue;
	private Date	submissionDeadline;
	private Date	notificationDeadline;
	private Date	cameraReadyDeadline;
	private Date	startDate;
	private Date	endDate;
	private String	summary;
	private Double	fee;
	private Boolean	isFinalMode;


	// Getters and Setters
	@NotBlank
	public String getTitle() {
		return this.title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	@NotBlank
	public String getAcronym() {
		return this.acronym;
	}

	public void setAcronym(final String acronym) {
		this.acronym = acronym;
	}

	@NotBlank
	public String getVenue() {
		return this.venue;
	}

	public void setVenue(final String venue) {
		this.venue = venue;
	}

	@NotNull
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	public Date getSubmissionDeadline() {
		return this.submissionDeadline;
	}

	public void setSubmissionDeadline(final Date submissionDeadline) {
		this.submissionDeadline = submissionDeadline;
	}

	@NotNull
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	public Date getNotificationDeadline() {
		return this.notificationDeadline;
	}

	public void setNotificationDeadline(final Date notificationDeadline) {
		this.notificationDeadline = notificationDeadline;
	}

	@NotNull
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	public Date getCameraReadyDeadline() {
		return this.cameraReadyDeadline;
	}

	public void setCameraReadyDeadline(final Date cameraReadyDeadline) {
		this.cameraReadyDeadline = cameraReadyDeadline;
	}

	@NotNull
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(final Date startDate) {
		this.startDate = startDate;
	}

	@NotNull
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(final Date endDate) {
		this.endDate = endDate;
	}

	@NotBlank
	public String getSummary() {
		return this.summary;
	}

	public void setSummary(final String summary) {
		this.summary = summary;
	}

	@NotNull
	@Min(0)
	@Digits(integer = 8, fraction = 2)
	public Double getFee() {
		return this.fee;
	}

	public void setFee(final Double fee) {
		this.fee = fee;
	}

	@NotNull
	public Boolean getIsFinalMode() {
		return this.isFinalMode;
	}

	public void setIsFinalMode(final Boolean isFinalMode) {
		this.isFinalMode = isFinalMode;
	}


	// Relationships
	private Collection<Activity>		activities;
	private Category					category;
	private Collection<Registration>	registrations;


	@Valid
	@EachNotNull
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@Fetch(FetchMode.SELECT)
	public Collection<Activity> getActivities() {
		return this.activities;
	}

	public void setActivities(final Collection<Activity> activities) {
		this.activities = activities;
	}

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Category getCategory() {
		return this.category;
	}

	public void setCategory(final Category category) {
		this.category = category;
	}

	@Valid
	@EachNotNull
	@OneToMany(mappedBy = "conference", fetch = FetchType.EAGER)
	@Fetch(FetchMode.SELECT)
	public Collection<Registration> getRegistrations() {
		return this.registrations;
	}

	public void setRegistrations(final Collection<Registration> registrations) {
		this.registrations = registrations;
	}

}

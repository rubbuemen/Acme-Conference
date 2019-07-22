
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import cz.jirutka.validator.collection.constraints.EachNotNull;

@Entity
@Access(AccessType.PROPERTY)
public class Submission extends DomainEntity {

	// Attributes
	private String	ticker;
	private Date	moment;
	private String	status;
	private Boolean	isAssigned;
	private Boolean	isNotified;


	// Getters and Setters
	@NotBlank
	@Pattern(regexp = "^[A-Z]{3}[-][A-Z0-9]{4}$")
	@Column(unique = true)
	public String getTicker() {
		return this.ticker;
	}

	public void setTicker(final String ticker) {
		this.ticker = ticker;
	}

	@NotNull
	@Past
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getMoment() {
		return this.moment;
	}

	public void setMoment(final Date moment) {
		this.moment = moment;
	}

	@NotBlank
	@Pattern(regexp = "^UNDER-REVIEW|ACCEPTED|REJECTED$")
	public String getStatus() {
		return this.status;
	}

	public void setStatus(final String status) {
		this.status = status;
	}

	@NotNull
	public Boolean getIsAssigned() {
		return this.isAssigned;
	}

	public void setIsAssigned(final Boolean isAssigned) {
		this.isAssigned = isAssigned;
	}

	@NotNull
	public Boolean getIsNotified() {
		return this.isNotified;
	}

	public void setIsNotified(final Boolean isNotified) {
		this.isNotified = isNotified;
	}


	// Relationships
	private Paper					paper;
	private Conference				conference;
	private Collection<Reviewer>	reviewers;


	@NotNull
	@Valid
	@OneToOne(optional = false)
	public Paper getPaper() {
		return this.paper;
	}

	public void setPaper(final Paper paper) {
		this.paper = paper;
	}

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Conference getConference() {
		return this.conference;
	}

	public void setConference(final Conference conference) {
		this.conference = conference;
	}

	@Valid
	@EachNotNull
	@ManyToMany
	public Collection<Reviewer> getReviewers() {
		return this.reviewers;
	}

	public void setReviewers(final Collection<Reviewer> reviewers) {
		this.reviewers = reviewers;
	}

}

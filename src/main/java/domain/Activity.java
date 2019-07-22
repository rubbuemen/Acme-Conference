
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import cz.jirutka.validator.collection.constraints.EachNotBlank;
import cz.jirutka.validator.collection.constraints.EachURL;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Access(AccessType.PROPERTY)
public abstract class Activity extends Commentable {

	// Attributes
	private String				title;
	private Collection<String>	speakers;
	private Date				startMoment;
	private Integer				duration;
	private String				room;
	private String				summary;
	private Collection<String>	attachments;


	// Getters and Setters
	@NotBlank
	public String getTitle() {
		return this.title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	@ElementCollection(fetch = FetchType.EAGER)
	@NotEmpty
	@EachNotBlank
	public Collection<String> getSpeakers() {
		return this.speakers;
	}

	public void setSpeakers(final Collection<String> speakers) {
		this.speakers = speakers;
	}

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getStartMoment() {
		return this.startMoment;
	}

	public void setStartMoment(final Date startMoment) {
		this.startMoment = startMoment;
	}

	@Min(0)
	@NotNull
	public Integer getDuration() {
		return this.duration;
	}

	public void setDuration(final Integer duration) {
		this.duration = duration;
	}

	@NotBlank
	public String getRoom() {
		return this.room;
	}

	public void setRoom(final String room) {
		this.room = room;
	}

	@NotBlank
	public String getSummary() {
		return this.summary;
	}

	public void setSummary(final String summary) {
		this.summary = summary;
	}

	@ElementCollection(fetch = FetchType.EAGER)
	@EachNotBlank
	@EachURL
	public Collection<String> getAttachments() {
		return this.attachments;
	}

	public void setAttachments(final Collection<String> attachments) {
		this.attachments = attachments;
	}

}

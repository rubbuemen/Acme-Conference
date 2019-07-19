
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;

import cz.jirutka.validator.collection.constraints.EachNotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Report extends DomainEntity {

	// Attributes
	private Integer				originalityScore;
	private Integer				qualityScore;
	private Integer				readabilityScore;
	private String				status;
	private Collection<String>	comments;


	// Getters and Setters
	@NotNull
	@Range(min = 0, max = 10)
	public Integer getOriginalityScore() {
		return this.originalityScore;
	}

	public void setOriginalityScore(final Integer originalityScore) {
		this.originalityScore = originalityScore;
	}

	@NotNull
	@Range(min = 0, max = 10)
	public Integer getQualityScore() {
		return this.qualityScore;
	}

	public void setQualityScore(final Integer qualityScore) {
		this.qualityScore = qualityScore;
	}

	@NotNull
	@Range(min = 0, max = 10)
	public Integer getReadabilityScore() {
		return this.readabilityScore;
	}

	public void setReadabilityScore(final Integer readabilityScore) {
		this.readabilityScore = readabilityScore;
	}

	@NotBlank
	@Pattern(regexp = "^BORDER-LINE|ACCEPT|REJECT$")
	public String getStatus() {
		return this.status;
	}

	public void setStatus(final String status) {
		this.status = status;
	}

	@ElementCollection(fetch = FetchType.EAGER)
	@NotEmpty
	@EachNotBlank
	public Collection<String> getComments() {
		return this.comments;
	}

	public void setComments(final Collection<String> comments) {
		this.comments = comments;
	}


	// Relationships
	private Submission	submission;


	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Submission getSubmission() {
		return this.submission;
	}

	public void setSubmission(final Submission submission) {
		this.submission = submission;
	}

}

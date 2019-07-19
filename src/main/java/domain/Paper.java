
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.URL;

import cz.jirutka.validator.collection.constraints.EachNotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Paper extends DomainEntity {

	// Attributes
	private String				title;
	private Collection<String>	aliasAuthors;
	private String				summary;
	private String				document;
	private Boolean				isCameraReadyVersion;


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
	public Collection<String> getAliasAuthors() {
		return this.aliasAuthors;
	}

	public void setAliasAuthors(final Collection<String> aliasAuthors) {
		this.aliasAuthors = aliasAuthors;
	}

	@NotBlank
	public String getSummary() {
		return this.summary;
	}

	public void setSummary(final String summary) {
		this.summary = summary;
	}

	@URL
	@NotBlank
	public String getDocument() {
		return this.document;
	}

	public void setDocument(final String document) {
		this.document = document;
	}

	@NotNull
	public Boolean getIsCameraReadyVersion() {
		return this.isCameraReadyVersion;
	}

	public void setIsCameraReadyVersion(final Boolean isCameraReadyVersion) {
		this.isCameraReadyVersion = isCameraReadyVersion;
	}

}

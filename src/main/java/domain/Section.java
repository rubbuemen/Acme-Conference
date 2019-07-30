
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;

import org.hibernate.validator.constraints.NotBlank;

import cz.jirutka.validator.collection.constraints.EachNotBlank;
import cz.jirutka.validator.collection.constraints.EachURL;

@Entity
@Access(AccessType.PROPERTY)
public class Section extends DomainEntity {

	// Attributes
	private String				titleSec;
	private String				summarySec;
	private Collection<String>	pictures;


	// Getters and Setters
	@NotBlank
	public String getTitleSec() {
		return this.titleSec;
	}

	public void setTitleSec(final String titleSec) {
		this.titleSec = titleSec;
	}

	@NotBlank
	public String getSummarySec() {
		return this.summarySec;
	}

	public void setSummarySec(final String summarySec) {
		this.summarySec = summarySec;
	}

	public void setPictures(final Collection<String> pictures) {
		this.pictures = pictures;
	}

	@ElementCollection(fetch = FetchType.EAGER)
	@EachNotBlank
	@EachURL
	public Collection<String> getPictures() {
		return this.pictures;
	}

}

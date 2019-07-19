
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.validation.Valid;

import org.hibernate.validator.constraints.NotEmpty;

import cz.jirutka.validator.collection.constraints.EachNotBlank;
import cz.jirutka.validator.collection.constraints.EachNotNull;

@Entity
@Access(AccessType.PROPERTY)
public class Reviewer extends Actor {

	// Attributes
	private Collection<String>	keywords;


	// Getters and Setters
	@ElementCollection(fetch = FetchType.EAGER)
	@NotEmpty
	@EachNotBlank
	public Collection<String> getKeywords() {
		return this.keywords;
	}

	public void setKeywords(final Collection<String> keywords) {
		this.keywords = keywords;
	}


	// Relationships
	private Collection<Report>	reports;


	@Valid
	@EachNotNull
	@OneToMany
	public Collection<Report> getReports() {
		return this.reports;
	}

	public void setReports(final Collection<Report> reports) {
		this.reports = reports;
	}

}

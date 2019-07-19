
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Topic extends DomainEntity {

	// Attributes
	private String	nameEnglish;
	private String	nameSpanish;


	// Getters and Setters
	@NotBlank
	@Column(unique = true)
	public String getNameEnglish() {
		return this.nameEnglish;
	}

	public void setNameEnglish(final String nameEnglish) {
		this.nameEnglish = nameEnglish;
	}

	@NotBlank
	@Column(unique = true)
	public String getNameSpanish() {
		return this.nameSpanish;
	}

	public void setNameSpanish(final String nameSpanish) {
		this.nameSpanish = nameSpanish;
	}
}

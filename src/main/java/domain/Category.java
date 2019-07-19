
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.Valid;

import org.hibernate.validator.constraints.NotBlank;

import cz.jirutka.validator.collection.constraints.EachNotNull;

@Entity
@Access(AccessType.PROPERTY)
public class Category extends DomainEntity {

	// Attributes
	private String	titleEnglish;
	private String	titleSpanish;


	// Getters and Setters
	@NotBlank
	@Column(unique = true)
	public String getTitleEnglish() {
		return this.titleEnglish;
	}

	public void setTitleEnglish(final String titleEnglish) {
		this.titleEnglish = titleEnglish;
	}

	@NotBlank
	@Column(unique = true)
	public String getTitleSpanish() {
		return this.titleSpanish;
	}

	public void setTitleSpanish(final String titleSpanish) {
		this.titleSpanish = titleSpanish;
	}


	// Relationships
	private Category				parentCategory;
	private Collection<Category>	childsCategory;


	@Valid
	@ManyToOne(optional = true)
	public Category getParentCategory() {
		return this.parentCategory;
	}

	public void setParentCategory(final Category parentCategory) {
		this.parentCategory = parentCategory;
	}

	@Valid
	@EachNotNull
	@OneToMany(mappedBy = "parentCategory")
	public Collection<Category> getChildsCategory() {
		return this.childsCategory;
	}

	public void setChildsCategory(final Collection<Category> childsCategory) {
		this.childsCategory = childsCategory;
	}

}

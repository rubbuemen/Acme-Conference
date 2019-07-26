
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.validation.Valid;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import cz.jirutka.validator.collection.constraints.EachNotNull;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Access(AccessType.PROPERTY)
public abstract class Commentable extends DomainEntity {

	// Relationships
	private Collection<Comment>	comments;


	@Valid
	@EachNotNull
	@OneToMany(fetch = FetchType.EAGER)
	@Fetch(FetchMode.SELECT)
	public Collection<Comment> getComments() {
		return this.comments;
	}

	public void setComments(final Collection<Comment> comments) {
		this.comments = comments;
	}
}

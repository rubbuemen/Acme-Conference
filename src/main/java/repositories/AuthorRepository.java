/*
 * ActorRepository.java
 * 
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Author;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Integer> {

	@Query("select a from Author a where a.submissions.size > 0")
	Collection<Author> findAuthorsWithSubmissionsConference();

	@Query("select a from Author a where a.registrations.size > 0")
	Collection<Author> findAuthorsWithRegistrationsConference();

	@Query("select a from Author a join a.submissions s where s.id = ?1")
	Author findAuthorBySubmissionId(int submissionId);

	@Query("select a from Author a join a.registrations r where r.id = ?1")
	Author findAuthorByRegistrationId(int registrationId);

	@Query("select distinct a from Author a join a.submissions s join s.paper p where p.id = ?1")
	Author findAuthorByPaperId(int paperId);

}

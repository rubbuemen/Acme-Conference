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

import domain.Conference;

@Repository
public interface ConferenceRepository extends JpaRepository<Conference, Integer> {

	@Query("select c from Conference c where c.isFinalMode = 1 and c.startDate > CURRENT_DATE")
	Collection<Conference> findForthcomingConferencesFinalMode();

	@Query("select c from Conference c where c.isFinalMode = 1 and c.startDate < CURRENT_DATE")
	Collection<Conference> findPastConferencesFinalMode();

	@Query("select c from Conference c where c.isFinalMode = 1 and CURRENT_DATE between c.startDate and c.endDate")
	Collection<Conference> findRunningConferencesFinalMode();

	@Query("select distinct c from Conference c where c.isFinalMode = 1 and (c.title like %?1% or c.venue like %?1% or c.summary like %?1%)")
	Collection<Conference> findConferencesFinalModeBySingleKeyWord(String singleKeyWord);

	@Query("select c from Conference c where c.isFinalMode = 1 and c.submissionDeadline >= CURRENT_DATE")
	Collection<Conference> findConferencesToSubmit();

	@Query("select c from Conference c where c.isFinalMode = 1 and c.startDate >= CURRENT_DATE")
	Collection<Conference> findConferencesFinalModeNotStartDateDeadlineNotRegistrated();

	@Query("select distinct c from Conference c join c.registrations r join r.author a where a.id = ?1")
	Collection<Conference> findConferencesRegistratedByAuthorId(int authorId);

}

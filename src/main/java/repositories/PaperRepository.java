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

import domain.Paper;

@Repository
public interface PaperRepository extends JpaRepository<Paper, Integer> {

	@Query("select p from Submission s join s.conference c join s.paper p where p.isCameraReadyVersion = 1 and c.id = ?1")
	Collection<Paper> findPapersCameraReadyByConferenceId(int conferenceId);

	@Query("select p from Conference c join c.activities pre join pre.paper p where c.id = ?1")
	Collection<Paper> findPapersCameraReadyAlreadyAssignedByConferenceId(int conferenceId);

}

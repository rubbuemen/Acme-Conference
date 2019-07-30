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

import domain.Panel;

@Repository
public interface PanelRepository extends JpaRepository<Panel, Integer> {

	@Query("select a from Conference c join c.activities a where c.id = ?1 and a in (select p from Panel p)")
	Collection<Panel> findPanelsByConferenceId(int conferenceId);
}

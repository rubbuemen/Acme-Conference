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

import domain.Report;

@Repository
public interface ReportRepository extends JpaRepository<Report, Integer> {

	@Query("select re from Reviewer r join r.reports re where r.id = ?1")
	Collection<Report> findReportsByReviewerId(int reviewerId);

	@Query("select re from Report re join re.submission s where s.id = ?1")
	Collection<Report> findReportsBySubmissionId(int submissionId);

}

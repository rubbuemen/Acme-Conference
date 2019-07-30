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

import domain.Submission;

@Repository
public interface SubmissionRepository extends JpaRepository<Submission, Integer> {

	@Query("select s from Author a join a.submissions s where a.id = ?1")
	Collection<Submission> findSubmissionsByAuthorId(int authorId);

	@Query("select s from Submission s order by s.status")
	Collection<Submission> findSubmissionsByStatus();

	@Query("select s from Submission s join s.reviewers r where r.id = ?1")
	Collection<Submission> findSubmissionsToReportByReviewerId(int reviewerId);

	@Query("select s from Reviewer r join r.reports re join re.submission s where r.id = ?1")
	Collection<Submission> findSubmissionsAlreadyReportedByReviewerId(int reviewerId);

	@Query("select s from Submission s join s.conference c where s.status like 'UNDER-REVIEW' and c.id = ?1")
	Collection<Submission> findSubmissionsUnderReviewByConferenceId(int conferenceId);

	@Query("select s from Submission s join s.conference c where (s.status like 'ACCEPTED' or s.status like 'REJECTED') and s.isNotified = 0 and c.notificationDeadline >= CURRENT_DATE")
	Collection<Submission> findSubmissionsAcceptedOrRejectedNotNotifiedNoDeadline();
}

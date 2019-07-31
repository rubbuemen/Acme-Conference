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

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Administrator;

@Repository
public interface AdministratorRepository extends JpaRepository<Administrator, Integer> {

	@Query("select min(1*(select count(s) from Submission s where s.conference.id = c.id)), max(1*(select count(s) from Submission s where s.conference.id = c.id)), avg(1.0*(select count(s) from Submission s where s.conference.id = c.id)), stddev(1.0*(select count(s) from Submission s where s.conference.id = c.id)) from Conference c")
	String dashboardQueryC1();

	@Query("select min(c.registrations.size), max(c.registrations.size), avg(c.registrations.size), stddev(c.registrations.size) from Conference c")
	String dashboardQueryC2();

	@Query("select min(c.fee), max(c.fee), avg(c.fee), stddev(c.fee) from Conference c")
	String dashboardQueryC3();

	@Query("select min(datediff(c.endDate, c.startDate)), max(datediff(c.endDate, c.startDate)), avg(datediff(c.endDate, c.startDate)), stddev(datediff(c.endDate, c.startDate)) from Conference c")
	String dashboardQueryC4();

	@Query("select min(1*(select count(c) from Conference c where c.category.id = ca.id)), max(1*(select count(c) from Conference c where c.category.id = ca.id)), avg(1.0*(select count(c) from Conference c where c.category.id = ca.id)), stddev(1.0*(select count(c) from Conference c where c.category.id = ca.id)) from Category ca")
	String dashboardQueryB1();

	@Query("select min(c.comments.size), max(c.comments.size), avg(c.comments.size), stddev(c.comments.size) from Conference c")
	String dashboardQueryB2();

	@Query("select min(a.comments.size), max(a.comments.size), avg(a.comments.size), stddev(a.comments.size) from Activity a")
	String dashboardQueryB3();

}

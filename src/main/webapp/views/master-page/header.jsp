<%--
 * header.jsp
 *
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>

<div>
	<a href="welcome/index.do"><img src="${bannerUrl}" alt="${nameSystem} Co., Inc." /></a>
</div>

<div>
	<ul id="jMenu">
		<!-- Do not forget the "fNiv" class for the first level links !! -->		
		<security:authorize access="isAnonymous()">
			<li><a class="fNiv" href="conference/listGeneric.do"><spring:message code="master.page.conferences" /></a></li>
			<li><a class="fNiv"><spring:message code="master.page.register" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="actor/register-author.do"><spring:message code="master.page.register.author" /></a></li>
					<li><a href="actor/register-reviewer.do"><spring:message code="master.page.register.reviewer" /></a></li>
					<li><a href="actor/register-sponsor.do"><spring:message code="master.page.register.sponsor" /></a></li>
				</ul>
			</li>
			<li><a class="fNiv" href="security/login.do"><spring:message code="master.page.login" /></a></li>
		</security:authorize>
				
		<security:authorize access="hasRole('AUTHOR')">
			<li>
				<a class="fNiv"><spring:message code="master.page.author" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="submission/author/list.do"><spring:message code="master.page.submissions" /></a></li>
					<li><a href="registration/author/list.do"><spring:message code="master.page.registrations" /></a></li>
				</ul>
			</li>
		</security:authorize>
		
		<security:authorize access="hasRole('REVIEWER')">
			<li>
				<a class="fNiv"><spring:message code="master.page.reviewer" /></a>
				<ul>
					<li class="arrow"></li>
<%-- 					<li><a href="position/reviewer/list.do"><spring:message code="master.page.positions" /></a></li> --%>
				</ul>
			</li>
		</security:authorize>
		
		<security:authorize access="hasRole('SPONSOR')">
			<li>
				<a class="fNiv"><spring:message code="master.page.sponsor" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="sponsorship/sponsor/list.do"><spring:message code="master.page.sponsorships" /></a></li>
				</ul>
			</li>
		</security:authorize>
		
		
		<security:authorize access="hasRole('ADMIN')">
			<li>
				<a class="fNiv"><spring:message code="master.page.admin" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="actor/administrator/register-administrator.do"><spring:message code="master.page.register.admin" /></a></li>
					<li><a href="dashboard/administrator/show.do"><spring:message code="master.page.dashboard" /></a></li>	
					<li><a href="systemConfiguration/administrator/show.do"><spring:message code="master.page.systemConfiguration" /></a></li>	
				</ul>
			</li>
		</security:authorize>
		
		<security:authorize access="isAuthenticated()">
		<li><a class="fNiv" href="conference/listGeneric.do"><spring:message code="master.page.conferences" /></a></li>
		<li><a class="fNiv" href="message/list.do"><spring:message code="master.page.messages" /></a></li>
			<li>
				<a class="fNiv"> 
					<spring:message code="master.page.profile" /> 
			        (<security:authentication property="principal.username" />)
				</a>
				<ul>
					<li class="arrow"></li>
					<security:authorize access="hasRole('AUTHOR')">
						<li><a href="actor/author/edit.do"><spring:message code="master.page.edit.profile" /></a></li>
					</security:authorize>
					<security:authorize access="hasRole('REVIEWER')">
						<li><a href="actor/reviewer/edit.do"><spring:message code="master.page.edit.profile" /></a></li>
					</security:authorize>
					<security:authorize access="hasRole('SPONSOR')">
						<li><a href="actor/sponsor/edit.do"><spring:message code="master.page.edit.profile" /></a></li>
					</security:authorize>
					<security:authorize access="hasRole('ADMIN')">
						<li><a href="actor/administrator/edit.do"><spring:message code="master.page.edit.profile" /></a></li>
					</security:authorize>
					<li><a href="j_spring_security_logout"><spring:message code="master.page.logout" /> </a></li>
				</ul>
			</li>
		</security:authorize>
	</ul>
</div>

<div>
	<a href="?language=en">en</a> | <a href="?language=es">es</a>
</div>
<%--
 * index.jsp
 *
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>
<jsp:useBean id="date" class="java.util.Date" />
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


<display:table pagesize="5" class="displaytag" name="submissions" requestURI="${requestURI}" id="row">

	<spring:message code="submission.ticker" var="ticker" />
	<display:column property="ticker" title="${ticker}" />
	
	<spring:message code="submission.moment" var="moment" />
	<display:column title="${moment}">
			<fmt:formatDate var="format" value="${row.moment}" pattern="dd/MM/YYYY HH:mm" />
			<jstl:out value="${format}" />
	</display:column>
	
	<spring:message code="submission.status" var="status" />
	<display:column property="status" title="${status}" />
	
	<spring:message code="submission.conference" var="conference" />
	<display:column title="${conference}">
			<spring:message code="conference.title" />: ${row.conference.title}<br />
			<spring:message code="conference.acronym" />: ${row.conference.acronym}<br />
			<spring:message code="conference.venue" />: ${row.conference.venue}<br />
			<spring:message code="conference.submissionDeadline" />:
			<fmt:formatDate var="format" value="${row.conference.submissionDeadline}" pattern="dd/MM/YYYY" />
			<jstl:out value="${format}" /><br />
			<spring:message code="conference.notificationDeadline" />:
			<fmt:formatDate var="format" value="${row.conference.notificationDeadline}" pattern="dd/MM/YYYY" />
			<jstl:out value="${format}" /><br />
			<spring:message code="conference.cameraReadyDeadline" />:
			<fmt:formatDate var="format" value="${row.conference.cameraReadyDeadline}" pattern="dd/MM/YYYY" />
			<jstl:out value="${format}" /><br />
			<spring:message code="conference.startDate" />:
			<fmt:formatDate var="format" value="${row.conference.startDate}" pattern="dd/MM/YYYY" />
			<jstl:out value="${format}" /><br />
			<spring:message code="conference.endDate" />:
			<fmt:formatDate var="format" value="${row.conference.endDate}" pattern="dd/MM/YYYY" />
			<jstl:out value="${format}" /><br />
			<spring:message code="conference.summary" />: ${row.conference.summary}<br />
			<spring:message code="conference.fee" />: ${row.conference.fee}<br />
	</display:column>
	
	<spring:message code="submission.paper" var="paper" />
	<display:column title="${paper}">
			<spring:message code="submission.paper.title" />: ${row.paper.title}<br />
			<spring:message code="submission.paper.aliasAuthors" />: ${row.paper.aliasAuthors}<br />
			<spring:message code="submission.paper.summary" />: ${row.paper.summary}<br />
			<spring:message code="submission.paper.document" />: ${row.paper.document}<br />
	</display:column>
	
	<security:authorize access="hasRole('AUTHOR')">
		<spring:message code="submission.upload" var="uploadCameraReadyVersion" />
		<display:column title="${uploadCameraReadyVersion}" >
			<jstl:choose>
				<jstl:when test="${!row.paper.isCameraReadyVersion}">
					<jstl:choose>
						<jstl:when test="${row.status != 'ACCEPTED'}">
							<spring:message code="submission.notAcceptedCameraReadyVersion" />
						</jstl:when>
						<jstl:when test="${row.conference.cameraReadyDeadline < date}">
							<spring:message code="submission.deadlineElipsedCameraReadyVersion" />
						</jstl:when>
						<jstl:otherwise>
							<acme:button url="submission/author/upload.do?submissionId=${row.id}" code="button.upload" />
						</jstl:otherwise>
					</jstl:choose>
				</jstl:when>
				<jstl:otherwise>
					<spring:message code="submission.isCameraReadyVersion" />
				</jstl:otherwise>
			</jstl:choose>
		</display:column>
		
		<spring:message code="submission.reports" var="reports" />
		<display:column title="${reports}" >
			<jstl:choose>
				<jstl:when test="${row.isNotified}">
					<acme:button url="report/author/list.do?submissionId=${row.id}" code="button.show" />
				</jstl:when>
				<jstl:otherwise>
					<spring:message code="submission.isNotNotified" />
				</jstl:otherwise>
			</jstl:choose>
		</display:column>
	</security:authorize>
	
	<security:authorize access="hasRole('ADMIN')">
		<spring:message code="submission.author" var="author" />
		<display:column title="${author}">
			<acme:button url="author/show.do?authorId=${mapSubmissionAuthor.get(row).id}" code="button.more" />
		</display:column>
		
		<spring:message code="submission.assign" var="assign" />
		<display:column title="${assign}" >
			<jstl:choose>
				<jstl:when test="${!row.isAssigned}">
					<jstl:choose>
						<jstl:when test="${row.conference.startDate < date}">
							<spring:message code="submission.startDatePassed" />
						</jstl:when>
						<jstl:otherwise>
							<acme:button url="submission/administrator/assign.do?submissionId=${row.id}" code="button.assign" />
						</jstl:otherwise>
					</jstl:choose>
				</jstl:when>
				<jstl:otherwise>
					<jstl:choose>
						<jstl:when test="${fn:length(row.reviewers) > 0}">
							<spring:message code="submission.isAssigned" />
							<ul>
								<jstl:forEach items="${row.reviewers}" var="reviewer" >
									<li><jstl:out value="${reviewer.userAccount.username}" /></li>
							  	</jstl:forEach>
						  	</ul>
						</jstl:when>
						<jstl:otherwise>
							<spring:message code="submission.isAssignedNone" />
						</jstl:otherwise>
					</jstl:choose>
					
				</jstl:otherwise>
			</jstl:choose>
		</display:column>
		
	</security:authorize>
			
</display:table>

<security:authorize access="hasRole('AUTHOR')">
	<acme:button url="submission/author/create.do" code="button.create" />
</security:authorize>
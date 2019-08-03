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

<form name="singleKeyWord" id="singleKeyWord" action="conference/listGeneric.do" method="POST" >
	<spring:message code="conference.searchBySingleKeyWord" />: 
	<input type="text" name="singleKeyWord" required>
		
	<spring:message code="button.search" var="search" />
	<input type="submit" name="search" value="${search}" />
</form>
<br />

<security:authorize access="isAuthenticated()"><jstl:set var="sort" value="true" /></security:authorize>
<security:authorize access="isAnonymous()"><jstl:set var="sort" value="false" /></security:authorize>

<display:table class="displaytag" name="conferences" id="row" requestURI="conference/listGeneric.do">
	<spring:message code="conference.title" var="title" />
	<display:column property="title" title="${title}" />
	
	<spring:message code="conference.acronym" var="acronym" />
	<display:column property="acronym" title="${acronym}" />
	
	<spring:message code="conference.venue" var="venue" />
	<display:column property="venue" title="${venue}" />
	
	<spring:message code="conference.submissionDeadline" var="submissionDeadline" />
	<display:column title="${submissionDeadline}">
		<fmt:formatDate var="format" value="${row.submissionDeadline}" pattern="dd/MM/YYYY" />
		<jstl:out value="${format}" />
	</display:column>
	
	<spring:message code="conference.notificationDeadline" var="notificationDeadline" />
	<display:column title="${notificationDeadline}">
		<fmt:formatDate var="format" value="${row.notificationDeadline}" pattern="dd/MM/YYYY" />
		<jstl:out value="${format}" />
	</display:column>
	
	<spring:message code="conference.cameraReadyDeadline" var="cameraReadyDeadline" />
	<display:column title="${cameraReadyDeadline}">
		<fmt:formatDate var="format" value="${row.cameraReadyDeadline}" pattern="dd/MM/YYYY" />
		<jstl:out value="${format}" />
	</display:column>
	
	<spring:message code="conference.startDate" var="startDate" />
	<display:column title="${startDate}">
		<fmt:formatDate var="format" value="${row.startDate}" pattern="dd/MM/YYYY" />
		<jstl:out value="${format}" />
	</display:column>
	
	<spring:message code="conference.endDate" var="endDate" />
	<display:column title="${endDate}">
		<fmt:formatDate var="format" value="${row.endDate}" pattern="dd/MM/YYYY" />
		<jstl:out value="${format}" />
	</display:column>
	
	<spring:message code="conference.summary" var="summary" />
	<display:column property="summary" title="${summary}" />
	
	<spring:message code="conference.fee" var="fee" />
	<display:column property="fee" title="${fee}" />
	
	<spring:message code="conference.category" var="category" />
	<display:column title="${category}" sortable="${sort}">
		<jstl:if test="${language eq 'en'}">
			<jstl:out value="${row.category.titleEnglish}" />
		</jstl:if>
		<jstl:if test="${language eq 'es'}">
			<jstl:out value="${row.category.titleSpanish}" />
		</jstl:if>
	</display:column>
	
	<spring:message code="conference.sponsorship" var="sponsorship" />
	<display:column title="${sponsorship}" >
		<jstl:if test="${randomSponsorship.containsKey(row)}">
			<jstl:set var="banner" value="${randomSponsorship.get(row).banner}"/>
			<img src="<jstl:out value='${banner}'/>" width="200px" height="100px" />
		</jstl:if>
	</display:column>
	
	<spring:message code="conference.activities" var="activities" />
	<display:column title="${activities}" >
		<acme:button url="activity/listGeneric.do?conferenceId=${row.id}" code="button.show" />
	</display:column>
	
	<spring:message code="conference.comments" var="comments" />
	<display:column title="${comments}" >
		<acme:button url="comment/list.do?commentableId=${row.id}" code="button.show" />
	</display:column>
	
	<spring:message code="conference.downloadPDF" var="downloadPDF" />
	<display:column title="${downloadPDF}">
		<jstl:choose>
			<jstl:when test="${row.cameraReadyDeadline < date}">
				<acme:button url="conference/download.do?conferenceId=${row.id}" code="button.download" />
			</jstl:when>
			<jstl:otherwise>
				<spring:message code="conference.downloadPDFNoDeadline"/>
			</jstl:otherwise>
		</jstl:choose>
	</display:column>
</display:table>
<br/><br/>

<details>
<summary style="font-size: 26px; cursor:pointer;"><spring:message code="conference.forthcomingConferences"/></summary>

<display:table class="displaytag" name="forthcomingConferences" id="row1" requestURI="conference/listGeneric.do">
	<spring:message code="conference.title" var="title" />
	<display:column property="title" title="${title}" />
	
	<spring:message code="conference.acronym" var="acronym" />
	<display:column property="acronym" title="${acronym}" />
	
	<spring:message code="conference.venue" var="venue" />
	<display:column property="venue" title="${venue}" />
	
	<spring:message code="conference.submissionDeadline" var="submissionDeadline" />
	<display:column title="${submissionDeadline}">
		<fmt:formatDate var="format" value="${row1.submissionDeadline}" pattern="dd/MM/YYYY" />
		<jstl:out value="${format}" />
	</display:column>
	
	<spring:message code="conference.notificationDeadline" var="notificationDeadline" />
	<display:column title="${notificationDeadline}">
		<fmt:formatDate var="format" value="${row1.notificationDeadline}" pattern="dd/MM/YYYY" />
		<jstl:out value="${format}" />
	</display:column>
	
	<spring:message code="conference.cameraReadyDeadline" var="cameraReadyDeadline" />
	<display:column title="${cameraReadyDeadline}">
		<fmt:formatDate var="format" value="${row1.cameraReadyDeadline}" pattern="dd/MM/YYYY" />
		<jstl:out value="${format}" />
	</display:column>
	
	<spring:message code="conference.startDate" var="startDate" />
	<display:column title="${startDate}">
		<fmt:formatDate var="format" value="${row1.startDate}" pattern="dd/MM/YYYY" />
		<jstl:out value="${format}" />
	</display:column>
	
	<spring:message code="conference.endDate" var="endDate" />
	<display:column title="${endDate}">
		<fmt:formatDate var="format" value="${row1.endDate}" pattern="dd/MM/YYYY" />
		<jstl:out value="${format}" />
	</display:column>
	
	<spring:message code="conference.summary" var="summary" />
	<display:column property="summary" title="${summary}" />
	
	<spring:message code="conference.fee" var="fee" />
	<display:column property="fee" title="${fee}" />
	
	<spring:message code="conference.category" var="category" />
	<display:column title="${category}" sortable="${sort}">
		<jstl:if test="${language eq 'en'}">
			<jstl:out value="${row1.category.titleEnglish}" />
		</jstl:if>
		<jstl:if test="${language eq 'es'}">
			<jstl:out value="${row1.category.titleSpanish}" />
		</jstl:if>
	</display:column>
	
	<spring:message code="conference.sponsorship" var="sponsorship" />
	<display:column title="${sponsorship}" >
		<jstl:if test="${randomSponsorship1.containsKey(row1)}">
			<jstl:set var="banner" value="${randomSponsorship1.get(row1).banner}"/>
			<img src="<jstl:out value='${banner}'/>" width="200px" height="100px" />
		</jstl:if>
	</display:column>
	
	<spring:message code="conference.activities" var="activities" />
	<display:column title="${activities}" >
		<acme:button url="activity/listGeneric.do?conferenceId=${row1.id}" code="button.show" />
	</display:column>
	
	<spring:message code="conference.comments" var="comments" />
	<display:column title="${comments}" >
		<acme:button url="comment/list.do?commentableId=${row1.id}" code="button.show" />
	</display:column>
	
	<spring:message code="conference.downloadPDF" var="downloadPDF" />
	<display:column title="${downloadPDF}">
		<jstl:choose>
			<jstl:when test="${row1.cameraReadyDeadline < date}">
				<acme:button url="conference/download.do?conferenceId=${row1.id}" code="button.download" />
			</jstl:when>
			<jstl:otherwise>
				<spring:message code="conference.downloadPDFNoDeadline"/>
			</jstl:otherwise>
		</jstl:choose>
	</display:column>
</display:table>

</details><br/>

<details>
<summary style="font-size: 26px; cursor:pointer;"><spring:message code="conference.pastConferences"/></summary>

<display:table class="displaytag" name="pastConferences" id="row2" requestURI="conference/listGeneric.do">
	<spring:message code="conference.title" var="title" />
	<display:column property="title" title="${title}" />
	
	<spring:message code="conference.acronym" var="acronym" />
	<display:column property="acronym" title="${acronym}" />
	
	<spring:message code="conference.venue" var="venue" />
	<display:column property="venue" title="${venue}" />
	
	<spring:message code="conference.submissionDeadline" var="submissionDeadline" />
	<display:column title="${submissionDeadline}">
		<fmt:formatDate var="format" value="${row2.submissionDeadline}" pattern="dd/MM/YYYY" />
		<jstl:out value="${format}" />
	</display:column>
	
	<spring:message code="conference.notificationDeadline" var="notificationDeadline" />
	<display:column title="${notificationDeadline}">
		<fmt:formatDate var="format" value="${row2.notificationDeadline}" pattern="dd/MM/YYYY" />
		<jstl:out value="${format}" />
	</display:column>
	
	<spring:message code="conference.cameraReadyDeadline" var="cameraReadyDeadline" />
	<display:column title="${cameraReadyDeadline}">
		<fmt:formatDate var="format" value="${row2.cameraReadyDeadline}" pattern="dd/MM/YYYY" />
		<jstl:out value="${format}" />
	</display:column>
	
	<spring:message code="conference.startDate" var="startDate" />
	<display:column title="${startDate}">
		<fmt:formatDate var="format" value="${row2.startDate}" pattern="dd/MM/YYYY" />
		<jstl:out value="${format}" />
	</display:column>
	
	<spring:message code="conference.endDate" var="endDate" />
	<display:column title="${endDate}">
		<fmt:formatDate var="format" value="${row2.endDate}" pattern="dd/MM/YYYY" />
		<jstl:out value="${format}" />
	</display:column>
	
	<spring:message code="conference.summary" var="summary" />
	<display:column property="summary" title="${summary}" />
	
	<spring:message code="conference.fee" var="fee" />
	<display:column property="fee" title="${fee}" />
	
	<spring:message code="conference.category" var="category" />
	<display:column title="${category}" sortable="${sort}">
		<jstl:if test="${language eq 'en'}">
			<jstl:out value="${row2.category.titleEnglish}" />
		</jstl:if>
		<jstl:if test="${language eq 'es'}">
			<jstl:out value="${row2.category.titleSpanish}" />
		</jstl:if>
	</display:column>
	
	<spring:message code="conference.sponsorship" var="sponsorship" />
	<display:column title="${sponsorship}" >
		<jstl:if test="${randomSponsorship2.containsKey(row2)}">
			<jstl:set var="banner" value="${randomSponsorship2.get(row2).banner}"/>
			<img src="<jstl:out value='${banner}'/>" width="200px" height="100px" />
		</jstl:if>
	</display:column>
	
	<spring:message code="conference.activities" var="activities" />
	<display:column title="${activities}" >
		<acme:button url="activity/listGeneric.do?conferenceId=${row2.id}" code="button.show" />
	</display:column>
	
	<spring:message code="conference.comments" var="comments" />
	<display:column title="${comments}" >
		<acme:button url="comment/list.do?commentableId=${row2.id}" code="button.show" />
	</display:column>
	
	<spring:message code="conference.downloadPDF" var="downloadPDF" />
	<display:column title="${downloadPDF}">
		<jstl:choose>
			<jstl:when test="${row2.cameraReadyDeadline < date}">
				<acme:button url="conference/download.do?conferenceId=${row2.id}" code="button.download" />
			</jstl:when>
			<jstl:otherwise>
				<spring:message code="conference.downloadPDFNoDeadline"/>
			</jstl:otherwise>
		</jstl:choose>
	</display:column>
</display:table>

</details><br/>

<details>
<summary style="font-size: 26px; cursor:pointer;"><spring:message code="conference.runningConferences"/></summary>

<display:table class="displaytag" name="runningConferences" id="row3" requestURI="conference/listGeneric.do">
	<spring:message code="conference.title" var="title" />
	<display:column property="title" title="${title}" />
	
	<spring:message code="conference.acronym" var="acronym" />
	<display:column property="acronym" title="${acronym}" />
	
	<spring:message code="conference.venue" var="venue" />
	<display:column property="venue" title="${venue}" />
	
	<spring:message code="conference.submissionDeadline" var="submissionDeadline" />
	<display:column title="${submissionDeadline}">
		<fmt:formatDate var="format" value="${row3.submissionDeadline}" pattern="dd/MM/YYYY" />
		<jstl:out value="${format}" />
	</display:column>
	
	<spring:message code="conference.notificationDeadline" var="notificationDeadline" />
	<display:column title="${notificationDeadline}">
		<fmt:formatDate var="format" value="${row3.notificationDeadline}" pattern="dd/MM/YYYY" />
		<jstl:out value="${format}" />
	</display:column>
	
	<spring:message code="conference.cameraReadyDeadline" var="cameraReadyDeadline" />
	<display:column title="${cameraReadyDeadline}">
		<fmt:formatDate var="format" value="${row3.cameraReadyDeadline}" pattern="dd/MM/YYYY" />
		<jstl:out value="${format}" />
	</display:column>
	
	<spring:message code="conference.startDate" var="startDate" />
	<display:column title="${startDate}">
		<fmt:formatDate var="format" value="${row3.startDate}" pattern="dd/MM/YYYY" />
		<jstl:out value="${format}" />
	</display:column>
	
	<spring:message code="conference.endDate" var="endDate" />
	<display:column title="${endDate}">
		<fmt:formatDate var="format" value="${row3.endDate}" pattern="dd/MM/YYYY" />
		<jstl:out value="${format}" />
	</display:column>
	
	<spring:message code="conference.summary" var="summary" />
	<display:column property="summary" title="${summary}" />
	
	<spring:message code="conference.fee" var="fee" />
	<display:column property="fee" title="${fee}" />
	
	<spring:message code="conference.category" var="category" />
	<display:column title="${category}" sortable="${sort}">
		<jstl:if test="${language eq 'en'}">
			<jstl:out value="${row3.category.titleEnglish}" />
		</jstl:if>
		<jstl:if test="${language eq 'es'}">
			<jstl:out value="${row3.category.titleSpanish}" />
		</jstl:if>
	</display:column>
	
	<spring:message code="conference.sponsorship" var="sponsorship" />
	<display:column title="${sponsorship}" >
		<jstl:if test="${randomSponsorship3.containsKey(row3)}">
			<jstl:set var="banner" value="${randomSponsorship3.get(row3).banner}"/>
			<img src="<jstl:out value='${banner}'/>" width="200px" height="100px" />
		</jstl:if>
	</display:column>
	
	<spring:message code="conference.activities" var="activities" />
	<display:column title="${activities}" >
		<acme:button url="activity/listGeneric.do?conferenceId=${row3.id}" code="button.show" />
	</display:column>
	
	<spring:message code="conference.comments" var="comments" />
	<display:column title="${comments}" >
		<acme:button url="comment/list.do?commentableId=${row3.id}" code="button.show" />
	</display:column>
	
	<spring:message code="conference.downloadPDF" var="downloadPDF" />
	<display:column title="${downloadPDF}">
		<jstl:choose>
			<jstl:when test="${row3.cameraReadyDeadline < date}">
				<acme:button url="conference/download.do?conferenceId=${row3.id}" code="button.download" />
			</jstl:when>
			<jstl:otherwise>
				<spring:message code="conference.downloadPDFNoDeadline"/>
			</jstl:otherwise>
		</jstl:choose>
	</display:column>
</display:table>

</details><br/>
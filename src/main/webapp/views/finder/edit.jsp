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

<form:form action="${actionURL}" modelAttribute="finder">
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="conferences" />

	<acme:textbox code="finder.keyWord" path="keyWord" placeholder="Lorem Ipsum" />
	<br />

	<acme:textbox code="finder.minDate" path="minDate" placeholder="dd/MM/yyyy" />
	<br />
	
	<acme:textbox code="finder.maxDate" path="maxDate" placeholder="dd/MM/yyyy" />
	<br />
	
	<acme:textbox code="finder.maxFee" path="maxFee" placeholder="NNN.NN" type="number" min="0" step="0.01" />
	<br />
	
	<jstl:if test="${language eq 'en'}">
		<jstl:set var="titleCategory" value="titleEnglish" />
	</jstl:if>
	<jstl:if test="${language eq 'es'}">
		<jstl:set var="titleCategory" value="titleSpanish" />
	</jstl:if>
	
	<acme:selectOptional items="${categories}" itemLabel="${titleCategory}" code="finder.category" path="category"/>
	<br />
	
	<acme:submit name="save" code="button.search" />

</form:form>
<br />

<display:table pagesize="5" class="displaytag" name="conferences" requestURI="${actionURL}" id="row">

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
	<display:column title="${category}">
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
			
</display:table>
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

<display:table pagesize="5" class="displaytag" name="registrations" requestURI="${requestURI}" id="row">

	<spring:message code="registration.creditCard" var="creditCard" />
	<display:column title="${creditCard}">
			<spring:message code="creditCard.holderName" />: ${row.creditCard.holderName}<br />
			<spring:message code="creditCard.brandName" />: ${row.creditCard.brandName}<br />
			<spring:message code="creditCard.number" />: ${row.creditCard.number}<br />
			<spring:message code="creditCard.expirationMonth" />: ${row.creditCard.expirationMonth}<br />
			<spring:message code="creditCard.expirationYear" />: ${row.creditCard.expirationYear}<br />
			<spring:message code="creditCard.cvv" />: ${row.creditCard.cvv}
	</display:column>
	
	<spring:message code="registration.conference" var="conference" />
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
</display:table>

<acme:button url="registration/author/create.do" code="button.create" />
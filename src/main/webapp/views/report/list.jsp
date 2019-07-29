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

<display:table pagesize="5" class="displaytag" name="reports" requestURI="${requestURI}" id="row">

	<spring:message code="report.originalityScore" var="originalityScore" />
	<display:column property="originalityScore" title="${originalityScore}" />
	
	<spring:message code="report.qualityScore" var="qualityScore" />
	<display:column property="qualityScore" title="${qualityScore}" />
	
	<spring:message code="report.readabilityScore" var="readabilityScore" />
	<display:column property="readabilityScore" title="${readabilityScore}" />
	
	<spring:message code="report.status" var="status" />
	<display:column property="status" title="${status}" />
	
	<spring:message code="report.comments" var="comments" />
	<display:column property="comments" title="${comments}" />
	
	<spring:message code="report.submission" var="submission" />
	<display:column title="${submission}">
			<spring:message code="submission.ticker" />: ${row.submission.ticker}<br />
			<spring:message code="submission.moment" />:
			<fmt:formatDate var="format" value="${row.submission.moment}" pattern="dd/MM/YYYY HH:mm" />
			<jstl:out value="${format}" /><br />
	</display:column>
	
	<spring:message code="report.decideReport" var="decideReport" />
		<display:column title="${decideReport}">
			<jstl:choose>
			<jstl:when test="${row.status eq 'BORDER-LINE'}">
				<acme:button url="report/reviewer/accept.do?reportId=${row.id}" code="button.accept" />
				<acme:button url="report/reviewer/reject.do?reportId=${row.id}" code="button.reject" />
			</jstl:when>	
			<jstl:otherwise>
				<spring:message code="report.alreadyDecided" />
			</jstl:otherwise>
			
			</jstl:choose>
			
		</display:column>
	

</display:table>

<acme:button url="report/reviewer/create.do" code="button.create" />
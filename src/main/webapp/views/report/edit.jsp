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

<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form action="${actionURL}" modelAttribute="report">
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="status" />
	
	<acme:textbox code="report.originalityScore" path="originalityScore" placeholder="N" type="number" min="0" max="10" />
	<br />
	
	<acme:textbox code="report.qualityScore" path="qualityScore" placeholder="N" type="number" min="0" max="10" />
	<br />
	
	<acme:textbox code="report.readabilityScore" path="readabilityScore" placeholder="N" type="number" min="0" max="10" />
	<br />
	
	<acme:textarea code="report.comments" path="comments" placeholder="Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aenean at auctor massa" />
	<br />
	
	<acme:select items="${submissions}" itemLabel="ticker" code="report.submission" path="submission"/>
	<br />
	
	<jstl:choose>
		<jstl:when test="${report.id == 0}">
			<acme:submit name="save" code="button.register" />
		</jstl:when>
		<jstl:otherwise>
			<acme:submit name="save" code="button.save" />
		</jstl:otherwise>
	</jstl:choose>
	
	<acme:cancel url="report/reviewer/list.do" code="button.cancel" />
</form:form>
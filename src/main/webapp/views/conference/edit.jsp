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

<form:form action="${actionURL}" modelAttribute="conference">
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="activities" />
	<form:hidden path="registrations" />
	<form:hidden path="comments" />
	<form:hidden path="isFinalMode" />
	
	<acme:textbox code="conference.title" path="title" placeholder="Lorem Ipsum"/>
	<br />
	
	<acme:textbox code="conference.acronym" path="acronym" placeholder="LI"/>
	<br />
	
	<acme:textbox code="conference.venue" path="venue" placeholder="Lorem Ipsum"/>
	<br />
	
	<acme:textbox code="conference.submissionDeadline" path="submissionDeadline" placeholder="dd/MM/yyyy"  />
	<br />
	
	<acme:textbox code="conference.notificationDeadline" path="notificationDeadline" placeholder="dd/MM/yyyy"  />
	<br />
	
	<acme:textbox code="conference.cameraReadyDeadline" path="cameraReadyDeadline" placeholder="dd/MM/yyyy"  />
	<br />
	
	<acme:textbox code="conference.startDate" path="startDate" placeholder="dd/MM/yyyy"  />
	<br />
	
	<acme:textbox code="conference.endDate" path="endDate" placeholder="dd/MM/yyyy"  />
	<br />
	
	<acme:textarea code="conference.summary" path="summary" placeholder="Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aenean at auctor massa"/>
	<br />
	
	<acme:textbox code="conference.fee" path="fee" placeholder="NNN.NN" type="number" min="0" step="0.01" />
	<br />
	
	<jstl:if test="${language eq 'en'}">
		<jstl:set var="titleCategory" value="titleEnglish" />
	</jstl:if>
	<jstl:if test="${language eq 'es'}">
		<jstl:set var="titleCategory" value="titleSpanish" />
	</jstl:if>
	
	<acme:select items="${categories}" itemLabel="${titleCategory}" code="conference.category" path="category"/>
	<br />
	
	<jstl:choose>
		<jstl:when test="${conference.id == 0}">
			<acme:submit name="save" code="button.register" />
		</jstl:when>
		<jstl:otherwise>
			<acme:submit name="save" code="button.save" />
		</jstl:otherwise>
	</jstl:choose>
	
	<acme:cancel url="conference/administrator/list.do" code="button.cancel" />
</form:form>
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

<form:form action="${actionURL}" modelAttribute="activity">
	<form:hidden path="id" />
	<form:hidden path="version" />
	<input type="hidden" name="conferenceId" value="${conference.id}">
	
	<acme:textbox code="activity.title" path="title" placeholder="Lorem Ipsum"/>
	<br />

	<acme:textarea code="activity.speakers" path="speakers" placeholder="Lorem ipsum dolor, Lorem ipsum dolor, Lorem ipsum dolor, ..."/>
	<br />
	
	<acme:textbox code="activity.startMoment" path="startMoment" placeholder="dd/MM/yyyy HH:mm"  />
	<br />
	
	<acme:textbox code="activity.duration" path="duration" placeholder="NNN" type="number" min="0" />
	<br />
	
	<acme:textbox code="activity.room" path="room" placeholder="Lorem Ipsum"/>
	<br />
	
	<acme:textbox code="activity.summary" path="summary" placeholder="Lorem Ipsum"/>
	<br />
	
	<acme:textarea code="activity.attachments" path="attachments" placeholder="http://LoremIpsum.com, http://LoremIpsum.com, http://LoremIpsum.com, ..."/>
	<br />
		
	<jstl:if test="${type == 'presentation'}">
		<jstl:choose>
			<jstl:when test="${activity.id == 0}">
				<acme:select items="${papers}" itemLabel="title" code="activity.paper" path="paper" />
				<br />
			</jstl:when>
			<jstl:otherwise>
				<form:hidden path="paper" />
			</jstl:otherwise>
		</jstl:choose>
	</jstl:if>
	
	<jstl:choose>
		<jstl:when test="${activity.id == 0}">
			<jstl:if test="${type == 'panel'}">
				<acme:submit name="savePanel" code="button.register" />
			</jstl:if>
			<jstl:if test="${type == 'presentation'}">
				<acme:submit name="savePresentation" code="button.register" />
			</jstl:if>
		</jstl:when>
		<jstl:otherwise>
			<jstl:if test="${type == 'tutorial'}">
				<acme:submit name="saveTutorial" code="button.save" />
			</jstl:if>
			<jstl:if test="${type == 'panel'}">
				<acme:submit name="savePanel" code="button.save" />
			</jstl:if>
			<jstl:if test="${type == 'presentation'}">
				<acme:submit name="savePresentation" code="button.save" />
			</jstl:if>
		</jstl:otherwise>
	</jstl:choose>
	
	<acme:cancel url="activity/administrator/list.do?conferenceId=${conference.id}" code="button.cancel" />
</form:form>
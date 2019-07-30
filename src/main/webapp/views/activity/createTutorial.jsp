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
	<form:hidden path="sections" />
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
	

	<form:form modelAttribute='section'>
	<fieldset>
		<legend><spring:message code="activity.section"/></legend>
		<acme:textbox code="activity.section.title" path="titleSec" placeholder="Lorem Ipsum"/>
		<br />
		
		<acme:textbox code="activity.section.summary" path="summarySec" placeholder="Lorem Ipsum"/>
		<br />
		
		<acme:textarea code="activity.section.pictures" path="pictures" placeholder="http://LoremIpsum.com, http://LoremIpsum.com, http://LoremIpsum.com, ..."/>
		<br />
		<spring:message code="activity.createMoreSections"/>.
	</fieldset>
	<br />
	
	<acme:submit name="saveTutorial" code="button.register" />
	
	<acme:cancel url="activity/administrator/list.do?conferenceId=${conference.id}" code="button.cancel" />
	</form:form>
</form:form>
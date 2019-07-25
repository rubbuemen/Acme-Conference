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

<form:form action="${actionURL}" modelAttribute="submission">
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="ticker" />
	<form:hidden path="moment" />
	<form:hidden path="status" />
	<form:hidden path="isAssigned" />
	<form:hidden path="isNotified" />
	<form:hidden path="paper.isCameraReadyVersion" />
	
	<fieldset>
	    <legend><spring:message code="submission.paper"/></legend>
	    <acme:textbox code="submission.paper.title" path="paper.title" placeholder="Lorem Ipsum"/>
		<br />
	
		<acme:textbox code="submission.paper.aliasAuthors" path="paper.aliasAuthors" placeholder="Lorem ipsum, Lorem ipsum, Lorem ipsum, Lorem ipsum" />
		<br />
		
		<acme:textbox code="submission.paper.summary" path="paper.summary" placeholder="Lorem Ipsum"/>
		<br />
		
		<acme:textbox code="submission.paper.document" path="paper.document" placeholder="http://LoremIpsum.com" type="url" />
		<br />
	</fieldset>
	<br />
	
	<acme:select items="${conferences}" itemLabel="title" code="submission.conference" path="conference" />
	<br />

	<acme:submit name="save" code="button.register" />
	<acme:cancel url="submission/author/list.do" code="button.cancel" />
</form:form>
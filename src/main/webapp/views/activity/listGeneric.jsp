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

<details>
<summary style="font-size: 26px; cursor:pointer;"><spring:message code="activity.tutorials"/></summary>

<display:table class="displaytag" name="tutorials" id="row1">
	<spring:message code="activity.title" var="title" />
	<display:column property="title" title="${title}" />
	
	<spring:message code="activity.speakers" var="speakers" />
	<display:column property="speakers" title="${speakers}" />
	
	<spring:message code="activity.startMoment" var="startMoment" />
	<display:column title="${startMoment}">
		<fmt:formatDate var="format" value="${row1.startMoment}" pattern="dd/MM/YYYY HH:mm" />
		<jstl:out value="${format}" />
	</display:column>
	
	<spring:message code="activity.duration" var="duration" />
	<display:column property="duration" title="${duration}" />
	
	<spring:message code="activity.room" var="room" />
	<display:column property="room" title="${room}" />
	
	<spring:message code="activity.summary" var="summary" />
	<display:column property="summary" title="${summary}" />
	
	<spring:message code="activity.attachments" var="attachments" />
	<display:column property="attachments" title="${attachments}" />
	
	<spring:message code="activity.sections" var="sections" />
		<display:column title="${sections}">
			<acme:button url="section/listGeneric.do?tutorialId=${row1.id}" code="button.show" />
		</display:column>
	
	<spring:message code="activity.comments" var="comments" />
	<display:column title="${comments}" >
		<acme:button url="comment/list.do?commentableId=${row1.id}" code="button.show" />
	</display:column>
</display:table>

</details><br/>

<details>
<summary style="font-size: 26px; cursor:pointer;"><spring:message code="activity.panels"/></summary>

<display:table class="displaytag" name="panels" id="row2">
	<spring:message code="activity.title" var="title" />
	<display:column property="title" title="${title}" />
	
	<spring:message code="activity.speakers" var="speakers" />
	<display:column property="speakers" title="${speakers}" />
	
	<spring:message code="activity.startMoment" var="startMoment" />
	<display:column title="${startMoment}">
		<fmt:formatDate var="format" value="${row2.startMoment}" pattern="dd/MM/YYYY HH:mm" />
		<jstl:out value="${format}" />
	</display:column>
	
	<spring:message code="activity.duration" var="duration" />
	<display:column property="duration" title="${duration}" />
	
	<spring:message code="activity.room" var="room" />
	<display:column property="room" title="${room}" />
	
	<spring:message code="activity.summary" var="summary" />
	<display:column property="summary" title="${summary}" />
	
	<spring:message code="activity.attachments" var="attachments" />
	<display:column property="attachments" title="${attachments}" />
		
	<spring:message code="activity.comments" var="comments" />
	<display:column title="${comments}" >
		<acme:button url="comment/list.do?commentableId=${row2.id}" code="button.show" />
	</display:column>
</display:table>

</details><br/>

<details>
<summary style="font-size: 26px; cursor:pointer;"><spring:message code="activity.presentations"/></summary>

<display:table class="displaytag" name="presentations" id="row3">
	<spring:message code="activity.title" var="title" />
	<display:column property="title" title="${title}" />
	
	<spring:message code="activity.speakers" var="speakers" />
	<display:column property="speakers" title="${speakers}" />
	
	<spring:message code="activity.startMoment" var="startMoment" />
	<display:column title="${startMoment}">
		<fmt:formatDate var="format" value="${row3.startMoment}" pattern="dd/MM/YYYY HH:mm" />
		<jstl:out value="${format}" />
	</display:column>
	
	<spring:message code="activity.duration" var="duration" />
	<display:column property="duration" title="${duration}" />
	
	<spring:message code="activity.room" var="room" />
	<display:column property="room" title="${room}" />
	
	<spring:message code="activity.summary" var="summary" />
	<display:column property="summary" title="${summary}" />
	
	<spring:message code="activity.attachments" var="attachments" />
	<display:column property="attachments" title="${attachments}" />
	
	<spring:message code="activity.paper" var="paper" />
	<display:column title="${paper}">
			<spring:message code="activity.paper.title" />: ${row3.paper.title}<br />
			<spring:message code="activity.paper.aliasAuthors" />: ${row3.paper.aliasAuthors}<br />
			<spring:message code="activity.paper.summary" />: ${row3.paper.summary}<br />
			<spring:message code="activity.paper.document" />: ${row3.paper.document}<br />
	</display:column>
	
	<spring:message code="activity.comments" var="comments" />
	<display:column title="${comments}" >
		<acme:button url="comment/list.do?commentableId=${row3.id}" code="button.show" />
	</display:column>
</display:table>
</details><br/>

<acme:button url="conference/listGeneric.do" code="button.back" />

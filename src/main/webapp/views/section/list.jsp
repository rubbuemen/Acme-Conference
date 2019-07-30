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

<display:table pagesize="5" class="displaytag" name="sections" requestURI="${requestURI}" id="row">

	<spring:message code="activity.section.title" var="titleSec" />
	<display:column property="titleSec" title="${titleSec}" />
	
	<spring:message code="activity.section.summary" var="summarySec" />
	<display:column property="summarySec" title="${summarySec}" />
	
	<spring:message code="activity.section.pictures" var="pictures" />
	<display:column property="pictures" title="${pictures}" />
	
	<spring:message code="section.editH" var="editH" />
	<display:column title="${editH}">
		<acme:button url="section/administrator/edit.do?tutorialId=${tutorial.id}&sectionId=${row.id}" code="button.edit" />
	</display:column>
	
	<spring:message code="section.deleteH" var="deleteH" />
	<display:column title="${deleteH}">
		<acme:button url="section/administrator/delete.do?tutorialId=${tutorial.id}&sectionId=${row.id}" code="button.delete" />
	</display:column>
			
</display:table>

<acme:button url="section/administrator/create.do?tutorialId=${tutorial.id}" code="button.create" />
<acme:button url="activity/administrator/list.do?conferenceId=${conference.id}" code="button.back" />
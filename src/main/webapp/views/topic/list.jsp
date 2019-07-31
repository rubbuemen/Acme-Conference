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
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>

<display:table pagesize="5" class="displaytag" name="topics" requestURI="${requestURI}" id="row">

	<spring:message code="topic.nameEnglish" var="nameEnglish" />
	<display:column property="nameEnglish" title="${nameEnglish}" />
	
	<spring:message code="topic.nameSpanish" var="nameSpanish" />
	<display:column property="nameSpanish" title="${nameSpanish}" />
	
	<spring:message code="topic.edit" var="editH" />
	<display:column title="${editH}">
		<acme:button url="topic/administrator/edit.do?topicId=${row.id}" code="button.edit" />
	</display:column>
	
	<spring:message code="topic.delete" var="deleteH" />
	<jstl:set var = "positionsUsed" value = "${topicsUsed}"/>
	<jstl:set var = "position" value = "${row}"/>
	<display:column title="${deleteH}">
		<jstl:choose>
			<jstl:when test="${!fn:contains(positionsUsed, position)}">
				<acme:button url="topic/administrator/delete.do?topicId=${row.id}" code="button.delete" />
			</jstl:when>
			<jstl:otherwise>
				<spring:message code="topic.error.occupied" />
			</jstl:otherwise>
		</jstl:choose>
	</display:column>
			
</display:table>

<acme:button url="topic/administrator/create.do" code="button.create" />
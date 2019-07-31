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
			
</display:table>

<acme:button url="activity/listGeneric.do?conferenceId=${conference.id}" code="button.back" />
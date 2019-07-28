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

<ul style="list-style-type: disc;">
	<spring:message code="actor.name" var="name" />
	<li><b>${name}:</b> <jstl:out value="${author.name}" /></li>
	
	<jstl:if test="${not empty author.middleName}">
		<spring:message code="actor.middleName" var="middleName" />
		<li><b>${middleName}:</b> <jstl:out value="${author.middleName}" /></li>
	</jstl:if>
	
	<spring:message code="actor.surname" var="surname" />
	<li><b>${surname}:</b> <jstl:out value="${author.surname}" /></li>
	
	<jstl:if test="${not empty author.photo}">
		<spring:message code="actor.photo" var="photo" />
		<li><b>${photo}:</b></li>
		<img src="<jstl:out value='${author.photo}' />"  width="200px" height="200px" />
	</jstl:if>
	
	<spring:message code="actor.email" var="email" />
	<li><b>${email}:</b> <jstl:out value="${author.email}" /></li>
	
	<jstl:if test="${not empty author.phoneNumber}">
		<spring:message code="actor.phoneNumber" var="phoneNumber" />
		<li><b>${phoneNumber}:</b> <jstl:out value="${author.phoneNumber}" /></li>
	</jstl:if>
	
	<jstl:if test="${not empty author.address}">
		<spring:message code="actor.address" var="address" />
		<li><b>${address}:</b> <jstl:out value="${author.address}" /></li>
	</jstl:if>
	<spring:message code="author.score" var="score" />
	<li>
	<b>${score}: </b>
	<jstl:choose>
		<jstl:when test="${author.score != null}">
			<jstl:out value="${author.score}" />
		</jstl:when>
		<jstl:otherwise>
		N/A
		</jstl:otherwise>
	</jstl:choose>
	</li>
</ul>
<security:authorize access="hasRole('ADMIN')">
	<acme:button url="submission/administrator/list.do" code="button.back" />
</security:authorize>
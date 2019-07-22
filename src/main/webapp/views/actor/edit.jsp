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

<spring:message code="actor.confirm.phone" var="confirmPhone" />
<form:form id="form" action="${actionURL}" modelAttribute="actor" onsubmit="return checkPhone('${confirmPhone}');">
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="messages" />
	<form:hidden path="userAccount.authorities" value="${authority}" />
	<form:hidden path="userAccount.statusAccount" />
	<jstl:if test="${authority == 'AUTHOR'}">
		<jstl:choose>
			<jstl:when test="${actor.id != 0}">
				<form:hidden path="finder" />
			</jstl:when>
			<jstl:otherwise>
				<form:hidden path="finder" value="0" />
			</jstl:otherwise>
		</jstl:choose>
		<form:hidden path="registrations" />
		<form:hidden path="submissions" />
		<form:hidden path="score" />
	</jstl:if>
	<jstl:if test="${authority == 'REVIEWER'}">
		<form:hidden path="reports" />
	</jstl:if>
	<jstl:if test="${authority == 'SPONSOR'}">
		<form:hidden path="sponsorships" />
	</jstl:if>

	<fieldset>
		<legend><spring:message code="actor.userAccount"/></legend>
		<jstl:choose>
			<jstl:when test="${actor.id != 0}">
				<acme:textbox code="actor.username" path="userAccount.username" readonly="true" />
				<form:hidden path="userAccount" />
			</jstl:when>
			<jstl:otherwise>
				<acme:textbox code="actor.username" path="userAccount.username" placeholder="LoremIpsum" />
				<br />
				<acme:password code="actor.password" path="userAccount.password" placeholder="Lorem Ipsum" />
			</jstl:otherwise>
		</jstl:choose>
	</fieldset>
	<br />
	
	<fieldset>
		<legend><spring:message code="actor.personalData"/></legend>
		<acme:textbox code="actor.name" path="name" placeholder="Lorem Ipsum"/>
		<br />
	
		<acme:textbox code="actor.middleName" path="middleName" placeholder="Lorem Ipsum" />
		<br />
		
		<acme:textbox code="actor.surname" path="surname" placeholder="Lorem Ipsum" />
		<br />
	
		<acme:textbox code="actor.photo" path="photo" placeholder="http://LoremIpsum.com" type="url" />
		<jstl:if test="${not empty actor.photo}">
			<br />
			<img src="<jstl:out value='${actor.photo}' />"  width="200px" height="200px"  />
			<br />
		</jstl:if>
		<br />
	
		<acme:textbox code="actor.email" path="email" placeholder="Lorem Ipsum" type="email" />
		<br />
	
		<acme:textbox code="actor.phoneNumber" path="phoneNumber" placeholder="+999 (999) 999999999" type="tel"/>
		<br />
	
		<acme:textbox code="actor.address" path="address" placeholder="Lorem Ipsum" />
		<br />
	
		<jstl:if test="${authority == 'REVIEWER'}">
			<acme:textarea code="reviewer.keywords" path="keywords" placeholder="Lorem ipsum, Lorem ipsum, Lorem ipsum, Lorem ipsum" />
			<br />
		</jstl:if>
	</fieldset>
	<br />

	<jstl:choose>
		<jstl:when test="${actor.id == 0}">
			<acme:submit name="save" code="button.register"/>
		</jstl:when>
		<jstl:otherwise>
			<acme:submit name="save" code="button.save"/>
		</jstl:otherwise>
	</jstl:choose>
	<acme:cancel url="welcome/index.do" code="button.cancel" />

</form:form>
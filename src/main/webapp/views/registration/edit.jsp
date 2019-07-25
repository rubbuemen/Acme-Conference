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

<form:form action="${actionURL}" modelAttribute="registration">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="author" />
	
	<fieldset>
		<legend>
			<spring:message code="registration.creditCard" />
		</legend>
		<acme:textbox code="creditCard.holderName" path="creditCard.holderName" placeholder="Lorem Ipsum" />
		<br />

		<acme:selectString items="${creditCardBrands}" itemLabel="creditCard.brandName" code="creditCard.brandName" path="creditCard.brandName"/>
		<br />
		
		<acme:textbox code="creditCard.number" path="creditCard.number"	placeholder="NNNNNNNNNNNNNNNN" />
		<br />
		
		<acme:textbox code="creditCard.expirationMonth" path="creditCard.expirationMonth" placeholder="MM" type="number" min="1" max="12" />
		<br />
		
		<acme:textbox code="creditCard.expirationYear" path="creditCard.expirationYear" placeholder="YY" type="number" min="0" max="99" />
		<br />
		
		<acme:textbox code="creditCard.cvv" path="creditCard.cvv" placeholder="NNN" type="number" min="000" max="999" />
		<br />
	</fieldset>
	<br />
		
	<acme:select items="${conferences}" itemLabel="title" code="registration.conference" path="conference"/>
	<br />
	
	<acme:submit name="save" code="button.save" />
	<acme:cancel url="registration/author/list.do" code="button.cancel" />

</form:form>
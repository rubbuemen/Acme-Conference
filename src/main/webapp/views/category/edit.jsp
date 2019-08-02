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

<form:form action="${actionURL}" modelAttribute="category">
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="childCategories" />
	<input type="hidden" name="categoryParentOld" value="${categoryParentOld}" />

	<acme:textbox code="category.titleEnglish" path="titleEnglish" placeholder="Lorem Ipsum"/>
	<br />

	<acme:textbox code="category.titleSpanish" path="titleSpanish" placeholder="Lorem Ipsum"/>
	<br />
	
	<jstl:if test="${language eq 'en'}">
		<jstl:set var="titleCategory" value="titleEnglish" />
	</jstl:if>
	<jstl:if test="${language eq 'es'}">
		<jstl:set var="titleCategory" value="titleSpanish" />
	</jstl:if>
	
	<acme:select items="${categories}" itemLabel="${titleCategory}" code="category.parentCategory" path="parentCategory"/>
	<br />

	<jstl:choose>
		<jstl:when test="${category.id == 0}">
			<acme:submit name="save" code="button.create" />
		</jstl:when>
		<jstl:otherwise>
			<acme:submit name="save" code="button.save" />
		</jstl:otherwise>
	</jstl:choose>
	
	<acme:cancel url="category/administrator/list.do" code="button.cancel" />
</form:form>
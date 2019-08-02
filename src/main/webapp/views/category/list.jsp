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

<jstl:if test="${language eq 'en'}">
	<jstl:set var="titleCategory" value="${parentCategory.titleEnglish}" />
</jstl:if>
<jstl:if test="${language eq 'es'}">
	<jstl:set var="titleCategory" value="${parentCategory.titleSpanish}" />
</jstl:if>
	
<jstl:if test="${parentCategory != null}">
	<h3><jstl:out value="${titleCategory}" /></h3>
</jstl:if>
	

<display:table class="displaytag" name="categories" requestURI="${requestURI}" id="row">

	<spring:message code="category.titleEnglish" var="titleEnglish" />
	<display:column property="titleEnglish" title="${titleEnglish}" />
	
	<spring:message code="category.titleSpanish" var="titleSpanish" />
	<display:column property="titleSpanish" title="${titleSpanish}" />
	
	<spring:message code="category.showCategories" var="showCategories" />
	<display:column title="${showCategories}">
	<jstl:if test="${not empty row.childCategories}">
		<acme:button url="category/administrator/list.do?parentCategoryId=${row.id}" code="button.show" />
	</jstl:if>	
	</display:column>
	
	<spring:message code="category.edit" var="editH" />
	<display:column title="${editH}" >
	<jstl:choose>
		<jstl:when test="${row.parentCategory == null}">
			<spring:message code="category.error.rootCategory" />
		</jstl:when>
		<jstl:otherwise>
			<acme:button url="category/administrator/edit.do?categoryId=${row.id}" code="button.edit" />
		</jstl:otherwise>
	</jstl:choose>
	</display:column>
	
	<spring:message code="category.delete" var="deleteH" />
		<display:column title="${deleteH}" >
	<jstl:choose>
		<jstl:when test="${row.parentCategory == null}">
			<spring:message code="category.error.rootCategory" />
		</jstl:when>
		<jstl:otherwise>
			<acme:button url="category/administrator/delete.do?categoryId=${row.id}" code="button.delete" />	
		</jstl:otherwise>
	</jstl:choose>
	</display:column>
</display:table>

<acme:button url="category/administrator/create.do" code="button.create" />
<jstl:if test="${parentCategory != null}">
	<jstl:choose>
		<jstl:when test="${parentCategory.parentCategory == null}">
			<acme:button url="category/administrator/list.do" code="button.back" />
		</jstl:when>
		<jstl:otherwise>
			<acme:button url="category/administrator/list.do?parentCategoryId=${parentCategory.parentCategory.id}" code="button.back" />
		</jstl:otherwise>
	</jstl:choose>
</jstl:if>
	
			
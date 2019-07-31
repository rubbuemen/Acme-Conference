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

<display:table pagesize="5" class="displaytag" name="comments" requestURI="${requestURI}" id="row">

	<spring:message code="comment.title" var="title" />
	<display:column property="title" title="${title}" />
	
	<spring:message code="comment.moment" var="moment" />
	<display:column title="${moment}">
			<fmt:formatDate var="format" value="${row.moment}" pattern="dd/MM/YYYY HH:mm" />
			<jstl:out value="${format}" />
	</display:column>
	
	<spring:message code="comment.author" var="author" />
	<display:column property="author" title="${author}" />
	
	<spring:message code="comment.text" var="text" />
	<display:column property="text" title="${text}" />
			
</display:table>

<acme:button url="comment/create.do?commentableId=${commentable.id}" code="button.create" />
<acme:button url="conference/listGeneric.do" code="button.backConferences" />
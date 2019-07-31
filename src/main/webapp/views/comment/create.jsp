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

<form:form action="${actionURL}" modelAttribute="comment">
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="moment" />
	<input type="hidden" name="commentableId" value="${commentable.id}">
	
	<acme:textbox code="comment.title" path="title" placeholder="Lorem Ipsum"/>
	<br />
			
	<acme:textbox code="comment.author" path="author" placeholder="Lorem Ipsum"/>
	<br />
	
	<acme:textarea code="comment.text" path="text" placeholder="Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aenean at auctor massa"/>
	<br />
	
	<acme:submit name="save" code="button.register" />
	
	<acme:cancel url="comment/list.do?commentableId=${commentable.id}" code="button.cancel" />
</form:form>
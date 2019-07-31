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


<details>
<summary style="font-size: 26px; cursor:pointer;"><spring:message code="dashboard.queryC1"/></summary>

<ul>
<li><b><spring:message code="dashboard.min"/>:</b> <jstl:out value="${minQueryC1 == \"null\" ? 0 : minQueryC1}"></jstl:out></li>
<li><b><spring:message code="dashboard.max"/>:</b> <jstl:out value="${maxQueryC1 == \"null\" ? 0 : maxQueryC1}"></jstl:out></li>
<li><b><spring:message code="dashboard.avg"/>:</b> <jstl:out value="${avgQueryC1 == \"null\" ? 0 : avgQueryC1}"></jstl:out></li>
<li><b><spring:message code="dashboard.stddev"/>:</b> <jstl:out value="${stddevQueryC1 == \"null\" ? 0 : stddevQueryC1}"></jstl:out></li>
</ul>
</details><br/>

<details>
<summary style="font-size: 26px; cursor:pointer;"><spring:message code="dashboard.queryC2"/></summary>
<ul>
<li><b><spring:message code="dashboard.min"/>:</b> <jstl:out value="${minQueryC2 == \"null\" ? 0 : minQueryC2}"></jstl:out></li>
<li><b><spring:message code="dashboard.max"/>:</b> <jstl:out value="${maxQueryC2 == \"null\" ? 0 : maxQueryC2}"></jstl:out></li>
<li><b><spring:message code="dashboard.avg"/>:</b> <jstl:out value="${avgQueryC2 == \"null\" ? 0 : avgQueryC2}"></jstl:out></li>
<li><b><spring:message code="dashboard.stddev"/>:</b> <jstl:out value="${stddevQueryC2 == \"null\" ? 0 : stddevQueryC2}"></jstl:out></li>
</ul>
</details><br/>

<details>
<summary style="font-size: 26px; cursor:pointer;"><spring:message code="dashboard.queryC3"/></summary>
<ul>
<li><b><spring:message code="dashboard.min"/>:</b> <jstl:out value="${minQueryC3 == \"null\" ? 0 : minQueryC3}"></jstl:out></li>
<li><b><spring:message code="dashboard.max"/>:</b> <jstl:out value="${maxQueryC3 == \"null\" ? 0 : maxQueryC3}"></jstl:out></li>
<li><b><spring:message code="dashboard.avg"/>:</b> <jstl:out value="${avgQueryC3 == \"null\" ? 0 : avgQueryC3}"></jstl:out></li>
<li><b><spring:message code="dashboard.stddev"/>:</b> <jstl:out value="${stddevQueryC3 == \"null\" ? 0 : stddevQueryC3}"></jstl:out></li>
</ul>
</details><br/>

<details>
<summary style="font-size: 26px; cursor:pointer;"><spring:message code="dashboard.queryC4"/></summary>
<ul>
<li><b><spring:message code="dashboard.min"/>:</b> <jstl:out value="${minQueryC4 == \"null\" ? 0 : minQueryC4}"></jstl:out></li>
<li><b><spring:message code="dashboard.max"/>:</b> <jstl:out value="${maxQueryC4 == \"null\" ? 0 : maxQueryC4}"></jstl:out></li>
<li><b><spring:message code="dashboard.avg"/>:</b> <jstl:out value="${avgQueryC4 == \"null\" ? 0 : avgQueryC4}"></jstl:out></li>
<li><b><spring:message code="dashboard.stddev"/>:</b> <jstl:out value="${stddevQueryC4 == \"null\" ? 0 : stddevQueryC4}"></jstl:out></li>
</ul>
</details><br/>

<details>
<summary style="font-size: 26px; cursor:pointer;"><spring:message code="dashboard.queryB1"/></summary>
<ul>
<li><b><spring:message code="dashboard.min"/>:</b> <jstl:out value="${minQueryB1 == \"null\" ? 0 : minQueryB1}"></jstl:out></li>
<li><b><spring:message code="dashboard.max"/>:</b> <jstl:out value="${maxQueryB1 == \"null\" ? 0 : maxQueryB1}"></jstl:out></li>
<li><b><spring:message code="dashboard.avg"/>:</b> <jstl:out value="${avgQueryB1 == \"null\" ? 0 : avgQueryB1}"></jstl:out></li>
<li><b><spring:message code="dashboard.stddev"/>:</b> <jstl:out value="${stddevQueryB1 == \"null\" ? 0 : stddevQueryB1}"></jstl:out></li>
</ul>
</details><br/>

<details>
<summary style="font-size: 26px; cursor:pointer;"><spring:message code="dashboard.queryB2"/></summary>
<ul>
<li><b><spring:message code="dashboard.min"/>:</b> <jstl:out value="${minQueryB2 == \"null\" ? 0 : minQueryB2}"></jstl:out></li>
<li><b><spring:message code="dashboard.max"/>:</b> <jstl:out value="${maxQueryB2 == \"null\" ? 0 : maxQueryB2}"></jstl:out></li>
<li><b><spring:message code="dashboard.avg"/>:</b> <jstl:out value="${avgQueryB2 == \"null\" ? 0 : avgQueryB2}"></jstl:out></li>
<li><b><spring:message code="dashboard.stddev"/>:</b> <jstl:out value="${stddevQueryB2 == \"null\" ? 0 : stddevQueryB2}"></jstl:out></li>
</ul>
</details><br/>

<details>
<summary style="font-size: 26px; cursor:pointer;"><spring:message code="dashboard.queryB3"/></summary>
<ul>
<li><b><spring:message code="dashboard.min"/>:</b> <jstl:out value="${minQueryB3 == \"null\" ? 0 : minQueryB3}"></jstl:out></li>
<li><b><spring:message code="dashboard.max"/>:</b> <jstl:out value="${maxQueryB3 == \"null\" ? 0 : maxQueryB3}"></jstl:out></li>
<li><b><spring:message code="dashboard.avg"/>:</b> <jstl:out value="${avgQueryB3 == \"null\" ? 0 : avgQueryB3}"></jstl:out></li>
<li><b><spring:message code="dashboard.stddev"/>:</b> <jstl:out value="${stddevQueryB3 == \"null\" ? 0 : stddevQueryB3}"></jstl:out></li>
</ul>
</details><br/>

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
<li><b><spring:message code="dashboard.avg"/>:</b> <jstl:out value="${avgQueryC1 == \"null\" ? 0 : avgQueryC1}"></jstl:out></li>
<li><b><spring:message code="dashboard.min"/>:</b> <jstl:out value="${minQueryC1 == \"null\" ? 0 : minQueryC1}"></jstl:out></li>
<li><b><spring:message code="dashboard.max"/>:</b> <jstl:out value="${maxQueryC1 == \"null\" ? 0 : maxQueryC1}"></jstl:out></li>
<li><b><spring:message code="dashboard.stddev"/>:</b> <jstl:out value="${stddevQueryC1 == \"null\" ? 0 : stddevQueryC1}"></jstl:out></li>
</ul>

</details><br/>

<details>
<summary style="font-size: 26px; cursor:pointer;"><spring:message code="dashboard.queryC2"/></summary>

<ul>
<li><b><spring:message code="dashboard.avg"/>:</b> <jstl:out value="${avgQueryC2 == \"null\" ? 0 : avgQueryC2}"></jstl:out></li>
<li><b><spring:message code="dashboard.min"/>:</b> <jstl:out value="${minQueryC2 == \"null\" ? 0 : minQueryC2}"></jstl:out></li>
<li><b><spring:message code="dashboard.max"/>:</b> <jstl:out value="${maxQueryC2 == \"null\" ? 0 : maxQueryC2}"></jstl:out></li>
<li><b><spring:message code="dashboard.stddev"/>:</b> <jstl:out value="${stddevQueryC2 == \"null\" ? 0 : stddevQueryC2}"></jstl:out></li>
</ul>

</details><br/>

<details>
<summary style="font-size: 26px; cursor:pointer;"><spring:message code="dashboard.queryC3"/></summary>

<display:table class="displaytag" name="queryC3" id="row1">
	<spring:message code="actor.name" var="name" />
	<display:column property="name" title="${name}" />

	<spring:message code="actor.surnames" var="surnames" />
	<display:column property="surnames" title="${surnames}" />

	<spring:message code="actor.VATNumber" var="VATNumber" />
	<display:column property="VATNumber" title="${VATNumber}" />

	<spring:message code="actor.photo" var="photo" />
	<display:column title="${photo}" >
		<img src="<jstl:out value="${row1.photo}"/>" width="200px" height="200px" />
	</display:column>

	<spring:message code="actor.email" var="email" />
	<display:column property="email" title="${email}" />

	<spring:message code="actor.phoneNumber" var="phoneNumber" />
	<display:column property="phoneNumber" title="${phoneNumber}" />

	<spring:message code="actor.address" var="address" />
	<display:column property="address" title="${address}" />
	
	<spring:message code="reviewer.commercialName" var="commercialName" />
	<display:column property="commercialName" title="${commercialName}" />
</display:table>

</details><br/>

<details>
<summary style="font-size: 26px; cursor:pointer;"><spring:message code="dashboard.queryC4"/></summary>

<display:table class="displaytag" name="queryC4" id="row2">
	<spring:message code="actor.name" var="name" />
	<display:column property="name" title="${name}" />

	<spring:message code="actor.surnames" var="surnames" />
	<display:column property="surnames" title="${surnames}" />

	<spring:message code="actor.VATNumber" var="VATNumber" />
	<display:column property="VATNumber" title="${VATNumber}" />

	<spring:message code="actor.photo" var="photo" />
	<display:column title="${photo}" >
		<img src="<jstl:out value="${row2.photo}"/>" width="200px" height="200px" />
	</display:column>

	<spring:message code="actor.email" var="email" />
	<display:column property="email" title="${email}" />

	<spring:message code="actor.phoneNumber" var="phoneNumber" />
	<display:column property="phoneNumber" title="${phoneNumber}" />

	<spring:message code="actor.address" var="address" />
	<display:column property="address" title="${address}" />
</display:table>

</details><br/>

<details>
<summary style="font-size: 26px; cursor:pointer;"><spring:message code="dashboard.queryC5"/></summary>

<ul>
<li><b><spring:message code="dashboard.avg"/>:</b> <jstl:out value="${avgQueryC5 == \"null\" ? 0 : avgQueryC5}"></jstl:out></li>
<li><b><spring:message code="dashboard.min"/>:</b> <jstl:out value="${minQueryC5 == \"null\" ? 0 : minQueryC5}"></jstl:out></li>
<li><b><spring:message code="dashboard.max"/>:</b> <jstl:out value="${maxQueryC5 == \"null\" ? 0 : maxQueryC5}"></jstl:out></li>
<li><b><spring:message code="dashboard.stddev"/>:</b> <jstl:out value="${stddevQueryC5 == \"null\" ? 0 : stddevQueryC5}"></jstl:out></li>
</ul>

</details><br/>

<details>
<summary style="font-size: 26px; cursor:pointer;"><spring:message code="dashboard.queryC6"/></summary>

<h3><spring:message code="dashboard.queryC6.bestPosition"/></h3>
<display:table class="displaytag" name="queryC6_1" id="row3">
	<spring:message code="position.ticker" var="ticker" />
	<display:column property="ticker" title="${ticker}" />
	
	<spring:message code="position.title" var="title" />
	<display:column property="title" title="${title}" />
	
	<spring:message code="position.description" var="description" />
	<display:column property="description" title="${description}" />
	
	<spring:message code="position.deadline" var="deadline" />
	<display:column title="${deadline}">
			<fmt:formatDate var="format" value="${row.deadline}" pattern="dd/MM/YYYY" />
			<jstl:out value="${format}" />
	</display:column>
	
	<spring:message code="position.skills" var="skills" />
	<display:column title="${skills}" >
	<ul>
	<jstl:forEach items="${row.skills}" var="skill">
		<li><jstl:out value="${skill}"/></li>
	</jstl:forEach>
	</ul>
	</display:column>
	
	<spring:message code="position.technologies" var="technologies" />
	<display:column title="${technologies}" >
	<ul>
	<jstl:forEach items="${row.technologies}" var="technology">
		<li><jstl:out value="${technology}"/></li>
	</jstl:forEach>
	</ul>
	</display:column>
	
	<spring:message code="position.salary" var="salary" />
	<display:column property="salary" title="${salary}" />
	
	<spring:message code="position.profile" var="profile" />
	<display:column property="profile" title="${profile}" />
</display:table>

<h3><spring:message code="dashboard.queryC6.worstPosition"/></h3>
<display:table class="displaytag" name="queryC6_2" id="row4">
	<spring:message code="position.ticker" var="ticker" />
	<display:column property="ticker" title="${ticker}" />
	
	<spring:message code="position.title" var="title" />
	<display:column property="title" title="${title}" />
	
	<spring:message code="position.description" var="description" />
	<display:column property="description" title="${description}" />
	
	<spring:message code="position.deadline" var="deadline" />
	<display:column title="${deadline}">
			<fmt:formatDate var="format" value="${row.deadline}" pattern="dd/MM/YYYY" />
			<jstl:out value="${format}" />
	</display:column>
	
	<spring:message code="position.skills" var="skills" />
	<display:column title="${skills}" >
	<ul>
	<jstl:forEach items="${row.skills}" var="skill">
		<li><jstl:out value="${skill}"/></li>
	</jstl:forEach>
	</ul>
	</display:column>
	
	<spring:message code="position.technologies" var="technologies" />
	<display:column title="${technologies}" >
	<ul>
	<jstl:forEach items="${row.technologies}" var="technology">
		<li><jstl:out value="${technology}"/></li>
	</jstl:forEach>
	</ul>
	</display:column>
	
	<spring:message code="position.salary" var="salary" />
	<display:column property="salary" title="${salary}" />
	
	<spring:message code="position.profile" var="profile" />
	<display:column property="profile" title="${profile}" />
</display:table>

</details><br/>

<details>
<summary style="font-size: 26px; cursor:pointer;"><spring:message code="dashboard.queryB1"/></summary>

<ul>
<li><b><spring:message code="dashboard.avg"/>:</b> <jstl:out value="${avgQueryB1 == \"null\" ? 0 : avgQueryB1}"></jstl:out></li>
<li><b><spring:message code="dashboard.min"/>:</b> <jstl:out value="${minQueryB1 == \"null\" ? 0 : minQueryB1}"></jstl:out></li>
<li><b><spring:message code="dashboard.max"/>:</b> <jstl:out value="${maxQueryB1 == \"null\" ? 0 : maxQueryB1}"></jstl:out></li>
<li><b><spring:message code="dashboard.stddev"/>:</b> <jstl:out value="${stddevQueryB1 == \"null\" ? 0 : stddevQueryB1}"></jstl:out></li>
</ul>

</details><br/>

<details>
<summary style="font-size: 26px; cursor:pointer;"><spring:message code="dashboard.queryB2"/></summary>

<ul>
<li><b><spring:message code="dashboard.avg"/>:</b> <jstl:out value="${avgQueryB2 == \"null\" ? 0 : avgQueryB2}"></jstl:out></li>
<li><b><spring:message code="dashboard.min"/>:</b> <jstl:out value="${minQueryB2 == \"null\" ? 0 : minQueryB2}"></jstl:out></li>
<li><b><spring:message code="dashboard.max"/>:</b> <jstl:out value="${maxQueryB2 == \"null\" ? 0 : maxQueryB2}"></jstl:out></li>
<li><b><spring:message code="dashboard.stddev"/>:</b> <jstl:out value="${stddevQueryB2 == \"null\" ? 0 : stddevQueryB2}"></jstl:out></li>
</ul>

</details><br/>


<details>
<summary style="font-size: 26px; cursor:pointer;"><spring:message code="dashboard.queryB3"/></summary>

<ul>
<li><b><spring:message code="dashboard.ratio"/>:</b> <jstl:out value="${ratioQueryB3 == \"null\" ? 0 : ratioQueryB3}"></jstl:out></li>
</ul>

</details><br/>

<details>
<summary style="font-size: 26px; cursor:pointer;"><spring:message code="dashboard.queryAcmeAuthorsC1"/></summary>

<ul>
<li><b><spring:message code="dashboard.avg"/>:</b> <jstl:out value="${avgQueryAcmeAuthorsC1 == \"null\" ? 0 : avgQueryAcmeAuthorsC1}"></jstl:out></li>
<li><b><spring:message code="dashboard.min"/>:</b> <jstl:out value="${minQueryAcmeAuthorsC1 == \"null\" ? 0 : minQueryAcmeAuthorsC1}"></jstl:out></li>
<li><b><spring:message code="dashboard.max"/>:</b> <jstl:out value="${maxQueryAcmeAuthorsC1 == \"null\" ? 0 : maxQueryAcmeAuthorsC1}"></jstl:out></li>
<li><b><spring:message code="dashboard.stddev"/>:</b> <jstl:out value="${stddevQueryAcmeAuthorsC1 == \"null\" ? 0 : stddevQueryAcmeAuthorsC1}"></jstl:out></li>
</ul>

</details><br/>

<details>
<summary style="font-size: 26px; cursor:pointer;"><spring:message code="dashboard.queryAcmeAuthorsC2"/></summary>

<ul>
<li><b><spring:message code="dashboard.avg"/>:</b> <jstl:out value="${avgQueryAcmeAuthorsC2 == \"null\" ? 0 : avgQueryAcmeAuthorsC2}"></jstl:out></li>
<li><b><spring:message code="dashboard.min"/>:</b> <jstl:out value="${minQueryAcmeAuthorsC2 == \"null\" ? 0 : minQueryAcmeAuthorsC2}"></jstl:out></li>
<li><b><spring:message code="dashboard.max"/>:</b> <jstl:out value="${maxQueryAcmeAuthorsC2 == \"null\" ? 0 : maxQueryAcmeAuthorsC2}"></jstl:out></li>
<li><b><spring:message code="dashboard.stddev"/>:</b> <jstl:out value="${stddevQueryAcmeAuthorsC2 == \"null\" ? 0 : stddevQueryAcmeAuthorsC2}"></jstl:out></li>
</ul>

</details><br/>

<details>
<summary style="font-size: 26px; cursor:pointer;"><spring:message code="dashboard.queryAcmeAuthorsC3"/></summary>

<display:table class="displaytag" name="queryAcmeAuthorsC3" id="row5">
	<spring:message code="actor.name" var="name" />
	<display:column property="name" title="${name}" />

	<spring:message code="actor.surnames" var="surnames" />
	<display:column property="surnames" title="${surnames}" />

	<spring:message code="actor.VATNumber" var="VATNumber" />
	<display:column property="VATNumber" title="${VATNumber}" />

	<spring:message code="actor.photo" var="photo" />
	<display:column title="${photo}" >
		<img src="<jstl:out value="${row5.photo}"/>" width="200px" height="200px" />
	</display:column>

	<spring:message code="actor.email" var="email" />
	<display:column property="email" title="${email}" />

	<spring:message code="actor.phoneNumber" var="phoneNumber" />
	<display:column property="phoneNumber" title="${phoneNumber}" />

	<spring:message code="actor.address" var="address" />
	<display:column property="address" title="${address}" />
	
	<spring:message code="reviewer.commercialName" var="commercialName" />
	<display:column property="commercialName" title="${commercialName}" />
</display:table>

</details><br/>

<details>
<summary style="font-size: 26px; cursor:pointer;"><spring:message code="dashboard.queryAcmeAuthorsC4"/></summary>

<ul>
<li><b><spring:message code="dashboard.avg"/>:</b> <jstl:out value="${avgQueryAcmeAuthorsC4 == \"null\" ? 0 : avgQueryAcmeAuthorsC4}"></jstl:out></li>
</ul>

</details><br/>

<details>
<summary style="font-size: 26px; cursor:pointer;"><spring:message code="dashboard.queryAcmeAuthorsB1"/></summary>

<ul>
<li><b><spring:message code="dashboard.avg"/>:</b> <jstl:out value="${avgQueryAcmeAuthorsB1 == \"null\" ? 0 : avgQueryAcmeAuthorsB1}"></jstl:out></li>
<li><b><spring:message code="dashboard.min"/>:</b> <jstl:out value="${minQueryAcmeAuthorsB1 == \"null\" ? 0 : minQueryAcmeAuthorsB1}"></jstl:out></li>
<li><b><spring:message code="dashboard.max"/>:</b> <jstl:out value="${maxQueryAcmeAuthorsB1 == \"null\" ? 0 : maxQueryAcmeAuthorsB1}"></jstl:out></li>
<li><b><spring:message code="dashboard.stddev"/>:</b> <jstl:out value="${stddevQueryAcmeAuthorsB1 == \"null\" ? 0 : stddevQueryAcmeAuthorsB1}"></jstl:out></li>
</ul>

</details><br/>

<details>
<summary style="font-size: 26px; cursor:pointer;"><spring:message code="dashboard.queryAcmeAuthorsB2"/></summary>

<display:table class="displaytag" name="queryAcmeAuthorsB2" id="row6">
	<spring:message code="actor.name" var="name" />
	<display:column property="name" title="${name}" />

	<spring:message code="actor.surnames" var="surnames" />
	<display:column property="surnames" title="${surnames}" />

	<spring:message code="actor.VATNumber" var="VATNumber" />
	<display:column property="VATNumber" title="${VATNumber}" />

	<spring:message code="actor.photo" var="photo" />
	<display:column title="${photo}" >
		<img src="<jstl:out value="${row6.photo}"/>" width="200px" height="200px" />
	</display:column>

	<spring:message code="actor.email" var="email" />
	<display:column property="email" title="${email}" />

	<spring:message code="actor.phoneNumber" var="phoneNumber" />
	<display:column property="phoneNumber" title="${phoneNumber}" />

	<spring:message code="actor.address" var="address" />
	<display:column property="address" title="${address}" />
	
	<spring:message code="sponsor.make" var="make" />
	<display:column property="make" title="${make}" />
</display:table>

</details><br/>

<details>
<summary style="font-size: 26px; cursor:pointer;"><spring:message code="dashboard.queryAcmeAuthorsA1"/></summary>

<ul>
<li><b><spring:message code="dashboard.avg"/>:</b> <jstl:out value="${avgQueryAcmeAuthorsA1 == \"null\" ? 0 : avgQueryAcmeAuthorsA1}"></jstl:out></li>
<li><b><spring:message code="dashboard.min"/>:</b> <jstl:out value="${minQueryAcmeAuthorsA1 == \"null\" ? 0 : minQueryAcmeAuthorsA1}"></jstl:out></li>
<li><b><spring:message code="dashboard.max"/>:</b> <jstl:out value="${maxQueryAcmeAuthorsA1 == \"null\" ? 0 : maxQueryAcmeAuthorsA1}"></jstl:out></li>
<li><b><spring:message code="dashboard.stddev"/>:</b> <jstl:out value="${stddevQueryAcmeAuthorsA1 == \"null\" ? 0 : stddevQueryAcmeAuthorsA1}"></jstl:out></li>
</ul>

</details><br/>

<details>
<summary style="font-size: 26px; cursor:pointer;"><spring:message code="dashboard.queryAcmeAuthorsA2"/></summary>

<ul>
<li><b><spring:message code="dashboard.avg"/>:</b> <jstl:out value="${avgQueryAcmeAuthorsA2 == \"null\" ? 0 : avgQueryAcmeAuthorsA2}"></jstl:out></li>
<li><b><spring:message code="dashboard.min"/>:</b> <jstl:out value="${minQueryAcmeAuthorsA2 == \"null\" ? 0 : minQueryAcmeAuthorsA2}"></jstl:out></li>
<li><b><spring:message code="dashboard.max"/>:</b> <jstl:out value="${maxQueryAcmeAuthorsA2 == \"null\" ? 0 : maxQueryAcmeAuthorsA2}"></jstl:out></li>
<li><b><spring:message code="dashboard.stddev"/>:</b> <jstl:out value="${stddevQueryAcmeAuthorsA2 == \"null\" ? 0 : stddevQueryAcmeAuthorsA2}"></jstl:out></li>
</ul>

</details><br/>

<details>
<summary style="font-size: 26px; cursor:pointer;"><spring:message code="dashboard.queryAcmeAuthorsA3"/></summary>

<display:table class="displaytag" name="queryAcmeAuthorsA3" id="row7">
	<spring:message code="actor.name" var="name" />
	<display:column property="name" title="${name}" />

	<spring:message code="actor.surnames" var="surnames" />
	<display:column property="surnames" title="${surnames}" />

	<spring:message code="actor.VATNumber" var="VATNumber" />
	<display:column property="VATNumber" title="${VATNumber}" />

	<spring:message code="actor.photo" var="photo" />
	<display:column title="${photo}" >
		<img src="<jstl:out value="${row7.photo}"/>" width="200px" height="200px" />
	</display:column>

	<spring:message code="actor.email" var="email" />
	<display:column property="email" title="${email}" />

	<spring:message code="actor.phoneNumber" var="phoneNumber" />
	<display:column property="phoneNumber" title="${phoneNumber}" />

	<spring:message code="actor.address" var="address" />
	<display:column property="address" title="${address}" />
	
	<spring:message code="sponsor.make" var="make" />
	<display:column property="make" title="${make}" />
</display:table>

</details><br/>
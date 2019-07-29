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
<jsp:useBean id="date" class="java.util.Date" />

<display:table pagesize="5" class="displaytag" name="conferences" requestURI="${requestURI}" id="row">
	<spring:message code="conference.title" var="title" />
	<display:column property="title" title="${title}" />
	
	<spring:message code="conference.acronym" var="acronym" />
	<display:column property="acronym" title="${acronym}" />
	
	<spring:message code="conference.venue" var="venue" />
	<display:column property="venue" title="${venue}" />
	
	<spring:message code="conference.submissionDeadline" var="submissionDeadline" />
	<display:column title="${submissionDeadline}">
		<fmt:formatDate var="format" value="${row.submissionDeadline}" pattern="dd/MM/YYYY" />
		<jstl:out value="${format}" />
	</display:column>
	
	<spring:message code="conference.notificationDeadline" var="notificationDeadline" />
	<display:column title="${notificationDeadline}">
		<fmt:formatDate var="format" value="${row.notificationDeadline}" pattern="dd/MM/YYYY" />
		<jstl:out value="${format}" />
	</display:column>
	
	<spring:message code="conference.cameraReadyDeadline" var="cameraReadyDeadline" />
	<display:column title="${cameraReadyDeadline}">
		<fmt:formatDate var="format" value="${row.cameraReadyDeadline}" pattern="dd/MM/YYYY" />
		<jstl:out value="${format}" />
	</display:column>
	
	<spring:message code="conference.startDate" var="startDate" />
	<display:column title="${startDate}">
		<fmt:formatDate var="format" value="${row.startDate}" pattern="dd/MM/YYYY" />
		<jstl:out value="${format}" />
	</display:column>
	
	<spring:message code="conference.endDate" var="endDate" />
	<display:column title="${endDate}">
		<fmt:formatDate var="format" value="${row.endDate}" pattern="dd/MM/YYYY" />
		<jstl:out value="${format}" />
	</display:column>
	
	<spring:message code="conference.summary" var="summary" />
	<display:column property="summary" title="${summary}" />
	
	<spring:message code="conference.fee" var="fee" />
	<display:column property="fee" title="${fee}" />
	
	<spring:message code="conference.category" var="category" />
	<display:column title="${category}">
		<jstl:if test="${language eq 'en'}">
			<jstl:out value="${row.category.titleEnglish}" />
		</jstl:if>
		<jstl:if test="${language eq 'es'}">
			<jstl:out value="${row.category.titleSpanish}" />
		</jstl:if>
	</display:column>
	
	<spring:message code="conference.sponsorship" var="sponsorship" />
	<display:column title="${sponsorship}" >
		<jstl:if test="${randomSponsorship.containsKey(row)}">
			<jstl:set var="banner" value="${randomSponsorship.get(row).banner}"/>
			<img src="<jstl:out value='${banner}'/>" width="200px" height="100px" />
		</jstl:if>
	</display:column>
	
	<spring:message code="conference.edit" var="editH" />
	<display:column title="${editH}" >
		<jstl:if test="${!row.isFinalMode}">
			<acme:button url="conference/administrator/edit.do?conferenceId=${row.id}" code="button.edit" />
		</jstl:if>	
	</display:column>
	
	<spring:message code="conference.delete" var="deleteH" />
	<display:column title="${deleteH}" >
		<jstl:if test="${!row.isFinalMode}">
			<acme:button url="conference/administrator/delete.do?conferenceId=${row.id}" code="button.delete" />
		</jstl:if>	
	</display:column>
	
	<spring:message code="conference.changeFinalMode" var="changeFinalModeH" />
	<display:column title="${changeFinalModeH}" >
		<jstl:choose>
			<jstl:when test="${!row.isFinalMode}">
				<acme:button url="conference/administrator/change.do?conferenceId=${row.id}" code="button.change" />
			</jstl:when>
			<jstl:when test="${row.isFinalMode}">
				<spring:message code="conference.isFinalMode" />
			</jstl:when>
		</jstl:choose>
	</display:column>
	
	<spring:message code="conference.runDecisionMakingProcedure" var="runDecisionMakingProcedure" />
	<display:column title="${runDecisionMakingProcedure}" >
		<jstl:if test="${row.isFinalMode}">
			<jstl:choose>
				<jstl:when test="${!row.isDecisionProcedureDone}">
					<jstl:choose>
						<jstl:when test="${row.submissionDeadline >= date}">
							<spring:message code="conference.decisionSubmissionDeadline" />
						</jstl:when>
						<jstl:when test="${row.startDate <= date}">
							<spring:message code="conference.decisionStartDate" />
						</jstl:when>
						<jstl:otherwise>
							<acme:button url="conference/administrator/decisionMakingProcedure.do?conferenceId=${row.id}" code="button.runDecisionMakingProcedure" />
						</jstl:otherwise>
					</jstl:choose>
				</jstl:when>
				<jstl:otherwise>
					<spring:message code="conference.isDecisionProcedureDone" />
				</jstl:otherwise>
			</jstl:choose>
		</jstl:if>
	</display:column>
</display:table>

<acme:button url="conference/administrator/create.do" code="button.create" />
<br /><br /><br />

<details>
<summary style="font-size: 26px; cursor:pointer;"><spring:message code="conference.conferencesSubmissionDeadlineLastFiveDays"/></summary>

<display:table class="displaytag" name="conferencesSubmissionDeadlineLastFiveDays" id="row1">
	<spring:message code="conference.title" var="title" />
	<display:column property="title" title="${title}" />
	
	<spring:message code="conference.acronym" var="acronym" />
	<display:column property="acronym" title="${acronym}" />
	
	<spring:message code="conference.venue" var="venue" />
	<display:column property="venue" title="${venue}" />
	
	<spring:message code="conference.submissionDeadline" var="submissionDeadline" />
	<display:column title="${submissionDeadline}">
		<fmt:formatDate var="format" value="${row1.submissionDeadline}" pattern="dd/MM/YYYY" />
		<jstl:out value="${format}" />
	</display:column>
	
	<spring:message code="conference.notificationDeadline" var="notificationDeadline" />
	<display:column title="${notificationDeadline}">
		<fmt:formatDate var="format" value="${row1.notificationDeadline}" pattern="dd/MM/YYYY" />
		<jstl:out value="${format}" />
	</display:column>
	
	<spring:message code="conference.cameraReadyDeadline" var="cameraReadyDeadline" />
	<display:column title="${cameraReadyDeadline}">
		<fmt:formatDate var="format" value="${row1.cameraReadyDeadline}" pattern="dd/MM/YYYY" />
		<jstl:out value="${format}" />
	</display:column>
	
	<spring:message code="conference.startDate" var="startDate" />
	<display:column title="${startDate}">
		<fmt:formatDate var="format" value="${row1.startDate}" pattern="dd/MM/YYYY" />
		<jstl:out value="${format}" />
	</display:column>
	
	<spring:message code="conference.endDate" var="endDate" />
	<display:column title="${endDate}">
		<fmt:formatDate var="format" value="${row1.endDate}" pattern="dd/MM/YYYY" />
		<jstl:out value="${format}" />
	</display:column>
	
	<spring:message code="conference.summary" var="summary" />
	<display:column property="summary" title="${summary}" />
	
	<spring:message code="conference.fee" var="fee" />
	<display:column property="fee" title="${fee}" />
	
	<spring:message code="conference.category" var="category" />
	<display:column title="${category}">
		<jstl:if test="${language eq 'en'}">
			<jstl:out value="${row1.category.titleEnglish}" />
		</jstl:if>
		<jstl:if test="${language eq 'es'}">
			<jstl:out value="${row1.category.titleSpanish}" />
		</jstl:if>
	</display:column>
	
	<spring:message code="conference.sponsorship" var="sponsorship" />
	<display:column title="${sponsorship}" >
		<jstl:if test="${randomSponsorship1.containsKey(row1)}">
			<jstl:set var="banner" value="${randomSponsorship1.get(row1).banner}"/>
			<img src="<jstl:out value='${banner}'/>" width="200px" height="100px" />
		</jstl:if>
	</display:column>
	
	<spring:message code="conference.edit" var="editH" />
	<display:column title="${editH}" >
		<jstl:if test="${!row1.isFinalMode}">
			<acme:button url="conference/administrator/edit.do?conferenceId=${row1.id}" code="button.edit" />
		</jstl:if>	
	</display:column>
	
	<spring:message code="conference.delete" var="deleteH" />
	<display:column title="${deleteH}" >
		<jstl:if test="${!row1.isFinalMode}">
			<acme:button url="conference/administrator/delete.do?conferenceId=${row1.id}" code="button.delete" />
		</jstl:if>	
	</display:column>
	
	<spring:message code="conference.changeFinalMode" var="changeFinalModeH" />
	<display:column title="${changeFinalModeH}" >
		<jstl:choose>
			<jstl:when test="${!row1.isFinalMode}">
				<acme:button url="conference/administrator/change.do?conferenceId=${row1.id}" code="button.change" />
			</jstl:when>
			<jstl:when test="${row1.isFinalMode}">
				<spring:message code="conference.isFinalMode" />
			</jstl:when>
		</jstl:choose>
	</display:column>
	
	<spring:message code="conference.runDecisionMakingProcedure" var="runDecisionMakingProcedure" />
	<display:column title="${runDecisionMakingProcedure}" >
		<jstl:if test="${row1.isFinalMode}">
			<jstl:choose>
				<jstl:when test="${!row1.isDecisionProcedureDone}">
					<jstl:choose>
						<jstl:when test="${row1.submissionDeadline >= date}">
							<spring:message code="conference.decisionSubmissionDeadline" />
						</jstl:when>
						<jstl:when test="${row1.startDate <= date}">
							<spring:message code="conference.decisionStartDate" />
						</jstl:when>
						<jstl:otherwise>
							<acme:button url="conference/administrator/decisionMakingProcedure.do?conferenceId=${row1.id}" code="button.runDecisionMakingProcedure" />
						</jstl:otherwise>
					</jstl:choose>
				</jstl:when>
				<jstl:otherwise>
					<spring:message code="conference.isDecisionProcedureDone" />
				</jstl:otherwise>
			</jstl:choose>
		</jstl:if>
	</display:column>
</display:table>

</details><br/>

<details>
<summary style="font-size: 26px; cursor:pointer;"><spring:message code="conference.conferencesNotificationDeadlineInLessFiveDays"/></summary>

<display:table class="displaytag" name="conferencesNotificationDeadlineInLessFiveDays" id="row2">
	<spring:message code="conference.title" var="title" />
	<display:column property="title" title="${title}" />
	
	<spring:message code="conference.acronym" var="acronym" />
	<display:column property="acronym" title="${acronym}" />
	
	<spring:message code="conference.venue" var="venue" />
	<display:column property="venue" title="${venue}" />
	
	<spring:message code="conference.submissionDeadline" var="submissionDeadline" />
	<display:column title="${submissionDeadline}">
		<fmt:formatDate var="format" value="${row2.submissionDeadline}" pattern="dd/MM/YYYY" />
		<jstl:out value="${format}" />
	</display:column>
	
	<spring:message code="conference.notificationDeadline" var="notificationDeadline" />
	<display:column title="${notificationDeadline}">
		<fmt:formatDate var="format" value="${row2.notificationDeadline}" pattern="dd/MM/YYYY" />
		<jstl:out value="${format}" />
	</display:column>
	
	<spring:message code="conference.cameraReadyDeadline" var="cameraReadyDeadline" />
	<display:column title="${cameraReadyDeadline}">
		<fmt:formatDate var="format" value="${row2.cameraReadyDeadline}" pattern="dd/MM/YYYY" />
		<jstl:out value="${format}" />
	</display:column>
	
	<spring:message code="conference.startDate" var="startDate" />
	<display:column title="${startDate}">
		<fmt:formatDate var="format" value="${row2.startDate}" pattern="dd/MM/YYYY" />
		<jstl:out value="${format}" />
	</display:column>
	
	<spring:message code="conference.endDate" var="endDate" />
	<display:column title="${endDate}">
		<fmt:formatDate var="format" value="${row2.endDate}" pattern="dd/MM/YYYY" />
		<jstl:out value="${format}" />
	</display:column>
	
	<spring:message code="conference.summary" var="summary" />
	<display:column property="summary" title="${summary}" />
	
	<spring:message code="conference.fee" var="fee" />
	<display:column property="fee" title="${fee}" />
	
	<spring:message code="conference.category" var="category" />
	<display:column title="${category}">
		<jstl:if test="${language eq 'en'}">
			<jstl:out value="${row2.category.titleEnglish}" />
		</jstl:if>
		<jstl:if test="${language eq 'es'}">
			<jstl:out value="${row2.category.titleSpanish}" />
		</jstl:if>
	</display:column>
	
	<spring:message code="conference.sponsorship" var="sponsorship" />
	<display:column title="${sponsorship}" >
		<jstl:if test="${randomSponsorship2.containsKey(row2)}">
			<jstl:set var="banner" value="${randomSponsorship2.get(row2).banner}"/>
			<img src="<jstl:out value='${banner}'/>" width="200px" height="100px" />
		</jstl:if>
	</display:column>
	
	<spring:message code="conference.edit" var="editH" />
	<display:column title="${editH}" >
		<jstl:if test="${!row2.isFinalMode}">
			<acme:button url="conference/administrator/edit.do?conferenceId=${row2.id}" code="button.edit" />
		</jstl:if>	
	</display:column>
	
	<spring:message code="conference.delete" var="deleteH" />
	<display:column title="${deleteH}" >
		<jstl:if test="${!row2.isFinalMode}">
			<acme:button url="conference/administrator/delete.do?conferenceId=${row2.id}" code="button.delete" />
		</jstl:if>	
	</display:column>
	
	<spring:message code="conference.changeFinalMode" var="changeFinalModeH" />
	<display:column title="${changeFinalModeH}" >
		<jstl:choose>
			<jstl:when test="${!row2.isFinalMode}">
				<acme:button url="conference/administrator/change.do?conferenceId=${row2.id}" code="button.change" />
			</jstl:when>
			<jstl:when test="${row2.isFinalMode}">
				<spring:message code="conference.isFinalMode" />
			</jstl:when>
		</jstl:choose>
	</display:column>
	
	<spring:message code="conference.runDecisionMakingProcedure" var="runDecisionMakingProcedure" />
	<display:column title="${runDecisionMakingProcedure}" >
		<jstl:if test="${row2.isFinalMode}">
			<jstl:choose>
				<jstl:when test="${!row2.isDecisionProcedureDone}">
					<jstl:choose>
						<jstl:when test="${row2.submissionDeadline >= date}">
							<spring:message code="conference.decisionSubmissionDeadline" />
						</jstl:when>
						<jstl:when test="${row2.startDate <= date}">
							<spring:message code="conference.decisionStartDate" />
						</jstl:when>
						<jstl:otherwise>
							<acme:button url="conference/administrator/decisionMakingProcedure.do?conferenceId=${row2.id}" code="button.runDecisionMakingProcedure" />
						</jstl:otherwise>
					</jstl:choose>
				</jstl:when>
				<jstl:otherwise>
					<spring:message code="conference.isDecisionProcedureDone" />
				</jstl:otherwise>
			</jstl:choose>
		</jstl:if>
	</display:column>
</display:table>

</details><br/>

<details>
<summary style="font-size: 26px; cursor:pointer;"><spring:message code="conference.conferencesCameraReadyDeadlineInLessFiveDays"/></summary>

<display:table class="displaytag" name="conferencesCameraReadyDeadlineInLessFiveDays" id="row3">
	<spring:message code="conference.title" var="title" />
	<display:column property="title" title="${title}" />
	
	<spring:message code="conference.acronym" var="acronym" />
	<display:column property="acronym" title="${acronym}" />
	
	<spring:message code="conference.venue" var="venue" />
	<display:column property="venue" title="${venue}" />
	
	<spring:message code="conference.submissionDeadline" var="submissionDeadline" />
	<display:column title="${submissionDeadline}">
		<fmt:formatDate var="format" value="${row3.submissionDeadline}" pattern="dd/MM/YYYY" />
		<jstl:out value="${format}" />
	</display:column>
	
	<spring:message code="conference.notificationDeadline" var="notificationDeadline" />
	<display:column title="${notificationDeadline}">
		<fmt:formatDate var="format" value="${row3.notificationDeadline}" pattern="dd/MM/YYYY" />
		<jstl:out value="${format}" />
	</display:column>
	
	<spring:message code="conference.cameraReadyDeadline" var="cameraReadyDeadline" />
	<display:column title="${cameraReadyDeadline}">
		<fmt:formatDate var="format" value="${row3.cameraReadyDeadline}" pattern="dd/MM/YYYY" />
		<jstl:out value="${format}" />
	</display:column>
	
	<spring:message code="conference.startDate" var="startDate" />
	<display:column title="${startDate}">
		<fmt:formatDate var="format" value="${row3.startDate}" pattern="dd/MM/YYYY" />
		<jstl:out value="${format}" />
	</display:column>
	
	<spring:message code="conference.endDate" var="endDate" />
	<display:column title="${endDate}">
		<fmt:formatDate var="format" value="${row3.endDate}" pattern="dd/MM/YYYY" />
		<jstl:out value="${format}" />
	</display:column>
	
	<spring:message code="conference.summary" var="summary" />
	<display:column property="summary" title="${summary}" />
	
	<spring:message code="conference.fee" var="fee" />
	<display:column property="fee" title="${fee}" />
	
	<spring:message code="conference.category" var="category" />
	<display:column title="${category}">
		<jstl:if test="${language eq 'en'}">
			<jstl:out value="${row3.category.titleEnglish}" />
		</jstl:if>
		<jstl:if test="${language eq 'es'}">
			<jstl:out value="${row3.category.titleSpanish}" />
		</jstl:if>
	</display:column>
	
	<spring:message code="conference.sponsorship" var="sponsorship" />
	<display:column title="${sponsorship}" >
		<jstl:if test="${randomSponsorship3.containsKey(row3)}">
			<jstl:set var="banner" value="${randomSponsorship3.get(row3).banner}"/>
			<img src="<jstl:out value='${banner}'/>" width="200px" height="100px" />
		</jstl:if>
	</display:column>
	
	<spring:message code="conference.edit" var="editH" />
	<display:column title="${editH}" >
		<jstl:if test="${!row3.isFinalMode}">
			<acme:button url="conference/administrator/edit.do?conferenceId=${row3.id}" code="button.edit" />
		</jstl:if>	
	</display:column>
	
	<spring:message code="conference.delete" var="deleteH" />
	<display:column title="${deleteH}" >
		<jstl:if test="${!row3.isFinalMode}">
			<acme:button url="conference/administrator/delete.do?conferenceId=${row3.id}" code="button.delete" />
		</jstl:if>	
	</display:column>
	
	<spring:message code="conference.changeFinalMode" var="changeFinalModeH" />
	<display:column title="${changeFinalModeH}" >
		<jstl:choose>
			<jstl:when test="${!row3.isFinalMode}">
				<acme:button url="conference/administrator/change.do?conferenceId=${row3.id}" code="button.change" />
			</jstl:when>
			<jstl:when test="${row3.isFinalMode}">
				<spring:message code="conference.isFinalMode" />
			</jstl:when>
		</jstl:choose>
	</display:column>
	
	<spring:message code="conference.runDecisionMakingProcedure" var="runDecisionMakingProcedure" />
	<display:column title="${runDecisionMakingProcedure}" >
		<jstl:if test="${row3.isFinalMode}">
			<jstl:choose>
				<jstl:when test="${!row3.isDecisionProcedureDone}">
					<jstl:choose>
						<jstl:when test="${row3.submissionDeadline >= date}">
							<spring:message code="conference.decisionSubmissionDeadline" />
						</jstl:when>
						<jstl:when test="${row3.startDate <= date}">
							<spring:message code="conference.decisionStartDate" />
						</jstl:when>
						<jstl:otherwise>
							<acme:button url="conference/administrator/decisionMakingProcedure.do?conferenceId=${row3.id}" code="button.runDecisionMakingProcedure" />
						</jstl:otherwise>
					</jstl:choose>
				</jstl:when>
				<jstl:otherwise>
					<spring:message code="conference.isDecisionProcedureDone" />
				</jstl:otherwise>
			</jstl:choose>
		</jstl:if>
	</display:column>
</display:table>

</details><br/>

<details>
<summary style="font-size: 26px; cursor:pointer;"><spring:message code="conference.conferencesStartDateInLessFiveDays"/></summary>

<display:table class="displaytag" name="conferencesStartDateInLessFiveDays" id="row4">
	<spring:message code="conference.title" var="title" />
	<display:column property="title" title="${title}" />
	
	<spring:message code="conference.acronym" var="acronym" />
	<display:column property="acronym" title="${acronym}" />
	
	<spring:message code="conference.venue" var="venue" />
	<display:column property="venue" title="${venue}" />
	
	<spring:message code="conference.submissionDeadline" var="submissionDeadline" />
	<display:column title="${submissionDeadline}">
		<fmt:formatDate var="format" value="${row4.submissionDeadline}" pattern="dd/MM/YYYY" />
		<jstl:out value="${format}" />
	</display:column>
	
	<spring:message code="conference.notificationDeadline" var="notificationDeadline" />
	<display:column title="${notificationDeadline}">
		<fmt:formatDate var="format" value="${row4.notificationDeadline}" pattern="dd/MM/YYYY" />
		<jstl:out value="${format}" />
	</display:column>
	
	<spring:message code="conference.cameraReadyDeadline" var="cameraReadyDeadline" />
	<display:column title="${cameraReadyDeadline}">
		<fmt:formatDate var="format" value="${row4.cameraReadyDeadline}" pattern="dd/MM/YYYY" />
		<jstl:out value="${format}" />
	</display:column>
	
	<spring:message code="conference.startDate" var="startDate" />
	<display:column title="${startDate}">
		<fmt:formatDate var="format" value="${row4.startDate}" pattern="dd/MM/YYYY" />
		<jstl:out value="${format}" />
	</display:column>
	
	<spring:message code="conference.endDate" var="endDate" />
	<display:column title="${endDate}">
		<fmt:formatDate var="format" value="${row4.endDate}" pattern="dd/MM/YYYY" />
		<jstl:out value="${format}" />
	</display:column>
	
	<spring:message code="conference.summary" var="summary" />
	<display:column property="summary" title="${summary}" />
	
	<spring:message code="conference.fee" var="fee" />
	<display:column property="fee" title="${fee}" />
	
	<spring:message code="conference.category" var="category" />
	<display:column title="${category}">
		<jstl:if test="${language eq 'en'}">
			<jstl:out value="${row4.category.titleEnglish}" />
		</jstl:if>
		<jstl:if test="${language eq 'es'}">
			<jstl:out value="${row4.category.titleSpanish}" />
		</jstl:if>
	</display:column>
	
	<spring:message code="conference.sponsorship" var="sponsorship" />
	<display:column title="${sponsorship}" >
		<jstl:if test="${randomSponsorship4.containsKey(row4)}">
			<jstl:set var="banner" value="${randomSponsorship4.get(row4).banner}"/>
			<img src="<jstl:out value='${banner}'/>" width="200px" height="100px" />
		</jstl:if>
	</display:column>
	
	<spring:message code="conference.edit" var="editH" />
	<display:column title="${editH}" >
		<jstl:if test="${!row4.isFinalMode}">
			<acme:button url="conference/administrator/edit.do?conferenceId=${row4.id}" code="button.edit" />
		</jstl:if>	
	</display:column>
	
	<spring:message code="conference.delete" var="deleteH" />
	<display:column title="${deleteH}" >
		<jstl:if test="${!row4.isFinalMode}">
			<acme:button url="conference/administrator/delete.do?conferenceId=${row4.id}" code="button.delete" />
		</jstl:if>	
	</display:column>
	
	<spring:message code="conference.changeFinalMode" var="changeFinalModeH" />
	<display:column title="${changeFinalModeH}" >
		<jstl:choose>
			<jstl:when test="${!row4.isFinalMode}">
				<acme:button url="conference/administrator/change.do?conferenceId=${row4.id}" code="button.change" />
			</jstl:when>
			<jstl:when test="${row4.isFinalMode}">
				<spring:message code="conference.isFinalMode" />
			</jstl:when>
		</jstl:choose>
	</display:column>
	
	<spring:message code="conference.runDecisionMakingProcedure" var="runDecisionMakingProcedure" />
	<display:column title="${runDecisionMakingProcedure}" >
		<jstl:if test="${row4.isFinalMode}">
			<jstl:choose>
				<jstl:when test="${!row4.isDecisionProcedureDone}">
					<jstl:choose>
						<jstl:when test="${row4.submissionDeadline >= date}">
							<spring:message code="conference.decisionSubmissionDeadline" />
						</jstl:when>
						<jstl:when test="${row4.startDate <= date}">
							<spring:message code="conference.decisionStartDate" />
						</jstl:when>
						<jstl:otherwise>
							<acme:button url="conference/administrator/decisionMakingProcedure.do?conferenceId=${row4.id}" code="button.runDecisionMakingProcedure" />
						</jstl:otherwise>
					</jstl:choose>
				</jstl:when>
				<jstl:otherwise>
					<spring:message code="conference.isDecisionProcedureDone" />
				</jstl:otherwise>
			</jstl:choose>
		</jstl:if>
	</display:column>
</display:table>

</details><br/>
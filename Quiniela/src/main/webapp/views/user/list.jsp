<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<div class="table-responsive">
	<display:table name="users" id="row" requestURI="${requestURI}"
		pagesize="10" class="table table-hover" keepStatus="true">


		<display:column>
			<a href="quiniela/administrator/list2.do?userId=${row.id}">Pronósticos</a>
		</display:column>


		<spring:message code="user.name" var="name" />
		<display:column property="name" title="${name}"
			sortable="${true}" />
			
		<spring:message code="user.surname" var="surname" />
		<display:column property="surname" title="${surname}"
			sortable="${true}" />
			
		<spring:message code="user.emailAddress" var="emailAddress" />
		<display:column property="emailAddress" title="${emailAddress}"
			sortable="${true}" />
			
		<spring:message code="user.telefono" var="telefono" />
		<display:column property="telefono" title="${telefono}"
			sortable="${true}" />

	</display:table>


</div>
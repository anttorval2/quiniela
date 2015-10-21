<%--
 * action-2.jsp
 *
 * Copyright (C) 2013 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>


<form:form action="${requestURI}" modelAttribute="user"
	enctype="multipart/form-data">

	<div class="col-md-5  col-md-offset-3" style="margin-bottom: 50px;">
		<br />


		<form:hidden path="id" />
		<form:hidden path="version" />
		<form:hidden path="quinielas" />
		<form:hidden path="userAccount.authorities" />
		
		
		<acme:textbox code="register.username" path="userAccount.username" />
		<acme:password code="register.password" path="userAccount.password" />
		<acme:textbox code="register.name" path="name" />
		<acme:textbox code="register.surname" path="surname" />
		<acme:textbox code="register.emailAddress" path="emailAddress" />
		<acme:textbox code="register.telefono" path="telefono" />


		<acme:submit code="register.save" name="save" />

		<acme:cancel url="welcome/index.do" code="register.cancel" />


	</div>
</form:form>

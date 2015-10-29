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



<div class="col-md-5  col-md-offset-3" style="margin-bottom: 20%;">
	<br /> <br />

	<form:form action="${requestURI}" modelAttribute="quiniela">


		<form:hidden path="id" />
		<form:hidden path="version" />
		<form:hidden path="partidos" />
		<form:hidden path="administrator" />


		<acme:textbox code="quiniela.jornada" path="jornada" />
		<acme:textbox code="quiniela.fechaLimite" path="fechaLimite" placeholder="dd/mm/yy"/>

		<acme:submit code="quiniela.save" name="save" />

	</form:form>

</div>
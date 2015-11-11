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


<div class="col-md-12" >
<div class="table-responsive">
	<display:table name="quinielas" id="row" requestURI="${requestURI}"
		pagesize="10" class="table table-hover" keepStatus="true">

		<security:authorize access="hasRole('ADMINISTRATOR')">
			<display:column>
				<a href="partido/administrator/list.do?quinielaId=${row.id}">Ver</a>
			</display:column>
			
			<jstl:if test="${esQuinielaUsuario == true}">
			<jstl:if test="${row.valid == false}">
			<display:column>
				<a href="quiniela/administrator/habilitar.do?quinielaId=${row.id}&userId=${row.user.id}">Habilitar</a>
			</display:column>
			</jstl:if>
			<jstl:if test="${row.valid == true}">
			<display:column>
				<a href="quiniela/administrator/deshabilitar.do?quinielaId=${row.id}&userId=${row.user.id}">Deshabilitar</a>
			</display:column>
			</jstl:if>
			</jstl:if>
			
		</security:authorize>
		<security:authorize access="hasRole('USER')">
			<display:column>
				<a href="partido/user/list.do?quinielaId=${row.id}">Ver</a>
			</display:column>
		</security:authorize>


		<spring:message code="quiniela.jornada" var="jornada" />
		<display:column property="jornada" title="${jornada}" 
			sortable="${true}" />

		<spring:message code="quiniela.fechaLimite" var="fechaLimite" />
		<display:column property="fechaLimite" title="${fechaLimite}" format="{0,date,dd-MM-yy}"
			sortable="${true}" />



	</display:table>
</div>
</div>
<br />
<br />
<div class="col-md-3  col-md-offset-4">
<jstl:if test="${canCreate==true}">
	<div class="row">
		<a href="quiniela/administrator/crear.do">
			<button type="button" class="btn btn-default btn-lg btn-block">
				<spring:message code="quiniela.crear" />
			</button>
		</a>
	</div>
</jstl:if>
</div>

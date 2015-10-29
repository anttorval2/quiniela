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
	<display:table name="partidos" id="row" requestURI="${requestURI}"
		pagesize="15" class="table table-hover" keepStatus="true">


		<spring:message code="partido.equipo1" var="equipo1" />
		<display:column property="equipo1" title="${equipo1}"
			sortable="${true}" />

		<spring:message code="partido.equipo2" var="equipo2" />
		<display:column property="equipo2" title="${equipo2}"
			sortable="${true}" />

		<spring:message code="partido.resultado" var="resultado" />
		<display:column property="resultado" title="${resultado}"
			sortable="${true}" />

		<%-- 		<security:authorize access="hasRole('ADMINISTRATOR')">
			<jstl:if test="${canEdit==true}">
				<display:column>
					<a href="partido/administrator/edit.do?partidoId=${row.id}"><spring:message
							code="partido.edit" /></a>
				</display:column>
			</jstl:if>
		</security:authorize> --%>

		<%-- 		<security:authorize access="hasRole('USER')">
			<jstl:if test="${canEdit==true}">
				<display:column>
					<a href="partido/user/edit.do?partidoId=${row.id}"><spring:message
							code="partido.edit" /></a>
				</display:column>
			</jstl:if>
		</security:authorize> --%>


	</display:table>

	<security:authorize access="hasRole('USER')">
		<jstl:if test="${mostrarAciertos==true}">
			<br>
			<br>
			<h3>Número de aciertos : ${q.numAciertos}</h3>
		</jstl:if>
	</security:authorize>

</div>


<security:authorize access="hasRole('USER')">
	<jstl:if test="${canEdit==true}">
		<br />
		<br />
		<div class="col-md-3  col-md-offset-4">
			<jstl:if test="${canEdit==true}">
				<div class="row">
					<a href="partido/user/edit.do?quinielaId=${quinielaId}">
						<button type="button" class="btn btn-default btn-lg btn-block">
							<spring:message code="partido.crearPronosticos" />
						</button>
					</a>
				</div>
				<br>
			</jstl:if>
		</div>
	</jstl:if>
</security:authorize>

<security:authorize access="hasRole('ADMINISTRATOR')">
	<br />
	<br />
	<div class="col-md-3  col-md-offset-4">
		<div class="row">
			<a href="partido/administrator/print.do?quinielaId=${quinielaId}">
				<button type="button" class="btn btn-default btn-lg btn-block">
					<spring:message code="partido.pdf" />
				</button>
			</a>
		</div>
		<br/>
		<jstl:if test="${canEdit==true}">
			<div class="row">
				<a href="partido/administrator/edit.do?quinielaId=${quinielaId}">
					<button type="button" class="btn btn-default btn-lg btn-block">
						<spring:message code="partido.crearPronosticos" />
					</button>
				</a>
			</div>
			<br>
		</jstl:if>
		<jstl:if test="${canCreate==true}">
			<div class="row">
				<a href="partido/administrator/crear.do?quinielaId=${quinielaId}">
					<button type="button" class="btn btn-default btn-lg btn-block">
						<spring:message code="partido.crear" />
					</button>
				</a>
			</div>
			<br>
		</jstl:if>
		<jstl:if test="${canCalcularAciertos==true}">
			<div class="row">
				<a href="partido/administrator/calculo.do?quinielaId=${quinielaId}">
					<button type="button" class="btn btn-default btn-lg btn-block">
						<spring:message code="partido.calcularAciertos" />
					</button>
				</a>
			</div>
		</jstl:if>
	</div>

</security:authorize>
<jstl:if test="${mostrarMensaje==true}">
	<br/>
	<br/>
	<br/>
	<br/>
	<h3>
		<spring:message code="partido.mensaje" />
		&nbsp; ${ganador}
	</h3>
</jstl:if>
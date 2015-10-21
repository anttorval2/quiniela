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


<div class="col-md-7  col-md-offset-3" style="margin-bottom: 20%;">
	<br /> <br />

	<security:authorize access="hasRole('ADMINISTRATOR')">
	
		<form:form action="${requestURI}" modelAttribute="partido">


			<form:hidden path="id" />
			<form:hidden path="version" />
			<form:hidden path="quiniela" />

			<jstl:if test="${isEdit==false}">
			<div class="col-md-7  col-md-offset-1" style="margin-bottom: 20%;">
				<acme:textbox code="partido.equipo1" path="equipo1" />
				<acme:textbox code="partido.equipo2" path="equipo2" />
				
				<acme:submit code="partido.save" name="save" />
				</div>
			</jstl:if>
			<jstl:if test="${isEdit==true}">
				<form:hidden path="equipo1" />
				<form:hidden path="equipo2" />
				<div class="col-lg-8 col-md-8 col-sm-8 mb">
				<div class="panel panel-default">
					<div class="panel-heading">
						<h3 class="centered">
							<b>${partido.equipo1} - ${partido.equipo2}</b>
						</h3>
					</div>
					<div class="panel-body centered" >
				<h4>

					<form:radiobutton path="resultado" value="1" />
					1   &nbsp;&nbsp;
					<form:radiobutton path="resultado" value="X" />
					X    &nbsp;&nbsp;
					<form:radiobutton path="resultado" value="2" />
					2    &nbsp;&nbsp;
				</h4>
				<acme:submit code="partido.save" name="save" />
					</div>
				</div>
			</div>
			</jstl:if>

			

		</form:form>
	</security:authorize>


	<security:authorize access="hasRole('USER')">
		<jstl:if test="${isEdit==true}">
			<form:form action="${requestURI}" modelAttribute="partido">


				<form:hidden path="id" />
				<form:hidden path="version" />
				<form:hidden path="quiniela" />
				<form:hidden path="equipo1" />
				<form:hidden path="equipo2" />


<%-- <h3>${partido.equipo1} - ${partido.equipo2} : <br></h3>
				<h4>
					<form:label path="resultado">

						<spring:message code="partido.resultado2" /> &nbsp;&nbsp;

					</form:label>
					<form:radiobutton path="resultado" value="1" />
					1   &nbsp;&nbsp;
					<form:radiobutton path="resultado" value="X" />
					X    &nbsp;&nbsp;
					<form:radiobutton path="resultado" value="2" />
					2    &nbsp;&nbsp;
				</h4> --%>
				
				
				
				
				
				
				
				<div class="col-lg-8 col-md-8 col-sm-8 mb">
				<div class="panel panel-default">
					<div class="panel-heading">
						<h3 class="centered">
							<b>${partido.equipo1} - ${partido.equipo2}</b>
						</h3>
					</div>
					<div class="panel-body centered" >
				<h4>

					<form:radiobutton path="resultado" value="1" />
					1   &nbsp;&nbsp;
					<form:radiobutton path="resultado" value="X" />
					X    &nbsp;&nbsp;
					<form:radiobutton path="resultado" value="2" />
					2    &nbsp;&nbsp;
				</h4>
				<acme:submit code="partido.save" name="save" />
					</div>
				</div>
			</div>
				
				
				
				
				
				
				
				
				
				
				
				
				

				

			</form:form>
		</jstl:if>
	</security:authorize>

</div>
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
		<jstl:if test="${isEdit==false}">
			<form:form action="${requestURI}" modelAttribute="partido">

				<form:hidden path="id" />
				<form:hidden path="version" />
				<form:hidden path="quiniela" />

				<div class="col-md-7  col-md-offset-1" style="margin-bottom: 20%;">
					<acme:textbox code="partido.equipo1" path="equipo1" />
					<acme:textbox code="partido.equipo2" path="equipo2" />

					<acme:submit code="partido.save" name="save" />
				</div>
			</form:form>
		</jstl:if>


		<jstl:if test="${isEdit==true}">
			<form:form action="${requestURI}" modelAttribute="prueba">
				<jstl:forEach items="${prueba.prueba}" var="partido"
					varStatus="loop">
					<form:hidden path="prueba[${loop.index}].id" />
					<form:hidden path="prueba[${loop.index}].version" />
					<form:hidden path="prueba[${loop.index}].quiniela" />
					<form:hidden path="prueba[${loop.index}].equipo1" />
					<form:hidden path="prueba[${loop.index}].equipo2" />
					<div class="col-lg-8 col-md-8 col-sm-8 mb">
						<div class="panel panel-default">
							<div class="panel-heading">
								<h3 class="centered">
									<b>${partido.equipo1} - ${partido.equipo2}</b>
								</h3>
							</div>
							<div class="panel-body centered">
								<h4>
									<form:radiobutton path="prueba[${loop.index}].resultado"
										value="1" />
									1 &nbsp;&nbsp;
									<form:radiobutton path="prueba[${loop.index}].resultado"
										value="X" />
									X &nbsp;&nbsp;
									<form:radiobutton path="prueba[${loop.index}].resultado"
										value="2" />
									2 &nbsp;&nbsp;
								</h4>
							</div>
						</div>
					</div>
				</jstl:forEach>
				<div class="col-lg-8 col-md-8 col-sm-8 mb centered">
					<acme:submit code="partido.save" name="save" />
				</div>

			</form:form>
		</jstl:if>

	</security:authorize>


	<security:authorize access="hasRole('USER')">
		<jstl:if test="${isEdit==true}">
			<%-- 			<form:form action="${requestURI}" modelAttribute="partido">


				<form:hidden path="id" />
				<form:hidden path="version" />
				<form:hidden path="quiniela" />
				<form:hidden path="equipo1" />
				<form:hidden path="equipo2" />

				<div class="col-lg-8 col-md-8 col-sm-8 mb">
					<div class="panel panel-default">
						<div class="panel-heading">
							<h3 class="centered">
								<b>${partido.equipo1} - ${partido.equipo2}</b>
							</h3>
						</div>
						<div class="panel-body centered">
							<h4>

								<form:radiobutton path="resultado" value="1" />
								1 &nbsp;&nbsp;
								<form:radiobutton path="resultado" value="X" />
								X &nbsp;&nbsp;
								<form:radiobutton path="resultado" value="2" />
								2 &nbsp;&nbsp;
							</h4>
							<acme:submit code="partido.save" name="save" />
						</div>
					</div>
				</div>

			</form:form> --%>

			<form:form action="${requestURI}" modelAttribute="prueba">
				<jstl:forEach items="${prueba.prueba}" var="partido"
					varStatus="loop">
					<form:hidden path="prueba[${loop.index}].id" />
					<form:hidden path="prueba[${loop.index}].version" />
					<form:hidden path="prueba[${loop.index}].quiniela" />
					<form:hidden path="prueba[${loop.index}].equipo1" />
					<form:hidden path="prueba[${loop.index}].equipo2" />
					<div class="col-lg-8 col-md-8 col-sm-8 mb">
						<div class="panel panel-default">
							<div class="panel-heading">
								<h3 class="centered">
									<b>${partido.equipo1} - ${partido.equipo2}</b>
								</h3>
							</div>
							<div class="panel-body centered">
								<h4>
									<form:radiobutton path="prueba[${loop.index}].resultado"
										value="1" />
									1 &nbsp;&nbsp;
									<form:radiobutton path="prueba[${loop.index}].resultado"
										value="X" />
									X &nbsp;&nbsp;
									<form:radiobutton path="prueba[${loop.index}].resultado"
										value="2" />
									2 &nbsp;&nbsp;
								</h4>
							</div>
						</div>
					</div>
				</jstl:forEach>
				<div class="col-lg-8 col-md-8 col-sm-8 mb centered">
					<acme:submit code="partido.save" name="save" />
				</div>

			</form:form>
		</jstl:if>
	</security:authorize>

</div>
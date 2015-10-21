<%--
 * header.jsp
 *
 * Copyright (C) 2014 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>


<aside>
 <div id="sidebar" class="nav-collapse ">
  <!-- sidebar menu start-->
  <ul class="sidebar-menu" id="nav-accordion">
   <security:authorize access="isAuthenticated()">

    <p class="centered">
     <!-- ADMINISTRATOR -->
     <security:authorize access="hasRole('ADMINISTRATOR')">
     <i class="fa fa-futbol-o fa-4x"></i>
      <h5 class="centered"><security:authentication      property="principal.username" /></h5>
     </security:authorize>
     <!-- USER -->
     <security:authorize access="hasRole('USER')">
     <i class="fa fa-futbol-o fa-4x"></i>
     <h5 class="centered"><security:authentication      property="principal.username" /></h5>
     </security:authorize>

    </p>
    <h5 class="centered"></h5>
   </security:authorize>

   <!-- ADMINISTRATOR -->
   <security:authorize access="hasRole('ADMINISTRATOR')">


    <li class=""><a href="quiniela/administrator/list.do"> <i class="fa fa-calendar-o"></i> <span>Jornadas</span>
    </a></li>
    <li class=""><a href="user/administrator/list.do"> <i class="fa fa-users"></i> <span>Usuarios</span>
    </a></li>
        <li class=""><a href="register/administrator/edit.do"> <i class="fa fa-key"></i> <span>Registro</span>
    </a></li>


   </security:authorize>
   
   <security:authorize access="hasRole('USER')">
   <li class=""><a href="quiniela/user/list.do"> <i class="fa fa-calendar-o"></i>Jornadas</span>
    </a></li>

   </security:authorize>

  
  </ul>
  <!-- sidebar menu end-->
 </div>
</aside>

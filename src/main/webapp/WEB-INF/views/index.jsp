<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="javax.servlet.http.HttpSession"%>
<%@ page import="com.mycompany.slateboard.pojo.Course"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="javax.servlet.http.Cookie"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Slateboard</title>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
<script type="text/javascript"
	src="https://cdnjs.cloudflare.com/ajax/libs/mdbootstrap/4.14.1/js/mdb.min.js"></script>
<link
	href="https://cdnjs.cloudflare.com/ajax/libs/mdbootstrap/4.14.1/css/mdb.min.css"
	rel="stylesheet">
<link rel="stylesheet"
	href="https://use.fontawesome.com/releases/v5.8.2/css/all.css">
<link rel="icon" type="image/png" sizes="32x32"
	href="<c:url value="/resources/favicon/favicon-32x32.png" />">
<script src="<c:url value="/resources/js/index.js" />"></script>
</head>
<body>

	<!--login modal-->
	<div class="modal fade" id="modalLoginForm" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header text-center">
					<h4 class="modal-title w-100 font-weight-bold">Sign in</h4>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div style="color:red;" id="login_errors"></div>
				<form id="login_form" action="login_auth.htm" method="post">
					<div class="modal-body mx-3">

						<div class="md-form mb-5">
							<i class="fas fa-envelope prefix grey-text"></i> <input
								type="email" name="login_email" id="defaultForm-email"
								class="form-control" required> <label for="defaultForm-email">Your email</label>
						</div>

						<div class="md-form mb-4">
							<i class="fas fa-lock prefix grey-text"></i> <input
								type="password" name="login_password" id="defaultForm-pass"
								class="form-control" required> <label for="defaultForm-pass">Your
								password</label>
						</div>
						<div class="form-group mb-4">
							<label>What's your role?</label> &ensp; <label
								class="radio-inline"><input type="radio"
								class="signup_role_radio" name="login_role" value="professor"
								required>Professor</label> <label class="radio-inline"><input
								type="radio" class="signup_role_radio" name="login_role"
								value="teaching_assistant">Teaching Assistant</label> <label
								class="radio-inline"><input type="radio"
								class="signup_role_radio" name="login_role" value="student">Student</label>
						</div>
					</div>
					<div class="modal-footer d-flex justify-content-center">
						<button type="submit" class="btn btn-default">Login</button>
					</div>
				</form>
			</div>
		</div>
	</div>

	<!--Sign up Modal-->
	<div class="modal fade" id="modalRegisterForm" tabindex="-1"
		role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header text-center">
					<h4 class="modal-title w-100 font-weight-bold">Sign up</h4>
					<button type="button" class="close" data-dismiss="modal"
						aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div style="color:red;" id="signup_errors"></div>
				<form id="signup_form" action="signup_auth.htm" method="post">
					<div class="modal-body mx-3">
						<div class="md-form mb-5">
							<i class="fas fa-user prefix grey-text"></i> <input type="text"
								name="su_name" id="orangeForm-name"
								class="form-control" required> <label
								for="orangeForm-name">Your
								name</label>
						</div>
						<div class="md-form mb-5">
							<i class="fas fa-envelope prefix grey-text"></i> <input
								type="email" name="su_email" id="orangeForm-email"
								class="form-control" required> <label
								for="orangeForm-email">Your
								email</label>
						</div>

						<div class="md-form mb-4">
							<i class="fas fa-lock prefix grey-text"></i> <input
								type="password" name="su_password" id="orangeForm-pass"
								class="form-control" required> <label
								for="orangeForm-pass">Your
								password</label>
						</div>

						<div class="md-form mb-4">
							<i class="fas fa-check-double prefix grey-text"></i> <input
								type="password" name="su_confirm_password"
								id="orangeForm-confpass" class="form-control" required>
							<label
								for="orangeForm-confpass">Confirm password</label>
						</div>
						<div class="md-form mb-5">
							<i class="fas fa-phone prefix grey-text"></i> <input type="text"
								minlength="10" maxlength="10" name="su_phone"
								id="orangeForm-phone" class="form-control" required>
							<label
								for="orangeForm-phone">Your Phone Number</label>
						</div>
						<div class="form-group mb-4">
							<label>What's your role?</label> &ensp; <label
								class="radio-inline"><input type="radio"
								class="signup_role_radio" name="su_role" value="professor"
								required>Professor</label> <label class="radio-inline"><input
								type="radio" class="signup_role_radio" name="su_role"
								value="teaching_assistant">Teaching Assistant</label> <label
								class="radio-inline"><input type="radio"
								class="signup_role_radio" name="su_role" value="student">Student</label>
						</div>
						<div class="md-form mb-5">
							<i class="fas fa-id-card prefix grey-text"></i> <input
								type="text" minlength="9" maxlength="9"
								name="su_identification_number" id="orangeForm-idnum"
								class="form-control" required> <label for="orangeForm-idnum">Your
								Identification Number</label>
						</div>
					</div>
					<div class="modal-footer d-flex justify-content-center">
						<button type="submit" name="su_submit" id="signup_submit"
							class="btn btn-deep-orange">Sign up</button>
					</div>
				</form>
			</div>
		</div>
	</div>
	<div class="jumbotron text-center" style="margin-bottom: 0;">
		<h1>Slateboard</h1>
		<p style="padding-left: 20%;">~ A Virtual Classroom.</p>

	</div>
	<nav class="navbar navbar-inverse">
		<div class="container-fluid">
			<%
				HttpSession s = request.getSession();
				String role = (String) s.getAttribute("role");
				ArrayList<Course> courses = (ArrayList<Course>) s.getAttribute("courses");
				if (role != null) {
			%>
			<div class="form-group">
				<label for="sel1" class="text-light">Course:</label> <select
					class="form-control" id="sel1">
					<%
						if (courses != null) {
								for (Course course : courses) {
					%>
					<option id="<%=course.getId()%>" value="<%=course.getId()%>"><%=course.getTitle()%></option>
					<%
						}
							}
					%>
				</select>
			</div>
			<%
				}
			%>
			<ul class="nav navbar-nav">
				<li><a href="main.htm">Home</a></li>
				<%
					if (role != null
							&& (role.equals("professor") || role.equals("teaching_assistant") || role.equals("student"))) {
				%>
				<li class="dropdown"><a class="dropdown-toggle"
					data-toggle="dropdown" href="#">Course Material<span
						class="caret"></span></a>
					<ul class="dropdown-menu">
						<li><a href="course.htm">View</a></li>
						<%
							if (role.equals("professor")) {
						%>
						<li><a href="course-coursematerial.htm">Post</a></li>
						<%
							}
						%>
					</ul></li>
				<li class="dropdown"><a class="dropdown-toggle"
					data-toggle="dropdown" href="#">Assignment<span class="caret"></span></a>
					<ul class="dropdown-menu">
						<li><a href="course_assignment.htm">View</a></li>
						<%
							if (role.equals("professor")) {
						%>
						<li><a href="course-assignment.htm">Post</a></li>
						<%
							} else if (role.equals("student")) {
						%>
						<li><a href="view-submissions.htm">View Submissions</a></li>
						<li><a href="submit-assignment.htm">Submit</a></li>
						<%
							}
						%>
					</ul></li>
				<%
					if (role.equals("professor") || role.equals("teaching_assistant")) {
				%>
				<li><a href="student-information.htm">Students</a></li>
				<%
					}
					}
				%>
			</ul>
			<!--  if session got role, hide signup and login and add logout-->
			<ul class="nav navbar-nav navbar-right">
				<%
					if (s.getAttribute("role") == null || s.getAttribute("role").equals("")) {
				%>
				<li><a href="#" data-toggle="modal"
					data-target="#modalRegisterForm"><span
						class="glyphicon glyphicon-user mb-4"></span> Sign Up</a></li>
				<li><a href="#" data-toggle="modal"
					data-target="#modalLoginForm"><span
						class="glyphicon glyphicon-log-in mb-4"></span> Login</a></li>
				<%
					} else {
				%>
				<li><a href="logout.htm"
					class="glyphicon glyphicon-log-in mb-4" id="logout"> Logout</a></li>
				<%
					}
				%>
			</ul>
		</div>
	</nav>
	<div class="container">
		<div class="row">
			<div class="col-sm-6">
				<img src="<c:url value="/resources/favicon/sb_g.png" />">
			</div>
			<div class="col-sm-6" style="font-size: 20px;">
				<br> <br>
				<p>
					<i>Slateboard is a virtual
						classroom. It has different features for different roles: </i>
				</p>
				<ul>
					<li>
						<p>
							<i><b>If you are a Professor, </b> it will enable you to
								upload course material and assignments as well as grade your
								students</i>
						</p>
					</li>
					<li>
						<p>
							<i><b>If you are a Teaching Assistant, </b> it will enable
								you to view course material and grade students</i>
						</p>
					</li>
					<li>
						<p>
							<i><b>If you are a Student, </b> it will enable you to view
								course material and assignments as well as upload your
								submission</i>
						</p>
					</li>
				</ul>
			</div>
		</div>
	</div>
</body>
</html>
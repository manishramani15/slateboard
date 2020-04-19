<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="javax.servlet.http.HttpSession"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="com.mycompany.slateboard.pojo.StudentCourse"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Slateboard - Student Evaluation</title>
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
<script src="<c:url value="/resources/js/grade.js" />"></script>
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
				<form action="login_auth.htm" method="post">
					<div class="modal-body mx-3">

						<div class="md-form mb-5">
							<i class="fas fa-envelope prefix grey-text"></i> <input
								type="email" name="login_email" id="defaultForm-email"
								class="form-control validate"> <label data-error="wrong"
								data-success="right" for="defaultForm-email">Your email</label>
						</div>

						<div class="md-form mb-4">
							<i class="fas fa-lock prefix grey-text"></i> <input
								type="password" name="login_password" id="defaultForm-pass"
								class="form-control validate"> <label data-error="wrong"
								data-success="right" for="defaultForm-pass">Your
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
				<form id="signup_form" action="signup_auth.htm" method="post">
					<div class="modal-body mx-3">
						<div class="md-form mb-5">
							<i class="fas fa-user prefix grey-text"></i> <input type="text"
								name="su_name" id="orangeForm-name"
								class="form-control validate" required> <label
								data-error="wrong" data-success="right" for="orangeForm-name">Your
								name</label>
						</div>
						<div class="md-form mb-5">
							<i class="fas fa-envelope prefix grey-text"></i> <input
								type="email" name="su_email" id="orangeForm-email"
								class="form-control validate" required> <label
								data-error="wrong" data-success="right" for="orangeForm-email">Your
								email</label>
						</div>

						<div class="md-form mb-4">
							<i class="fas fa-lock prefix grey-text"></i> <input
								type="password" name="su_password" id="orangeForm-pass"
								class="form-control validate" required> <label
								data-error="wrong" data-success="right" for="orangeForm-pass">Your
								password</label>
						</div>

						<div class="md-form mb-4">
							<i class="fas fa-check-double prefix grey-text"></i> <input
								type="password" name="su_confirm_password"
								id="orangeForm-confpass" class="form-control validate" required>
							<label data-error="wrong" data-success="right"
								for="orangeForm-confpass">Confirm password</label>
						</div>
						<div class="md-form mb-5">
							<i class="fas fa-phone prefix grey-text"></i> <input type="text"
								minlength="10" maxlength="10" name="su_phone"
								id="orangeForm-phone" class="form-control validate" required>
							<label data-error="wrong" data-success="right"
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
								class="form-control validate" required> <label
								data-error="wrong" data-success="right" for="orangeForm-idnum">Your
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
			<ul class="nav navbar-nav">
				<li><a href="main.htm">Home</a></li>
				<%
					HttpSession s = request.getSession();
					String role = (String) s.getAttribute("role");
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
					class="glyphicon glyphicon-log-in mb-4"> Logout</a></li>
				<%
					}
				%>
			</ul>
		</div>
	</nav>
	<div class="container">
		<div class="container">
			<%
				StudentCourse studentCourse = (StudentCourse) request.getAttribute("studentCourse");
				double assignmentAverage = (Double) request.getAttribute("assignmentAverage");
				if (studentCourse != null) {
			%>

			<h2><%=studentCourse.getStudent().getName()%></h2>
			<div class="card">
				<div class="card-header">
					Roll Number:
					<%=studentCourse.getStudent().getRollNumber()%>
					<br> Email:
					<%=studentCourse.getStudent().getEmail()%><br> Course Title:
					<%=studentCourse.getCourse().getTitle()%><br>
				</div>
				<div class="card-body">
					Credits Achieved:
					<%
					if (studentCourse.getCreditsAchieved() != null) {
							out.println(studentCourse.getCreditsAchieved());
						} else {
							out.println("Not Evaluated");
						}
				%>
				</div>

				<div class="card-footer">
					Assignment Average Percentage:
					<%=assignmentAverage%>%
				</div>

				<!--  -->
				<div class="modal fade" id="myModal" role="dialog">
					<div class="modal-dialog modal-sm">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal">&times;</button>
								<h4 class="modal-title">Fill Credits Achieved below</h4>
							</div>
							<div class="modal-body">
								<form action="set-credits-achieved.htm" method="post">
									<input type="number" name="creditsAchieved" min="0"
										max="<%=studentCourse.getCourse().getCredits()%>"></br> <input
										type="hidden" name="id" value="<%=studentCourse.getId()%>">
									<!--  getId not working  -->
									<input type="hidden" name="studentId"
										value="<%=studentCourse.getStudent().getId()%>"> <input
										type="submit" value="Submit">
								</form>
							</div>
							<div class="modal-footer">
								<button type="button" class="btn btn-default"
									data-dismiss="modal">Close</button>
							</div>
						</div>
					</div>
				</div>
			</div>
			</span>
			<%
					if (s.getAttribute("role") != null && s.getAttribute("role").equals("professor")) {
				%>
			<button type="button" class="btn btn-primary cole-sm-3"
				data-toggle="modal" data-target="#myModal">Evaluate</button>
				
				<% } %>
			</span>
			<!--  -->
		</div>
		<%
			} else {
				out.println("Server Error! The expected content is not available at the server.");
			}
		%>
	</div>
	</div>


</body>
</html>
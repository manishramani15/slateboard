<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="com.mycompany.slateboard.pojo.Student"%>
<%@ page import="com.mycompany.slateboard.pojo.Course"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Slateboard - Assign Student</title>
</head>
<body>
<a href="logout_admin.htm">Logout</a> <br><br>
<form method="post" action="assign-student-handler.htm">
		<label>Select Course: </label>
		<select name="courseId" multiple required>
			<%
				List<Course> courses = (List<Course>) request.getAttribute("courses");
				if (courses != null) {
					for (Course course : courses) {
			%>
			<option value="<%=course.getId()%>"><%=course.getTitle()%></option>
			<%
				}
				}
			%>
		</select><br>
	<label>Select Student: </label>
		<select name="studentId" multiple required>
			<%
				List<Student> students = (List<Student>) request.getAttribute("students");
				if (students != null) {
					for (Student student : students) {
			%>
			<option value="<%=student.getId()%>"><%=student.getName()%></option>
			<%
				}
				}
			%>
		</select><br>
	<input type="submit" value="Assign">
</form>
</body>
</html>
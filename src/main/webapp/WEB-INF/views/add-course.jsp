<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="com.mycompany.slateboard.pojo.Professor"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Slateboard - Add Course</title>
</head>
<body>
<a href="logout_admin.htm">Logout</a> <br> <br>
<form method="post" action="add-course-handler.htm">
	<label>Course Title: </label>
	<input type="text" name="courseTitle" required> <br>
	<label>Course Credits: </label>
	<input type="number" name="courseCredits" min="0" required><br>
	<label>Select Professor: </label>
		<select name="professorId">
			<%
				List<Professor> professors = (List<Professor>) request.getAttribute("professors");
				if (professors != null) {
					for (Professor professor : professors) {
			%>
			<option value="<%=professor.getId()%>"><%=professor.getName()%></option>
			<%
				}
				}
			%>
		</select><br>
	<input type="submit" value="Add Course">
</form>
</body>
</html>
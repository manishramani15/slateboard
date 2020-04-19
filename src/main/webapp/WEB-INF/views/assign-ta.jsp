<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="com.mycompany.slateboard.pojo.TeachingAssistant"%>
<%@ page import="com.mycompany.slateboard.pojo.Course"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Slateboard - Assign Teaching Assistant</title>
</head>
<body>
<a href="logout_admin.htm">Logout</a> <br><br>
<form method="post" action="assign-ta-handler.htm">
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
	<label>Select Teaching Assistant: </label>
		<select name="teachingAssistantId" multiple required>
			<%
				List<TeachingAssistant> teachingAssistants = (List<TeachingAssistant>) request.getAttribute("teachingAssistants");
				if (teachingAssistants != null) {
					for (TeachingAssistant teachingAssistant : teachingAssistants) {
			%>
			<option value="<%=teachingAssistant.getId()%>"><%=teachingAssistant.getName()%></option>
			<%
				}
				}
			%>
		</select><br>
	<input type="submit" value="Assign">
</form>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page import="com.mycompany.slateboard.pojo.StudentCourse"%>
<%@ page import="java.util.ArrayList"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h2>User Form View</h2>

<%-- <%
ArrayList<StudentCourse> studentCourses = (ArrayList<StudentCourse>) request.getAttribute("studentCourses");
for(StudentCourse studentCourse: studentCourses) {
/* 	out.println("abc");
	out.println(student);
	out.println(student.getId());
	out.println(student.getStudent()); */
	out.println(studentCourse.getStudent().getName());
}
%> --%>
<%-- <form:form method="POST" modelAttribute="courseMaterial" enctype="multipart/form-data" action="posted-coursematerial.htm">
Notes: <form:input path="notes" /><br/>
Photo: <input type="file" name="fileAttached" /><br/>
<input type="submit" value="SUBMIT FORM" />
</form:form>  --%>
</body>
</html>

<%-- <form:form method="POST" action="/spring-mvc-basics/addEmployee"
  modelAttribute="employee">
    <form:label path="name">Name</form:label>
    <form:input path="name" />
     
    <form:label path="id">Id</form:label>
    <form:input path="id" />
     
    <input type="submit" value="Submit" />
</form:form> --%>
<%-- <form class="form-horizontal" action="posted-coursematerial.htm" method="post" enctype="multipart/form-data">
			<div class="form-group">
				<label class="control-label col-sm-2" for="file">File:</label>
				<div class="col-sm-10">
					<input type="file" class="form-control" id="file" name="course_file">
				</div>
			</div>
			<div class="form-group">
				<label class="control-label col-sm-2" for="notes">Comments(Notes):</label>
				<div class="col-sm-10">
					<textarea class="form-control" rows="5" id="notes" name="notes"></textarea>
				</div>
			</div>
			<div class="form-group">
				<div class="col-sm-offset-2 col-sm-10">
					<button type="submit" class="btn btn-default">Add</button>
				</div>
			</div>
		</form> --%>
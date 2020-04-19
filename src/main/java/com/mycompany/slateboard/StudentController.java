package com.mycompany.slateboard;

import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.mycompany.slateboard.Dao.AssignmentDao;
import com.mycompany.slateboard.Dao.CourseMaterialDao;
import com.mycompany.slateboard.Dao.StudentAssignmentDao;
import com.mycompany.slateboard.Dao.StudentCourseDao;
import com.mycompany.slateboard.Dao.StudentDao;
import com.mycompany.slateboard.pojo.Assignment;
import com.mycompany.slateboard.pojo.Course;
import com.mycompany.slateboard.pojo.CourseMaterial;
import com.mycompany.slateboard.pojo.Student;
import com.mycompany.slateboard.pojo.StudentAssignment;
import com.mycompany.slateboard.pojo.StudentCourse;

@Controller
public class StudentController {
	
	public boolean isAllowed(String[] allowedRoles, HttpServletRequest request) {
		boolean res = false;
		String role = (String) request.getSession().getAttribute("role");
		
		if(role != null) {
			for(String allowedRole: allowedRoles) {
				res = res || allowedRole.equals(role);
			}
		}
		return res;
	}
	
	@RequestMapping(value = "/student-information.htm", method = RequestMethod.GET)
	public String displayStudents(HttpServletRequest request, HttpServletResponse response) throws IOException {
		if(!isAllowed(new String[] {"teaching_assistant", "professor"}, request)) {
			return "unauthorized-access";
		}
		HttpSession session = request.getSession();
		Course course = (Course) session.getAttribute("course");
		StudentCourseDao scd = new StudentCourseDao();
		List<StudentCourse> studentCourses = scd.getStudentCourse(course.getId());
		
		request.setAttribute("studentCourses", studentCourses);
		return "students";
	}
	
	
	@RequestMapping(value = "/submissions.htm", method = RequestMethod.GET)
	public String displaySubmissions(HttpServletRequest request, HttpServletResponse response) throws IOException {
		if(!isAllowed(new String[] {"teaching_assistant", "professor"}, request)) {
			return "unauthorized-access";
		}
		int studentId = Integer.parseInt(request.getParameter("id"));
		StudentAssignmentDao sad = new StudentAssignmentDao(); 
		List<StudentAssignment> studentAssignments = sad.getStudentsAssignmentSubmissions(studentId);
		Course course = (Course) request.getSession().getAttribute("course");
		List<StudentAssignment> filteredStudentAssignments = new ArrayList<StudentAssignment>();

		for(StudentAssignment studentAssignment: studentAssignments) {
			if(studentAssignment.getAssignment().getCourse().getId() == course.getId()) {
				filteredStudentAssignments.add(studentAssignment);
			}
		}
		
		request.setAttribute("studentAssignments", filteredStudentAssignments);
		return "assignment-submissions";
	}
	
	@RequestMapping(value = "/set-grade.htm", method = RequestMethod.POST)
	public String setGrade(HttpServletRequest request, HttpServletResponse response) throws IOException {
		if(!isAllowed(new String[] {"teaching_assistant", "professor"}, request)) {
			return "unauthorized-access";
		}
		int grade = Integer.parseInt(request.getParameter("grade"));
		int id = Integer.parseInt(request.getParameter("id"));
		int studentId = Integer.parseInt(request.getParameter("studentId"));
		StudentAssignmentDao sad = new StudentAssignmentDao();
		boolean res = sad.setGrade(id, grade);
		request.setAttribute("message", res);
		List<StudentAssignment> studentAssignments = sad.getStudentsAssignmentSubmissions(studentId);
		
		Course course = (Course) request.getSession().getAttribute("course");
		List<StudentAssignment> filteredStudentAssignments = new ArrayList<StudentAssignment>();
		for(StudentAssignment studentAssignment: studentAssignments) {
			if(studentAssignment.getAssignment().getCourse().getId() == course.getId()) {
				filteredStudentAssignments.add(studentAssignment);
			}
		}
		
		request.setAttribute("studentAssignments", filteredStudentAssignments);
		return "assignment-submissions";
	}
	
	@RequestMapping(value = "/submit-assignment.htm", method = RequestMethod.GET)
	public String postCourseMaterial(ModelMap model, HttpServletRequest request, HttpServletResponse response) throws IOException {
		if(!isAllowed(new String[] {"student"}, request)) {
			return "unauthorized-access";
		}
		HttpSession session = request.getSession();
		Course course = (Course) session.getAttribute("course");
		AssignmentDao assignmentDao = new AssignmentDao();
		List<Assignment> assignments = assignmentDao.getAssignmentsFromCourse(course.getId());
		request.setAttribute("assignments", assignments);
		if(request.getParameter("message") != null || request.getParameter("message") != "") {
			request.setAttribute("message", request.getParameter("message"));
		}
		model.addAttribute("studentAssignment", new StudentAssignment()); 
		return "submit-assignment"; 
	}
	
	@RequestMapping(value = "submitted-assignment.htm", method = RequestMethod.POST)
	public String handleForm(@ModelAttribute("studentAssignment") StudentAssignment studentAssignment, BindingResult result, SessionStatus status, HttpServletRequest request) {
		if(!isAllowed(new String[] {"student"}, request)) {
			return "unauthorized-access";
		}
		if (result.hasErrors()) {
			return "redirect:/submit-assignment.htm"; // a
		}

		HttpSession session = request.getSession();
		StudentDao sd = new StudentDao();
		if(session.getAttribute("id") == null) {
			return "test";
		}
		int studentId = (Integer) session.getAttribute("id");
		Student student = sd.getStudent(studentId);
		int assignmentId = Integer.parseInt(request.getParameter("assignmentId")); 
		AssignmentDao ad = new AssignmentDao();
		Assignment assignment = ad.getAssignment(assignmentId);
		Instant instant = Instant.ofEpochMilli(assignment.getCreatedAt().getTime());
		LocalDateTime uploadedOn = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
		LocalDateTime deadline = LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).plusDays(7); //deadline can be taken from one to one mapping table
		if(deadline.isBefore(LocalDateTime.now())) {
			String message = "deadline";
			return "redirect:/submit-assignment.htm?message=" + message;

		}
		studentAssignment.setAssignment(assignment);
		studentAssignment.setStudent(student);
		studentAssignment.setGrade(-1);//not graded
		CommonsMultipartFile fileAttached = studentAssignment.getFileAttached();
		String filename = "file" + Calendar.getInstance().getTimeInMillis() + "." + FilenameUtils.getExtension(fileAttached.getOriginalFilename());
		studentAssignment.setFilePath(filename);
		long fileSize = fileAttached.getSize();
		File file = new File("/Users/manish/Desktop/spring 2020/Web Tools/img", filename);
		try {
			fileAttached.transferTo(file);
		} catch (IllegalStateException e) {
			return "redirect:/submit-assignment.htm";
		} catch (IOException e) {
			return "redirect:/submit-assignment.htm";
		}
		
		StudentAssignmentDao sad = new StudentAssignmentDao();
		int res = sad.addStudentAssignment(studentAssignment);
		if(res == 0) {
			return "redirect:/submit-assignment.htm";
		} else if(res == 2) {
			String message = "submission-exist";
			return "redirect:/submit-assignment.htm?message=" + message;
		}
		request.setAttribute("item", studentAssignment);
		status.setComplete();
		
		return "post-success";
	}

	@RequestMapping(value = "/student.htm", method = RequestMethod.GET)
	public String studentInfoDeliver(HttpServletRequest request, HttpServletResponse response) throws IOException {
		if(!isAllowed(new String[] {"teaching_assistant", "professor"}, request)) {
			return "unauthorized-access";
		}
		HttpSession session = request.getSession();
		Course course = (Course) session.getAttribute("course");
		AssignmentDao assignmentDao = new AssignmentDao();
		List<Assignment> assignments = assignmentDao.getAssignmentsFromCourse(course.getId());
		int studentId = Integer.parseInt(request.getParameter("id"));
		int courseId = course.getId();
		List<StudentAssignment> studentAssignments = new StudentAssignmentDao().getStudentsAssignmentSubmissions(studentId);
		double assignmentAverage = 0.0;
		int count = assignments.size();
		for(StudentAssignment studentAssignment: studentAssignments) {
			if(studentAssignment.getAssignment().getCourse().getId() == course.getId()) {
				assignmentAverage += studentAssignment.getGrade();
			}
		}
		
		assignmentAverage /= count;
		if(assignmentAverage < 0.0) {
			assignmentAverage = 0.0;
		}
		StudentCourse studentCourse = new StudentCourseDao().getStudentCourseInstance(studentId, courseId);
		request.setAttribute("studentCourse", studentCourse);
		request.setAttribute("assignmentAverage", assignmentAverage);
		return "student-evaluation";
	}
	
	
	@RequestMapping(value = "/set-credits-achieved.htm", method = RequestMethod.POST)
	public String setCreditsAchieved(HttpServletRequest request, HttpServletResponse response) throws IOException {
		if(!isAllowed(new String[] {"professor"}, request)) {
			return "unauthorized-access";
		}
		int creditsAchieved = Integer.parseInt(request.getParameter("creditsAchieved"));
		int id = Integer.parseInt(request.getParameter("id"));
		int studentId = Integer.parseInt(request.getParameter("studentId"));
		StudentCourseDao scd = new StudentCourseDao();
		boolean res = scd.setCreditsAchieved(id, creditsAchieved);
		request.setAttribute("message", res);
		return "redirect:/student.htm?id=" + studentId;
	}
	
	@RequestMapping(value = "/view-submissions.htm", method = RequestMethod.GET)
	public String viewSubmissions(HttpServletRequest request, HttpServletResponse response) throws IOException {
		if(!isAllowed(new String[] {"student"}, request)) {
			return "unauthorized-access";
		}
		HttpSession session = request.getSession();
		Course course = (Course) session.getAttribute("course");
		int studentId = (Integer) session.getAttribute("id");
		List<StudentAssignment> studentAssignments = new StudentAssignmentDao().getStudentsAssignmentSubmissions(studentId);
		List<StudentAssignment> filteredStudentAssignments = new ArrayList<StudentAssignment>();
		for(StudentAssignment studentAssignment: studentAssignments) {
			if(studentAssignment.getAssignment().getCourse().getId() == course.getId()) {
				filteredStudentAssignments.add(studentAssignment);
			}
		}
		
		request.setAttribute("studentAssignments", filteredStudentAssignments);
		return "view-submissions";
	}
}

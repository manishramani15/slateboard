package com.mycompany.slateboard;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mycompany.slateboard.Dao.AdminDao;
import com.mycompany.slateboard.Dao.CourseDao;
import com.mycompany.slateboard.Dao.ProfessorDao;
import com.mycompany.slateboard.pojo.Assignment;
import com.mycompany.slateboard.pojo.Course;
import com.mycompany.slateboard.pojo.Professor;
import com.mycompany.slateboard.pojo.Student;
import com.mycompany.slateboard.pojo.StudentCourse;
import com.mycompany.slateboard.pojo.TeachingAssistant;
import com.mycompany.slateboard.pojo.TeachingAssistantCourse;

@Controller
public class AdminController {
	
	public boolean isAllowed(HttpServletRequest request) {
		Boolean role = (Boolean) request.getSession().getAttribute("roleAdmin");
		if(role != null) {
			return role;
		}
		return false;
	}
	
	@RequestMapping(value = "/admin.htm", method = RequestMethod.GET)
	public String adminHandler(HttpServletRequest request, HttpServletResponse response) throws IOException {
		return "admin-login-panel";
	}
	
	@RequestMapping(value = "/admin-login.htm", method = RequestMethod.POST)
	public String adminLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String loginId = request.getParameter("admin_id");
		String password = request.getParameter("admin_password");
		AdminDao adminDao = new AdminDao();
		if(adminDao.checkExistance(loginId, password)) {
			request.getSession().setAttribute("roleAdmin", true);
			return "admin-operations";
		}
		
		return "admin-login-panel";
	}
	
	@RequestMapping(value = "/add-course.htm", method = RequestMethod.GET)
	public String addCourse(HttpServletRequest request, HttpServletResponse response) throws IOException {
		if(!isAllowed(request)) {
			request.setAttribute("admin", true);
			return "unauthorized-access";
		}
		AdminDao adminDao = new AdminDao();
		List<Professor> professors = adminDao.getAllProfessors();
		request.setAttribute("professors", professors);
		return "add-course";
	}
	
	@RequestMapping(value = "/assign-ta.htm", method = RequestMethod.GET)
	public String assignTa(HttpServletRequest request, HttpServletResponse response) throws IOException {
		if(!isAllowed(request)) {
			request.setAttribute("admin", true);
			return "unauthorized-access";
		}
		AdminDao adminDao = new AdminDao();
		List<Course> courses = adminDao.getAllCourses();
		List<TeachingAssistant> teachingAssistants = adminDao.getAllTeachingAssistants();
		request.setAttribute("courses", courses);
		request.setAttribute("teachingAssistants", teachingAssistants);
		return "assign-ta";
	}
	
	@RequestMapping(value = "/assign-student.htm", method = RequestMethod.GET)
	public String assignStudent(HttpServletRequest request, HttpServletResponse response) throws IOException {
		if(!isAllowed(request)) {
			request.setAttribute("admin", true);
			return "unauthorized-access";
		}
		AdminDao adminDao = new AdminDao();
		List<Course> courses = adminDao.getAllCourses();
		List<Student> students = adminDao.getAllStudents();
		request.setAttribute("courses", courses);
		request.setAttribute("students", students);
		return "assign-student";
	}
	
	@RequestMapping(value = "/add-course-handler.htm", method = RequestMethod.POST)
	public String addCourseHandler(HttpServletRequest request, HttpServletResponse response) throws IOException {
		if(!isAllowed(request)) {
			request.setAttribute("admin", true);
			return "unauthorized-access";
		}
		int courseCredits = Integer.parseInt(request.getParameter("courseCredits"));
		String courseTitle = request.getParameter("courseTitle");
		int professorId = Integer.parseInt(request.getParameter("professorId"));
		ProfessorDao professorDao = new ProfessorDao();
		Professor professor = professorDao.getProfessor(professorId);
		Course course = new Course();
		course.setCredits(courseCredits);
		course.setTitle(courseTitle);
		course.setProfessor(professor);
		boolean res = professorDao.addCourse(professor, course);
		if(!res) {
			request.setAttribute("message", "Course Cannot be added.");
		} else {			
			request.setAttribute("message", "Course Added Successfully!");
		}
		return "admin-operation-success";
	}

	@RequestMapping(value = "/assign-ta-handler.htm", method = RequestMethod.POST)
	public String assignTaHandler(HttpServletRequest request, HttpServletResponse response) throws IOException {
		if(!isAllowed(request)) {
			request.setAttribute("admin", true);
			return "unauthorized-access";
		}
		AdminDao adminDao = new AdminDao();
		boolean res = true;
		String[] teachingAssistantIds = request.getParameterValues("teachingAssistantId");
		String[] courseIds = request.getParameterValues("courseId");
		for(String courseId: courseIds) {
			for(String teachingAssistantId: teachingAssistantIds) {
				TeachingAssistantCourse teachingAssistantCourse = new TeachingAssistantCourse();
				TeachingAssistant teachingAssistant = adminDao.getTeachingAssistant(Integer.parseInt(teachingAssistantId));
				Course course = new CourseDao().getCourse(Integer.parseInt(courseId));
				teachingAssistantCourse.setCourse(course);
				teachingAssistantCourse.setTeachingAssistant(teachingAssistant);
				res = res && adminDao.addTeachingAssistantCourse(teachingAssistantCourse);
			}
		}
		if(!res) {
			request.setAttribute("message", "Cannot be assigned.");
		} else {			
			request.setAttribute("message", "Assigned Successfully!");
		}
		return "admin-operation-success";
	}
	
	@RequestMapping(value = "/assign-student-handler.htm", method = RequestMethod.POST)
	public String assignStudentHandler(HttpServletRequest request, HttpServletResponse response) throws IOException {
		if(!isAllowed(request)) {
			request.setAttribute("admin", true);
			return "unauthorized-access";
		}
		AdminDao adminDao = new AdminDao();
		boolean res = true;
		String[] studentIds = request.getParameterValues("studentId");
		String[] courseIds = request.getParameterValues("courseId");
		for(String courseId: courseIds) {
			for(String studentId: studentIds) {
				StudentCourse studentCourse = new StudentCourse();
				Student student = adminDao.getStudent(Integer.parseInt(studentId));
				Course course = new CourseDao().getCourse(Integer.parseInt(courseId));
				studentCourse.setCourse(course);
				studentCourse.setStudent(student);
				res = res && adminDao.addStudentCourse(studentCourse);
				}
		}
		if(!res) {
			request.setAttribute("message", "Cannot be assigned.");
		} else {			
			request.setAttribute("message", "Assigned Successfully!");
		}
		return "admin-operation-success";
	}
	
	@RequestMapping(value = "/logout_admin.htm", method = RequestMethod.GET)
	public String adminLogoutHandler(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession httpSession = request.getSession();
		httpSession.removeAttribute("roleAdmin");
		return "admin-login-panel";
	}
	
}

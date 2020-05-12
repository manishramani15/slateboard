package com.mycompany.slateboard;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mycompany.slateboard.Dao.ProfessorDao;
import com.mycompany.slateboard.Dao.StudentDao;
import com.mycompany.slateboard.Dao.TeachingAssistantDao;
import com.mycompany.slateboard.pojo.Course;
import com.mycompany.slateboard.pojo.Professor;
import com.mycompany.slateboard.pojo.Student;
import com.mycompany.slateboard.pojo.TeachingAssistant;

@Controller
public class HomeController {

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		return "index";
	}

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
	@RequestMapping(value = "/signup_auth.htm", method = RequestMethod.POST)
	public String signupRequestHandler(HttpServletRequest request, HttpServletResponse response) {
		String su_role = request.getParameter("su_role");
		if (su_role != null) {
			if (su_role.equals("professor")) {
				ProfessorDao professorDao = new ProfessorDao();
				String name = request.getParameter("su_name");
				String email = request.getParameter("su_email");
				String password = request.getParameter("su_password");
				String identificationNumber = request.getParameter("su_identification_number");
				String phone = request.getParameter("su_phone");
				boolean exist = professorDao.checkProfessorExistance(identificationNumber);
				if (!exist) {
					int result = professorDao.addProfessor(name, email, password, phone, identificationNumber);
					if (result == 1) {
						request.setAttribute("message", "Signup success! Please login to continue");
					} else {
						request.setAttribute("message", "Signup Failed. Please try to sign up again.");
					}
				} else {
					request.setAttribute("message", "Your credentials already exist, Please login to continue.");
				}
			} else if(su_role.equals("teaching_assistant")) {
				TeachingAssistantDao teachingAssistantDao = new TeachingAssistantDao();
				String name = request.getParameter("su_name");
				String email = request.getParameter("su_email");
				String password = request.getParameter("su_password");
				String identificationNumber = request.getParameter("su_identification_number");
				String phone = request.getParameter("su_phone");
				boolean exist = teachingAssistantDao.checkTeachingAssistantExistance(identificationNumber);
				if (!exist) {
					int result = teachingAssistantDao.addTeachingAssistant(name, email, password, phone, identificationNumber);
					if (result == 1) {
						request.setAttribute("message", "Signup success! Please login to continue");
					} else {
						request.setAttribute("message", "Signup Failed. Please try to sign up again.");
					}
				} else {
					request.setAttribute("message", "Your credentials already exist, Please login to continue.");
				}
			} else if(su_role.equals("student")) {
				StudentDao studentDao = new StudentDao();
				String name = request.getParameter("su_name");
				String email = request.getParameter("su_email");
				String password = request.getParameter("su_password");
				String identificationNumber = request.getParameter("su_identification_number");
				String phone = request.getParameter("su_phone");
				boolean exist = studentDao.checkStudentExistance(identificationNumber);
				if (!exist) {
					int result = studentDao.addStudent(name, email, password, phone, identificationNumber);
					if (result == 1) {
						request.setAttribute("message", "Signup success! Please login to continue");
					} else {
						request.setAttribute("message", "Signup Failed. Please try to sign up again.");
					}
				} else {
					request.setAttribute("message", "Your credentials already exist, Please login to continue.");
				}
			}
		} 
		return "index";

	}

	@RequestMapping(value = "/login_auth.htm", method = RequestMethod.POST)
	public String loginRequestHandler(HttpServletRequest request, HttpServletResponse response) {
		String login_role = request.getParameter("login_role");
		if (login_role != null) {
			if (login_role.equals("professor")) {
				ProfessorDao professorDao = new ProfessorDao();
				String email = request.getParameter("login_email");
				String password = request.getParameter("login_password");
				Professor professor = professorDao.getProfessor(email, password);
				List<Course> courses = professorDao.getCourses(professor.getId());
				if (professor != null) {
					request.setAttribute("message", "login success, id = " + professor.getId());
					HttpSession httpSession = request.getSession();
					httpSession.setAttribute("id", professor.getId());
					httpSession.setAttribute("role", "professor");
					httpSession.setAttribute("courses", courses);
					if(courses.size() != 0) {
						httpSession.setAttribute("course", courses.get(0));
					}
				} else {
					request.setAttribute("message", "login fail");
				}
			} else if(login_role.equals("student")) {
				//to be implemented
				StudentDao studentDao = new StudentDao();
				String email = request.getParameter("login_email");
				String password = request.getParameter("login_password");
				Student student = studentDao.getStudent(email, password); //
				List<Course> courses = studentDao.getCourses(student.getId());
				if (student != null) {
					request.setAttribute("message", "login success, id = " + student.getId());
					HttpSession httpSession = request.getSession();
					httpSession.setAttribute("id", student.getId());
					httpSession.setAttribute("role", "student");
					httpSession.setAttribute("courses", courses);
					if(courses.size() != 0) {
						httpSession.setAttribute("course", courses.get(0));
					}
				} else {
					request.setAttribute("message", "login fail");
				}
				
			} else if(login_role.equals("teaching_assistant")) {
				TeachingAssistantDao teachingAssistantDao = new TeachingAssistantDao();
				String email = request.getParameter("login_email");
				String password = request.getParameter("login_password");
				TeachingAssistant teachingAssistant = teachingAssistantDao.getTeachingAssistant(email, password); //
				List<Course> courses = teachingAssistantDao.getCourses(teachingAssistant.getId());
				if (teachingAssistant != null) {
					request.setAttribute("message", "login success, id = " + teachingAssistant.getId());
					HttpSession httpSession = request.getSession();
					httpSession.setAttribute("id", teachingAssistant.getId());
					httpSession.setAttribute("role", "teaching_assistant");
					httpSession.setAttribute("courses", courses);
					if(courses.size() != 0) {
						httpSession.setAttribute("course", courses.get(0));
					}
				} else {
					request.setAttribute("message", "login fail");
				}
			}
		}
		return "index";
	}
	

	@RequestMapping(value = "/logout.htm", method = RequestMethod.GET)
	public String logoutHandler(HttpServletRequest request, HttpServletResponse response) {
		if(!isAllowed(new String[] {"student", "teaching_assistant", "professor"}, request)) {
			return "unauthorized-access";
		}
		HttpSession httpSession = request.getSession();
		httpSession.removeAttribute("id");
		httpSession.removeAttribute("role");
		httpSession.removeAttribute("courses");
		Cookie cookie = new Cookie("courseId", "");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
		return "index";
	}

}

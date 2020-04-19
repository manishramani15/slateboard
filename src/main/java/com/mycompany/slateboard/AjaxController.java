package com.mycompany.slateboard;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mycompany.slateboard.Dao.AdminDao;
import com.mycompany.slateboard.Dao.CourseDao;
import com.mycompany.slateboard.pojo.Course;

@RestController
public class AjaxController {

	@RequestMapping(value = "/load-course.htm", method = RequestMethod.GET)
	 public String loadCourseInSession(HttpServletRequest request, HttpServletResponse response) {
		int courseId = Integer.parseInt(request.getParameter("courseId"));
		HttpSession session = request.getSession();
		CourseDao cd = new CourseDao();
		Course course = cd.getCourse(courseId);
		session.setAttribute("course", course);
		return "success";
	}
	
	@RequestMapping(value = "/email-existance.htm", method = RequestMethod.GET)
	 public String emailExistance(HttpServletRequest request, HttpServletResponse response) {
		AdminDao adminDao = new AdminDao();
		String role = request.getParameter("role");
		String email = request.getParameter("email");
		String identificationNumber = request.getParameter("identificationNumber");
		boolean exist = adminDao.emailExist(role, email, identificationNumber);
		if(exist) {
			return "exist";
		}
		return "success";
	}
	
	@RequestMapping(value = "/email-password-check.htm", method = RequestMethod.GET)
	 public String emailPasswordChecker(HttpServletRequest request, HttpServletResponse response) {
		AdminDao adminDao = new AdminDao();
		String role = request.getParameter("role");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		boolean exist = adminDao.emailExistForLogin(role, email);
		if(!exist) {
			return "emailNotExist";
		} else {
			boolean comboExist = adminDao.emailPasswordExist(role, email, password);
			if(!comboExist) {
				return "combinationNotExist";
			}
		}
		return "success";
	}
}

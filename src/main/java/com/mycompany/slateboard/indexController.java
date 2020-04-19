package com.mycompany.slateboard;


import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mycompany.slateboard.Dao.AssignmentDao;
import com.mycompany.slateboard.Dao.CourseDao;
import com.mycompany.slateboard.Dao.ProfessorDao;
import com.mycompany.slateboard.Dao.StudentAssignmentDao;
import com.mycompany.slateboard.Dao.StudentCourseDao;
import com.mycompany.slateboard.Dao.StudentDao;
import com.mycompany.slateboard.pojo.Course;
import com.mycompany.slateboard.pojo.Professor;


@Controller
public class indexController {

	@RequestMapping(value = {"/index.htm", "/main.htm"}, method = RequestMethod.GET)
	public String handleRequest(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
////		if(session.getAttribute("id") != null) {			
////			session.setAttribute("id", null);
////		} else {
////			session.setAttribute("id", 1);
////		}
//		String su_role = request.getParameter("su_role");
//        String login_role = request.getParameter("login_role");
////        String confirmPassword = request.getParameter("su_confirm_password");
//        if (su_role != null) {
//            if (su_role.equals("professor")) {
//                ProfessorDao professorDao = new ProfessorDao();
//                String name = request.getParameter("su_name");
//                String email = request.getParameter("su_email");
//                String password = request.getParameter("su_password");
//                String identificationNumber = request.getParameter("su_identification_number");
//                String phone = request.getParameter("su_phone");
//                boolean exist = professorDao.checkProfessorExistance(identificationNumber);
//                if (!exist) {
//                    int result = professorDao.addProfessor(name, email, password, phone, identificationNumber);
//                    if (result == 1) {
//                        request.setAttribute("message", "Signup success! Please login to continue");
//                    } else {
//                        request.setAttribute("message", "Signup Failed. Please try to sign up again.");   
//                    }
//                } else {
//                    request.setAttribute("message", "Your credentials already exist, Please login to continue.");
//                }
//            }
//        } else if (login_role != null) {
//            if (login_role.equals("professor")) {
//                ProfessorDao professorDao = new ProfessorDao();
//                String email = request.getParameter("login_email");
//                String password = request.getParameter("login_password");
//                Professor professor = professorDao.getProfessor(email, password);
//                List<Course> courses = professorDao.getCourses(professor.getId());
//                if (professor != null) {
//                    request.setAttribute("message", "login success, id = " + professor.getId());
//                    HttpSession httpSession= request.getSession();
//                    httpSession.setAttribute("user", professor);
//                    httpSession.setAttribute("role", "professor");
//                    httpSession.setAttribute("courses", courses);
//                } else {
//                    request.setAttribute("message", "login fail");
//                }
//            }
//        }// else {
////            mv = new ModelAndView("index");
////        }
		return "index";
	}
}

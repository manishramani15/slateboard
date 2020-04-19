package com.mycompany.slateboard;

import java.io.File;
import java.io.IOException;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.mycompany.slateboard.Dao.CourseDao;
import com.mycompany.slateboard.Dao.CourseMaterialDao;
import com.mycompany.slateboard.Dao.ProfessorDao;
import com.mycompany.slateboard.pojo.Course;
import com.mycompany.slateboard.pojo.CourseMaterial;

@Controller
public class CourseMaterialController {

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
	
	@RequestMapping(value = "/view-coursematerial.htm", method = RequestMethod.GET)
	public String viewCourses(HttpServletRequest request, HttpServletResponse response) {
		// send in request scope here by getting prof by session
		HttpSession session = request.getSession();
		ProfessorDao professorDao = new ProfessorDao();
		List<Course> courses = professorDao.getCourses((Integer) session.getAttribute("id"));
		request.setAttribute("courses", courses);
		return "view-coursematerial";
	}

	@RequestMapping(value = "/course.htm", method = RequestMethod.GET)
	public String viewCourseDetails(HttpServletRequest request, HttpServletResponse response) {

		if(!isAllowed(new String[] {"student", "teaching_assistant", "professor"}, request)) {
			return "unauthorized-access";
		}
		HttpSession session = request.getSession();
		Course course = (Course) session.getAttribute("course");
		CourseMaterialDao courseMaterialDao = new CourseMaterialDao();
		List<CourseMaterial> courseMaterials = courseMaterialDao
				.getCourseMaterialFromCourse(course.getId());
		request.setAttribute("courseMaterials", courseMaterials);
		return "course-details";
	}

	@RequestMapping(value = "/post-coursematerial.htm", method = RequestMethod.GET)
	public String viewCoursesCourseMaterial(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		if(!isAllowed(new String[] {"professor"}, request)) {
			return "unauthorized-access";
		}
		HttpSession session = request.getSession();
		ProfessorDao professorDao = new ProfessorDao();
		List<Course> courses = professorDao.getCourses((Integer) session.getAttribute("id"));
		request.setAttribute("courses", courses);
		return "course-coursematerial";
	}
	
	@RequestMapping(value = "/course-coursematerial.htm", method = RequestMethod.GET)
	public String postCourseMaterial(ModelMap model, HttpServletRequest request, HttpServletResponse response) throws IOException {
		if(!isAllowed(new String[] {"professor"}, request)) {
			return "unauthorized-access";
		}
		model.addAttribute("courseMaterial", new CourseMaterial()); 
		return "post-coursematerial";
	}

	
	@RequestMapping(value = "/posted-coursematerial.htm", method = RequestMethod.POST)
	public String handleForm(@ModelAttribute("courseMaterial") CourseMaterial courseMaterial, BindingResult result, SessionStatus status, HttpServletRequest request) {
		if(!isAllowed(new String[] {"professor"}, request)) {
			return "unauthorized-access";
		}
		if (result.hasErrors()) {
			return "post-coursematerial";
		}
		
		HttpSession session = request.getSession();
		Course course = (Course) session.getAttribute("course");
		CommonsMultipartFile fileAttached = courseMaterial.getFileAttached();
		String filename = "file" + Calendar.getInstance().getTimeInMillis() + "." + FilenameUtils.getExtension(fileAttached.getOriginalFilename());
		courseMaterial.setFilePath(filename);
		File file = new File("/Users/manish/Desktop/spring 2020/Web Tools/img", filename);
		try {
			fileAttached.transferTo(file);
		} catch (IllegalStateException e) {
			return "post-coursematerial";
		} catch (IOException e) {
			return "post-coursematerial";
		}
		
		courseMaterial.setNotes(request.getParameter("notes"));
		courseMaterial.setCourse(course);
		CourseMaterialDao cmd = new CourseMaterialDao();
		int res = cmd.addCourseMaterial(courseMaterial);

		if(res != 1) {
			return "post-coursematerial";
		}
		request.setAttribute("item", courseMaterial);
		status.setComplete();
		
		return "post-success";
	}

}

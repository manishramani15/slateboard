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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.mycompany.slateboard.Dao.AssignmentDao;
import com.mycompany.slateboard.Dao.CourseDao;
import com.mycompany.slateboard.Dao.CourseMaterialDao;
import com.mycompany.slateboard.Dao.ProfessorDao;
import com.mycompany.slateboard.pojo.Assignment;
import com.mycompany.slateboard.pojo.Course;
import com.mycompany.slateboard.pojo.CourseMaterial;

@Controller
public class AssignmentController {
	
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
	
	@RequestMapping(value = "/view-assignment.htm", method = RequestMethod.GET)
	public String viewCourses(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		ProfessorDao professorDao = new ProfessorDao();
		List<Course> courses = professorDao.getCourses((Integer) session.getAttribute("id"));
		request.setAttribute("courses", courses);
		return "view-assignment";
	}
	
	@RequestMapping(value = "/course_assignment.htm", method = RequestMethod.GET)
	public String viewCourseDetails(HttpServletRequest request, HttpServletResponse response) {
		if(!isAllowed(new String[] {"student", "teaching_assistant", "professor"}, request)) {
			return "unauthorized-access";
		}
		HttpSession session = request.getSession();
		Course course = (Course) session.getAttribute("course");
		AssignmentDao assignmentDao = new AssignmentDao();
		List<Assignment> assignments = assignmentDao
				.getAssignmentsFromCourse(course.getId());
		request.setAttribute("assignments", assignments);
		return "assignment-details";
	}
	
	@RequestMapping(value = "/post-assignment.htm", method = RequestMethod.GET)
	public String viewCoursesAssignments(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		ProfessorDao professorDao = new ProfessorDao();
		List<Course> courses = professorDao.getCourses((Integer) session.getAttribute("id"));
		request.setAttribute("courses", courses);
		return "course-assignment";
	}
	
	@RequestMapping(value = "/course-assignment.htm", method = RequestMethod.GET)
	public String postAssignment(ModelMap model, HttpServletRequest request, HttpServletResponse response) throws IOException {
		if(!isAllowed(new String[] {"professor"}, request)) {
			return "unauthorized-access";
		}
		HttpSession session = request.getSession();
		Course course = (Course) session.getAttribute("course");
		request.setAttribute("courseId", course.getId());
		model.addAttribute("assignment", new Assignment()); 
		return "post-assignment";
	}
	
	@RequestMapping(value = "/posted-assignment.htm", method = RequestMethod.POST)
	public String handleForm(@ModelAttribute("assignment") Assignment assignment, BindingResult result, SessionStatus status, HttpServletRequest request) {
		if (result.hasErrors()) {
			return "post-assignment";
		}
		Course course = new CourseDao().getCourse(Integer.parseInt(request.getParameter("courseId")));

		CommonsMultipartFile fileAttached = assignment.getFileAttached();
		String filename = "file" + Calendar.getInstance().getTimeInMillis() + "." + FilenameUtils.getExtension(fileAttached.getOriginalFilename());
		assignment.setFilePath(filename);
		File file = new File("/Users/manish/Desktop/spring 2020/Web Tools/img", filename);
		try {
			fileAttached.transferTo(file);
		} catch (IllegalStateException e) {
			return "post-assignment";
		} catch (IOException e) {
			return "post-assignment";
		}
		
		assignment.setNotes(request.getParameter("notes"));
		assignment.setCourse(course);
		Integer daysToDeadline = Integer.parseInt(request.getParameter("daysToDeadline"));
		assignment.setDaysToDeadline(daysToDeadline);
		AssignmentDao assignmentDao = new AssignmentDao();
		boolean res = assignmentDao.addAssignment(filename, request.getParameter("notes"), course, daysToDeadline);

		if(!res) {
			return "post-assignment";
		}
		request.setAttribute("item", assignment);
		status.setComplete();
		return "post-success";
	}
	
	@RequestMapping(value = "/delete-assignment.htm", method = RequestMethod.GET)
	public String deleteAssignment(ModelMap model, HttpServletRequest request, HttpServletResponse response) throws IOException {
		if(!isAllowed(new String[] {"professor"}, request)) {
			return "unauthorized-access";
		}
		int assignmentId = Integer.parseInt(request.getParameter("assignmentId"));
		AssignmentDao assignmentDao = new AssignmentDao();
		boolean res = assignmentDao.deleteAssignment(assignmentId);
		if(res) {	
			return "redirect:/course_assignment.htm";
		}
		return "error";
	}
}

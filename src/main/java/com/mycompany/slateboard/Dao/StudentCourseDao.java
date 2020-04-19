package com.mycompany.slateboard.Dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.query.Query;

import com.mycompany.slateboard.pojo.Course;
import com.mycompany.slateboard.pojo.Student;
import com.mycompany.slateboard.pojo.StudentCourse;

public class StudentCourseDao extends DAO {

	public boolean addStudentCourse(Student student, Course course) {
		boolean result = false;
		try {
			StudentCourse sc = new StudentCourse();
			sc.setCourse(course);
			sc.setStudent(student);
			begin();
			getSession().save(sc);
			commit();
			result = true;
		} catch (HibernateException e) {
			e.printStackTrace();
			rollback();
		} finally {
			close();
		}
		return result;
	}
	
	public List<StudentCourse> getStudentCourse(int courseId) {
		List<StudentCourse> studentsCourses = new ArrayList<StudentCourse>();
		try {
			begin();
			Query q = getSession().createQuery("FROM StudentCourse where course.id = :id");
			q.setInteger("id", courseId);
			studentsCourses = q.list();
			commit();
		} catch (HibernateException e) {
			e.printStackTrace();
			rollback();
		} finally {
			close();
		}
		return studentsCourses;
	}
	
	public List<StudentCourse> getStudentCourseByStudentId(int studentId) {
		List<StudentCourse> studentsCourses = new ArrayList<StudentCourse>();
		try {
			begin();
			Query q = getSession().createQuery("FROM StudentCourse where student.id = :id");
			q.setInteger("id", studentId);
			studentsCourses = q.list();
			commit();
		} catch (HibernateException e) {
			e.printStackTrace();
			rollback();
		} finally {
			close();
		}
		return studentsCourses;
	}
	
	public StudentCourse getStudentCourseInstance(int studentId, int courseId) {
		StudentCourse studentCourse = null;
		try {
			begin();
			Query q = getSession().createQuery("FROM StudentCourse where course.id = :cid and student.id = :sid");
			q.setInteger("sid", studentId);
			q.setInteger("cid", courseId);
			studentCourse = (StudentCourse) q.uniqueResult();
			commit();
		} catch (HibernateException e) {
			e.printStackTrace();
			rollback();
		} finally {
			close();
		}
		return studentCourse;
	}

	public boolean setCreditsAchieved(int id, int creditsAchieved) {
		boolean res = false;
		try {
			begin();
			Query q = getSession().createQuery("UPDATE StudentCourse set creditsAchieved = :ca where id = :id");
			q.setInteger("id", id);
			q.setInteger("ca", creditsAchieved);
			int result = q.executeUpdate();
			commit();
			res = true;
		} catch (HibernateException e) {
			e.printStackTrace();
			rollback();
		} finally {
			close();
		}
		return res;
	}
	
	
	
	
}

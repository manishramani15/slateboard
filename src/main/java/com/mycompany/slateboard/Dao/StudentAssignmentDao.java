package com.mycompany.slateboard.Dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.query.Query;

import com.mycompany.slateboard.pojo.Assignment;
import com.mycompany.slateboard.pojo.Course;
import com.mycompany.slateboard.pojo.CourseMaterial;
import com.mycompany.slateboard.pojo.Student;
import com.mycompany.slateboard.pojo.StudentAssignment;
import com.mycompany.slateboard.pojo.StudentCourse;

public class StudentAssignmentDao extends DAO {
	public boolean addStudentAssignment(Student student, Assignment assignment) {
		boolean result = false;
		try {
			StudentAssignment sa = new StudentAssignment();
			sa.setAssignment(assignment);
			sa.setStudent(student);
//			sa.setGrade(0);
			begin();
			getSession().save(sa);
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
	
	public List<StudentAssignment> getStudentsAssignmentSubmissions(int studentId) {
		List<StudentAssignment> studentsAssignments = new ArrayList<StudentAssignment>();
		try {
			begin();
			Query q = getSession().createQuery("FROM StudentAssignment where student.id = :id");
			q.setInteger("id", studentId);
			studentsAssignments = q.list();
			commit();
		} catch (HibernateException e) {
			e.printStackTrace();
			rollback();
		} finally {
			close();
		}
		return studentsAssignments;
	}
	
	public boolean setGrade(int id, int grade) {
		boolean res = false;
		try {
			begin();
			Query q = getSession().createQuery("UPDATE StudentAssignment set grade = :grade where id = :id");
			q.setInteger("id", id);
			q.setInteger("grade", grade);
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
	
	public int checkStudentAssignmentExistance(StudentAssignment studentAssignment) {
		int res = 0;
		StudentAssignment sa = null;
		try {
			begin();
			Query q = getSession().createQuery("FROM StudentAssignment where student.id = :sid and assignment = :aid");
			q.setInteger("sid", studentAssignment.getStudent().getId());
			q.setInteger("aid", studentAssignment.getAssignment().getId());
			sa = (StudentAssignment) q.uniqueResult();
			commit();
			if(sa != null) {
				res = 2;
			}
		} catch (HibernateException e) {
			e.printStackTrace();
			rollback();
		} finally {
			close();
		}
		return res;
	}
	
	public int addStudentAssignment(StudentAssignment studentAssignment) {
        int result = 0;
        result = checkStudentAssignmentExistance(studentAssignment);
        if(result == 2) {
        	return result;
        }
        try {
            begin();
            getSession().save(studentAssignment);
            commit();
            result = 1;
        } catch (HibernateException e) {
            e.printStackTrace();
            rollback();
        } finally {
            close();
        }
        return result;
    }

	public StudentAssignment getStudentsAssignmentSubmissions(int studentId, int assignmentId) {
		StudentAssignment studentAssignment = null;
		try {
			begin();
			Query q = getSession().createQuery("FROM StudentAssignment where student.id = :sid and assignment.id = :aid");
			q.setInteger("sid", studentId);
			q.setInteger("aid", assignmentId);
			studentAssignment = (StudentAssignment) q.uniqueResult();
			commit();
		} catch (HibernateException e) {
			e.printStackTrace();
			rollback();
		} finally {
			close();
		}
		return studentAssignment;
	}

	
}

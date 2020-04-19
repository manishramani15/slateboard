package com.mycompany.slateboard.Dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.query.Query;

import com.mycompany.slateboard.pojo.Assignment;
import com.mycompany.slateboard.pojo.Course;
import com.mycompany.slateboard.pojo.CourseMaterial;
import com.mycompany.slateboard.pojo.Professor;

public class AssignmentDao extends DAO {

	public boolean addAssignment(String filePath, String notes, Course course) {
		boolean result = false;
        try {
            Assignment assignment = new Assignment();
            assignment.setFilePath(filePath);
            assignment.setNotes(notes);
            assignment.setCourse(course);
        	begin();
            getSession().save(assignment);
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
	
	public Assignment getAssignment(int id) {
		Assignment assignment = null;
    	
    	try {
    		begin();
    		Query q = getSession().createQuery("from Assignment where id = :id");
    		q.setInteger("id", id);
            assignment = (Assignment) q.uniqueResult();
            commit();
        } catch(HibernateException e) {
            e.printStackTrace();
            rollback();
        } finally {
            close();
        }
    	
    	return assignment;
	}
	
	public List<Assignment> getAssignmentsFromCourse(int courseId) {
		List<Assignment> assignments = null;
		try {
            begin();
            Query q = getSession().createQuery("from Assignment where course.id = :id");
            q.setInteger("id", courseId);
            assignments = q.list();
            commit();
        } catch(HibernateException e) {
            e.printStackTrace();
            rollback();
        } finally {
            close();
        }
		return assignments;
	}
	
    public int addAssignmentSubmission(Assignment assignment) {
        int result = 0;
        try {
            begin();
            getSession().save(assignment);
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

	public boolean deleteAssignment(int assignmentId) {
		boolean res = false;
		try {
            begin();
            Query q1 = getSession().createQuery("delete from StudentAssignment where assignment.id = :aid");
            q1.setInteger("aid", assignmentId).executeUpdate();
            Query q2 = getSession().createQuery("delete from Assignment where id = :aid");
            q2.setInteger("aid", assignmentId).executeUpdate();
            commit();
            res = true;
        } catch(HibernateException e) {
            e.printStackTrace();
            rollback();
        } finally {
            close();
        }
		return res;		
	}
	
}

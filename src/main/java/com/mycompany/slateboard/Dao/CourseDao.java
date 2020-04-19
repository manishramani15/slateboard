package com.mycompany.slateboard.Dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.query.Query;

import com.mycompany.slateboard.pojo.Course;
import com.mycompany.slateboard.pojo.Professor;
import com.mycompany.slateboard.pojo.Student;

public class CourseDao extends DAO {

//	public Course getCourse(int id) {
//    	Course course = null;
//    	
//    	try {
//    		begin();
//    		Query q = getSession().createQuery("from Course where id = :id");
//    		q.setInteger("id", id);
//            course = (Course) q.uniqueResult();
//            commit();
//        } catch(HibernateException e) {
//            e.printStackTrace();
//            rollback();
//        } finally {
//            close();
//        }
//    	
//    	return course;
//    }
	
    public Course getCourse(int id) {
    	List<Course> courses = new ArrayList<Course>();
        Course course = null;
        try {
            begin();
            Query q = getSession().createQuery("from Course where id = :id");
            q.setInteger("id", id);
            courses = q.list();
            commit();
            course = courses.get(0);
        } catch(HibernateException e) {
            e.printStackTrace();
            rollback();
        } finally {
            close();
        }
        
        return course;
    }
}

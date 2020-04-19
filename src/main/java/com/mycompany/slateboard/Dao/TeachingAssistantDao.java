package com.mycompany.slateboard.Dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.query.Query;

import com.mycompany.slateboard.pojo.Course;
import com.mycompany.slateboard.pojo.Professor;
import com.mycompany.slateboard.pojo.Student;
import com.mycompany.slateboard.pojo.StudentCourse;
import com.mycompany.slateboard.pojo.TeachingAssistant;
import com.mycompany.slateboard.pojo.TeachingAssistantCourse;

public class TeachingAssistantDao extends DAO {
    public TeachingAssistant getTeachingAssistant(String email, String password) {
    	TeachingAssistant teachingAssistant = null;
      try {
          begin();
          Query q = getSession().createQuery("from TeachingAssistant where email = :email and password = :password");
          q.setString("email", email);
          q.setString("password", password);
          teachingAssistant = (TeachingAssistant) q.uniqueResult();
          commit();
      } catch(HibernateException e) {
          e.printStackTrace();
          rollback();
      } finally {
          close();
      }
      return teachingAssistant;
  }
    
    public List<Course> getCourses(int ta_id) {
    	List<TeachingAssistantCourse> teachingAssistantCourses = getCourseByTeachingAssistantId(ta_id);
    	List<Course> courses = new ArrayList<Course>();
    	for(TeachingAssistantCourse teachingAssistantCourse: teachingAssistantCourses) {
    		Course course = teachingAssistantCourse.getCourse();
    		courses.add(course);
    	}
        return courses;
    }
    
	public List<TeachingAssistantCourse> getCourseByTeachingAssistantId(int ta_id) {
		List<TeachingAssistantCourse> teachingAssistantCourse = new ArrayList<TeachingAssistantCourse>();
		try {
			begin();
			Query q = getSession().createQuery("FROM TeachingAssistantCourse where teachingAssistant.id = :id");
			q.setInteger("id", ta_id);
			teachingAssistantCourse = q.list();
			commit();
		} catch (HibernateException e) {
			e.printStackTrace();
			rollback();
		} finally {
			close();
		}
		return teachingAssistantCourse;
	}
	
    public boolean checkTeachingAssistantExistance(String taIdentificationNumber) {
        List<TeachingAssistant> teachingAssistants = new ArrayList<TeachingAssistant>();
        try {
            begin();
            Query q = getSession().createQuery("from TeachingAssistant where taIdentificationNumber = :pid");
            q.setString("pid", taIdentificationNumber);
            teachingAssistants = q.list();
            commit();
        } catch(HibernateException e) {
            e.printStackTrace();
            rollback();
        } finally {
            close();
        }
        
        return teachingAssistants.size() >= 1;
    }
    
    public int addTeachingAssistant(String name, String email, String password, String phone, String taIdentificationNumber) {
        int result = 0;
        try {
            TeachingAssistant teachingAssistant = new TeachingAssistant();
            teachingAssistant.setName(name);
            teachingAssistant.setEmail(email);
            teachingAssistant.setPassword(password);
            teachingAssistant.setPhone(phone);
            teachingAssistant.setTaIdentificationNumber(taIdentificationNumber);
            begin();
            getSession().save(teachingAssistant);
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
}

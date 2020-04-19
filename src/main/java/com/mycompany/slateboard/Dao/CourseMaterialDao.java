package com.mycompany.slateboard.Dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.query.Query;

import com.mycompany.slateboard.pojo.CourseMaterial;
import com.mycompany.slateboard.pojo.Professor;

public class CourseMaterialDao extends DAO {

	public List<CourseMaterial> getCourseMaterialFromCourse(int courseId) {
		List<CourseMaterial> courseMaterials = null;
		try {
            begin();
            Query q = getSession().createQuery("from CourseMaterial where course.id = :id");
            q.setInteger("id", courseId);
            courseMaterials = q.list();
            commit();
        } catch(HibernateException e) {
            e.printStackTrace();
            rollback();
        } finally {
            close();
        }
		return courseMaterials;
	}
	
	public CourseMaterial getCourseMaterial(int id) {
		CourseMaterial courseMaterial = null;
		try {
			begin();
			Query q = getSession().createQuery("from CourseMaterial where id = :id");
			q.setInteger("id", id);
			courseMaterial = (CourseMaterial) q.uniqueResult();
			commit();
		}  catch(HibernateException e) {
            e.printStackTrace();
            rollback();
        } finally {
            close();
        }
		return courseMaterial;
	}
	

    public int addCourseMaterial(CourseMaterial courseMaterial) {
        int result = 0;
        try {
            begin();
            getSession().save(courseMaterial);
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

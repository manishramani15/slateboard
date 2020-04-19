package com.mycompany.slateboard.Dao;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.query.Query;

import com.mycompany.slateboard.pojo.Course;
import com.mycompany.slateboard.pojo.Professor;

public class ProfessorDao extends DAO {

	public boolean checkProfessorExistance(String professorIdentificationNumber) {
		List<Professor> professors = new ArrayList<Professor>();
		try {
			begin();
			Query q = getSession().createQuery("from Professor where professor_identification_number = :pid"); // it
																												// should
																												// be
																												// professorIdentificationNumber
			q.setString("pid", professorIdentificationNumber);
			professors = q.list();
			commit();
		} catch (HibernateException e) {
			e.printStackTrace();
			rollback();
		} finally {
			close();
		}

		return professors.size() >= 1;
	}

	public int addProfessor(String name, String email, String password, String phone,
			String professorIdentificationNumber) {
		int result = 0;
		try {
			Professor professor = new Professor();
			professor.setName(name);
			professor.setEmail(email);
			professor.setPassword(password);
			professor.setPhone(phone);
			professor.setProfessorIdentificationNumber(professorIdentificationNumber);
			begin();
			getSession().save(professor);
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

	public boolean addCourse(Professor professor, Course course) {
		// check if exist
		boolean exist = courseExist(professor, course);
		boolean result = false;
		if (!exist) {
			try {
				course.setProfessor(professor);
				begin();
				getSession().save(course);
				commit();
				result = true;
			} catch (HibernateException e) {
				e.printStackTrace();
				rollback();
			} finally {
				close();
			}
		} else {
			result = true;
		}

		return result;
	}

	public boolean courseExist(Professor professor, Course course) {
		List<Course> courses = null;
		try {
			begin();
			Query q = getSession().createQuery("from Course where id = :id and professor.id = :pid"); // it																									// professorIdentificationNumber
			q.setInteger("pid", professor.getId());
			q.setInteger("id", course.getId());			
			courses = q.list();
			commit();
		} catch (HibernateException e) {
			e.printStackTrace();
			rollback();
		} finally {
			close();
		}

		return courses.size() >= 1;
	}

//    public void deleteMessageById(long id){
//        try {
//            beginTransaction();
//            Query q = getSession().createQuery("from Message where id= :id");
//            q.setLong("id", id);
//            Message msgToDelete = (Message)q.uniqueResult();
//            getSession().delete(msgToDelete);
//            commit();
//        } catch (HibernateException e) {
//            e.printStackTrace();
//            rollbackTransaction();
//        } finally {
//            close();
//        }
//    }

	public Professor getProfessor(int id) {
		List<Professor> professors = new ArrayList<Professor>();
		Professor professor = null;
		try {
			begin();
			Query q = getSession().createQuery("from Professor where id = :id");
			q.setInteger("id", id);
			professors = q.list();
			commit();
			professor = professors.get(0);
		} catch (HibernateException e) {
			e.printStackTrace();
			rollback();
		} finally {
			close();
		}

		return professor;
	}

	public List<Course> getCourses(int professor_id) {
		List<Course> courses = new ArrayList<Course>();
		try {
			begin();
			Query q = getSession().createQuery("from Course where professor.id = :id");
			q.setInteger("id", professor_id);
			courses = q.list();
			commit();
//            professor = professors.get(0);
		} catch (HibernateException e) {
			e.printStackTrace();
			rollback();
		} finally {
			close();
		}

		return courses;
	}

	public Professor getProfessor(String email, String password) {
//        List<Professor> professors = new ArrayList<Professor>();
		Professor professor = null;
		try {
			begin();
			Query q = getSession().createQuery("from Professor where email = :email and password = :password");
			q.setString("email", email);
			q.setString("password", password);
			professor = (Professor) q.uniqueResult();
			commit();
		} catch (HibernateException e) {
			e.printStackTrace();
			rollback();
		} finally {
			close();
		}

		return professor;
	}
}

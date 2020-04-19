package com.mycompany.slateboard.Dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.query.Query;

import com.mycompany.slateboard.pojo.Admin;
import com.mycompany.slateboard.pojo.Assignment;
import com.mycompany.slateboard.pojo.Course;
import com.mycompany.slateboard.pojo.Professor;
import com.mycompany.slateboard.pojo.Student;
import com.mycompany.slateboard.pojo.StudentCourse;
import com.mycompany.slateboard.pojo.TeachingAssistant;
import com.mycompany.slateboard.pojo.TeachingAssistantCourse;

public class AdminDao extends DAO {

	public boolean checkExistance(String loginId, String password) {
		boolean res = false;
		try {
			begin();
			Query q = getSession().createQuery("from Admin where loginId = :loginId and password = :password");
			q.setString("loginId", loginId);
			q.setString("password", password);
			Admin admin = (Admin) q.uniqueResult();
			commit();
			if(admin != null)
				res = true;
		} catch(HibernateException e) {
	          e.printStackTrace();
	          rollback();
	      } finally {
	          close();
	      }
		return res;
	}
	
	public List<Professor> getAllProfessors() {
		List<Professor> professors = null;
		try {
			begin();
			Query q = getSession().createQuery("from Professor");
			professors = q.list();
			commit();
		} catch (HibernateException e) {
			e.printStackTrace();
			rollback();
		} finally {
			close();
		}
		return professors;
	}
	
	public List<TeachingAssistant> getAllTeachingAssistants() {
		List<TeachingAssistant> teachingAssistants = null;
		try {
			begin();
			Query q = getSession().createQuery("from TeachingAssistant");
			teachingAssistants = q.list();
			commit();
		} catch (HibernateException e) {
			e.printStackTrace();
			rollback();
		} finally {
			close();
		}
		return teachingAssistants;
	}
	
	public List<Student> getAllStudents() {
		List<Student> students = null;
		try {
			begin();
			Query q = getSession().createQuery("from Student");
			students = q.list();
			commit();
		} catch (HibernateException e) {
			e.printStackTrace();
			rollback();
		} finally {
			close();
		}
		return students;
	}
	
	public List<Course> getAllCourses() {
		List<Course> courses = null;
		try {
			begin();
			Query q = getSession().createQuery("from Course");
			courses = q.list();
			commit();
		} catch (HibernateException e) {
			e.printStackTrace();
			rollback();
		} finally {
			close();
		}
		return courses;
	}

	public TeachingAssistant getTeachingAssistant(int id) {
		TeachingAssistant teachingAssistant = null;
    	
    	try {
    		begin();
    		Query q = getSession().createQuery("from TeachingAssistant where id = :id");
    		q.setInteger("id", id);
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
	public Student getStudent(int id) {
		Student student = null;
    	
    	try {
    		begin();
    		Query q = getSession().createQuery("from Student where id = :id");
    		q.setInteger("id", id);
    		student = (Student) q.uniqueResult();
            commit();
        } catch(HibernateException e) {
            e.printStackTrace();
            rollback();
        } finally {
            close();
        }
    	
    	return student;
	}
	
	public boolean addTeachingAssistantCourse(TeachingAssistantCourse teachingAssistantCourse) {
		boolean result = false;
		boolean exist = teachingAssistantCourseExist(teachingAssistantCourse);
        if(!exist) {
        	try {
        		begin();
        		getSession().save(teachingAssistantCourse);
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
	
	private boolean teachingAssistantCourseExist(TeachingAssistantCourse teachingAssistantCourse) {
		List<TeachingAssistantCourse> teachingAssistantCourses = null;
		try {
			begin();
			Query q = getSession().createQuery("from TeachingAssistantCourse where course.id = :cid and teachingAssistant.id = :taid"); // it																									// professorIdentificationNumber
			q.setInteger("cid", teachingAssistantCourse.getCourse().getId());
			q.setInteger("taid", teachingAssistantCourse.getTeachingAssistant().getId());			
			teachingAssistantCourses = q.list();
			commit();
		} catch (HibernateException e) {
			e.printStackTrace();
			rollback();
		} finally {
			close();
		}

		return teachingAssistantCourses.size() >= 1;
	}
	
	public boolean addStudentCourse(StudentCourse studentCourse) {
		boolean exist = studentCourseExist(studentCourse);
		boolean result = false;
		if(!exist) {			
			try {
				begin();
				getSession().save(studentCourse);
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

	private boolean studentCourseExist(StudentCourse studentCourse) {
		List<StudentCourse> studentCourses = null;
		try {
			begin();
			Query q = getSession().createQuery("from StudentCourse where course.id = :cid and student.id = :sid"); // it																									// professorIdentificationNumber
			q.setInteger("cid", studentCourse.getCourse().getId());
			q.setInteger("sid", studentCourse.getStudent().getId());			
			studentCourses = q.list();
			commit();
		} catch (HibernateException e) {
			e.printStackTrace();
			rollback();
		} finally {
			close();
		}

		return studentCourses.size() >= 1;
	}
	
	public boolean emailExist(String role, String email, String identificationNumber) {
		boolean res = false;
		if(role.equals("professor")) {
			List<Professor> professors = new ArrayList<Professor>();
			try {
				begin();
				Query q = getSession().createQuery("from Professor where email = :email or professorIdentificationNumber = :pid"); // it
				q.setString("email", email);
				q.setString("pid", identificationNumber);
				professors = q.list();
				commit();
			} catch (HibernateException e) {
				e.printStackTrace();
				rollback();
			} finally {
				close();
			}
			res = professors.size() >= 1;
		} else if(role.equals("teaching_assistant")) {
			List<TeachingAssistant> tas = new ArrayList<TeachingAssistant>();
			try {
				begin();
				Query q = getSession().createQuery("from TeachingAssistant where email = :email or taIdentificationNumber = :taid"); // it
				q.setString("email", email);
				q.setString("taid", identificationNumber);
				tas = q.list();
				commit();
			} catch (HibernateException e) {
				e.printStackTrace();
				rollback();
			} finally {
				close();
			}
			res = tas.size() >= 1;
		} else if(role.equals("student")) {
			List<Student> students = new ArrayList<Student>();
			try {
				begin();
				Query q = getSession().createQuery("from Student where email = :email or rollNumber = :sid"); // it
				q.setString("email", email);
				q.setString("sid", identificationNumber);
				students = q.list();
				commit();
			} catch (HibernateException e) {
				e.printStackTrace();
				rollback();
			} finally {
				close();
			}
			res = students.size() >= 1;
		}
		return res;
	}

	public boolean emailExistForLogin(String role, String email) {
		boolean res = false;
		if(role.equals("professor")) {
			List<Professor> professors = new ArrayList<Professor>();
			try {
				begin();
				Query q = getSession().createQuery("from Professor where email = :email"); // it
				q.setString("email", email);
				professors = q.list();
				commit();
			} catch (HibernateException e) {
				e.printStackTrace();
				rollback();
			} finally {
				close();
			}
			res = professors.size() >= 1;
		} else if(role.equals("teaching_assistant")) {
			List<TeachingAssistant> tas = new ArrayList<TeachingAssistant>();
			try {
				begin();
				Query q = getSession().createQuery("from TeachingAssistant where email = :email"); // it
				q.setString("email", email);
				tas = q.list();
				commit();
			} catch (HibernateException e) {
				e.printStackTrace();
				rollback();
			} finally {
				close();
			}
			res = tas.size() >= 1;
		} else if(role.equals("student")) {
			List<Student> students = new ArrayList<Student>();
			try {
				begin();
				Query q = getSession().createQuery("from Student where email = :email"); // it
				q.setString("email", email);
				students = q.list();
				commit();
			} catch (HibernateException e) {
				e.printStackTrace();
				rollback();
			} finally {
				close();
			}
			res = students.size() >= 1;
		}
		return res;
	}

	public boolean emailPasswordExist(String role, String email, String password) {
		boolean res = false;
		if(role.equals("professor")) {
			List<Professor> professors = new ArrayList<Professor>();
			try {
				begin();
				Query q = getSession().createQuery("from Professor where email = :email and password = :password"); // it
				q.setString("email", email);
				q.setString("password", password);
				professors = q.list();
				commit();
			} catch (HibernateException e) {
				e.printStackTrace();
				rollback();
			} finally {
				close();
			}
			res = professors.size() >= 1;
		} else if(role.equals("teaching_assistant")) {
			List<TeachingAssistant> tas = new ArrayList<TeachingAssistant>();
			try {
				begin();
				Query q = getSession().createQuery("from TeachingAssistant where email = :email and password = :password"); // it
				q.setString("email", email);
				q.setString("password", password);
				tas = q.list();
				commit();
			} catch (HibernateException e) {
				e.printStackTrace();
				rollback();
			} finally {
				close();
			}
			res = tas.size() >= 1;
		} else if(role.equals("student")) {
			List<Student> students = new ArrayList<Student>();
			try {
				begin();
				Query q = getSession().createQuery("from Student where email = :email and password = :password"); // it
				q.setString("email", email);
				q.setString("password", password);
				students = q.list();
				commit();
			} catch (HibernateException e) {
				e.printStackTrace();
				rollback();
			} finally {
				close();
			}
			res = students.size() >= 1;
		}
		return res;
	}
	
}

package com.mycompany.slateboard.Dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.query.Query;

import com.mycompany.slateboard.pojo.Course;
import com.mycompany.slateboard.pojo.Professor;
import com.mycompany.slateboard.pojo.Student;
import com.mycompany.slateboard.pojo.StudentCourse;

public class StudentDao extends DAO {

    public int addStudent(String name, String email, String password, String phone, String rollNumber) {
        int result = 0;
        try {
            Student student = new Student();
            student.setName(name);
            student.setEmail(email);
            student.setPassword(password);
            student.setPhone(phone);
            student.setRollNumber(rollNumber);
            begin();
            getSession().save(student);
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
    
    public boolean deleteStudent(int id) {
    	boolean result = false;
    	//todo delete or nullify all fk in student associations.
    	try {
    		begin();
    		Query q = getSession().createQuery("delete from Student where id = :id");
    		q.setInteger("id", id);
            int res = q.executeUpdate();
            commit();
            result = true;
        } catch(HibernateException e) {
            e.printStackTrace();
            rollback();
        } finally {
            close();
        }
    	
    	return result;
    }
    
    public Student getStudent(String email, String password) {
    	Student student = null;
      try {
          begin();
          Query q = getSession().createQuery("from Student where email = :email and password = :password");
          q.setString("email", email);
          q.setString("password", password);
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
    
    public List<Course> getCourses(int student_id) {
    	List<StudentCourse> studentCourses = new StudentCourseDao().getStudentCourseByStudentId(student_id);
    	List<Course> courses = new ArrayList<Course>();
    	for(StudentCourse studentCourse: studentCourses) {
    		Course course = studentCourse.getCourse();
    		courses.add(course);
    	}
        return courses;
    }
    
    public boolean checkStudentExistance(String rollNumber) {
        List<Student> students = new ArrayList<Student>();
        try {
            begin();
            Query q = getSession().createQuery("from Student where rollNumber = :pid");
            q.setString("pid", rollNumber);
            students = q.list();
            commit();
        } catch(HibernateException e) {
            e.printStackTrace();
            rollback();
        } finally {
            close();
        }
        
        return students.size() >= 1;
    }
}

package com.mycompany.slateboard.pojo;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "student")
public class Student {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;
	
	@Column(name = "roll_number")
	String rollNumber;
	
	@Column(name = "name")
	String name;
	
	@Column(name = "email")
	String email;
	
	@Column(name = "password")
	String password;
	
	@Column(name = "phone_number")
	String phone;

	@OneToMany(mappedBy = "student")
	private Set<StudentCourse> studentsCourses;
	
	@OneToMany(mappedBy = "student")
	private Set<StudentAssignment> studentsAssignments;
	
	public Set<StudentAssignment> getStudentsAssignments() {
		return studentsAssignments;
	}

	public void setStudentsAssignments(Set<StudentAssignment> studentsAssignments) {
		this.studentsAssignments = studentsAssignments;
	}

	public Set<StudentCourse> getStudentsCourses() {
		return studentsCourses;
	}

	public void setStudentsCourses(Set<StudentCourse> studentsCourses) {
		this.studentsCourses = studentsCourses;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRollNumber() {
		return rollNumber;
	}

	public void setRollNumber(String rollNumber) {
		this.rollNumber = rollNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	
}

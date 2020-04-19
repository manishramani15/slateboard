package com.mycompany.slateboard.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "student_course")
public class StudentCourse {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "credits_achieved")
	private Integer creditsAchieved;
	
	@Column(name = "grade", nullable = true)
	private String grade;
	
	@ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
	private Student student;
	
	@ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
	private Course course;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Integer getCreditsAchieved() {
		return creditsAchieved;
	}

	public void setCreditsAchieved(int creditsAchieved) {
		this.creditsAchieved = creditsAchieved;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}
	
	
}

package com.mycompany.slateboard.pojo;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="course")
public class Course {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "title")
	private String title;
	
	@Column(name = "credits")
	private int credits;
	
	@ManyToOne
    @JoinColumn(name="professor_id", nullable=false)
    private Professor professor;
	
	@OneToMany(mappedBy = "course")
	private Set<StudentCourse> studentsCourses;
	
	@OneToMany(mappedBy = "course")
	private Set<Assignment> assignments;
	
	@OneToMany(mappedBy = "course")
	private Set<CourseMaterial> courseMaterials;

	public Set<Assignment> getAssignments() {
		return assignments;
	}

	public void setAssignments(Set<Assignment> assignments) {
		this.assignments = assignments;
	}

	public Set<CourseMaterial> getCourseMaterials() {
		return courseMaterials;
	}

	public void setCourseMaterials(Set<CourseMaterial> courseMaterials) {
		this.courseMaterials = courseMaterials;
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getCredits() {
		return credits;
	}

	public void setCredits(int credits) {
		this.credits = credits;
	}

	public Professor getProfessor() {
		return professor;
	}

	public void setProfessor(Professor professor) {
		this.professor = professor;
	}
	
}

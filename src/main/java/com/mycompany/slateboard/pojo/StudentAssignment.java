package com.mycompany.slateboard.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

@Entity
@Table(name = "student_assignment")
public class StudentAssignment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "grade", nullable = true)
	private int grade;
	
	@Column(name = "file_path")
	private String filePath;
	
	@Transient
	private CommonsMultipartFile fileAttached;
	
	public CommonsMultipartFile getFileAttached() {
		return fileAttached;
	}

	public void setFileAttached(CommonsMultipartFile fileAttached) {
		this.fileAttached = fileAttached;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	@ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
	private Student student;
	
	@ManyToOne
    @JoinColumn(name = "assignment_id", nullable = false)
	private Assignment assignment;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getGrade() {
		return grade;
	}

	public void setGrade(int grade) {
		this.grade = grade;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public Assignment getAssignment() {
		return assignment;
	}

	public void setAssignment(Assignment assignment) {
		this.assignment = assignment;
	}
	
	
	
}

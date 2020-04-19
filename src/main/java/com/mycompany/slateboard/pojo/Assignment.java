package com.mycompany.slateboard.pojo;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.util.Date;
import java.util.Set;


@Entity
@Table(name="assignment")
public class Assignment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "file_path")
	private String filePath;
	
	@Column(name = "notes")
	private String notes;
	
	@Transient
	private CommonsMultipartFile fileAttached;
	
	public CommonsMultipartFile getFileAttached() {
		return fileAttached;
	}

	public void setFileAttached(CommonsMultipartFile fileAttached) {
		this.fileAttached = fileAttached;
	}

	@Basic(optional = false)
	@Column(name = "created_at", insertable = false, updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdAt;
	
	@ManyToOne
    @JoinColumn(name="course_id", nullable=false)
	private Course course;

	@OneToMany(mappedBy = "assignment")
	private Set<StudentAssignment> studentsAssignments;
	
	
	public Set<StudentAssignment> getStudentsAssignments() {
		return studentsAssignments;
	}

	public void setStudentsAssignments(Set<StudentAssignment> studentsAssignments) {
		this.studentsAssignments = studentsAssignments;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}
	
	
	
	
	
	
	
	

}

package com.mycompany.slateboard.pojo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "ta_course")
public class TeachingAssistantCourse {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne
    @JoinColumn(name = "ta_id", nullable = false)
	private TeachingAssistant teachingAssistant;
	
	@ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
	private Course course;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public TeachingAssistant getTeachingAssistant() {
		return teachingAssistant;
	}

	public void setTeachingAssistant(TeachingAssistant teachingAssistant) {
		this.teachingAssistant = teachingAssistant;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}
	
	
}

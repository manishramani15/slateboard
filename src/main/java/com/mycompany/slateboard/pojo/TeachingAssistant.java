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
@Table(name = "teaching_assistant")
public class TeachingAssistant {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;

	@Column(name = "ta_identification_number")
	String taIdentificationNumber;

	@Column(name = "name")
	String name;

	@Column(name = "email")
	String email;

	@Column(name = "password")
	String password;

	@Column(name = "phone_number")
    String phone;
	
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTaIdentificationNumber() {
		return taIdentificationNumber;
	}

	public void setTaIdentificationNumber(String taIdentificationNumber) {
		this.taIdentificationNumber = taIdentificationNumber;
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

	public Set<TeachingAssistantCourse> getTeachingAssistantCourse() {
		return teachingAssistantCourse;
	}

	public void setTeachingAssistantCourse(Set<TeachingAssistantCourse> teachingAssistantCourse) {
		this.teachingAssistantCourse = teachingAssistantCourse;
	}

	@OneToMany(mappedBy = "teachingAssistant")
	private Set<TeachingAssistantCourse> teachingAssistantCourse;

}

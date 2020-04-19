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
@Table(name = "professor")
public class Professor {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;
	
	@Column(name = "name")
    String name;
	
	@Column(name = "email")
	String email;
	
	@Column(name = "password")
    String password;
	
	@Column(name = "phone_number")
    String phone;
	
	@Column(name = "professor_identification_number")
    String professorIdentificationNumber;

	@OneToMany(mappedBy = "professor")
	private Set<Course> courses;
	
    public Set<Course> getCourses() {
		return courses;
	}

	public void setCourses(Set<Course> courses) {
		this.courses = courses;
	}

	public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Professor{" + "id=" + id + ", name=" + name + ", email=" + email + ", password=" + password + ", phone=" + phone + ", professorIdentificationNumber=" + professorIdentificationNumber + '}';
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

    public String getProfessorIdentificationNumber() {
        return professorIdentificationNumber;
    }

    public void setProfessorIdentificationNumber(String professorIdentificationNumber) {
        this.professorIdentificationNumber = professorIdentificationNumber;
    }
}

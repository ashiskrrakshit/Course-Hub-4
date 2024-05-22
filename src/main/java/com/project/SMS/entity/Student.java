package com.project.SMS.entity;


import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Entity
@Table(name = "student")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Student 
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name="firstname")
	private String firstName;
	
	@Column(name="lastname")
	private String lastName;
	
	@Column(unique = true, nullable = false)
	private String email;
	
	@Column(name="mobilenumber")
	private String mobileNumber;
	
	
	@OneToMany(mappedBy = "student")
	private List<Review> review;
	
	
	@ManyToMany(cascade = CascadeType.PERSIST)
	@JoinTable(name = "STUDENT_COURSE", 
		joinColumns = @JoinColumn(name="STUDENT_ID",referencedColumnName = "id"), 
		inverseJoinColumns = @JoinColumn(name = "COURSE_ID", referencedColumnName = "id"))
	private List<Course> courses = new ArrayList<>();
	

}


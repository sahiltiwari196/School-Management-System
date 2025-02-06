	package com.smsystem.model;
	
	import java.util.ArrayList;
	import java.util.HashSet;
	import java.util.List;
	import java.util.Set;
	
	import jakarta.persistence.*;
	
	@Entity
	@DiscriminatorValue("STUDENT")
	 
	public class Student extends User {
	
	    private String name;
	    private String dateOfBirth;
	    private String gender;
	
	    @Column(unique = true, nullable = true)
	    private String studentCode; 
	
	    @OneToMany(cascade = CascadeType.ALL, mappedBy = "student")
	    private List<StudentAddress> addresses = new ArrayList<>();
	
	    @ManyToMany
	    @JoinTable(
	        name = "student_course",
	        joinColumns = @JoinColumn(name = "student_id"),
	        inverseJoinColumns = @JoinColumn(name = "course_id")
	    )
	    private Set<Course> courses = new HashSet<>();
	
	    public Student() {}
	
	    // Ensure username is set as studentCode
	    public void setStudentCode(String studentCode) {
	        this.studentCode = studentCode;
	        super.setUsername(studentCode); // Enforce username = studentCode
	    }
	
	    public String getStudentCode() {
	        return studentCode;
	 
	    }
	
		public String getName() {
			return name;
		}
	
		public void setName(String name) {
			this.name = name;
		}
	
		public String getDateOfBirth() {
			return dateOfBirth;
		}
	
		public void setDateOfBirth(String dateOfBirth) {
			this.dateOfBirth = dateOfBirth;
		}
	
		public String getGender() {
			return gender;
		}
	
		public void setGender(String gender) {
			this.gender = gender;
		}
	
		public List<StudentAddress> getAddresses() {
			return addresses;
		}
	
		public void setAddresses(List<StudentAddress> addresses) {
			this.addresses = addresses;
			
		}
	
		public Set<Course> getCourses() {
			return courses;
		}
	
		public void setCourses(Set<Course> courses) {
			this.courses = courses;
		}
	    
	    
	}

package com.smsystem.util;

import org.springframework.stereotype.Component;

import com.smsystem.dto.CourseBean;
import com.smsystem.dto.StudentRegistrationRequest;
import com.smsystem.model.Course;
import com.smsystem.model.Student;
import com.smsystem.model.StudentAddress;

import jakarta.validation.Valid;

@Component
public class DtoMapping {
	
	
	public static Student getStudentFromStudentRegistrationRequest(StudentRegistrationRequest request) {
		Student student = new Student();
        student.setName(request.getName());
        student.setDateOfBirth(request.getDateOfBirth());
        student.setGender(request.getGender());
        student.setStudentCode(request.getStudentCode()); 
        student.setPassword(PasswordUtil.encodePassword(request.getDateOfBirth()));
        
        if(request.getAddresses()!=null&&request.getAddresses().size()>0) {
        	for(StudentAddress sa : request.getAddresses()) {
        		sa.setStudent(student);
        	}
        	
        	student.setAddresses(request.getAddresses());
        }
        return student;
	}

	public static Course mapCourseBeanToCourse(@Valid CourseBean courseBean) {
	
		 if (courseBean == null) {
	            return null;
	        }
	        Course course = new Course();
	        course.setName(courseBean.getName());
	        course.setDescription(courseBean.getDescription());
	        course.setType(courseBean.getType());
	        course.setDuration(courseBean.getDuration());
	        course.setTopics(courseBean.getTopics());
	        return course;
	}	

}

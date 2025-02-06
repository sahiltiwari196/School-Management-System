package com.smsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.smsystem.dto.CourseBean;
import com.smsystem.exception.GlobalExceptionHandler;
import com.smsystem.exception.UnauthorizedException;
import com.smsystem.service.AuthenticationService;
import com.smsystem.service.CourseService;
import com.smsystem.util.DtoMapping;

import jakarta.validation.Valid;

@RestController
@RequestMapping
public class CourseController {

	@Autowired
	AuthenticationService authenticationService;
	
	@Autowired
	CourseService courseService;
	
	@PostMapping("/uploadCourse")
	public ResponseEntity<Object> uploadCourse(@RequestAttribute("userID") String userID,
			@RequestAttribute("token") String token, @Valid @RequestBody CourseBean request) {

		try {
			authenticationService.authenticateUser(userID, "ADMIN");
			
			courseService.uploadCourse(DtoMapping.mapCourseBeanToCourse(request));

		} catch (UnauthorizedException e) {
			return GlobalExceptionHandler.handleUnauthorized(e);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body("Something Went Wrong");
		}
		return ResponseEntity.ok("success");
	}
	
	  @PostMapping("/assign-course")
	    public ResponseEntity<Object>  assignCourse(@RequestAttribute("userID") String userID,@RequestParam String studentId, @RequestParam Long courseId) {
			try {
		  	authenticationService.authenticateUser(userID, "ADMIN");
		  
	        courseService.assignCourseToStudent(studentId, courseId);
	        
		} catch (UnauthorizedException e) {
			return GlobalExceptionHandler.handleUnauthorized(e);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body("Something Went Wrong");
		}
	        return ResponseEntity.ok("Course Assigned Successfully");
	    }
	  
	 
	  
}

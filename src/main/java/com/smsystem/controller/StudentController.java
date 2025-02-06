package com.smsystem.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.smsystem.dto.StudentRegistrationRequest;
import com.smsystem.exception.GlobalExceptionHandler;
import com.smsystem.exception.UnauthorizedException;
import com.smsystem.model.Course;
import com.smsystem.model.Student;
import com.smsystem.service.AuthenticationService;
import com.smsystem.service.StudentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/students")
public class StudentController {
	@Autowired
	private StudentService studentService;

	@Autowired
	AuthenticationService authenticationService;

//	public StudentController(StudentService studentService) {
//		this.studentService = studentService;
//	}

	@PostMapping("/register")
	public ResponseEntity<Object> registerStudent(@RequestAttribute("userID") String userID,
			@RequestAttribute("token") String token, @Valid @RequestBody StudentRegistrationRequest request) {

		try {
			authenticationService.authenticateUser(userID, "ADMIN");

			studentService.registerStudent(request, userID);

		} catch (UnauthorizedException e) {
			return GlobalExceptionHandler.handleUnauthorized(e);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body("Something Went Wrong");
		}
		return ResponseEntity.ok("success");
	}

	

	@GetMapping("/search")
	public ResponseEntity<Object> searchStudentsByName(@RequestAttribute("userID") String userID,@RequestParam String name) {
		List<Student> students =null;
		try {
			authenticationService.authenticateUser(userID, "ADMIN");
			students = studentService.searchStudentByName(name);
		}catch (UnauthorizedException e) {
			return GlobalExceptionHandler.handleUnauthorized(e);
		}
		catch (Exception e) {
			return ResponseEntity.badRequest().body("Something Went Wrong");
		}
		return ResponseEntity.ok(students);
	}
	
	
	@GetMapping("/StudentsByCourse/{courseId}")
	public ResponseEntity<Object> getStudentsByCourse(@RequestAttribute("userID") String userID,@PathVariable Long courseId) {
		try {
			authenticationService.authenticateUser(userID, "ADMIN");
	    List<Student> students = studentService.getStudentsByCourse(courseId);
	    return ResponseEntity.ok(students);
		}catch (UnauthorizedException e) {
			return GlobalExceptionHandler.handleUnauthorized(e);
		}
		catch (Exception e) {
			return ResponseEntity.badRequest().body(null);
		}
		
	}
	
	
	
	@PostMapping("/{studentCode}")
	public ResponseEntity<Object> updateStudent(@RequestAttribute("userID") String userID ,@PathVariable String studentCode, @Valid @RequestBody Student student) {
		
		try {
		authenticationService.authenticateUser(userID, "STUDENT");
		studentService.updateStudentDetails(studentCode, student);
		
		
		return ResponseEntity.ok("Student Updated Successfully");
		} catch (UnauthorizedException e) {
			return GlobalExceptionHandler.handleUnauthorized(e);
		}
		catch (Exception e) {
			return ResponseEntity.badRequest().body(null);
		}

	}
	
	
	@GetMapping("/{studentCode}/courses")
	public ResponseEntity<Object>  getAssignedCourses(@RequestAttribute("userID") String userID,@PathVariable String studentCode) {
		try {
		authenticationService.authenticateUser(userID, "STUDENT");
	    Student student = studentService.getStudentById(studentCode);
	    return ResponseEntity.ok(new ArrayList<>(student.getCourses()));
		} catch (UnauthorizedException e) {
			return GlobalExceptionHandler.handleUnauthorized(e);
		}
		catch (Exception e) {
			return ResponseEntity.badRequest().body(null);
		}

	}
	
	
	
	@PostMapping("/{studentId}/leave-course/{courseId}")
	public ResponseEntity<Object>  leaveCourse(@RequestAttribute("userID") String userID ,@PathVariable String studentId, @PathVariable Long courseId) {
		try {
		authenticationService.authenticateUser(userID, "STUDENT");
	    studentService.removeCourseFromStudent(studentId, courseId);
	    return ResponseEntity.ok("Course removed successfully.");
		} catch (UnauthorizedException e) {
			return GlobalExceptionHandler.handleUnauthorized(e);
		}
		catch (Exception e) {
			return ResponseEntity.badRequest().body(null);
		}

	}

}

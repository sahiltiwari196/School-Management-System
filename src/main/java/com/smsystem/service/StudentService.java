package com.smsystem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.smsystem.dto.StudentRegistrationRequest;
import com.smsystem.exception.UnauthorizedException;
import com.smsystem.model.Course;
import com.smsystem.model.Student;
import com.smsystem.repository.CourseRepository;
import com.smsystem.repository.StudentRepository;
import com.smsystem.repository.UserRepository;
import com.smsystem.util.DtoMapping;

@Service
public class StudentService {
	@Autowired
	StudentRepository studentRepository;
	@Autowired
	UserRepository userRepository;

	@Autowired
	CourseRepository courseRepository;

	@Transactional
	public Student registerStudent(StudentRegistrationRequest request, String adminUsername)
			throws UnauthorizedException, Exception {
		// Validate if studentCode is unique
		if (studentRepository.findByStudentCode(request.getStudentCode()).isPresent()) {
			throw new IllegalArgumentException("Student Code already exists.");
		}

		return studentRepository.save(DtoMapping.getStudentFromStudentRegistrationRequest(request));
	}

	public List<Student> searchStudentByName(String name) {
		return studentRepository.findByNameContaining(name);
	}

	public void updateStudentDetails(String studentCode, Student updatedStudent) {
		Student student = studentRepository.findByStudentCode(studentCode)
				.orElseThrow(() -> new RuntimeException("Student Not Found"));

		student.setName(updatedStudent.getName());
		student.setDateOfBirth(updatedStudent.getDateOfBirth());
		student.setGender(updatedStudent.getGender());
		studentRepository.save(student);
	}

	public List<Student> getStudentsByCourse(Long courseId) {
		Course course = courseRepository.findById(courseId).orElseThrow(() -> new RuntimeException("Course Not Found"));
		return course.getStudents();
	}

	public Student getStudentById(String studentId) {
		return studentRepository.findByStudentCode(studentId)
				.orElseThrow(() -> new RuntimeException("Student Not Found"));
	}

	public void removeCourseFromStudent(String studentId, Long courseId) {
		Student student = studentRepository.findByStudentCode(studentId)
				.orElseThrow(() -> new RuntimeException("Student Not Found"));
		Course course = courseRepository.findById(courseId).orElseThrow(() -> new RuntimeException("Course Not Found"));
		student.getCourses().remove(course);
		studentRepository.save(student);
	}

}

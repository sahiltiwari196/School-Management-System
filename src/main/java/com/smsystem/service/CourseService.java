package com.smsystem.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smsystem.model.Course;
import com.smsystem.model.Student;
import com.smsystem.repository.CourseRepository;
import com.smsystem.repository.StudentRepository;

@Service
public class CourseService {
    @Autowired private CourseRepository courseRepository;
    @Autowired private StudentRepository studentRepository;

    public void uploadCourse(Course course) {
        courseRepository.save(course);
    }

    public List<Course> searchCourseByName(String name) {
        return courseRepository.findByNameContaining(name);
    }

    public void assignCourseToStudent(String studentId, Long courseId) {
        Student student = studentRepository.findByStudentCode(studentId).orElseThrow(() -> new RuntimeException("Student Not Found"));
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new RuntimeException("Course Not Found"));
        
        student.getCourses().add(course);
        studentRepository.save(student);
    }
}


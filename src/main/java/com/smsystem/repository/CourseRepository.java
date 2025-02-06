package com.smsystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smsystem.model.Course;

public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> findByType(String type);
    List<Course> findByNameContaining(String name);
}

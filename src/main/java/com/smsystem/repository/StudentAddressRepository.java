package com.smsystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smsystem.model.StudentAddress;

public interface StudentAddressRepository extends JpaRepository<StudentAddress, Long> {
    List<StudentAddress> findByStudentId(Long studentId);
}

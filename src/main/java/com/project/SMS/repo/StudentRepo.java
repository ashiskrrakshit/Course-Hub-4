package com.project.SMS.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.SMS.entity.Student;

@Repository
public interface StudentRepo extends JpaRepository<Student, Integer> {

}

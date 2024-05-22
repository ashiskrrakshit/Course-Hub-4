package com.project.SMS.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.SMS.entity.Course;

@Repository
public interface CourseRepo extends JpaRepository<Course, Integer> {

}

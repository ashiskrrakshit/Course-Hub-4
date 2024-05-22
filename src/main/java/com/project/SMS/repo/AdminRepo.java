package com.project.SMS.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.SMS.entity.Admin;
@Repository
public interface AdminRepo extends JpaRepository<Admin, Integer> {

}

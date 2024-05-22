package com.project.SMS.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.SMS.entity.Course;
import com.project.SMS.entity.Student;
import com.project.SMS.payloads.AdminDto;
import com.project.SMS.entity.Admin;
import com.project.SMS.service.CourseService;
import com.project.SMS.service.StudentService;
import com.project.SMS.service.AdminService;
@RestController
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	private AdminService adminService;
	
	
	// create a admin
	@PostMapping("/create")
	private ResponseEntity<AdminDto> createAdmin(@RequestBody AdminDto adminDto) {
		AdminDto createdAdmin = adminService.createAdmin(adminDto);
		return ResponseEntity.ok(createdAdmin);
	}
	
	
	//get all admin
	//@JsonIgnore
    @GetMapping("/all")
    public ResponseEntity<List<AdminDto>> getAllAdmins() {
        List<AdminDto> allAdmin = adminService.findAllAdmin();
        return new ResponseEntity<>(allAdmin, HttpStatus.OK);
    }
	


    // get admin by Id
    @GetMapping("/Id/{adminId}")
    public ResponseEntity<AdminDto> getAdminById(@PathVariable Integer adminId) {
    	AdminDto admin = adminService.getAdminById(adminId);
        if (admin != null) {
            return new ResponseEntity<>(admin, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    //update admin
	@PutMapping("/update/{adminId}")
	private ResponseEntity<AdminDto> updateAdmin(@RequestBody AdminDto adminDto, @PathVariable int adminId){
		AdminDto updateAdmin = adminService.updateAdmin(adminDto,adminId );
		return ResponseEntity.ok(updateAdmin);
	}
    
	//deleteUser(
	@DeleteMapping("/delete/admin/{adminId}")
	public ResponseEntity<String> deleteAdminById(@PathVariable Integer adminId) {
		String status = adminService.deleteAdmin(adminId);

		return new ResponseEntity<String>(status, HttpStatus.OK);
	}
	
	

}

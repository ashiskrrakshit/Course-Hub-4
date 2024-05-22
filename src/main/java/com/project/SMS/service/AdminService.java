package com.project.SMS.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.SMS.entity.Admin;
import com.project.SMS.entity.Course;
import com.project.SMS.entity.Review;
import com.project.SMS.exception.InvalidDataException;
import com.project.SMS.exception.ResourceNotFoundException;
import com.project.SMS.payloads.AdminDto;
import com.project.SMS.repo.AdminRepo;
import com.project.SMS.repo.CourseRepo;
import com.project.SMS.repo.StudentRepo;

@Service
public class AdminService {

	@Autowired
	private AdminRepo adminRepo;
	
	@Autowired
	private ModelMapper modelMapper;

	
	public AdminDto createAdmin(AdminDto adminDto) {
		if (adminDto == null) {
			throw new InvalidDataException("Admin data cannot be null");
		}
		
		Admin admin = this.dtoToAdmin(adminDto);
		
		Admin createdAdmin = adminRepo.save(admin);
		
		return this.adminToDto(createdAdmin);
	}

	
	
	public List<AdminDto> findAllAdmin() {
		
		List<Admin> admins = adminRepo.findAll();
		
		List<AdminDto> adminDtos = admins.stream().map(admin->adminToDto(admin)).collect(Collectors.toList());
			
		return adminDtos;
				
	}

	
	public AdminDto getAdminById(Integer adminId) {
	
		Admin admin = adminRepo.findById(adminId).orElseThrow(() -> new ResourceNotFoundException("Admin with ID " + adminId + " not found"));
		return this.adminToDto(admin);
	}
	
	//update admin
	public AdminDto updateAdmin(AdminDto adminDto, int adminId) {
	
		if (adminDto == null) {
        throw new InvalidDataException("Invalid admin data provided");
		}
    	Admin existingAdmin = adminRepo.findById(adminId).orElseThrow(() -> new RuntimeException("Admin not found"));
		existingAdmin.setFirstName(adminDto.getFirstName());
		existingAdmin.setLastName(adminDto.getLastName());
		existingAdmin.setEmail(adminDto.getEmail());
		existingAdmin.setMobileNumber(adminDto.getMobileNumber());
		
		
		
		Admin admin = adminRepo.save(existingAdmin);
		
		return this.adminToDto(admin);
	}
	

	// delete student
	public String deleteAdmin(Integer adminId) {
		if (adminRepo.existsById(adminId)) {
			adminRepo.deleteById(adminId);
			return "Admin with ID " + adminId + " deleted successfully.";
		} else {
			
			throw new ResourceNotFoundException("Admin with ID " + adminId + " not found.");
		}
	}
	
	
	public Admin dtoToAdmin(AdminDto adminDto) {
		Admin admin = modelMapper.map(adminDto, Admin.class);
		return admin;
	}
	
	public AdminDto adminToDto(Admin admin) {
		AdminDto adminDto = modelMapper.map(admin, AdminDto.class);
		return adminDto;
	}
	

}

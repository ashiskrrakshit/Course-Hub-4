package com.project.SMS.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import com.project.SMS.entity.Admin;
import com.project.SMS.payloads.AdminDto;
import com.project.SMS.repo.AdminRepo;
import com.project.SMS.service.AdminService;

public class AdminServiceTest {

    @Mock
    private AdminRepo adminRepo;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private AdminService adminService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateAdmin_Success() {
        // Test data
        AdminDto adminDto = AdminDto.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john@example.com")
                .mobileNumber("1234567890")
                .build();

        Admin admin = Admin.builder()
                .id(1)
                .firstName("John")
                .lastName("Doe")
                .email("john@example.com")
                .mobileNumber("1234567890")
                .build();

        // Mocking behavior
        when(modelMapper.map(adminDto, Admin.class)).thenReturn(admin);
        when(adminRepo.save(admin)).thenReturn(admin);
        when(modelMapper.map(admin, AdminDto.class)).thenReturn(adminDto);

        // Method call
        AdminDto createdAdminDto = adminService.createAdmin(adminDto);

        // Assertion
        assertEquals(adminDto, createdAdminDto);
    }

    @Test
    public void testFindAllAdmin_Success() {
        // Test data
        Admin admin1 = Admin.builder()
                .id(1)
                .firstName("John")
                .lastName("Doe")
                .email("john@example.com")
                .mobileNumber("1234567890")
                .build();

        Admin admin2 = Admin.builder()
                .id(2)
                .firstName("Jane")
                .lastName("Doe")
                .email("jane@example.com")
                .mobileNumber("9876543210")
                .build();

        // Mocking behavior
        when(adminRepo.findAll()).thenReturn(Arrays.asList(admin1, admin2));

        // Method call
        List<AdminDto> actualAdminDtos = adminService.findAllAdmin();

        // Assertion
        assertEquals(2, actualAdminDtos.size());
    }

    @Test
    public void testGetAdminById_Success() {
        // Test data
        int adminId = 1;
        Admin admin = Admin.builder()
                .id(adminId)
                .firstName("John")
                .lastName("Doe")
                .email("john@example.com")
                .mobileNumber("1234567890")
                .build();
        
        AdminDto adminDto1 = AdminDto.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john@example.com")
                .mobileNumber("1234567890")
                .build();

        // Mocking behavior
        when(adminRepo.findById(adminId)).thenReturn(Optional.of(admin));
        when(modelMapper.map(admin, AdminDto.class)).thenReturn(adminDto1);

        // Method call
        AdminDto adminDto = adminService.getAdminById(adminId);

        // Assertion
        assertEquals(admin.getFirstName(), adminDto.getFirstName());
        assertEquals(admin.getLastName(), adminDto.getLastName());
        assertEquals(admin.getEmail(), adminDto.getEmail());
        assertEquals(admin.getMobileNumber(), adminDto.getMobileNumber());
    }

    @Test
    public void testUpdateAdmin_Success() {
        // Test data
        int adminId = 1;
        AdminDto adminDto = AdminDto.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john@example.com")
                .mobileNumber("1234567890")
                .build();

        Admin existingAdmin = Admin.builder()
                .id(adminId)
                .firstName("Jane")
                .lastName("Doe")
                .email("jane@example.com")
                .mobileNumber("9876543210")
                .build();

        Admin updatedAdmin = Admin.builder()
                .id(adminId)
                .firstName("John")
                .lastName("Doe")
                .email("john@example.com")
                .mobileNumber("1234567890")
                .build();

        // Mocking behavior
        when(adminRepo.findById(adminId)).thenReturn(Optional.of(existingAdmin));
        when(adminRepo.save(any(Admin.class))).thenReturn(updatedAdmin);
        when(modelMapper.map(updatedAdmin, AdminDto.class)).thenReturn(adminDto);

        // Method call
        AdminDto updatedAdminDto = adminService.updateAdmin(adminDto, adminId);

        // Assertion
        assertEquals(adminDto, updatedAdminDto);
    }

    @Test
    public void testDeleteAdmin_Success() {
        // Test data
        int adminId = 1;

        // Mocking behavior
        when(adminRepo.existsById(adminId)).thenReturn(true);

        // Method call
        String result = adminService.deleteAdmin(adminId);

        // Assertion
        assertEquals("Admin with ID 1 deleted successfully.", result);
        verify(adminRepo, times(1)).deleteById(adminId);
    }
}







































//package com.project.SMS.AdminService;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.eq;
//import static org.mockito.Mockito.when;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.modelmapper.ModelMapper;
//
//import com.project.SMS.entity.Admin;
//import com.project.SMS.payloads.AdminDto;
//import com.project.SMS.repo.AdminRepo;
//import com.project.SMS.service.AdminService;
//
//@ExtendWith(MockitoExtension.class)
//public class AdminServiceTest {
//
//    @Mock
//    private AdminRepo adminRepo;
//
//    @Mock
//    private ModelMapper modelMapper;
//
//    @InjectMocks
//    private AdminService adminService;
//
//    
//    @Test
//    public void findAllAdminTest() {
//        Admin admin = new Admin(1,"ashis","rakshit","8758969889","ashis@gmail.com",new ArrayList<>());
//        List<Admin> admins = new ArrayList<>();
//        admins.add(admin);
//        
//        when(adminRepo.findAll()).thenReturn(admins);
//        
//        int size = adminService.findAllAdmin().size();
//        
//        assertEquals(1, size);
//    }
//    
//}

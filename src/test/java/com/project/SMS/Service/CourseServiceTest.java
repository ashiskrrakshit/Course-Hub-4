package com.project.SMS.Service;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.project.SMS.entity.Admin;
import com.project.SMS.entity.Course;
import com.project.SMS.exception.InvalidDataException;
import com.project.SMS.exception.ResourceNotFoundException;
import com.project.SMS.payloads.CourseDto;
import com.project.SMS.repo.AdminRepo;
import com.project.SMS.repo.CourseRepo;
import com.project.SMS.service.CourseService;

@ExtendWith(MockitoExtension.class)
public class CourseServiceTest {

    @Mock
    private CourseRepo courseRepo;

    @Mock
    private AdminRepo adminRepo;

    @InjectMocks
    private CourseService courseService;

    private Admin admin;
    private Course course;

    @BeforeEach
    public void setUp() {
        admin = Admin.builder().id(1).firstName("John").lastName("Doe").build();
        course = Course.builder().id(1).name("Math").description("Mathematics course").admin(admin).build();
    }

    @Test
    public void testCreateCourse_Success() {
        // Arrange
        CourseDto courseDto = CourseDto.builder().name("Math").description("Mathematics course").build();
        when(adminRepo.findById(1)).thenReturn(Optional.of(admin));
        when(courseRepo.save(any(Course.class))).thenReturn(course);

        // Act
        CourseDto createdCourse = courseService.createCourse(courseDto, 1);

        // Assert
        assertNotNull(createdCourse);
        assertEquals("Math", createdCourse.getName());
        assertEquals("Mathematics course", createdCourse.getDescription());
        assertEquals("John", createdCourse.getFirstName());
        assertEquals("Doe", createdCourse.getLastName());
    }

    @Test
    public void testCreateCourse_AdminNotFound_ExceptionThrown() {
        // Arrange
        CourseDto courseDto = CourseDto.builder().name("Math").description("Mathematics course").build();
        when(adminRepo.findById(2)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> courseService.createCourse(courseDto, 2));
    }

    @Test
    public void testUpdateCourse_Success() {
        // Arrange
        // Create a mock Course object
        Course course = new Course();
        course.setId(1);
        course.setName("Physics");
        course.setDescription("Physics course");
        course.setAdmin(admin); // assuming admin is properly initialized in the test setup
        
        // Stub the courseRepo.findById(1) call to return the mock Course object
        when(courseRepo.findById(1)).thenReturn(Optional.of(course));

        CourseDto courseDto = CourseDto.builder().name("Physics").description("Physics course").build();
        when(courseRepo.existsById(1)).thenReturn(true);
        when(adminRepo.findById(1)).thenReturn(Optional.of(admin));

        // Act
        CourseDto updatedCourse = courseService.updateCourse(courseDto, 1, 1);

        // Assert
        assertNotNull(updatedCourse);
        assertEquals("Physics", updatedCourse.getName());
        assertEquals("Physics course", updatedCourse.getDescription());
        assertEquals("John", updatedCourse.getFirstName());
        assertEquals("Doe", updatedCourse.getLastName());
    }


    @Test
    public void testUpdateCourse_CourseNotFound_ExceptionThrown() {
        // Arrange
        CourseDto courseDto = CourseDto.builder().name("Physics").description("Physics course").build();
        when(courseRepo.existsById(2)).thenReturn(false);

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> courseService.updateCourse(courseDto, 2, 1));
    }

    @Test
    public void testDeleteCourse_Success() {
        // Arrange
        course.setStudents(new ArrayList<>());
        when(courseRepo.existsById(1)).thenReturn(true);
        when(courseRepo.findById(1)).thenReturn(Optional.of(course));

        // Act
        boolean result = courseService.deleteCourse(1);

        // Assert
        assertTrue(result);
    }

    @Test
    public void testDeleteCourse_CourseNotFound_ExceptionThrown() {
        // Arrange
        when(courseRepo.existsById(2)).thenReturn(false);

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> courseService.deleteCourse(2));
    }
}

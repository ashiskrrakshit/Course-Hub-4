package com.project.SMS.Service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import com.project.SMS.entity.Course;
import com.project.SMS.entity.Student;
import com.project.SMS.exception.InvalidDataException;
import com.project.SMS.exception.ResourceNotFoundException;
import com.project.SMS.payloads.StudentDto;
import com.project.SMS.repo.CourseRepo;
import com.project.SMS.repo.StudentRepo;
import com.project.SMS.service.StudentService;

@ExtendWith(MockitoExtension.class)
public class StudentServiceTest {

    @Mock
    private StudentRepo studentRepo;

    @Mock
    private CourseRepo courseRepo;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private StudentService studentService;

    private Student student;

    @BeforeEach
    public void setUp() {
        student = Student.builder()
                .id(1)
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .mobileNumber("1234567890")
                .courses(new ArrayList<>())
                .build();
    }

    @Test
    public void testCreateStudent_Success() {
        // Arrange
        StudentDto studentDto = StudentDto.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .mobileNumber("1234567890")
                .build();

        when(modelMapper.map(studentDto, Student.class)).thenReturn(student);
        when(studentRepo.save(any(Student.class))).thenReturn(student);
        when(modelMapper.map(student, StudentDto.class)).thenReturn(studentDto);

        // Act
        StudentDto createdStudent = studentService.createStudent(studentDto);

        // Assert
        assertNotNull(createdStudent);
        assertEquals(studentDto.getFirstName(), createdStudent.getFirstName());
        assertEquals(studentDto.getLastName(), createdStudent.getLastName());
        assertEquals(studentDto.getEmail(), createdStudent.getEmail());
        assertEquals(studentDto.getMobileNumber(), createdStudent.getMobileNumber());
    }

    @Test
    public void testCreateStudent_NullDto_ExceptionThrown() {
        // Act & Assert
        assertThrows(InvalidDataException.class, () -> studentService.createStudent(null));
    }

    @Test
    public void testFindAllStudent_Success() {
        // Arrange
        List<Student> students = new ArrayList<>();
        students.add(student);

        when(studentRepo.findAll()).thenReturn(students);
        when(modelMapper.map(student, StudentDto.class)).thenReturn(StudentDto.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .mobileNumber("1234567890")
                .build());

        // Act
        List<StudentDto> foundStudents = studentService.findAllStudent();

        // Assert
        assertNotNull(foundStudents);
        assertEquals(1, foundStudents.size());
        assertEquals("John", foundStudents.get(0).getFirstName());
        assertEquals("Doe", foundStudents.get(0).getLastName());
        assertEquals("john.doe@example.com", foundStudents.get(0).getEmail());
        assertEquals("1234567890", foundStudents.get(0).getMobileNumber());
    }

    @Test
    public void testGetStudentById_Success() {
        // Arrange
        when(studentRepo.findById(1)).thenReturn(Optional.of(student));
        when(modelMapper.map(student, StudentDto.class)).thenReturn(StudentDto.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .mobileNumber("1234567890")
                .build());

        // Act
        StudentDto foundStudent = studentService.getStudentById(1);

        // Assert
        assertNotNull(foundStudent);
        assertEquals("John", foundStudent.getFirstName());
        assertEquals("Doe", foundStudent.getLastName());
        assertEquals("john.doe@example.com", foundStudent.getEmail());
        assertEquals("1234567890", foundStudent.getMobileNumber());
    }

    @Test
    public void testGetStudentById_InvalidId_ExceptionThrown() {
        // Arrange
        when(studentRepo.findById(2)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> studentService.getStudentById(2));
    }

    @Test
    public void testUpdateStudent_Success() {
        // Arrange
        StudentDto studentDto = StudentDto.builder()
                .firstName("Jane")
                .lastName("Doe")
                .email("jane.doe@example.com")
                .mobileNumber("9876543210")
                .build();

        when(studentRepo.findById(1)).thenReturn(Optional.of(student));
        when(studentRepo.save(any(Student.class))).thenReturn(student);
        when(modelMapper.map(student, StudentDto.class)).thenReturn(studentDto);

        // Act
        StudentDto updatedStudent = studentService.updateStudent(studentDto, 1);

        // Assert
        assertNotNull(updatedStudent);
        assertEquals("Jane", updatedStudent.getFirstName());
        assertEquals("Doe", updatedStudent.getLastName());
        assertEquals("jane.doe@example.com", updatedStudent.getEmail());
        assertEquals("9876543210", updatedStudent.getMobileNumber());
    }

    @Test
    public void testUpdateStudent_InvalidId_ExceptionThrown() {
        // Arrange
        StudentDto studentDto = StudentDto.builder()
                .firstName("Jane")
                .lastName("Doe")
                .email("jane.doe@example.com")
                .mobileNumber("9876543210")
                .build();

        when(studentRepo.findById(2)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> studentService.updateStudent(studentDto, 2));
    }

    @Test
    public void testEnrollStudent_Success() {
        // Arrange
        Course course = Course.builder().id(1).name("Math").build();

        when(studentRepo.findById(1)).thenReturn(Optional.of(student));
        when(courseRepo.findById(1)).thenReturn(Optional.of(course));

        // Act
        String result = studentService.enrollStudent(1, 1);

        // Assert
        assertEquals("Student with ID 1 enrolled in Course with Id 1 successfully.", result);
        assertTrue(student.getCourses().contains(course));
    }

    @Test
    public void testEnrollStudent_InvalidStudentId_ExceptionThrown() {
        // Arrange
        when(studentRepo.findById(2)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> studentService.enrollStudent(2, 1));
    }

    @Test
    public void testEnrollStudent_InvalidCourseId_ExceptionThrown() {
        // Arrange
        when(studentRepo.findById(1)).thenReturn(Optional.of(student));
        when(courseRepo.findById(2)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> studentService.enrollStudent(1, 2));
    }

    @Test
    public void testDeleteStudent_InvalidId_ExceptionThrown() {
        // Arrange
        when(studentRepo.existsById(2)).thenReturn(false);

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> studentService.deleteStudent(2));
    }
}
package com.project.SMS.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.SMS.entity.Admin;
import com.project.SMS.entity.Course;
import com.project.SMS.entity.Student;
import com.project.SMS.exception.InvalidDataException;
import com.project.SMS.exception.ResourceNotFoundException;
import com.project.SMS.payloads.StudentDto;
import com.project.SMS.repo.CourseRepo;
import com.project.SMS.repo.StudentRepo;
@Service
public class StudentService {

	@Autowired
	private StudentRepo studentRepo;
	
	@Autowired
	private CourseRepo courseRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	

	
	
	 public StudentDto createStudent(StudentDto studentDto) {
		if (studentDto == null) {
            throw new InvalidDataException("Student data cannot be null");
        }
	        Student student1 = dtoToStudent(studentDto);	        
	       Student student = studentRepo.save(student1);
	       
	       return studentToDto(student);
	        
	    }
	

	//find all student
	public List<StudentDto> findAllStudent() {
		List<Student> allStudent = studentRepo.findAll();
		List<StudentDto> student = allStudent.stream().map(student1->this.studentToDto(student1)).collect(Collectors.toList());
		return student;
	}


	//find student by studentId
	public StudentDto getStudentById(Integer studentId) {
        
        	Student student = studentRepo.findById(studentId).orElseThrow(() -> new ResourceNotFoundException("Student with ID " + studentId + " not found"));
        	
        	return studentToDto(student);
    }

	

	//update student	
	public StudentDto updateStudent(StudentDto studentDto, int studentId) {
		
		if (studentDto == null) {
        throw new InvalidDataException("Invalid student data provided");
		}
    	Student existingStudent = studentRepo.findById(studentId).orElseThrow(() -> new RuntimeException("Admin not found"));
    	existingStudent.setFirstName(studentDto.getFirstName());
    	existingStudent.setLastName(studentDto.getLastName());
    	existingStudent.setEmail(studentDto.getEmail());
    	existingStudent.setMobileNumber(studentDto.getMobileNumber());
    	
    	Student updatedStudent =  studentRepo.save(existingStudent);
    	
		return studentToDto(updatedStudent);
	}
	
	
	//enroll student with course
	public String enrollStudent(int studentId, int courseId) {
		Student student = studentRepo.findById(studentId).orElseThrow(()-> new ResourceNotFoundException("Student "+studentId+" not found"));
		Course course = courseRepo.findById(courseId).orElseThrow(()-> new ResourceNotFoundException("Course "+courseId+" not found"));
		student.getCourses().add(course);
		studentRepo.save(student);
		return "Student with ID " + studentId + " enrolled in Course with Id "+courseId+" successfully.";
		
	}

	
	//delete student
		public String deleteStudent(Integer studentId) {
	        if (studentRepo.existsById(studentId)) {
	            studentRepo.deleteById(studentId);
	            return "Student with ID " + studentId + " deleted successfully.";
	        } else {
	            ;
	        	throw new ResourceNotFoundException("Student with ID " + studentId + " not found.");
	        }
	    }
		
		
		public StudentDto studentToDto(Student student) {
			StudentDto studentDto = modelMapper.map(student, StudentDto.class);
			return studentDto;
		}
		
		public Student dtoToStudent(StudentDto studentDto) {
			Student student = modelMapper.map(studentDto, Student.class);
			return student;
		}
	}


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
import com.project.SMS.entity.Admin;
import com.project.SMS.entity.Student;
import com.project.SMS.payloads.StudentDto;
import com.project.SMS.service.StudentService;


@RestController
@RequestMapping("/student")
public class StudentController {
	
	@Autowired
	private StudentService studentService;
	
	
	// create a student by a student
	@PostMapping("/create")
	private ResponseEntity<StudentDto> createStudent(@RequestBody StudentDto studentDto) {
		StudentDto createdStudent = studentService.createStudent(studentDto);
		return ResponseEntity.ok(createdStudent);
	}
	
	
	//get all student
    @GetMapping("/all")
    public ResponseEntity<List<StudentDto>> getAllStudents() {
        List<StudentDto> allStudents = studentService.findAllStudent();
        return new ResponseEntity<>(allStudents, HttpStatus.OK);
    }
	


    // get student by studentId
    @GetMapping("/studentId/{studentId}")
    public ResponseEntity<StudentDto> getStudentById(@PathVariable Integer studentId) {
        StudentDto student = studentService.getStudentById(studentId);
        
        return new ResponseEntity<>(student, HttpStatus.OK);
        
    }
    
    
    //update student
	@PutMapping("/update/{studentId}")
	private ResponseEntity<StudentDto> updateStudent(@RequestBody StudentDto studentDto, @PathVariable int studentId){
		StudentDto updateStudent = studentService.updateStudent(studentDto,studentId );
		return ResponseEntity.ok(updateStudent);
	}
	
	// enroll a course by a student
	@PutMapping("/enroll/{studentId}/{courseId}")
	private ResponseEntity<String> addCourse(@PathVariable int studentId,@PathVariable int courseId){
		String enrolledStatus = studentService.enrollStudent(studentId,courseId);
		return new ResponseEntity<>(enrolledStatus,HttpStatus.OK);
	}
	
	//delete student(by student)
		@DeleteMapping("/delete/{studentId}")
		public ResponseEntity<String> deleteStudent(@PathVariable Integer studentId) {
			String status = studentService.deleteStudent(studentId);

			return new ResponseEntity<String>(status, HttpStatus.OK);
		}
	
}

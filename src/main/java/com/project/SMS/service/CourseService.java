package com.project.SMS.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.SMS.entity.Admin;
import com.project.SMS.entity.Course;
import com.project.SMS.entity.Student;
import com.project.SMS.exception.InvalidDataException;
import com.project.SMS.exception.ResourceNotFoundException;
import com.project.SMS.payloads.CourseDto;
import com.project.SMS.repo.AdminRepo;
import com.project.SMS.repo.CourseRepo;

@Service
public class CourseService {

	@Autowired
	private CourseRepo courseRepo;
	
	@Autowired
	private AdminRepo adminRepo;

	
	
	public CourseDto createCourse(CourseDto courseDto, int teacherId) {
		Admin admin = adminRepo.findById(teacherId).orElseThrow(() -> new ResourceNotFoundException("Admin with ID " + teacherId + " not found"));
		Course course = this.dtoToCourse(courseDto);
		course.setAdmin(admin);
		Course newCourse = courseRepo.save(course);
		return this.courseToDto(newCourse);
		
	}
	
	public Course dtoToCourse(CourseDto courseDto) {
		Course course = new Course();
		course.setName(courseDto.getName());
		course.setDescription(courseDto.getDescription());
		return course;
	}
	
	public CourseDto courseToDto(Course course) {
		CourseDto courseDto = new CourseDto();
		courseDto.setId(course.getId());
		courseDto.setName(course.getName());
		courseDto.setDescription(course.getDescription());
		courseDto.setFirstName(course.getAdmin().getFirstName());
		courseDto.setLastName(course.getAdmin().getLastName());
		return courseDto;
	}

	public List<CourseDto> findAllCourse() {
		List<Course> allCourses = courseRepo.findAll();
		
		List<CourseDto> allCourseDto = allCourses.stream().map(course->this.courseToDto(course)).collect(Collectors.toList());
		return allCourseDto;
	}

	public CourseDto updateCourse(CourseDto courseDto, int courseId, int teacherId) {
		
        if (courseDto == null) {
            throw new InvalidDataException("Invalid course data provided");
        }
		if(courseRepo.existsById(courseId)) 
		{
			Course existingCourse = courseRepo.findById(courseId).get();
			if(teacherId == existingCourse.getAdmin().getId()) 
			{
			existingCourse.setName(courseDto.getName());
			existingCourse.setDescription(courseDto.getDescription());
			existingCourse.setAdmin(adminRepo.findById(teacherId).get());
			
			 Course updatedCourse = courseRepo.save(existingCourse);
			 
			 return this.courseToDto(updatedCourse);
			}
		
			else 
			{
				throw new InvalidDataException("Invalid course and admin data provided");
			
			}	
		}
		//Course newCourse = courseRepo.save(this.dtoToCourse(courseDto));
		
		CourseDto newCourse = this.createCourse(courseDto, teacherId);
		
		return newCourse;
	}

	
// delete course	
	public boolean deleteCourse(int id) {
	    if(courseRepo.existsById(id)) {
	        Course course = courseRepo.findById(id).get();
	        for(Student student : course.getStudents()){
	            student.getCourses().remove(course);
	        }
	        course.getStudents().clear();
	        //courseRepo.save(course);
	        courseRepo.deleteById(id);
	        return true;
	    } else {
	        throw new ResourceNotFoundException("Course with ID " + id + " not found.");
	    }
	}
	
	
	
}

package com.project.SMS.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.SMS.entity.Course;
import com.project.SMS.entity.Review;
import com.project.SMS.entity.Student;
import com.project.SMS.exception.InvalidDataException;
import com.project.SMS.exception.ResourceNotFoundException;
import com.project.SMS.payloads.ReviewDto;
import com.project.SMS.repo.CourseRepo;
import com.project.SMS.repo.ReviewRepo;
import com.project.SMS.repo.StudentRepo;

@Service
public class ReviewService {

	@Autowired
	private ReviewRepo reviewRepo;

	@Autowired
	private CourseRepo courseRepo;

	@Autowired
	private StudentRepo studentRepo;

	//@Transactional
	public ReviewDto addReview(ReviewDto reviewDto, int courseId, int studentId) {

		if (reviewDto == null) {
			throw new InvalidDataException("Review cannot be null");

		}
		Course course = courseRepo.findById(courseId)
				.orElseThrow(() -> new ResourceNotFoundException("Course with ID " + courseId + " not found"));
		;
		Student student = studentRepo.findById(studentId)
				.orElseThrow(() -> new ResourceNotFoundException("Student with ID " + studentId + " not found"));
		;
		Review review = this.dtoToReview(reviewDto);
		review.setStudent(student);
		review.setCourse(course);
		Review newReview = reviewRepo.save(review);

		return this.reviewToDto(newReview);
	}

	public Review dtoToReview(ReviewDto reviewDto) {
		Review review = new Review();
		review.setRating(reviewDto.getRating());
		review.setDescription(reviewDto.getDescription());
		return review;
	}

	public ReviewDto reviewToDto(Review review) {
		ReviewDto reviewDto = new ReviewDto();
		reviewDto.setId(review.getId());
		reviewDto.setRating(review.getRating());
		reviewDto.setDescription(review.getDescription());
		reviewDto.setName(review.getCourse().getName());
		reviewDto.setFirstName(review.getStudent().getFirstName());
		reviewDto.setLastName(review.getStudent().getLastName());
		return reviewDto;
	}

	public ReviewDto updateReview(int ReviewId, ReviewDto reviewDto) {
		Review existingReview = reviewRepo.findById(ReviewId)
				.orElseThrow(() -> new RuntimeException("Review not found"));
		Review review = this.dtoToReview(reviewDto);
		existingReview.setRating(review.getRating());
		existingReview.setDescription(review.getDescription());
		existingReview.setStudent(existingReview.getStudent());
		existingReview.setCourse(existingReview.getCourse());

		Review updatedReview = reviewRepo.save(existingReview);
		return this.reviewToDto(updatedReview);
	}

	public boolean deleteReview(int reviewId) {
		if (reviewRepo.existsById(reviewId)) {
			reviewRepo.deleteById(reviewId);
			return true;
		}

		else {
			throw new ResourceNotFoundException("Review with ID " + reviewId + " not found.");
		}
	}
}

package com.project.SMS.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.SMS.payloads.ReviewDto;
import com.project.SMS.service.ReviewService;

@RestController
@RequestMapping("/review")
public class ReviewController {
	@Autowired
	private ReviewService reviewService;

		
	    @PostMapping("/add/{courseId}/{stuentId}")
	    public ResponseEntity<ReviewDto> addReview(@RequestBody ReviewDto reviewDto, @PathVariable int courseId, @PathVariable int stuentId) {
	        ReviewDto addedReview = reviewService.addReview(reviewDto,courseId,stuentId);
	        return new ResponseEntity<>(addedReview, HttpStatus.OK);
	    }
	   
	    @PutMapping("/update/{ReviewId}")
	    public ResponseEntity<ReviewDto> updateReview(@PathVariable int ReviewId, @RequestBody ReviewDto reviewDto) {
	        
	        ReviewDto updateReview = reviewService.updateReview(ReviewId, reviewDto);
	        return new ResponseEntity<>(updateReview, HttpStatus.OK);
	    }
	    
	    @DeleteMapping("/delete/{reviewId}")
	    public ResponseEntity<String> deleteReview(@PathVariable int reviewId) {
	        
	        boolean isDeleted = reviewService.deleteReview(reviewId);
			if(isDeleted) {
				return new ResponseEntity<String>("Review deleted successfully",HttpStatus.OK);
			}
			else {
				return new ResponseEntity<String>("Review not deleted",HttpStatus.NOT_FOUND);
			}
	       
	    }
	}

package com.project.SMS.payloads;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDto {
	private int id;
	private String rating;
	private String description;
	private String name;
	private String firstName;
	private String lastName;
	
}

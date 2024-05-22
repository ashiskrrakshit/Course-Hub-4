package com.project.SMS.payloads;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class StudentDto {
	
	private String firstName;
	private String lastName;
	private String email;
	private String mobileNumber;
	
}

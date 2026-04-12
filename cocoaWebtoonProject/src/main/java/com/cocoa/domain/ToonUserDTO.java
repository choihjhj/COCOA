package com.cocoa.domain;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ToonUserDTO {
	private String userId;
	private String pwd;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date birthday;
	private String userName;
	private String phone;
	private int cocoa;
	

}

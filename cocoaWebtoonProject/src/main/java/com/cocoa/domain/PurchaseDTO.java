package com.cocoa.domain;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class PurchaseDTO {
	private int purchaseId;
	private String userId;
	private int epId;
	@DateTimeFormat(pattern = "yyyy-MM-dd")	
	private Date purchaseDate;

}

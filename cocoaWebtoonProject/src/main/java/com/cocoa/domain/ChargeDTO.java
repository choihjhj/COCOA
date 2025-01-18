package com.cocoa.domain;

import java.util.Date;

import lombok.Data;
@Data
public class ChargeDTO {
	private int chargeId;
	private String userId;
	private int creditNumber;
	private int creditPwd;
	private int amount;
	private Date chargeDate;

}
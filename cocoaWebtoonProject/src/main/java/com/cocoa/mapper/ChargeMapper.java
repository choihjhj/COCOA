package com.cocoa.mapper;
import com.cocoa.domain.ChargeDTO;

public interface ChargeMapper {
	public int insert(ChargeDTO charge);
	public int update(ChargeDTO charge);

}
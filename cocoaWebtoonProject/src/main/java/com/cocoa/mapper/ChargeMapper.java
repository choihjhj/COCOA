package com.cocoa.mapper;
import com.cocoa.domain.ChargeDTO;

public interface ChargeMapper {
	public int insert(ChargeDTO charge);
	public boolean update(ChargeDTO charge);

}
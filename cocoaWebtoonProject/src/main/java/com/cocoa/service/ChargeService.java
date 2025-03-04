package com.cocoa.service;

import com.cocoa.domain.ChargeDTO;

public interface ChargeService {
	public int charge(ChargeDTO charge); //charge insert 이후 toonuser cocoa 증가 업데이트

}

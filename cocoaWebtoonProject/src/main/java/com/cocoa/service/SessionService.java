package com.cocoa.service;

import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;
import com.cocoa.domain.ToonUserDTO;

@Service
public class SessionService {
	// 세션에서 로그인된 사용자 정보 가져오기
    public ToonUserDTO getLoggedInUser(HttpServletRequest request) {
        return (ToonUserDTO) request.getSession().getAttribute("ToonUserDTO");
    }

}

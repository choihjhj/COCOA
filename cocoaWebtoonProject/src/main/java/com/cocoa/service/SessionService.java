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
    
    //중앙화된 세션 관리(세션에 저장된 데이터 삭제 관리 = 메모리 누수 관련)
    //get /toondetail, post /purchase 에서 사용
    public void clearPurchaseSessionData(HttpServletRequest request) {
        request.getSession().removeAttribute("toonId");
        request.getSession().removeAttribute("epId");
    }


}

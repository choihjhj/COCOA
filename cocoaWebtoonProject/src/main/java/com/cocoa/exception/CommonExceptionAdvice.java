package com.cocoa.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import lombok.extern.log4j.Log4j;

/*------------- servlet-context.xml파일에 이거 추가하기 <context:component-scan base-package="com.cocoa.exception" /> -----*/

@ControllerAdvice
@Log4j
public class CommonExceptionAdvice {
	
	@ExceptionHandler(Exception.class)
	public  ResponseEntity<String> except(Exception e, Model model) 
	{			
		log.error("오류 핸들러 : "+e.getMessage());
		model.addAttribute("exception",e); // view로 전달할 때를 위해 넣어두기
		return ResponseEntity.badRequest().body("Invalid request data : " + e.getMessage()); //바로 클라이언트로 return
		
	}
	
}

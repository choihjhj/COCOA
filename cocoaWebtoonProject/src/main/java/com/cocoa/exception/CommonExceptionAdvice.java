package com.cocoa.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.extern.log4j.Log4j;

/*------------- servlet-context.xml파일에 이거 추가하기 <context:component-scan base-package="com.cocoa.exception" /> -----*/

@ControllerAdvice
@Log4j
public class CommonExceptionAdvice {
	
	//모든 예외 처리
	@ExceptionHandler(Exception.class)
	public  ResponseEntity<String> except(Exception e, Model model) 
	{			
		log.error("오류 발생: " + e.getMessage(), e);
		
		// 예외 발생 시 Model에 에러 메시지 추가
        model.addAttribute("exception", "서버에서 문제가 발생했습니다. 다시 시도해 주세요.");
		return ResponseEntity.badRequest().body("Invalid request data : " + e.getMessage()); //바로 클라이언트로 return

	}
	
	// MissingServletRequestParameterException 처리
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public String handleMissingParam(MissingServletRequestParameterException e, RedirectAttributes rttr) {
        log.error("필수 파라미터 누락: " + e.getParameterName(), e);

        rttr.addFlashAttribute("errorMessage", "필수 파라미터 '" + e.getParameterName() + "'가 누락되었습니다. 다시 시도해 주세요.");
        return "redirect:/errorPage"; 
    }

	// IllegalArgumentException 처리
	@ExceptionHandler(IllegalArgumentException.class)
	public String handleIllegalArgumentException(IllegalArgumentException e,RedirectAttributes rttr) {
		log.error("잘못된 인자: " + e.getMessage(), e);

		rttr.addFlashAttribute("errorMessage", "잘못된 인자가 포함되었습니다. 다시 시도해 주세요.");
		return "redirect:/errorPage"; 
	}

	// NullPointerException 처리
	@ExceptionHandler(NullPointerException.class)
	public String handleNullPointerException(NullPointerException e,RedirectAttributes rttr) {
		log.error("NullPointerException 발생: " + e.getMessage(), e);

		rttr.addFlashAttribute("errorMessage", "서버에서 NullPointerException이 발생했습니다. 문제를 해결하기 위해 관리자에게 문의해주세요.");
		return "redirect:/errorPage"; 
	}
}

package com.backoffice.moffice.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(NoHandlerFoundException.class)
    public ModelAndView handle404(NoHandlerFoundException e) {
        log.error("페이지를 찾을 수 없습니다:{}", e.getMessage());

        ModelAndView mv = new ModelAndView("error/404");
        mv.addObject("errorCode", "404");
        mv.addObject("errorMessage", e.getMessage());
        mv.setStatus(HttpStatus.NOT_FOUND);

        return mv;
    }

    @ExceptionHandler(HttpClientErrorException.Unauthorized.class)
    public ModelAndView handleUnauthorized(HttpClientErrorException.Unauthorized e) {
        log.error("인증되지 않은 요청입니다.:{}", e.getMessage());

        ModelAndView mv = new ModelAndView("error/401");
        mv.addObject("errorCode", "401");
        mv.addObject("errorMessage", e.getMessage());
        mv.setStatus(HttpStatus.UNAUTHORIZED);

        return mv;
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView handleAllException(Exception e) {

        ModelAndView mv = new ModelAndView("error/500");
        mv.addObject("errorCode", "500");
        mv.addObject("errorMessage", e.getMessage());
        mv.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);

        return mv;
    }

}

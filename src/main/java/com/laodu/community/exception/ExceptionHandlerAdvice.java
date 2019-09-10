package com.laodu.community.exception;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice(basePackageClasses = ExceptionHandlerAdvice.class)
public class ExceptionHandlerAdvice {

    @ExceptionHandler(Exception.class)
    ModelAndView handle(HttpServletRequest request, Throwable e, Model model) {
        if (e instanceof CustomizeException){
            model.addAttribute("message", e.getMessage());

        }else{
            model.addAttribute("message", "unknown error, please contact laodu");
        }

        return new ModelAndView("error");
    }
}

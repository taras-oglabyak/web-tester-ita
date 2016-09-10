package com.softserve.webtester.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.softserve.webtester.editor.LabelEditor;
import com.softserve.webtester.editor.RequestEditor;
import com.softserve.webtester.exception.ResourceNotFoundException;

/**
 * ControllerAdvice class defines objects binding in all Controller's RequestMapping methods.
 * 
 * @author Taras Oglabyak
 *
 */
@ControllerAdvice
public class GeneralController {
    
    private static final String ERROR_MASSAGE = "message";

    @Autowired
    private LabelEditor labelEditor;

    @Autowired
    private RequestEditor requestEditor;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(List.class, "labels", labelEditor);
        binder.registerCustomEditor(List.class, "requests", requestEditor);
    }

    /**
     * Handles ResourceNotFoundException exception.
     * 
     * @param e instance of exception
     * @param model {@link Model} object
     * @return name of error view
     */
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ResourceNotFoundException.class)
    public String error404(ResourceNotFoundException e, Model model) {
        model.addAttribute(ERROR_MASSAGE, e.getMessage());
        return "error";
    }
    
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(RuntimeException.class)
    public String error500(RuntimeException e, Model model){
        model.addAttribute(ERROR_MASSAGE, e.getMessage());
        return "error";
    }
    
}
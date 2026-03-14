package com.example.apontamento.controller.exceptions;

import org.springframework.ui.Model;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class GlobalExceptionsHandler {

    // Valida se as informações inseridas pelo user estão corretas, se estiverem erradas vai redirecionar ao formulario
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public String handleValidationExceptions(MethodArgumentNotValidException ex, RedirectAttributes attributes){

        attributes.addFlashAttribute("error", "verifique se todos os campos foram preenchidos " +
                "corretamente.");
        return "redirect:/index";
    }

    // valida se houve algum erro no banco de dados
    @ExceptionHandler(Exception.class)
    public String handleGeneralException(Exception ex, Model model){
        model.addAttribute("error","ocorreu um erro interno. Tente novamente");
        ex.printStackTrace();
        return "error-page";
    }
}

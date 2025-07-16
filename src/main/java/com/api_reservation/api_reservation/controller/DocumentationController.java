package com.api_reservation.api_reservation.controller;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

@Hidden
@Controller
@RequestMapping
public class DocumentationController {

  @GetMapping("/")
  public void redirectToDocumentation(HttpServletResponse response){
    try{
      response.sendRedirect("/swagger-ui.html");
    }catch (IOException e){
      StringBuilder stringBuffer = new StringBuilder("UNEXPECTED ERROR");
      if (e.getMessage() != null) stringBuffer.append(e.getMessage());
    }
  }

}

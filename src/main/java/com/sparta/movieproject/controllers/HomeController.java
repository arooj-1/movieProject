package com.sparta.movieproject.controllers;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Hidden
public class HomeController {
    @GetMapping("/")
    public ResponseEntity<Void> redirectToSwaggerUI() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/swagger-ui/index.html");
        return ResponseEntity.status(HttpStatus.FOUND).headers(headers).build();
    }
}

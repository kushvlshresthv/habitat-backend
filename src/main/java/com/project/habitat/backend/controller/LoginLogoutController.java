package com.project.habitat.backend.controller;

import com.project.habitat.backend.response.ApiResponse;
import com.project.habitat.backend.response.ResponseMessage;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
public class LoginLogoutController {

    @GetMapping("login")
    public ResponseEntity<ApiResponse> tryLogin(HttpSession session) {
        return new ResponseEntity<ApiResponse>(new ApiResponse(ResponseMessage.LOGIN_SUCCESSFUL), HttpStatus.OK);
    }
}

package com.project.habitat.backend.controller;

import com.project.habitat.backend.response.ApiResponse;
import com.project.habitat.backend.response.ResponseMessage;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class LoginLogoutController {
    @GetMapping("login")
    public ResponseEntity<ApiResponse> tryLogin(HttpSession session) {
        return new ResponseEntity<ApiResponse>(new ApiResponse(ResponseMessage.LOGIN_SUCCESSFUL), HttpStatus.OK);
    }

    @GetMapping("/isAuthenticated")
    public ResponseEntity<ApiResponse> isAuthenticated(Authentication authentication) {

        if (authentication != null && !authentication.getName().equals("anonymous") && !authentication.getName().equals("anonymousUser")) {
            if (authentication.isAuthenticated()) {
                log.info("The user: {} is authenticated", authentication.getName());
                return ResponseEntity.ok(new ApiResponse("true"));
            }
        }
        return new ResponseEntity<ApiResponse>(new ApiResponse("false"), HttpStatus.UNAUTHORIZED);
    }
}

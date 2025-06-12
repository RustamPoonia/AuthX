package com.authx.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.authx.services.EmailService;
import com.authx.services.ProfileService;
import com.authx.userdto.RegisterRequest;
import com.authx.userdto.RegisterResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ProfileController {

   private final ProfileService profileService;

   private final EmailService emailService;


    @PostMapping("/register")
    public RegisterResponse register(@Valid @RequestBody RegisterRequest request)
    {
        RegisterResponse response = profileService.createProfile(request);
           emailService.sendWelcomeEmail(response.getEmail(), response.getName());
        return response;
    }
    
    // @GetMapping("test")
    // public String test(){
    //     return "Auth is working";
    // }

    @GetMapping("/profile")
    public RegisterResponse getpofile(@CurrentSecurityContext(expression = "authentication?.name") String email){
                return profileService.getProfile(email);

    }

}

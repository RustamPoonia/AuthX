package com.authx.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.authx.services.ProfileService;
import com.authx.services.ProfileServiceImpl;
import com.authx.userdto.RegisterRequest;
import com.authx.userdto.Response;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1.0")
// @RequiredArgsConstructor
public class ProfileController {

   private final ProfileService profileService;

    @Autowired
    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @PostMapping("/register")
    public Response register(@Valid @RequestBody RegisterRequest request)
    {
        Response response = profileService.createProfile(request);

        return response;
    }
}

package com.authx.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.authx.userdto.AuthRequest;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request){

        try{
            authenticate(request.getEmail(),request.getPassword());
            
        }
        catch(BadCredentialsException e){
            Map<String ,Object> error= new HashMap<>();
            error.put("error", true);
            error.put("error", "Email or password is incorrect");   
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
        catch(DisabledException e){
            Map<String ,Object> error= new HashMap<>();
            error.put("error", true);
            error.put("error", "Account is disabled");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
            
        }
        catch(Exception e){
            Map<String ,Object> error= new HashMap<>();
            error.put("error", true);
            error.put("error", "Authentication failed");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
        }

        

    }

    private void authenticate(String email, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
    }
}

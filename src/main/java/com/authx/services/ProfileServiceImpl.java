package com.authx.services;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.authx.entity.UserEntity;
import com.authx.repository.UserRepository;
import com.authx.userdto.RegisterRequest;
import com.authx.userdto.Response;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final UserRepository userRepo;
    @Override
    public Response createProfile(RegisterRequest request) {
          UserEntity newprofile = convertToUserEntity(request);

          if(!userRepo.existsByEmail(request.getEmail())){

             newprofile = userRepo.save(newprofile);

             return convertToProfileResponse(newprofile);
          }

          throw new ResponseStatusException(HttpStatus.CONFLICT,"Email already exists");
    }

    private Response convertToProfileResponse(UserEntity newprofile) {
           return Response.builder()
                        .email(newprofile.getEmail())
                        .isAccountVerified(false)
                        .name(newprofile.getName())
                        .userId(newprofile.getUserId())
                        .build();
    }

    private UserEntity convertToUserEntity(RegisterRequest request) {
       return UserEntity.builder()
                    .userId(UUID.randomUUID().toString())
                    .email(request.getEmail())
                    .name(request.getName())
                    .password(request.getPassword())
                    .isAccountVerified(false)
                    .verifyOtp(null)
                    .verifyOtpExpired(0L)
                    .resetPassword(null)
                    .resetOtpExpiredAt(0L)
                    .build();
    }

}

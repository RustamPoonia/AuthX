package com.authx.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.authx.mapper.UserMapper;
import com.authx.repository.UserRepository;
import com.authx.userdto.AdminResponse;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class AdminServiceimpl implements AdminService{

    private final UserRepository userRepo;
    
    @Override
    public List<AdminResponse> getAllUsers() {
          return userRepo.findAll().stream()
                            .map(UserMapper::toDTO)
                            .toList();    
    }

}

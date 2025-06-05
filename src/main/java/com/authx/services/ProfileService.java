package com.authx.services;

import com.authx.userdto.RegisterRequest;
import com.authx.userdto.Response;

public interface ProfileService {

   Response createProfile(RegisterRequest request);
}

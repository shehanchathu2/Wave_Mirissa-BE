package com.wave.Mirissa.services;

import com.wave.Mirissa.dtos.SignupRequest;
import com.wave.Mirissa.dtos.UserDTO;

public interface AuthService {
    UserDTO createUser(SignupRequest signupRequest);
}

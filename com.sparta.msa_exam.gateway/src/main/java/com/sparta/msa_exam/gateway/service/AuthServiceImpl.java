package com.sparta.msa_exam.gateway.service;

import com.sparta.msa_exam.gateway.client.AuthClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthClient authClient;


    public Boolean verifyUser(String userId) {
        return authClient.verifyUser(userId);
    }
}

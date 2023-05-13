package com.backend.social.security.auth;


import com.backend.social.contant.Role;
import com.backend.social.contant.UserConst;
import com.backend.social.entity.Users;
import com.backend.social.exception.UserExistsException;
import com.backend.social.mail.token.ConfirmationToken;
import com.backend.social.repository.UsersRepo;
import com.backend.social.security.CustomUserDetails;
import com.backend.social.security.jwt.JwtService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Timestamp;

import static com.backend.social.contant.UserConst.STATUS_ACTIVE;

@Service
@RequiredArgsConstructor
public class AuthenticationService {


    private final PasswordEncoder passwordEncoder;

    private final UsersRepo usersRepo;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;


    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                      request.getEmail(),
                      request.getPassword()
                )
        );
        var user = usersRepo.findByEmail(request.getEmail())
                .orElseThrow();
        if(user.getLocked()){
            throw new LockedException("user is locked");
        }
        if(!user.getEnable()){
           throw new  LockedException("user is unable");
        }

       String jwt = jwtService.generateToken(CustomUserDetails.builder().user(user).build());
       return AuthenticationResponse.builder()
                .token(jwt)
                .build();
    }


}

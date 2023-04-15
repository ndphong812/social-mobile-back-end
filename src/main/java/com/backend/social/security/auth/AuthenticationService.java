package com.backend.social.security.auth;


import com.backend.social.contant.Role;
import com.backend.social.contant.UserConst;
import com.backend.social.entity.Users;
import com.backend.social.exception.UserExistsException;
import com.backend.social.repository.UsersRepo;
import com.backend.social.security.CustomUserDetails;
import com.backend.social.security.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
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

    public AuthenticationResponse register(RegisterRequest request) throws RuntimeException{

        if(usersRepo.findByEmail(request.getEmail()).isPresent()){
            throw new UserExistsException("email is exist");
        }else{
            if (usersRepo.findByPhone(request.getPhone()).isPresent()){
                throw new UserExistsException("phone is exist");
            }
            Users user = Users.builder()
                    .email(request.getEmail())
                    .phone(request.getPhone())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .fullName(request.getFullName())
                    .nickName(request.getNickName())
                    .avatar(request.getAvatar())
                    .dob(request.getDob())
                    .createdAt(new Timestamp(System.currentTimeMillis()))
                    .status(UserConst.STATUS_WAIT)
                    .isOnline(true)
                    .role(Role.USER)
                    .build();
            Users userSaved = usersRepo.save(user);
            String jwt = jwtService.generateToken(CustomUserDetails.builder().user(userSaved).build());
            return AuthenticationResponse.builder()
                    .token(jwt)
                    .build();
        }
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                      request.getEmail(),
                      request.getPassword()
                )
        );
        var user = usersRepo.findByEmail(request.getEmail())
                .orElseThrow();

       String jwt = jwtService.generateToken(CustomUserDetails.builder().user(user).build());
       return AuthenticationResponse.builder()
                .token(jwt)
                .build();
    }
}

package com.backend.social.security.auth;


import com.backend.social.contant.Role;
import com.backend.social.entity.Users;
import com.backend.social.exception.UserExistsException;
import com.backend.social.mail.token.ConfirmationToken;
import com.backend.social.mail.token.ConfirmationTokenService;
import com.backend.social.repository.UsersRepo;
import com.backend.social.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RegistrationService {

    private final PasswordEncoder passwordEncoder;

    private final UsersRepo usersRepo;

    private final ConfirmationTokenService confirmationTokenService;

    private final UserService userService;

    public String register(RegisterRequest request) throws RuntimeException{

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
                    .locked(Boolean.FALSE)
                    .enable(Boolean.FALSE)
                    .isOnline(true)
                    .role(Role.USER)
                    .build();
            Users userSaved = usersRepo.save(user);

            //SEND MAIL

            String token = UUID.randomUUID().toString();

            ConfirmationToken confirmationToken = new ConfirmationToken(
                    token,
                    LocalDateTime.now(),
                    LocalDateTime.now().plusMinutes(15),
                    user
            );
            System.out.println(confirmationToken.toString());

            confirmationTokenService.saveConfirmationToken(
                    confirmationToken);

            String link = "http://localhost:8080/api/v1/registration/confirm?token=" + token;

            return "register successfully. please check email to verify account!"+"\n"+link;

        }
    }


    @Transactional
    public String confirmToken(String token) {
        ConfirmationToken confirmationToken = confirmationTokenService
                .getToken(token)
                .orElseThrow(() ->
                        new IllegalStateException("token not found"));

        if (confirmationToken.getConfirmedAt() != null) {
            throw new IllegalStateException("email already confirmed");
        }

        LocalDateTime expiredAt = confirmationToken.getExpiresAt();

        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("token expired");
        }

        confirmationTokenService.setConfirmedAt(token);
        userService.enableUser(
                confirmationToken.getUsers().getEmail());
        return "confirmed";
    }
}

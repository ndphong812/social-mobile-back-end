package com.backend.social.security.registration;


import com.backend.social.contant.CommonConst;
import com.backend.social.contant.Role;
import com.backend.social.entity.Users;
import com.backend.social.exception.UserExistsException;
import com.backend.social.mail.EmailSender;
import com.backend.social.mail.token.ConfirmationToken;
import com.backend.social.mail.token.ConfirmationTokenService;
import com.backend.social.repository.UsersRepo;
import com.backend.social.service.UserService;
import jakarta.transaction.Transactional;
import jakarta.validation.Constraint;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RegistrationService {

    private final PasswordEncoder passwordEncoder;

    private final UsersRepo usersRepo;

    private final ConfirmationTokenService confirmationTokenService;

    private final UserService userService;

    private final EmailSender emailSender;

    @Transactional
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

            String link = CommonConst.HOST+ "api/v1/registration/confirm?token=" + token;

            emailSender.send(user.getEmail(), "CONFIRMATION EMAIL", buildEmail(request.getFullName(),link));

            return "register successfully. please check email to verify account!"+"\n";

        }
    }


    @Transactional
    public String confirmToken(String token) {
        ConfirmationToken confirmationToken = confirmationTokenService
                .getToken(token)
                .orElseThrow(() ->
                        new IllegalStateException("token not found"));

        Boolean isEnabled = confirmationToken.getUsers().getEnable();
        if(isEnabled){
            throw new IllegalStateException("email already confirmed");
        }

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

    public String resendConfirmation(String email){
        //SEND MAIL

        Optional<Users> user = usersRepo.findByEmail(email);

        String token = UUID.randomUUID().toString();

        if(!user.isPresent()){
            throw new UsernameNotFoundException("email not found");
        }

        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                user.get()
        );
        System.out.println(confirmationToken.toString());

        confirmationTokenService.saveConfirmationToken(
                confirmationToken);

        String link = CommonConst.HOST+ "api/v1/registration/confirm?token=" + token;

        emailSender.send(user.get().getEmail(), "CONFIRMATION EMAIL", buildEmail(user.get().getFullName(),link));

        return "please check email to verify account!";
    }

    private String buildEmail(String name,String link){
        String text= "<div style=\"display: flex;width: 600px;height: 400px;color: black; background-color: #F3F3F3;justify-content: center;align-items: center;padding: 30px;margin: 0 auto\" id=\"container\">\n" +
                "        <div style=\"display: inline-block;justify-content: center;align-items: center;min-width: 500px;height: 300px;padding: 50px;background-color: white\" id=\"content\">\n" +
                "            <h1 style=\"color: black;\">CONFIRMATION</h1>\n" +
                "            <p>Dear <strong>"+name+"</strong>,</p>\n" +
                "            <p>To be sure your account is correct, please click the link below to complete.</p>\n" +
                "            <a style=\"display: flex;width: 200px; height: 50px; border-radius: 4px; background-color: #3F99E8;color: white;justify-content: center;align-items: center;cursor: pointer;font-weight: 700;text-decoration: none;margin: 0 auto\" \n" +
                "            href=\""+link+"\" > <div style=\"margin: auto;\" class=\"\">Confirm registration</div></a>          \n" +
                "            <p>Link will expire in 15 minutes.</p>\n" +
                "            <p>If you did not create this account, please ignore this email.</p>\n" +
                "            <p>Regards,</p> \n" +
                "            <p>The HCMUS Team</p>     \n" +
                "        </div>\n" +
                "    </div>";
        return text;
    }


}

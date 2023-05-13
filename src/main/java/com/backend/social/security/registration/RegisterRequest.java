package com.backend.social.security.registration;


import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Date;
import java.sql.Timestamp;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    @Email(message = "email is valid")
    @NotNull
    private String email;

    @NotNull
    @Size(min = 6,message = "The greatest length of the name is 6")
    private String password;

    @Nullable
    private  String phone;

    @NotNull
    private  String fullName;

    @Nullable
    private String nickName;

    private String avatar;

    @Past
    private Date dob;

    @CreationTimestamp
    private Timestamp createdAt;

    private String status;

    @NotNull
    private Boolean isOnline;

    @NotNull
    private String role;
}

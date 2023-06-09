package com.backend.social.security;

import com.backend.social.contant.UserConst;
import com.backend.social.entity.Users;
import com.backend.social.exception.UnverifiedUserException;
import com.backend.social.repository.UsersRepo;
import jakarta.validation.constraints.Null;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class CustomUserdetailsService implements UserDetailsService {

    private final UsersRepo usersRepo;
    @Override
    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<Users> user=usersRepo.findByEmail(username);

        if(!user.isPresent()){
            // UsernameNotFoundException
            throw new  UsernameNotFoundException("user not found");
        }
        return CustomUserDetails.builder()
            .user(user.get())
            .build();

    }
}

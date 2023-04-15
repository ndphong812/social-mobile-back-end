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


@Service
@RequiredArgsConstructor
public class CustomUserdetailsService implements UserDetailsService {

    private final UsersRepo usersRepo;
    @Override
    public CustomUserDetails loadUserByUsername(String username) throws RuntimeException {

        Users user=usersRepo.findByEmail(username).get();

        if(user == null){
            throw new  UsernameNotFoundException("User not found");
        }else if (user.getStatus().replace(" ","").equals(UserConst.STATUS_LOCK)){
            throw new LockedException("User is locked");
        }else if(user.getStatus().replace(" ","").equals(UserConst.STATUS_WAIT)){
            throw new UnverifiedUserException("User is unverified");
        } else{
            return CustomUserDetails.builder()
                .user(usersRepo.findByEmail(username).get())
                .build();
        }
    }
}

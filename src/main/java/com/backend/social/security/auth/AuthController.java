package com.backend.social.security.auth;



import com.backend.social.exception.UnverifiedUserException;
import com.backend.social.exception.UserExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/auth/")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService service;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request
    ){
        try{
            System.out.println(request.toString());
            return ResponseEntity.ok(service.register(request));
        }
        catch (UserExistsException e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ){
        try {
            return ResponseEntity.ok(service.authenticate(request));
        }catch (Exception e){

            if(e.getMessage().equals("User is locked")){
                System.out.println(e.getMessage());
                return ResponseEntity.status(HttpStatus.LOCKED).build();
            }else if (e.getMessage().equals("User is unverified")){
                System.out.println(e.getMessage());
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }else{
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }


        }

    }
}

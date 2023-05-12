package com.backend.social.security.auth;


import com.backend.social.exception.UserExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/registration")
@RequiredArgsConstructor
public class RegisterController {

    private final RegistrationService registrationService;

    @PostMapping
    public ResponseEntity<?> register(
            @RequestBody RegisterRequest request
    ){
        try{
            System.out.println(request.toString());
            return ResponseEntity.status(HttpStatus.CREATED).body(registrationService.register(request));
        }
        catch (UserExistsException e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @GetMapping(path = "confirm")
    public String confirm(@RequestParam("token") String token) {
        return registrationService.confirmToken(token);
    }
}

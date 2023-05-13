package com.backend.social.security.registration;


import com.backend.social.exception.UserExistsException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
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
        }catch (ConstraintViolationException e){
            String constraintViolation = "Field is incorrect: \n";
            if(request.getPassword().length()<6){
                constraintViolation+="password must be greater than 6 char\n";
            }
            for(ConstraintViolation ex : e.getConstraintViolations()){
                constraintViolation+=ex.getMessage()+"\n";
            }

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(constraintViolation);
        }
    }

    @PostMapping ("/resend")
    public String sendConfirmationToken(@RequestBody String request){
        try {
            return registrationService.resendConfirmation(request);
        }catch (UserExistsException e){
            e.printStackTrace();
            return "email not found";
        }
    }

    @GetMapping(path = "confirm")
    public String confirm(@RequestParam("token") String token) {
        try{

        return registrationService.confirmToken(token);
        }catch(IllegalStateException e){
            e.printStackTrace();
            return e.getMessage();
        }
    }


}

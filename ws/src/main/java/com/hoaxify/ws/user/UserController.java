package com.hoaxify.ws.user;

import com.hoaxify.ws.error.ApiError;
import com.hoaxify.ws.shared.GenericMessage;
import com.hoaxify.ws.user.dto.UserCreate;
import com.hoaxify.ws.user.exception.NotUniqueEmailException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/api/v1/users")
    GenericMessage createUser(@Valid @RequestBody UserCreate user){
        System.err.println("-------" + LocaleContextHolder.getLocale().getLanguage());
        userService.save(user.toUser());
        return new GenericMessage("User is created");
    }

    @PatchMapping("/api/v1/users/{token}/active")
    GenericMessage activateUser(@PathVariable String token){
        userService.activateUser(token);
        String message = "User is activated";
        return new GenericMessage(message);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<ApiError> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        ApiError apiError = new ApiError();
        apiError.setPath("/api/v1/users");
        apiError.setMessage("Validation Error");
        apiError.setStatus(400);
       // Map<String, String> validationErrors = new HashMap<>();
       // for (FieldError fieldError : exception.getBindingResult().getFieldErrors()) {
       //     validationErrors.put(fieldError.getField(), fieldError.getDefaultMessage());
       // }

        //üstteki yorum satırındaki kısım ile alttaki satır aynı işi yapıyor

        var validationErrors = exception.getBindingResult().getFieldErrors().stream().collect(Collectors.toMap
                (FieldError::getField, FieldError::getDefaultMessage, (existing, replacing) -> existing));

        apiError.setValidationErrors(validationErrors);
        return ResponseEntity.badRequest().body(apiError);
    }

    @ExceptionHandler(NotUniqueEmailException.class)
    ResponseEntity<ApiError> handleNotUniqueEmailEx(NotUniqueEmailException exception) {
        ApiError apiError = new ApiError();
        apiError.setPath("/api/v1/users");
        apiError.setMessage("Validation Error");
        apiError.setStatus(400);
        Map<String, String> validationErrors = new HashMap<>();
        validationErrors.put("email", "E-mail is use");
        apiError.setValidationErrors(validationErrors);
        return ResponseEntity.badRequest().body(apiError);
    }



}

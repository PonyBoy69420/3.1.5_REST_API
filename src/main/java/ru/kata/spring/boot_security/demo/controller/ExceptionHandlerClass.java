package ru.kata.spring.boot_security.demo.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.kata.spring.boot_security.demo.util.EmailDuplicateException;
import ru.kata.spring.boot_security.demo.util.UserErrorResponse;

@Slf4j
@RestControllerAdvice
public class ExceptionHandlerClass {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<UserErrorResponse> handlerInvalid(MethodArgumentNotValidException e){
        UserErrorResponse response = new UserErrorResponse(e.getFieldError().getDefaultMessage(),System.currentTimeMillis());
        System.out.println("Vrode rabotaet");
        log.error(e.getFieldError().getDefaultMessage());
        return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EmailDuplicateException.class)
    public ResponseEntity<UserErrorResponse> emailDuplicate(){
        UserErrorResponse error = new UserErrorResponse("This email is already used",System.currentTimeMillis());
        log.error(error.getMessage());
        return new ResponseEntity<>(error,HttpStatus.BAD_REQUEST);
    }

//    @Override
//    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
//    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request){
//        Map<String,Object> errors = new HashMap<>();
//        ex.getBindingResult().getFieldErrors().forEach(e->{
//            e.getDefaultMessage();
//            errors.put(e.getField(),e.getDefaultMessage());
//        });
//        return new ResponseEntity<>(errors,headers,status);
//    }

//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    @ExceptionHandler(UserNotCreatedException.class)
//    public ResponseEntity<UserErrorResponse> handlerInvalidNotCreated(UserNotCreatedException e){
//        UserErrorResponse response = new UserErrorResponse(e.getMessage(),System.currentTimeMillis());
//        System.out.println("Nu da tochno rabotaet");
//        return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
//    }
}

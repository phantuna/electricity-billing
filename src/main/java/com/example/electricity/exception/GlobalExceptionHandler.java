package com.example.electricity.exception;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.example.electricity.dto.response.ApiResponse;

@ControllerAdvice
public class GlobalExceptionHandler {
   @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<?>> handleException(Exception e) {
        ApiResponse<Object> response = new ApiResponse<>();
        response.setCode(999);
        response.setMessage(e.getMessage());
        return ResponseEntity.internalServerError().body(response);
    }

    
    @ExceptionHandler(value = AppException.class)
        ResponseEntity<ApiResponse> handlingAppException(AppException e){
            ErrorCode errorCode = e.getErrorCode();

            ApiResponse response = new ApiResponse<>();
            response.setCode(errorCode.getCode());
            response.setMessage(errorCode.getMessage());
            return ResponseEntity.badRequest().body(response);
        }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<?>> handleValidation(MethodArgumentNotValidException e) {

        List<String> errors = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(err -> err.getField() + ": " + err.getDefaultMessage())
                .toList();

        ApiResponse<Object> response = new ApiResponse<>();
        response.setCode(1002);
        response.setMessage("Validation failed");
        response.setResult(errors);

        return ResponseEntity.badRequest().body(response);
    }

}

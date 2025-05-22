package com.hanan.tacosonline.web.api;

import com.hanan.tacosonline.exceptions.TacoNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.Instant;

@RestControllerAdvice
public class TacoControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {TacoNotFoundException.class})
    public ResponseEntity<ApiError> handleTacoNotFoundException(TacoNotFoundException tacoException, WebRequest request){
        ApiError apiError = createApiError(request);
        apiError.setStatus(HttpStatus.NOT_FOUND);
        apiError.setMessage(tacoException.getMessage());
        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }

    private ApiError createApiError(WebRequest request){
        ApiError apiError = new ApiError();
        String path = ((ServletWebRequest)request).getRequest().getRequestURI();
        apiError.setPath(path);
        apiError.setInstant(Instant.now());
        return apiError;
    }
}

package space.monntterro.springrestkit.core.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

public interface BaseKitExceptionHandler {

    @ExceptionHandler(ApiKitException.class)
    default ProblemDetail handleHttpServerErrorException(ApiKitException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(e.getStatusCode());
        problemDetail.setTitle(e.getStatusMessage());
        problemDetail.setDetail(e.getReason());
        return problemDetail;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    default ProblemDetail handleValidationExceptions(MethodArgumentNotValidException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setTitle("Validation failed");

        Map<String, String> errors = new HashMap<>();
        for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }

        problemDetail.setProperty("errors", errors);
        return problemDetail;
    }
}

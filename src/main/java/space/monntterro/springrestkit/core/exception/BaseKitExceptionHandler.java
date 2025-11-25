package space.monntterro.springrestkit.core.exception;

import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;

public interface BaseKitExceptionHandler {

    @ExceptionHandler(ApiException.class)
    default ProblemDetail handleHttpServerErrorException(ApiException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(e.getStatusCode());
        problemDetail.setTitle(e.getStatusMessage());
        problemDetail.setDetail(e.getReason());
        return problemDetail;
    }
}

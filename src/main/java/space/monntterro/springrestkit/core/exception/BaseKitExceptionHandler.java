package space.monntterro.springrestkit.core.exception;

import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;

public interface BaseKitExceptionHandler {

    @ExceptionHandler(ApiKitException.class)
    default ProblemDetail handleHttpServerErrorException(ApiKitException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(e.getStatusCode());
        problemDetail.setTitle(e.getStatusMessage());
        problemDetail.setDetail(e.getReason());
        return problemDetail;
    }
}

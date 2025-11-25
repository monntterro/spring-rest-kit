package space.monntterro.springrestkit.core.exception;

import org.springframework.http.HttpStatus;

public class NotFoundException extends ApiException {
    public NotFoundException(String reason) {
        super(HttpStatus.BAD_REQUEST, reason);
    }
}

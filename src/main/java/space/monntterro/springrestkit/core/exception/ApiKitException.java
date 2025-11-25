package space.monntterro.springrestkit.core.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ApiKitException extends ResponseStatusException {
    private final HttpStatus status;

    public ApiKitException(HttpStatus status, String reason) {
        super(status, reason);
        this.status = status;
    }

    public String getStatusMessage() {
        return status.getReasonPhrase();
    }
}
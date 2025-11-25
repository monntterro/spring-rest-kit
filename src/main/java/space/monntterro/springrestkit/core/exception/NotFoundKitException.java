package space.monntterro.springrestkit.core.exception;

import org.springframework.http.HttpStatus;

public class NotFoundKitException extends ApiKitException {
    public NotFoundKitException(Object id, Class<?> resourceClass) {
        super(HttpStatus.BAD_REQUEST, "Resource not found with id: " + id.toString() + " and class: " + resourceClass.getSimpleName());
    }
}

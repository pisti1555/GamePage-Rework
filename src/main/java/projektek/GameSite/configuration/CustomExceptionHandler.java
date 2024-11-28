package projektek.GameSite.configuration;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import projektek.GameSite.controllers.CustomResponse;
import projektek.GameSite.exceptions.*;

@ControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Object> handleBadRequestException(BadRequestException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new CustomResponse(e.getMessage(), null, e.getErrors())
        );
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<Object> handleUnauthorizedException(UnauthorizedException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                new CustomResponse(e.getMessage(), null, e.getErrors())
        );
    }

    @ExceptionHandler(JwtTokenException.class)
    public ResponseEntity<Object> handleUnauthorizedException(JwtTokenException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                new CustomResponse(e.getMessage(), null, e.getErrors())
        );
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<Object> handleForbiddenException(ForbiddenException e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
                new CustomResponse(e.getMessage(), null)
        );
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> handleNotFoundException(NotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new CustomResponse(e.getMessage(), null)
        );
    }

    @ExceptionHandler(UnexpectedException.class)
    public ResponseEntity<Object> handleUnexpectedError(UnexpectedException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new CustomResponse(e.getMessage(), null)
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGlobalError(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new CustomResponse("Something went wrong", null)
        );
    }
}

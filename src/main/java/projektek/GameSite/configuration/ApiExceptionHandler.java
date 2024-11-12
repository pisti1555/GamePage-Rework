package projektek.GameSite.configuration;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import projektek.GameSite.controllers.ApiResponse;
import projektek.GameSite.exceptions.*;

@ControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Object> handleBadRequestException(BadRequestException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ApiResponse("error", e.getMessage(), null, e.getErrors())
        );
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<Object> handleUnauthorizedException(UnauthorizedException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                new ApiResponse("error", e.getMessage(), null, e.getErrors())
        );
    }

    @ExceptionHandler(JwtTokenException.class)
    public ResponseEntity<Object> handleUnauthorizedException(JwtTokenException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                new ApiResponse("error", e.getMessage(), null)
        );
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<Object> handleForbiddenException(ForbiddenException e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
                new ApiResponse("error", e.getMessage(), null)
        );
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> handleNotFoundException(NotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ApiResponse("error", e.getMessage(), null)
        );
    }

    @ExceptionHandler(UnexpectedException.class)
    public ResponseEntity<Object> handleUnexpectedError(UnexpectedException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new ApiResponse("error", e.getMessage(), null)
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGlobalError(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new ApiResponse("error", "Something went wrong", null)
        );
    }
}

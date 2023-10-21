package academy.ingress.mscategory.exception;

import static academy.ingress.mscategory.model.constants.ExceptionMessages.INTERNAL_SERVER_ERROR_EXCEPTION;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Component
@RestControllerAdvice
@Slf4j
public class GlobalHandler {

  @ExceptionHandler(Exception.class)
  @ResponseStatus(INTERNAL_SERVER_ERROR)
  public ErrorResponse handle(Exception e) {
    log.error("Exception: ", e);
    return new ErrorResponse(INTERNAL_SERVER_ERROR_EXCEPTION.getMessage());
  }

  @ExceptionHandler({MethodArgumentNotValidException.class})
  @ResponseStatus(BAD_REQUEST)
  public ErrorResponse handle(MethodArgumentNotValidException exception) {
    log.error("MethodArgumentNotValidException: ", exception);
    return new ErrorResponse(
        Objects.requireNonNull(exception.getBindingResult().getFieldError()).getDefaultMessage());
  }

  @ExceptionHandler(NotFoundException.class)
  @ResponseStatus(NOT_FOUND)
  public ErrorResponse handle(NotFoundException e) {
    log.error("NotFoundException: ", e);
    return new ErrorResponse(e.getMessage());
  }

  @ExceptionHandler(AlreadyExistsException.class)
  @ResponseStatus(BAD_REQUEST)
  public ErrorResponse handle(AlreadyExistsException e) {
    log.error("AlreadyExistsException: ", e);
    return new ErrorResponse(e.getMessage());
  }

}

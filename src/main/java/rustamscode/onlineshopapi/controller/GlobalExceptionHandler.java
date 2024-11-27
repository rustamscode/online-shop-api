package rustamscode.onlineshopapi.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import rustamscode.onlineshopapi.exception.*;
import rustamscode.onlineshopapi.model.ErrorMessage;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorMessage> productNotFoundException(ProductNotFoundException exception) {
        log.error(exception.getMessage(), exception);
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorMessage(exception.getMessage()));
    }

    @ExceptionHandler(SaleNotFoundException.class)
    public ResponseEntity<ErrorMessage> saleNotFoundException(SaleNotFoundException exception) {
        log.error(exception.getMessage(), exception);
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorMessage(exception.getMessage()));
    }

    @ExceptionHandler(SupplyNotFoundException.class)
    public ResponseEntity<ErrorMessage> supplyNotFoundException(SupplyNotFoundException exception) {
        log.error(exception.getMessage(), exception);
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorMessage(exception.getMessage()));
    }

    @ExceptionHandler(OutOfStockException.class)
    public ResponseEntity<ErrorMessage> outOfStockException(OutOfStockException exception) {
        log.error(exception.getMessage(), exception);
        return ResponseEntity
                .status(HttpStatus.NOT_ACCEPTABLE)
                .body(new ErrorMessage(exception.getMessage()));
    }

    @ExceptionHandler(InsufficientStockException.class)
    public ResponseEntity<ErrorMessage> insufficientStockException(InsufficientStockException exception) {
        log.error(exception.getMessage(), exception);
        return ResponseEntity
                .status(HttpStatus.NOT_ACCEPTABLE)
                .body(new ErrorMessage(exception.getMessage()));
    }
}

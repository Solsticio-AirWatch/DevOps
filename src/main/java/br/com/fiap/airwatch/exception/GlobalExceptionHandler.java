package br.com.fiap.airwatch.exception;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.*;
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String,Object>> notFound(ResourceNotFoundException ex) { return build(HttpStatus.NOT_FOUND,ex.getMessage()); }
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Map<String,Object>> business(BusinessException ex) { return build(HttpStatus.BAD_REQUEST,ex.getMessage()); }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,Object>> validation(MethodArgumentNotValidException ex) {
        Map<String,String> errs=new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(e->errs.put(e.getField(),e.getDefaultMessage()));
        Map<String,Object> b=new HashMap<>(); b.put("timestamp",LocalDateTime.now()); b.put("status",400); b.put("details",errs);
        return ResponseEntity.badRequest().body(b);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String,Object>> general(Exception ex) { return build(HttpStatus.INTERNAL_SERVER_ERROR,"Error: "+ex.getMessage()); }
    private ResponseEntity<Map<String,Object>> build(HttpStatus s,String msg) {
        Map<String,Object> b=new HashMap<>(); b.put("timestamp",LocalDateTime.now()); b.put("status",s.value()); b.put("message",msg);
        return ResponseEntity.status(s).body(b);
    }
}

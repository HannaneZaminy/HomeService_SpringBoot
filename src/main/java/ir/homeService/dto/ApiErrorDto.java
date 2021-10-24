package ir.homeService.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.http.HttpStatus;

import java.util.Date;
import java.util.List;

public class ApiErrorDto {
    private HttpStatus status;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private Date timestamp;
    private String message;
    private List<String> validationErrors;

    public ApiErrorDto(HttpStatus status, String message, List<String> validationErrors) {
        this.status = status;
        this.message = message;
        this.validationErrors = validationErrors;
    }


    public HttpStatus getStatus() {
        return status;
    }

    public ApiErrorDto setStatus(HttpStatus status) {
        this.status = status;
        return this;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public ApiErrorDto setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public ApiErrorDto setMessage(String message) {
        this.message = message;
        return this;
    }

    public List<String> getValidationErrors() {
        return validationErrors;
    }

    public ApiErrorDto setValidationErrors(List<String> validationErrors) {
        this.validationErrors = validationErrors;
        return this;
    }
}

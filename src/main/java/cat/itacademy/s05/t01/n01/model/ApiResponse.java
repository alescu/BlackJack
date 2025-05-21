package cat.itacademy.s05.t01.n01.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonRawValue;
import org.springframework.http.HttpStatus;

public class ApiResponse {
    private int statusCode;
    private String message;
    @JsonRawValue
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String result;

    public ApiResponse(HttpStatus httpStatus, String result) {
        this.statusCode = httpStatus.value();
        this.message = httpStatus.getReasonPhrase();
        this.result = result;
    }

    public ApiResponse(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}

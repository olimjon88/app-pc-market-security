package uz.pdp.utils;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
@Setter
public class ApiResponse<T> {

    // Response data, T - data type
    private T data;

    // Response success = true
    private boolean success;

    // success = false, error message
    private String Message;

    // response time milliseconds
    private Long timestamp;

    // Total data count (Umumiy ma'lumot soni)
    private Long totalCount;

    // Single response
    public ApiResponse(T data) {
        this.data = data;
        this.success = true;
        this.Message = "";
        this.timestamp = System.currentTimeMillis();
    }

    // Multiple response
    public ApiResponse(T data, Long totalCount) {
        this.data = data;
        this.success = true;
        this.Message = "";
        this.timestamp = System.currentTimeMillis();
        this.totalCount = totalCount;
    }

    // Error response
    public ApiResponse(String Message) {
        this.data = null;
        this.success = false;
        this.Message = Message;
        this.timestamp = System.currentTimeMillis();
    }

    // Error response
    public ApiResponse(T data, String Message) {
        this.data = data;
        this.success = false;
        this.Message = Message;
        this.timestamp = System.currentTimeMillis();
    }

    // Success true response (For example delete response)
    public ApiResponse() {
        this.success = true;
        this.Message = "";
        this.timestamp = System.currentTimeMillis();
    }

    public static <T> ResponseEntity<ApiResponse<T>> response(T data) {
        return ResponseEntity.ok(new ApiResponse<>(data));
    }

    public static <T> ResponseEntity<ApiResponse<T>> response(T data, Long totalCount) {
        return ResponseEntity.ok(new ApiResponse<>(data, totalCount));
    }

    public static <T> ResponseEntity<ApiResponse<T>> response(ApiResponse<T> responseData, HttpStatus status) {
        return new ResponseEntity<>(responseData, status);
    }

    public static <T> ResponseEntity<ApiResponse<T>> response(String errorMessage, HttpStatus httpStatus) {
        return new ResponseEntity<>(new ApiResponse<>(null, errorMessage), httpStatus);
    }

    public static <T> ResponseEntity<ApiResponse<T>> response(T data, HttpStatus httpStatus) {
        return new ResponseEntity<>(new ApiResponse<>(data), httpStatus);
    }

    public static <T> ResponseEntity<ApiResponse<T>> response(HttpStatus httpStatus) {
        return new ResponseEntity<>(new ApiResponse<>(null), httpStatus);
    }

    public static <T> ResponseEntity<ApiResponse<T>> response(T data, Long totalCount, HttpStatus httpStatus) {
        return new ResponseEntity<>(new ApiResponse<>(data, totalCount), httpStatus);
    }
}

package com.bookstore.bookstore.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponseModel<T> {
    private boolean success;
    private String message;
    private T data;

    public static <T> ApiResponseModel<T> create(T value, String msg, boolean success) {
        ApiResponseModel<T> response = new ApiResponseModel<>();
        response.setData(value);
        response.setMessage(msg);
        response.setSuccess(success);
        return response;
    }

    public static <T> ApiResponseModel<T> create(T value) {
        ApiResponseModel<T> response = new ApiResponseModel<>();
        response.setData(value);
        response.setMessage("");
        response.setSuccess(true);
        return response;
    }

    public static <T> ApiResponseModel<T> create(String msg, boolean success) {
        ApiResponseModel<T> response = new ApiResponseModel<>();
        response.setMessage(msg);
        response.setSuccess(success);
        return response;
    }
}

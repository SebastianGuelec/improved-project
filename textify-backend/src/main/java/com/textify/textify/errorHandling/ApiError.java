package com.textify.textify.errorHandling;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Map;

@Data
@NoArgsConstructor
public class ApiError {

    private long timestamp = new Date().getTime();

    private int status;

    private String message;

    private String url;

    private Map<String, String> validationErrors;

    public ApiError(int status, String message, String url) {
        super();
        this.status = status;
        this.message = message;
        this.url = url;
    }

}

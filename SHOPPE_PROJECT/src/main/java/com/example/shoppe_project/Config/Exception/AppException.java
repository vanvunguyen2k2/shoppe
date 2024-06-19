package com.example.shoppe_project.Config.Exception;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@JsonIgnoreProperties({"stackTrace", "cause", "suppressed", "localizedMessage"})
public class AppException extends RuntimeException{
    private Instant timestamp; // Thời gian xảy ra lỗi
    private int status;   // Trạng thái, mã lỗi
    private String message;   // nguyên nhân xảy ra lỗi
    private  String path;    // API xảy ra lỗi

    public AppException(int status, String error) {
        this.timestamp = Instant.now();
        this.status = status;
        this.message = error;
    }

    public AppException(ErrorResponseEnum errorResponseEnum) {
        this.timestamp = Instant.now();
        this.status = errorResponseEnum.status;
        this.message = errorResponseEnum.message;
    }
}

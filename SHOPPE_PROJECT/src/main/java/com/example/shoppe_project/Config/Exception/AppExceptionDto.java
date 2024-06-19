package com.example.shoppe_project.Config.Exception;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor

public class AppExceptionDto {

    private Instant timestamp; // Thời gian xảy ra lỗi
    private int status;   // Trạng thái, mã lỗi
    private String message;   // nguyên nhân xảy ra lỗi
    private  String path;    // API xảy ra lỗi

    public AppExceptionDto(int status, String error) {
        this.timestamp = Instant.now();
        this.status = status;
        this.message = error;
    }

    public AppExceptionDto(ErrorResponseEnum errorResponseEnum) {
        this.timestamp = Instant.now();
        this.status = errorResponseEnum.status;
        this.message = errorResponseEnum.message;
    }

    public AppExceptionDto(int status, String message, String path) {
        this.status = status;
        this.message = message;
        this.path = path;
        this.timestamp = Instant.now();
    }
}

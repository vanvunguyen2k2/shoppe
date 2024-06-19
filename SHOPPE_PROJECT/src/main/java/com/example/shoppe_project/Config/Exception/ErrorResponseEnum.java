package com.example.shoppe_project.Config.Exception;

public enum ErrorResponseEnum {
    NOT_FOUND_PRODUCT(404, "Không tìm thấy sản phẩm "),
    NOT_FOUND_ACCOUNT(404, "Không tìm thấy người dùng"),
    USERNAME_EXISTED(400, "TÊN NÀY ĐÃ ĐƯỢC SỬ DỤNG"),
    USERNAME_NOT_EXISTED(401, "ACCOUNT NOT EXIST"),
    PASSWORD_WRONG(401, "SAI MẬT KHẨU");


    public final int status;
    public final String message;
    ErrorResponseEnum(int status, String message) {
        this.status = status;
        this.message = message;
    }
}

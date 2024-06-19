package com.example.shoppe_project.Repository.utils;


import com.alibaba.fastjson.JSON;
import com.example.shoppe_project.Config.Exception.AppExceptionDto;
import com.example.shoppe_project.modal.dto.loginDto;
import com.example.shoppe_project.modal.entity.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@Slf4j
@Component
public class JWTTokenUtils {
    private static final long EXPIRATION_TIME = 864000000; // 10 days, thời hạn của token
    private static final String SECRET = "123456"; // Chữ ký bí mật
    private static final String PREFIX_TOKEN = "Bearer"; // Ký tự đầu của token
    private static final String AUTHORIZATION = "Authorization"; // Key của token trên header


    // Hàm này dùng để tạo ra token
    public String createAccessToken(loginDto loginDto) {
        // Tạo giá trị thời hạn token ( bằng thời gian hiện tại + 10 ngày hoặc tuỳ theo )
        Date expirationDate = new Date(System.currentTimeMillis() + EXPIRATION_TIME);
        String token = Jwts.builder()
                .setId(String.valueOf(loginDto.getUserId())) //set giá trị Id
                .setSubject(loginDto.getUsername()) // set giá trị subject
                .setIssuedAt(new Date())
                .setIssuer("VTI")
                .setExpiration(expirationDate) // set thời hạn của token
                .signWith(SignatureAlgorithm.HS512, SECRET) // khai báo phương thức mã hóa token và chữ ký bí mật
                .claim("authorities", loginDto.getRole().name()) // thêm trường authorities để lưu giá trị phân quyền
                .claim("user-Agent", loginDto.getUserAgent()).compact();// thêm trường user-Agent để lưu thông tin trình duyệt đang dùng
//                .compact();   // TẠO RA MÃ TOKEN TỪ CÁC THÔNG TIN TRÊN

        return token;
    }


    // Hàm này dùng để giải mã hóa token
    public loginDto parseAccessToken(String token) {
        loginDto loginDto = new loginDto();
        if (!token.isEmpty()) {
            try {
                token = token.replace(PREFIX_TOKEN, "").trim();
                Claims claims = Jwts.parser()
                        .setSigningKey(SECRET)
                        .parseClaimsJws(token).getBody();
                // Lấy ra các thông tin
                String user = claims.getSubject();
                Role role = Role.valueOf(claims.get("authorities").toString());
                String userAgent = claims.get("user-Agent").toString();
                // Gán các thông tin vào đối tượng LoginDto, có thể sử dụng constructor
                loginDto.setUsername(user);
                loginDto.setRole(role);
                loginDto.setUserAgent(userAgent);
            } catch (Exception e) {
                log.error(e.getMessage());
                return null;
            }
        }
        return loginDto;
    }

    public boolean checkToken(String token, HttpServletResponse response, HttpServletRequest httpServletRequest) {
        try {
            if (StringUtils.isBlank(token) || !token.startsWith(PREFIX_TOKEN)) { // token bị trống -> lỗi
                responseJson(response, new AppExceptionDto(401, "Token ko hợp lệ", httpServletRequest.getRequestURI()));
                return false;
            }
//            BỎ TỪ KHÓA  BEARER Ở 2 TOKEN VÀ TRIM() 2 ĐẦU
            token = token.replace(PREFIX_TOKEN, "").trim();


            loginDto loginDto = parseAccessToken(token);
            if (loginDto == null) { // Ko có token trên hệ thống
                responseJson(response, new AppExceptionDto(401, "Token ko tồn tại hoặc hết hạn", httpServletRequest.getRequestURI()));
                return false;
            }
        } catch (Exception e) {
            responseJson(response, new AppExceptionDto(401, e.getMessage(), httpServletRequest.getRequestURI()));
            return false;
        }
        return true;
    }


    // Hàm này dùng để response dữ liệu khi gặp lỗi
    private void responseJson(HttpServletResponse response, AppExceptionDto appException) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.setStatus(401);
        try {
            response.getWriter().print(JSON.toJSONString(appException));
        } catch (IOException e) {
            log.debug(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}





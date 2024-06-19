package com.example.shoppe_project.Controller;

import com.example.shoppe_project.Config.Exception.AppException;
import com.example.shoppe_project.Config.Exception.ErrorResponseEnum;
import com.example.shoppe_project.Repository.AccountRepository;
import com.example.shoppe_project.Repository.utils.JWTTokenUtils;
import com.example.shoppe_project.modal.dto.loginDto;
import com.example.shoppe_project.modal.dto.loginRequest;
import com.example.shoppe_project.modal.entity.Account;
import com.example.shoppe_project.modal.entity.BaseEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin("*")
public class AuthController {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
   private HttpServletRequest httpServletRequest;

    @Autowired
    private JWTTokenUtils jwtTokenUtils;

    /**
     * Hàm login: Cần set authenticated() cho API này
     * Principal: Đối tượng được sinh ra khi đã xác thực được người dùng
     * @param principal
     * @return
     */
    @GetMapping("/login-basic-v1")
    public loginDto loginDtov1(Principal principal){
        String username = principal.getName();

        // TÌM RA ĐƯỢC ĐỐI TƯỢNG ACCOUNT TỪ USERNAME
        Optional<Account> accountOptional = accountRepository.findByUsername(username);
        if (accountOptional.isEmpty()){
            throw new AppException(ErrorResponseEnum.NOT_FOUND_ACCOUNT);
        }
        Account account = accountOptional.get();
        loginDto loginDto = new loginDto();
        BeanUtils.copyProperties(account, loginDto);
        return loginDto;
    }


    /**
     * HÀM LOGIN, CÁCH NÀY CẦN PERNITALL() VỚI API ĐỂ XỬ LÝ DỮ LIỆU
     * @param username
     * @param password
     * @return
     */

    @GetMapping("/login-basic-v2")
    public loginDto loginDtov2(@RequestParam String username, @RequestParam String password){

        // TÌM RA ĐƯỢC ĐỐI TƯỢNG ACCOUNT TỪ USERNAME
        Optional<Account> accountOptional = accountRepository.findByUsername(username);
        if (accountOptional.isEmpty()){
            throw new AppException(ErrorResponseEnum.NOT_FOUND_ACCOUNT);
        }
        Account account = accountOptional.get();

        // SO SÁNH PASSWORD
        boolean checkPassword = passwordEncoder.matches(password, account.getPassword());
        if (checkPassword = false){
            throw new AppException(ErrorResponseEnum.PASSWORD_WRONG);
        }
        loginDto loginDto = new loginDto();
        BeanUtils.copyProperties(account, loginDto);
        return loginDto;
    }


    /**
     *
     *
     * @return
     */
    @PostMapping("/login-JWT")
    public loginDto loginJWT(@RequestBody loginRequest request  ){

        // TÌM RA ĐƯỢC ĐỐI TƯỢNG ACCOUNT TỪ USERNAME
        Optional<Account> accountOptional = accountRepository.findByUsername(request.getUsername());
        if (accountOptional.isEmpty()){
            throw new AppException(ErrorResponseEnum.NOT_FOUND_ACCOUNT);
        }
        Account account = accountOptional.get();

        // SO SÁNH PASSWORD
        boolean checkPassword = passwordEncoder.matches(request.getPassword(), account.getPassword());
        if (checkPassword = false){
            throw new AppException(ErrorResponseEnum.PASSWORD_WRONG);
        }
        loginDto loginDto = new loginDto();
        BeanUtils.copyProperties(account, loginDto);
        loginDto.setUserAgent(httpServletRequest.getHeader("User-Agent")); // Lấy thông tin trình duyệt đang sử dụng
        String token = jwtTokenUtils.createAccessToken(loginDto); // Tạo token
        loginDto.setToken(token); // Set giá trị token vào loginDto để trả về cho người dùng sử dụng
        loginDto.setUserId(account.getId());
        return loginDto;
    }




}

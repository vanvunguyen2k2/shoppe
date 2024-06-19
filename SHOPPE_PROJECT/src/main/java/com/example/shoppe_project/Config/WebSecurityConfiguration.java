package com.example.shoppe_project.Config;

import com.example.shoppe_project.Config.Exception.JwtRequestFilter;
import com.example.shoppe_project.Service.AccountService;
import io.swagger.models.HttpMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration // kết hợp với @Bean để tạo thành 1 bean trong spring IOC
    @EnableWebSecurity // KHAI BÁO Security
    @EnableGlobalMethodSecurity(prePostEnabled = true) // Để có thể phân quyền tại controller
    public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
        @Autowired
        private AccountService accountService;

        @Autowired
        private PasswordEncoder passwordEncoder;

        @Autowired
        private JwtRequestFilter jwtRequestFilter;


        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(accountService).// Cấu hình UserDetailsService để khi xác thực người dùng sẽ gọi tới hàm loadUserByUsername()
                    passwordEncoder(passwordEncoder);// Cấu hình phương thức để mã hoá mật khẩu
        }


        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.authorizeRequests()
                    // config những API ko cần xác thực
                    .antMatchers("api/not-authenticated", "/api/v1/account/create", "/api/v1/auth/login-basic-v2", "/api/v1/auth/login-JWT", "/api/v1/product/search", "/api/v1/order/**").permitAll()


                    // Config những API phải có Authority là ADMIN thì mới được truy cập
                    .antMatchers(String.valueOf(HttpMethod.GET),"/api/v1/product/**").hasAuthority("ADMIN")


                    // Config những API phải có Authority là ADMIN hoặc User thì mới được truy cập
                    .antMatchers("api/admin-or-user").hasAnyAuthority("ADMIN", "User")


                    .anyRequest().authenticated()// Những đường dẫn còn lại cần được xác thực


                    .and().httpBasic()// Kích hoạt cấu hình http basic trong Spring Security


// tắt tính năng Cross-Site Request Forgery (CSRF) trong Spring Security.
                    .and().cors().and().csrf().disable();
            http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

            //  KHAI BÁO LỚP FILTER SẼ ĐƯỢC THỰC HIỆN TRƯỚC KHI PHÂN QUYỀN
            http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        }


        @Override // Config cho đường dẫn (swagger) ko bị chặn bởi security
        public void configure(WebSecurity web) {
            web.ignoring().antMatchers("/swagger-ui/**")
                    .antMatchers("/swagger-resources/**")
                    .antMatchers("/v3/api-docs/**");
        }
    }

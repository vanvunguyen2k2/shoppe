package com.example.shoppe_project.Service;

import com.example.shoppe_project.Config.Exception.AppException;
import com.example.shoppe_project.Config.Exception.ErrorResponseEnum;
import com.example.shoppe_project.Repository.AccountRepository;
import com.example.shoppe_project.modal.dto.AccountRequestDTO;
import com.example.shoppe_project.modal.entity.Account;
import com.example.shoppe_project.modal.entity.Role;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional
public class AccountService implements IAccountService, UserDetailsService {
    @Autowired
    private final AccountRepository accountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private IEmailSenderService senderService ;

    @Override
    public List<Account> getAll() {
        return accountRepository.findAll();
    }

    @Override
    public Account create(AccountRequestDTO dto) {
        Optional<Account> optionalAccount = accountRepository.findByUsername(dto.getUsername());
        if (optionalAccount.isPresent()){
            throw new AppException(ErrorResponseEnum.USERNAME_EXISTED);
        }
        Account entity = new Account();
        BeanUtils.copyProperties(dto, entity);
        String pwEncoder = passwordEncoder.encode(dto.getPassword());
        entity.setPassword(pwEncoder);
        senderService.sendSinpleMessage(dto.getEmail(), "thông báo tạo mới tài khoản ","bạn vừa tạo thành công với tài khoản "+ dto.getUsername());
        return accountRepository.save(entity);
    }

    @Override
    public void delete(int id) {
        accountRepository.deleteById(id);
    }

    /**
     * DÙNG ĐỂ PRING SECURITY KIỂM TRA USERNAME CÓ TÊN TRONG HẸ THỐNG HAY KHÔNG
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Account> accountOptional = accountRepository.findByUsername(username);
        if (accountOptional.isEmpty()){
            throw new UsernameNotFoundException("UserName khong ton tai");
        }
        Account account = accountOptional.get();
//        Nếu có tồn tại -> tạo ra đối tượng userDetails từ Account
        /**
         * account.getusername(): username
         * account.getPassword(): mật khẩu đã đưuọc mã hóa
         * grantedAuthorityList: List quyền của user
         */
        List<GrantedAuthority> grantedAuthorityList = new ArrayList<>();
        grantedAuthorityList.add(account.getRole());
        return new User(account.getUsername(), account.getPassword(), grantedAuthorityList);
    }
}

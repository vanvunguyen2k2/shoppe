package com.example.shoppe_project.Controller;

import com.example.shoppe_project.Service.AccountService;
import com.example.shoppe_project.modal.dto.AccountRequestDTO;
import com.example.shoppe_project.modal.entity.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/account")
@CrossOrigin("*")
@Validated
public class AccountController {
    @Autowired
    private AccountService accountService;


    @GetMapping("/get-All")
    public List<Account> getAll(){
        return accountService.getAll();
    }

    @PostMapping("/create")
    public Account create(@RequestBody @Valid AccountRequestDTO dto){
        return accountService.create(dto);
    }

    @DeleteMapping("{id}")
    @PreAuthorize(value = "hasAnyAuthority('ADMIN')")
    public void delete(@PathVariable(name = "id") int id){
        accountService.delete(id);

    }
}

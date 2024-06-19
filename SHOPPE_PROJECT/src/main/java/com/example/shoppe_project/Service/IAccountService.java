package com.example.shoppe_project.Service;

import com.example.shoppe_project.modal.dto.AccountRequestDTO;
import com.example.shoppe_project.modal.entity.Account;

import java.util.List;

public interface IAccountService {

    List<Account> getAll();

    Account create(AccountRequestDTO dto);

    void delete(int id);
}

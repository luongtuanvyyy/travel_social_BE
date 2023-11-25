package com.app.mapper;

import com.app.dto.AccountDto;
import com.app.entity.Account;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AccountMapper {
    @Autowired
    ModelMapper modelMapper;

    public Account toAccount(AccountDto dto) {
        return dto == null ? null : modelMapper.map(dto, Account.class);
    }

    public AccountDto accountDto(Account account) {
        return account == null ? null : modelMapper.map(account, AccountDto.class);
    }
}

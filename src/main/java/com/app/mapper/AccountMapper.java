package com.app.mapper;

import com.app.dto.AccountDto;
import com.app.dto.AccountDto2;
import com.app.entity.Account;
import com.app.entity.Blog;
import com.app.entity.Follow;
import com.app.entity.Like;
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

    public AccountDto2 accountDto2(Account account) {
        return account == null ? null : modelMapper.map(account, AccountDto2.class);
    }

    public AccountDto2 accountDtoFromFollow(Follow follow) {
        if (follow == null || follow.getAccount() == null) {
            return null;
        }
        Account account = follow.getAccount();
        AccountDto2 accountDto = this.accountDto2(account);
        accountDto.setCreatedAt(follow.getCreatedAt());
        return accountDto;
    }

    public AccountDto2 accountDtoFromLike(Like like) {
        if (like == null || like.getAccount() == null) {
            return null;
        }
        Account account = like.getAccount();
        AccountDto2 accountDto = this.accountDto2(account);
        accountDto.setCreatedAt(like.getCreatedAt());
        return accountDto;
    }
}

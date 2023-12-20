package com.app.mapper;

import com.app.constant.NotificationType;
import com.app.dto.AccountCommentDto;
import com.app.dto.AccountDto;
import com.app.dto.Notification;
import com.app.entity.*;
import com.app.repository.AccountRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AccountMapper {
    @Autowired
    ModelMapper modelMapper;

    @Autowired
    AccountRepository accountRepository;

    public Account toAccount(AccountDto dto) {
        return dto == null ? null : modelMapper.map(dto, Account.class);
    }

    public AccountDto accountDto(Account account) {
        return account == null ? null : modelMapper.map(account, AccountDto.class);
    }

    public Notification toNotification(Account account, String notificationType, String description) {
        Notification notification = account == null ? null : modelMapper.map(account, Notification.class);
        notification.setNotificationType(notificationType);
        notification.setDescription(description);
        return notification;
    }

    public AccountCommentDto accountCommentDto(Account account, BlogComment blogComment) {
        AccountCommentDto accountCommentDto = account == null ? null : modelMapper.map(account, AccountCommentDto.class);
        accountCommentDto.setId(blogComment.getId());
        accountCommentDto.setContent(blogComment.getContent());
        return accountCommentDto;
    }

    public AccountCommentDto mapToAccountCommentDto(BlogComment blogComment) {
        Account account = accountRepository.findByEmail(blogComment.getCreatedBy()).orElse(null);
        AccountCommentDto commentDto = accountCommentDto(account, blogComment);

        List<AccountCommentDto> repliesDto = blogComment.getReplies().stream()
                .map(this::mapToAccountCommentDto)
                .collect(Collectors.toList());

        commentDto.setReplies(repliesDto);
        return commentDto;
    }

    public Notification notificationFromFollow(Follow follow) {
        if (follow == null || follow.getAccount() == null) {
            return null;
        }
        Account account = follow.getAccount();
        Notification notification = this.toNotification(account, NotificationType.FOLLOW_YOU, account.getEmail() +" Started following you");
        notification.setCreatedAt(follow.getCreatedAt());
        return notification;
    }

    public Notification notificationFromLike(Like like) {
        if (like == null || like.getAccount() == null) {
            return null;
        }
        Account account = like.getAccount();
        Notification notification = this.toNotification(account, NotificationType.LIKE_YOU, account.getEmail() +" Liked your profile");
        notification.setCreatedAt(like.getCreatedAt());
        return notification;
    }
}

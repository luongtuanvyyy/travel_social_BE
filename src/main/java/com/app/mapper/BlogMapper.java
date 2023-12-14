package com.app.mapper;

import com.app.dto.AccountDto;
import com.app.dto.BlogDto;
import com.app.entity.Account;
import com.app.entity.Blog;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BlogMapper {
    @Autowired
    ModelMapper modelMapper;

    public Blog toBlog(BlogDto dto) {
        return dto == null ? null : modelMapper.map(dto, Blog.class);
    }

    public BlogDto blogDto(Blog blog) {
        return blog == null ? null : modelMapper.map(blog, BlogDto.class);
    }
}

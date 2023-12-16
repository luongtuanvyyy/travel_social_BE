package com.app.service;

import com.app.payload.response.APIResponse;
import com.app.security.UserPrincipal;

public interface BlogInteractionService {
    APIResponse addComment (Integer blogId, String content);
    APIResponse deleteComment (Integer blogCommentId);
    APIResponse likeBlog (Integer blogId, UserPrincipal userPrincipal);
    APIResponse unlikeBlog (Integer blogId, UserPrincipal userPrincipal);
}

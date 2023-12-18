package com.app.repository;


import com.app.entity.Blog;
import com.app.entity.BlogReaction;
import com.app.modal.CommentModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlogReactionRepository extends JpaRepository<BlogReaction, Integer> {
    Page<BlogReaction> findAll(Specification<BlogReaction> spec, Pageable pageable);

    @Query(value = "SELECT NEW com.app.modal.CommentModel(" +
            "    a.name, " +
            "    a.avatar, " +
            "    br.comment, " +
            "    bn.createTime, " +
            "    a.isVerify ) " +
            "FROM " +
            "    BlogReaction br " +
            "    JOIN BlogNotification bn ON br.blog.id = bn.blog.id " +
            "    JOIN Account a ON br.account.id = a.id " +
            "WHERE " +
            "    br.blog.id = :blogId " +
            "    AND bn.notificationType = 2")
    List<CommentModel> getAllCommentByBlogId(Integer blogId);

    @Query("SELECT b FROM BlogReaction b WHERE b.reactionLike = true and b.blog.id = :id and b.createdBy = :mail")
    BlogReaction findByBlogIdAndReactionLikeTrue(@Param("id") Integer id, @Param("mail") String mail);
    @Query("SELECT b FROM BlogReaction b WHERE b.share = true and b.blog.id = :id and b.createdBy = :mail ")
    BlogReaction findByBlogIdAndShareTrue(@Param("id") Integer id, @Param("mail") String mail);


}


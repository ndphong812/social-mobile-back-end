package com.backend.social.repository;

import com.backend.social.entity.Posts;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;


@Repository
public interface PostsRepo extends JpaRepository<Posts,Long> {

    Page<Posts> findAll(Pageable pageable);
    Page<Posts> findByIsDeleted(Boolean isDeleted,Pageable pageable);
    List<Posts> findByUser_Id(Long userId);
    Optional<Posts> findById(Long id);


    @Query(value = "INSERT INTO posts(\"content\",image,user_id,shared_post) VALUES(:#{#posts.content},:#{#posts.image},:#{#posts.user.id},:#{#posts.sharedPost.id}) ",nativeQuery = true)
    @Modifying
    @Transactional
    Integer insert(Posts posts);

    @Modifying
    @Query("UPDATE Posts p SET "
            + "p.content = CASE WHEN :content IS NOT NULL THEN :content ELSE p.content END, "
            + "p.image = CASE WHEN :image IS NOT NULL THEN :image ELSE p.image END, "
            + "p.isDeleted = CASE WHEN :isDeleted IS NOT NULL THEN :isDeleted ELSE p.isDeleted END "
            + "WHERE p.id = :id")
    @Transactional
    void updatePostById(@Param("id") Long id, @Param("content") String content, @Param("image") String image,@Param("isDeleted") Boolean isDeleted);

}
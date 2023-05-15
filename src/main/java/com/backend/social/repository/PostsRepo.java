package com.backend.social.repository;

import com.backend.social.entity.Posts;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface PostsRepo extends JpaRepository<Posts,Long> {
    List<Posts> findAll();
    List<Posts> findByUser_Id(Long userId);
    Optional<Posts> findById(Long id);


    @Query(value = "INSERT INTO posts(\"content\",image,user_id,shared_post) VALUES(:#{#posts.content},:#{#posts.image},:#{#posts.user.id},:#{#posts.sharedPost.id}) ",nativeQuery = true)
    @Modifying
    @Transactional
    Integer insert(Posts posts);

}
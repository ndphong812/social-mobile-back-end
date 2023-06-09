package com.backend.social.service;

import com.backend.social.dto.PostsDto;
import com.backend.social.dto.request.PostsDtoRequest;
import com.backend.social.entity.Posts;
import org.springframework.data.domain.Pageable;


import java.util.List;
import java.util.Optional;

public interface PostService {

    List<Posts> findAllPost(Pageable pageable);

    List<PostsDto> findByUserId(Long userId);

    Optional<Posts> findById(Long id);

    Integer insert(PostsDtoRequest postsDto);

    void updatePostById(Long id, String content, String image, Boolean isDeleted);


}

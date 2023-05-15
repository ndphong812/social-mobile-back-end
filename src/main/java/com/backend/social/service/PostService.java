package com.backend.social.service;

import com.backend.social.dto.PostsDto;
import com.backend.social.dto.request.PostsDtoRequest;
import com.backend.social.entity.Posts;

import java.util.List;
import java.util.Optional;

public interface PostService {

    List<PostsDto> findByUserId(Long userId);

    Optional<Posts> findById(Long id);

    Integer insert(PostsDtoRequest postsDto);
}

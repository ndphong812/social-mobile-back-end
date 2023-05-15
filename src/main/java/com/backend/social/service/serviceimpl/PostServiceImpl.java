package com.backend.social.service.serviceimpl;

import com.backend.social.dto.PostsDto;
import com.backend.social.dto.request.PostsDtoRequest;
import com.backend.social.entity.Posts;
import com.backend.social.mapper.PostMapper;
import com.backend.social.repository.PostsRepo;
import com.backend.social.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostsRepo repo;

    private final PostMapper postMapper;

    @Override
    public List<PostsDto> findByUserId(Long userId) {
        return postMapper.toPostsDto(repo.findByUser_Id(userId));
    }

    @Override
    public Optional<Posts> findById(Long id) {
        return repo.findById(id);
    }

    @Override
    public Integer insert(PostsDtoRequest postsDto) {
        return repo.insert(postMapper.toPost(postsDto));
    }


}

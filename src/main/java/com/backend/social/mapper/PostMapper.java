package com.backend.social.mapper;


import com.backend.social.dto.PostsDto;
import com.backend.social.dto.request.PostsDtoRequest;
import com.backend.social.entity.Posts;
import com.backend.social.entity.Users;
import com.backend.social.exception.ResourceNotFoundException;
import com.backend.social.repository.PostsRepo;
import com.backend.social.repository.UsersRepo;
import com.backend.social.service.PostService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Component
@RequiredArgsConstructor
public class PostMapper {

    private final PostsRepo postsRepo;

    private final UsersRepo usersRepo;

    private PostsDto convertoPostDto(Posts posts){
        return PostsDto.builder()
                .id(posts.getId())
                .like(posts.getLike())
                .heart(posts.getHeart())
                .haha(posts.getHaha())
                .sharedPostId(posts.getSharedPost() == null ? null :posts.getSharedPost().getId())
                .sharedPost(null)
                .content(posts.getContent())
                .image(posts.getImage())
                .ownerId(posts.getUser().getId())
                .ownerName(posts.getUser().getFullName())
                .ownerAvatar(posts.getUser().getAvatar())
                .createdAt(posts.getCreatedAt())
                .isDeleted(posts.getIsDeleted())
                .numberComment(posts.getNumberComment())
                .build();
    }

    public PostsDto toPostDto(Posts posts){
        if(posts.getSharedPost() == null){
            return convertoPostDto(posts);
        }else{
            Optional<Posts> post = postsRepo.findById(posts.getSharedPost().getId());
            PostsDto dto = convertoPostDto(posts);
            dto.setSharedPost(convertoPostDto(post.get()));
            return dto;
        }
    }

    public List<PostsDto> toPostsDto(List<Posts> posts){
        List<PostsDto> postsDtos = new ArrayList<>();
        for (Posts post : posts) {
            postsDtos.add(toPostDto(post));
        }
        return postsDtos;
    }



    public Posts toPost(PostsDtoRequest dto){
        Optional<Users> userOptional = usersRepo.findById(dto.getOwnerId());
        Users user = userOptional.orElseThrow(() -> new ResourceNotFoundException("User not found with id:" + dto.getOwnerId()));

        //post is sharing post
        if(dto.getSharedPostId() != null){
            Optional<Posts> sharedPostOptional= postsRepo.findById(dto.getSharedPostId());
            Posts post = sharedPostOptional.orElseThrow(()-> new ResourceNotFoundException("Post not found with id: "+dto.getSharedPostId()));

            return Posts.builder()
                    .id(dto.getId())
                    .content(dto.getContent())
                    .image(dto.getImage())
                    .user(user)
                    .like(0)
                    .heart(0)
                    .haha(0)
                    .numberComment(0)
                    .sharedPost(post)
                    .build();
        }else {
            return Posts.builder()
                    .id(dto.getId())
                    .content(dto.getContent())
                    .image(dto.getImage())
                    .user(user)
                    .like(0)
                    .heart(0)
                    .haha(0)
                    .numberComment(0)
                    .sharedPost(null)
                    .build();
        }
    }

}

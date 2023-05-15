package com.backend.social.controller;

import com.backend.social.dto.PostsDto;
import com.backend.social.dto.request.PostsDtoRequest;
import com.backend.social.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1")
@RequiredArgsConstructor
public class PostController {


    private final PostService postService;

    //GET METHOD
    @GetMapping("/users/{userId}/posts")
    public ResponseEntity<?> getPostsOfUser(@PathVariable("userId") Long userId){
        return ResponseEntity.ok(postService.findByUserId(userId));
    }

    //POST METHOD
    @PostMapping("users/{userId}/posts")
    public ResponseEntity<?> createPost(@RequestBody PostsDtoRequest dto){
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(postService.insert(dto));
        }catch (RuntimeException e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e.getMessage());
        }
    }

    //PUT METHOD

    //DELETE METHOD

}

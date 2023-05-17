package com.backend.social.controller;

import com.backend.social.contant.CommonConst;
import com.backend.social.dto.request.PostsDtoRequest;
import com.backend.social.entity.Posts;
import com.backend.social.exception.ResourceNotFoundException;
import com.backend.social.service.PostService;
import jakarta.validation.constraints.Null;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1")
@RequiredArgsConstructor
public class PostsController {


    private final PostService postService;

    //GET METHOD
    @GetMapping("/posts")
    public ResponseEntity<?> getAllPost(@RequestParam(value = "page",defaultValue = "1") Integer page ){
        Pageable pageable = PageRequest.of(page-1, CommonConst.RECORD_PER_PAGE);
        List<Posts> posts = postService.findAllPost(pageable);
        return ResponseEntity.ok(posts);
    }

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
    @PutMapping("/posts")
    public ResponseEntity<?> updatePost(@RequestBody PostsDtoRequest dto){
        try{
            postService.updatePostById(dto.getId(), dto.getContent(), dto.getImage(), null);
            return ResponseEntity.status(HttpStatus.OK).body("Post updated successfully.");
        }catch (ResourceNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch(Exception e){
            String messageError = "Failed to update post";
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(messageError);
        }
    }


    //DELETE METHOD
    @DeleteMapping("/posts/{id}")
    public ResponseEntity<?> deletePost(@PathVariable Long id){
        try {
            postService.updatePostById(id,null,null,Boolean.TRUE);
            return ResponseEntity.ok("post deleted successfully.");
        }catch (ResourceNotFoundException e){
           return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
        catch (Exception e){
            String messageError = "Failed to update post";
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(messageError);
        }
    }
}

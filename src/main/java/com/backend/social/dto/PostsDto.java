package com.backend.social.dto;

import com.backend.social.entity.Posts;
import com.backend.social.entity.Users;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.sql.Timestamp;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostsDto {
    private Integer like;
    private Integer haha;
    private Integer heart;
    private Integer numberComment;
    private String content;
    private String image;
    private Boolean isDeleted;
    private Long ownerId;
    private String ownerName;
    private String ownerAvatar;
    private Timestamp createdAt;
    private Long sharedPostId;
    private PostsDto sharedPost;

}

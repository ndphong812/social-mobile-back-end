package com.backend.social.dto.request;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostsDtoRequest {
    private String content;
    private String image;
    private Long ownerId;
    private Long sharedPostId;
}

package com.backend.social.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Entity
@Table(name = "posts")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Posts {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "like")
    @NotNull
    @Min( value= 0,message = "least like is 0")
    private Integer like = 0;

    @Column(name = "heart")
    @NotNull
    @Min(value = 0,message = "least heart is 0")
    private Integer heart = 0;

    @Column(name = "haha")
    @NotNull
    @Min(value = 0,message = "least haha is 0")
    private Integer haha = 0;

    @Column(name = "content")
    @NotBlank(message = "content is not blank")
    private String content;

    @Column(name = "image",nullable = true)
    private String image;

    @Column(name = "is_deleted")
    private Boolean isDeleted = false;

    @Column(name = "created_at")
    @CreationTimestamp
    private Timestamp createdAt;

    @ManyToOne
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    @NotNull
    private Users user;

    @ManyToOne
    @JoinColumn(name = "shared_post",referencedColumnName = "id")
    private Posts sharedPost;

    @Column(name = "numberComment")
    @Min(0)
    private Integer numberComment = 0;
}

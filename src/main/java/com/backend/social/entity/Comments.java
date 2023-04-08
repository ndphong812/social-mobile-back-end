package com.backend.social.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Table(name = "comments")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Comments  implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "like")
    @Min(0)
    private Integer like;

    @Column(name = "heart")
    @Min(0)
    private Integer heart;

    @Column(name = "haha")
    @Min(0)
    private Integer haha;

    @Column(name = "content")
    @NotBlank
    private String content;

    @Column(name = "image",nullable = true)
    private String image;

    @ManyToOne
    @JoinColumn(name = "postId",referencedColumnName = "id")
    private Posts post;

    @ManyToOne
    @JoinColumn(name = "userId",referencedColumnName = "id")
    private Users user;

    @Column(name = "isDeleted")
    @NotNull
    private Boolean isDeleted;

    @Column(name = "avatar")
    private String avatar;

    @Column(name = "fullName")
    private String fullName;
}

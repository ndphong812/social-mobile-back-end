package com.backend.social.entity;


import com.backend.social.entity.idclass.ReactionsId;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Table(name = "reactions")
@IdClass(ReactionsId.class)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Reactions implements Serializable {

    @Id
    @Column(name="user_id")
    private Long userId;

    @Id
    @Column(name = "post_id")
    private Long postId;

    @Column(name = "type")
    @NotBlank
    @Size(max = 5)
    private String type;

}

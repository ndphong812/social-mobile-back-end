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
    @ManyToOne
    @JoinColumn(name="user_id")
    private Users user;

    @Id
    @ManyToOne
    @JoinColumn(name = "post_id")
    private Posts post;

    @Column(name = "type")
    @NotBlank
    @Size(max = 5)
    private String type;

}

package com.backend.social.entity;


import com.backend.social.entity.idclass.BlocksId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Table(name = "blocks")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@IdClass(BlocksId.class)
public class Blocks implements Serializable {

    @Id
    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;

    @Id
    @ManyToOne
    @JoinColumn(name = "blocked_user")
    private Users blockedUser;

}

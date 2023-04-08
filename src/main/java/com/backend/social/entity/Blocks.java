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
    @Column(name = "user_id")
    private Long userId;

    @Id
    @Column(name = "blocked_user")
    private Long blockedUserId;


    @ManyToOne
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    private Users user;

    @ManyToOne
    @JoinColumn(name = "blocked_user",referencedColumnName = "id")
    private Users blockedUser;


}

package com.backend.social.entity;


import com.backend.social.entity.idclass.FriendsId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Table(name = "friends")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@IdClass(FriendsId.class)
public class Friends implements Serializable {

    @Id
    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;

    @Id
    @ManyToOne
    @JoinColumn(name = "friend_id")
    private Users friend;

}

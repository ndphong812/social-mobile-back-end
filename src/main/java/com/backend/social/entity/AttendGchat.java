package com.backend.social.entity;


import com.backend.social.entity.idclass.AttendGchatId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Table(name = "attend_gchat")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@IdClass(AttendGchatId.class)
public class AttendGchat implements Serializable {

    @Id
    @Column(name = "user_id")
    private Long userId;

    @Id
    @Column(name = "groupchat_id")
    private Long groupChatId;

    @ManyToOne
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    private Users user;

    @ManyToOne
    @JoinColumn(name = "groupchat_id",referencedColumnName = "id")
    private GroupChats groupChat;

}

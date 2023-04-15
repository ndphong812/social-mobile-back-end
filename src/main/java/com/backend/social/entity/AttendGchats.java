package com.backend.social.entity;


import com.backend.social.entity.idclass.AttendGchatId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Table(name = "attend_gchats")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@IdClass(AttendGchatId.class)
public class AttendGchats implements Serializable {

    @Id
    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;

    @Id
    @ManyToOne
    @JoinColumn(name = "groupchat_id")
    private GroupChats groupChat;


}

package com.backend.social.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.sql.Timestamp;


@Entity
@Table(name = "messages")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Messages implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "content")
    @NotBlank
    private String content;

    @Column(name = "isDeleted")
    @NotNull
    private Boolean isDeleted;

    @Column(name = "sendTime")
    @CreationTimestamp
    private Timestamp sendTime;

    @Column(name = "linkFile",nullable = true)
    private String linkFile;

    @ManyToOne
    @JoinColumn(name = "userId",referencedColumnName = "id")
    private Users user;

    @ManyToOne
    @JoinColumn(name = "chat_id",referencedColumnName = "id",nullable = true)
    private Chats chat;

    @ManyToOne
    @JoinColumn(name = "chat_id",referencedColumnName = "id",nullable = true)
    private GroupChats groupChat;

    @Column(name = "avatar",nullable = true)
    private String avatar;

    @Column(name = "fullName")
    @NotBlank
    private String fullName;

    @Column(name = "type")
    @NotBlank
    private String type;
}

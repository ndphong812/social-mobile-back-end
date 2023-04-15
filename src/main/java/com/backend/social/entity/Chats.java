package com.backend.social.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Table(name = "chats")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Chats implements Serializable {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "isDeleted")
    @NotNull
    private Boolean isDeleted;

    @ManyToOne
    @JoinColumn(name = "first_user")
    @NotNull
    private Users firstUser;

    @ManyToOne
    @JoinColumn(name = "second_user")
    @NotNull
    private Users secondUser;
}

package com.backend.social.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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

    @Column(name = "firstUser")
    @NotNull
    private Long firstUser;

    @Column(name = "secondUser")
    @NotNull
    private Long secondUser;
}

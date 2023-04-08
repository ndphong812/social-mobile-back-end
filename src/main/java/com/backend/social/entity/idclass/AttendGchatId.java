package com.backend.social.entity.idclass;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;


@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class AttendGchatId implements Serializable {
    private Long userId;
    private Long groupChatId;
}

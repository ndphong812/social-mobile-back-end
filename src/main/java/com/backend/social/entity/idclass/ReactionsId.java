package com.backend.social.entity.idclass;

import com.backend.social.entity.Posts;
import com.backend.social.entity.Users;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.*;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ReactionsId implements Serializable {

    private Long user;

    private Long post;

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}

package com.backend.social.entity.idclass;

import com.backend.social.entity.Posts;
import com.backend.social.entity.Users;
import lombok.*;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ReactionsId implements Serializable {
    private Users userId;
    private Posts postId;

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}

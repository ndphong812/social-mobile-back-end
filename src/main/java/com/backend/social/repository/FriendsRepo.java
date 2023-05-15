package com.backend.social.repository;


import com.backend.social.entity.Friends;
import com.backend.social.entity.idclass.FriendsId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FriendsRepo extends JpaRepository<Friends, FriendsId> {
        List<Friends> findAll();
}

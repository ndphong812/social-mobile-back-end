package com.backend.social.service;

import com.backend.social.entity.Friends;

import java.util.List;

public interface FriendService {
    List<Friends> findAll();
}

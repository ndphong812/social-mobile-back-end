package com.backend.social.service.serviceimpl;

import com.backend.social.entity.Friends;
import com.backend.social.repository.FriendsRepo;
import com.backend.social.service.FriendService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FriendServiceImpl implements FriendService {

    private final FriendsRepo repo;

    @Override
    public List<Friends> findAll() {
        return repo.findAll();
    }
}

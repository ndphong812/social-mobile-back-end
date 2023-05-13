package com.backend.social.service.serviceimpl;

import com.backend.social.entity.Users;
import com.backend.social.repository.UsersRepo;
import com.backend.social.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UsersRepo repo;

    @Override
    public List<Users> findAll() {
        return repo.findAll();
    }

    @Override
    public int enableUser(String email) {
        return repo.enableUser(email);
    }


}

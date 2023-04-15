package com.backend.social.service.serviceimpl;

import com.backend.social.entity.Blocks;
import com.backend.social.repository.BlocksRepo;
import com.backend.social.service.BlockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class BlockSeriveImpl implements BlockService {

    @Autowired
    private BlocksRepo repo;

    @Override
    public List<Blocks> findAll() {
        return repo.findAll();
    }

    @Override
    public List<Blocks> findByUser() {
        return repo.findByUser_Id(1L);
    }
}

package com.backend.social.service;


import com.backend.social.entity.Blocks;
import org.springframework.stereotype.Service;

import java.util.List;


public interface BlockService {
    public List<Blocks> findAll();
    public List<Blocks> findByUser();
}

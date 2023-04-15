package com.backend.social.repository;

import com.backend.social.entity.Blocks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlocksRepo extends JpaRepository<Blocks,Long> {
    @Override
    List<Blocks> findAll();

    List<Blocks> findByUser_Id(Long userId);

}

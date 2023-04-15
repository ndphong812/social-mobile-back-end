package com.backend.social.controller;

import java.util.List;

import com.backend.social.config.DataSourceConfig;
import com.backend.social.entity.Blocks;
import com.backend.social.repository.UsersRepo;
import com.backend.social.service.BlockService;
import com.backend.social.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.social.entity.Users;

import javax.sql.DataSource;

@RestController
@RequestMapping("api/v1/")
public class UserController {


	@Autowired
	private UserService userService;

	@Autowired
	private BlockService blockService;

	@GetMapping("home")
	public List<Blocks> getHome(){
		return blockService.findAll();
	}
}

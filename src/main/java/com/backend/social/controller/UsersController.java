package com.backend.social.controller;

import com.backend.social.service.FriendService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/")
@AllArgsConstructor
public class UsersController {

	private final FriendService friendService;

}

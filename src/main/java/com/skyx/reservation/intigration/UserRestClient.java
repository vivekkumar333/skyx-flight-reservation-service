package com.skyx.reservation.intigration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.skyx.reservation.entity.User;

@Component
public class UserRestClient {
	
	@Autowired
	@Qualifier("resttemplate")
	RestTemplate restTemplate;
	
	private final static String USER_REST_URL = "http://skyx-user-service/skyx-airlines/userservice/user/";

	public User findUser(Long userId) {
		
		return restTemplate.getForObject(USER_REST_URL+userId, User.class);
	}
}

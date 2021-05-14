package com.skyx.reservation.intigration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.skyx.reservation.dto.AddCardDTO;

@Component
public class CardRestClient {
	
	@Autowired
	@Qualifier("resttemplate")
	RestTemplate restTemplate;
	
	public static final String USER_CARD_URL = "http://skyx-user-service/skyx-airlines/userservice/card/addcard/";
	
	public void addCard(AddCardDTO addCardDTO) {
		

		ResponseEntity<String> res= restTemplate.postForEntity(USER_CARD_URL, addCardDTO, String.class);
	
		System.out.println("======== Card Rest Client ========="+res.getBody());	
	}
}

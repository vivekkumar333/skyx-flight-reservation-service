package com.skyx.reservation.entity;

import java.util.List;

import lombok.Data;

@Data
public class User {
	private Long id;
	
    private String name;
    
    private String phone;
    
    private String email;
    
    private int loginStatus;
    
    private List<Card> cards;
}
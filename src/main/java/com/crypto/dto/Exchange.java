package com.crypto.dto;

import java.util.Map;

import lombok.Data;

@Data
public class Exchange {
	private String name;
	private String link;
	private Map<String, Coin> coinMap;
}

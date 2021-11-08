package com.crypto.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Recommendation {
	private String activity;
	private Coin coin;
	private Exchange exchange;
}

package com.crypto.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Coin {
	private String name;
	private String symbol;
	private String currency;
	private double buyPrice;
	private double sellPrice;
}

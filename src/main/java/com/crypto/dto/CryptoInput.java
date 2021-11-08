package com.crypto.dto;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class CryptoInput {
	List<String> coins;
	List<String> exchanges;
	List<String> currencies;
}

package com.crypto.helper;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Component
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PriceResponse {
	@JsonAlias({ "bid", "bidRate" })
	private String bidPrice;
	@JsonAlias({ "ask", "askRate" })
	private String askPrice;
}

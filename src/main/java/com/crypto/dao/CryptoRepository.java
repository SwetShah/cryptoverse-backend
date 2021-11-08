package com.crypto.dao;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import com.crypto.helper.PriceResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;

@Repository
@AllArgsConstructor(onConstructor = @__({ @Autowired }))
public class CryptoRepository {
	private RestTemplate restTemplate;
	private ObjectMapper mapper;

	public PriceResponse getAPIDetails(String url) {
		URI uri = null;
		PriceResponse priceResponse = new PriceResponse();
		try {
			uri = new URI(url);
			ResponseEntity<String> response = restTemplate.getForEntity(uri, String.class);
			String json = response.getBody();

			// TODO: One instance of Object Mapper can be created using the Singleton Design
			// pattern and used throughout the application
			priceResponse = mapper.readValue(json, PriceResponse.class);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return priceResponse;
	}

}

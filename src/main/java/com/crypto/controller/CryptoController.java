package com.crypto.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.crypto.dto.CryptoInput;
import com.crypto.dto.Exchange;
import com.crypto.service.CryptoService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/crypto")
@CrossOrigin(origins = "*")
@AllArgsConstructor(onConstructor = @__({ @Autowired }))
public class CryptoController {

	private CryptoService cryptoService;

	/*
	 * 
	 * 
	 * @param exchanges Comma separated list of exchanges
	 * 
	 * @param coins Comma separated list of coins
	 * 
	 * @param currency currency for getting the prices (currently only usd is
	 * supported for the scope of this assignment)
	 * 
	 * <p> This is for fetching prices for each exchange, coin and currency
	 */

	@PostMapping("/price")
	public ResponseEntity<Map> prices(@RequestBody CryptoInput cryptoInput)
			throws InterruptedException, ExecutionException {

		Map<String, String> errors = new HashMap<String, String>();
		try {
			Map<String, Exchange> prices = cryptoService.getCryptoPrice(cryptoInput.getExchanges(),
					cryptoInput.getCoins(), cryptoInput.getCurrencies());
			return ResponseEntity.ok(prices);
		} catch (Exception e) {
			e.printStackTrace();
			errors.put("Failure", "Something went wrong!");// Exception handling can be improved
			return ResponseEntity.badRequest().body(errors);
		}
	}

	/*
	 * 
	 * 
	 * @param exchanges Comma separated list of exchanges
	 * 
	 * @param coins Comma separated list of coins
	 * 
	 * @param currency currency for getting the prices (currently only usd is
	 * supported for the scope of this assignment)
	 * 
	 * <p> This is for recommending exchange for buy and sell
	 */
	@PostMapping("/recommendations")
	public ResponseEntity<List> recommendExchangeForTransaction(@RequestBody CryptoInput cryptoInput)
			throws InterruptedException, ExecutionException {

		Map<String, String> errors = new HashMap<String, String>();
		try {
			return ResponseEntity.ok(cryptoService.getRecommendations(cryptoInput.getExchanges(),
					cryptoInput.getCoins(), cryptoInput.getCurrencies()));
		} catch (Exception e) {
			e.printStackTrace();
			errors.put("Failure", "Something went wrong!"); // Exception handling can be improved
			return ResponseEntity.badRequest().body(new ArrayList<>(Arrays.asList(errors)));
		}
	}

	// TODO: Implement for future
	@GetMapping("/coin/symbol")
	public ResponseEntity<String> coinSymbol(@RequestParam(name = "coinName") String coinName) {

		return null;
	}

}

package com.crypto.helper;

import java.text.MessageFormat;
import java.util.Map;
import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import com.crypto.dao.CryptoRepository;
import com.crypto.dto.Coin;
import com.crypto.dto.Exchange;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor(onConstructor = @__({ @Autowired }))
public class AsyncService {

	private CryptoRepository cryptoRepository;

	/*
	 * A Trade off can be made to this method to make only single API call for each
	 * exchange and save the data in-memory and process further internally instead
	 * of making too many API calls. This also needs that there must be an API that
	 * returns me all the data in single call
	 */
	// This can be made @Async for multiple calls to network in parallel
	@Async
	public Future<Exchange> getEachCryptoPrice(String exchange, String coin, String currency,
			Map<String, Map<String, String>> apiDetails, Exchange exchangeInstance) {

		String url = apiDetails.get("API").get(exchange.toLowerCase());
		String currencyName = apiDetails.get("symbols").get(coin.toUpperCase());

		url = MessageFormat.format(url, coin.toLowerCase(), currency.toLowerCase());
		PriceResponse priceResponse = cryptoRepository.getAPIDetails(url);
		Coin coinDetails = new Coin(currencyName, coin, currency, Double.valueOf(priceResponse.getAskPrice()),
				Double.valueOf(priceResponse.getBidPrice()));
		exchangeInstance.getCoinMap().put(coin, coinDetails);

		return new AsyncResult<Exchange>(exchangeInstance);
	}

}

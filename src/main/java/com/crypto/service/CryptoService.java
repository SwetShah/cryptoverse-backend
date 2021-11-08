package com.crypto.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.crypto.dto.Coin;
import com.crypto.dto.Exchange;
import com.crypto.dto.Recommendation;
import com.crypto.helper.AsyncService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor(onConstructor = @__({ @Autowired }))
public class CryptoService {

	private AsyncService asyncService;

	public Map<String, Exchange> getCryptoPrice(List<String> exchanges, List<String> coins, List<String> currencies)
			throws InterruptedException, ExecutionException {
		Map<String, Map<String, String>> apiDetails = new HashMap<String, Map<String, String>>();

		// This will change to fetch the data from Consul
		apiDetails = getAllInfoNeeded();
		// To store the responses fetched from url
		Map<String, Exchange> mapOfExchanges = new HashMap<String, Exchange>();
		List<Future<Exchange>> futures = new ArrayList<Future<Exchange>>();

		for (String eachExchange : exchanges) {
			Map<String, Coin> coinMap = Collections.synchronizedMap(new HashMap<String, Coin>());
			Exchange exchangeInstance = new Exchange();
			exchangeInstance.setName(eachExchange);
			exchangeInstance.setLink(apiDetails.get("links").get(eachExchange.toLowerCase()));
			exchangeInstance.setCoinMap(coinMap);
			mapOfExchanges.put(eachExchange, exchangeInstance);

			for (String eachCoin : coins) {
				for (String eachCurrency : currencies) {
					futures.add(asyncService.getEachCryptoPrice(eachExchange, eachCoin, eachCurrency, apiDetails,
							exchangeInstance));
				}
			}
		}

		for (Future<Exchange> future : futures) {
			future.get();
		}

		return mapOfExchanges;
	}

	public List<Recommendation> getRecommendations(List<String> exchanges, List<String> coins, List<String> currencies)
			throws InterruptedException, ExecutionException {
		List<Recommendation> recommendations = new ArrayList<>();
		Map<Coin, String> mapOfCoinAndExchange = new HashMap<Coin, String>();
		Map<String, List<Coin>> mapOfEachCoins = new HashMap<String, List<Coin>>();
		Map<String, Exchange> prices = new HashMap<String, Exchange>();

		prices = getCryptoPrice(exchanges, coins, currencies);
		for (Map.Entry<String, Exchange> entry : prices.entrySet()) {
			for (String coin : entry.getValue().getCoinMap().keySet()) {
				Coin c = entry.getValue().getCoinMap().get(coin);
				mapOfCoinAndExchange.put(c, entry.getKey());

				if (!CollectionUtils.isEmpty(mapOfEachCoins.get(coin))) {
					mapOfEachCoins.get(coin).add(c);
				} else {
					mapOfEachCoins.put(coin, new ArrayList<>(Arrays.asList(c)));
				}
			}
		}

		for (String coinSymbol : mapOfEachCoins.keySet()) {
			List<Coin> coinList = mapOfEachCoins.get(coinSymbol);
			coinList.sort((Coin c1, Coin c2) -> Double.compare(c1.getBuyPrice(), c2.getBuyPrice()));
			Exchange buyExchange = prices.get(mapOfCoinAndExchange.get(coinList.get(0)));
			Recommendation buyRecommendation = new Recommendation("Buy", coinList.get(0), buyExchange);
			recommendations.add(buyRecommendation);

			coinList.sort((Coin c1, Coin c2) -> Double.compare(c2.getSellPrice(), c1.getSellPrice()));
			Exchange sellExchange = prices.get(mapOfCoinAndExchange.get(coinList.get(0)));
			Recommendation sellRecommendation = new Recommendation("Sell", coinList.get(0), sellExchange);
			recommendations.add(sellRecommendation);
		}

		return recommendations;
	}

	/*
	 * This is creating the properties manually This can be externalized using
	 * properties file or using some 3rd party applications such as Apache Consul
	 */
	public Map<String, Map<String, String>> getAllInfoNeeded() {
		Map<String, String> symbols = new HashMap<String, String>();
		Map<String, String> api = new HashMap<String, String>();
		Map<String, String> links = new HashMap<String, String>();
		Map<String, Map<String, String>> apiDetails = new HashMap<String, Map<String, String>>();

		symbols.put("BTC", "Bitcoin");
		symbols.put("ETH", "Ethereum");

		api.put("bittrex", "https://api.bittrex.com/v3/markets/{0}-{1}/ticker");
		api.put("gemini", "https://api.gemini.com/v1/pubticker/{0}{1}");

		links.put("bittrex", "https://bittrex.com/");
		links.put("gemini", "https://www.gemini.com/");

		apiDetails.put("API", api);
		apiDetails.put("links", links);
		apiDetails.put("symbols", symbols);

		return apiDetails;
	}
}

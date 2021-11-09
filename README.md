# cryptoverse-backend

Please refer the below link to see the working of the application live.

FrontEnd - https://cryptoverse-ui.herokuapp.com/

Backend - https://crypto-verse-swet.herokuapp.com/crypto/

API Details:

1. https://crypto-verse-swet.herokuapp.com/crypto/price (POST request)

- Getting the prices of each coins in corresponding exchanges.

**Input**: List of coins, exchanges and currencies to find the prices.

**Output**: Map of Exchanges and Currencies of each coin.


2. https://crypto-verse-swet.herokuapp.com/crypto/recommendations (POST request)

- Getting the recommmendations to buy and sell coins on what exchanges.

**Input**:  List of coins, exchanges and currencies to find the prices.

**Output**: List of all the coin details to buy and sell on correspoding Exchanges.


## 1. Are there any sub-optimal choices( or short cuts taken due to limited time ) in your implementation?
- There are many places where optimization can be made related to performance, code readability or redundancy, scalability which have been mentioned in the Code itself as comments.
- Code has been written to handle multiple exchanges for multiple coins but for the current scope only single currency is taken into consideration.
- API urls and Coin data such as BTC or Bitcoin or Exchange Data such as Bittrex or Gemini have been hardcoded but the prices are fetched in real-time from the API call.
- UI can be further improved with respect to code and designing.


## 2. Is any part of it over-designed? ( It is fine to over-design to showcase your skills as long as you are clear about it)
- Two different API’s have been created to fetch the price and get the recommendations. This can be merged into one but this would mean that each time the data is fetched from exchanges, it needs to be sorted and compared and then the result is sent to client which is not necessary for just viewing the prices and hence 2 different API’s.
- Currently, (numberOfExchanges*numberOfCoins) number of network calls are made my making the tradeoff of instead fetching the entire data from an exchange (considering the exchange provide an API to fetch the complete data of all the coins in one API call) and storing in-memory and then processing or making the network calls and fetching the exact data needed at each call. This trade off can be improved as the per the API used for fetching the data or processing it in-memory.


## 3. If you have to scale your solution to 100 users/second traffic what changes would you make, if any?
- Currently, the system is designed to handle the concurrent user requests for fetching the prices from exchanges which are independent of each other.
- This can be further improved by identifying an API of an exchange that can accept the list of coin-currency pairs and return the response accordingly. Some research on API finding can help in still reducing this calls and hence overall performance
- The application is using the custom thread pool provided by Spring Boot application to handle the asynchronous process. Need to research if some of the configuration changes that might help in improving the performance.

## 4. What are some other enhancements you would have made, if you had more time to do this implementation
- Some properties have been hardcoded in the application such as API url and cryptocurrency information which can be externalized using properties file or some 3rd party applications.
- Exception Handling and Logging are some of the features which can be implemented.
- Multiple exchanges and coins are considered but multiple currencies need to be implemented
- Some more research on API’s that accepts the list of inputs of coin and currency pair and generates output in a single network call saving too many network calls.
- Database can be introduced to store the transactions for future scope.

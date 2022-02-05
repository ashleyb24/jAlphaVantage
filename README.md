##jAlphaVantage

A Java wrapper to get stock and currency data from the Alpha Vantage API.

Alpha Vantage provides free APIs to access financial market data. 
This library implements a wrapper to get data provided by Alpha Vantage.
To begin, get an API Key from the [Alpha Vantage website](https://www.alphavantage.co/support/#api-key).

###Usage
By default, this library uses OkHttp to call the Alpha Vantage APIs. 
Alternatively you can make use of a Spring RestTemplate you may already have defined 
by including the RestTemplate object into the AlphaVantage constructor. 
####Global Quote
```java
public class Application {
    public static void main(String[] args) {
        String apiKey = "Your_API_Key";
        AlphaVantage alphaVantage = new AlphaVantage(apiKey);
        try {
            String symbol = "AAPL";
            GlobalQuote globalQuote = alphaVantage.execute(new GlobalQuoteQuery(symbol));
            System.out.println(globalQuote);
        } catch (AlphaVantageException e) {
            System.out.println("Error occurred");
        }
    }
}
```

####Currency Exchange
```java
public class Application {
    public static void main(String[] args) {
        String apiKey = "Your_API_Key";
        AlphaVantage alphaVantage = new AlphaVantage(apiKey);
        try {
            String fromCurrency = "GBP";
            String toCurrency = "USD";
            CurrencyExchange currencyExchange = alphaVantage.execute(new CurrencyExchangeQuery(fromCurrency, toCurrency));
            System.out.println(currencyExchange);
        } catch (AlphaVantageException e) {
            System.out.println("Error occurred");
        }
    }
}
```
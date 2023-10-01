package currencyConverter;
import java.util.HashMap;

public class Currency {
	private String name;
	private String shortName;
	private HashMap<String, Double> exchangeRates;

	// "Currency" Constructor
	public Currency(String nameValue, String shortNameValue, HashMap<String, Double> exchangeRates) {
		this.name = nameValue;
		this.shortName = shortNameValue;
		this.exchangeRates = exchangeRates;
	}
	// Getter for HashMap
	public HashMap<String, Double> getExchangeRates() {
		return exchangeRates;
	}

	// Getter for name
	public String getName() {
		return this.name;
	}

	// Getter for shortName
	public String getShortName() {
		return this.shortName;
	}

}

package currencyConverter;
import java.io.FileReader;
import java.util.ArrayList;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.IOException;
public class RateManager {
    private static RateManager instance;
    private ArrayList<Currency> currencies = new ArrayList<>();



    // Get a list of currency names
    public ArrayList<String> getCurrencyList() {
        ArrayList<String> currencyList = new ArrayList<>();
        for (Currency currency : currencies) {
            currencyList.add(currency.getName());
        }
        return currencyList;
    }

    // Get the singleton instance of RateManager
    public static RateManager getInstance() {
        if (instance == null) {
            instance = new RateManager();
        }
        return instance;
    }

    // Get the exchange rate between two currencies
    public Double getExchangeRate(String currency1, String currency2) {
        String shortname2 = "";
        for (Currency currency : currencies){
            if (currency.getName().equals(currency2)){
                shortname2 = currency.getShortName();
            }
        }
        for (Currency currency : currencies){
            if (currency.getName().equals(currency1)){
                return currency.getExchangeRates().get(shortname2);
            }
        }
        return null;
    }
}




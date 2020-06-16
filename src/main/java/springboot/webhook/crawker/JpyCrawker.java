package springboot.webhook.crawker;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class JpyCrawker {
	public static Map<String, String> getExchangeRate() throws IOException {
	  	
    	// URL
        String url = "http://rate.bot.com.tw/Pages/Static/UIP003.zh-TW.htm";            
        Document doc = Jsoup.connect(url).get();
        Element usd = doc.select("td.rate-content-sight.text-right.print_hide").get(1);
        Element hkd = doc.select("td.rate-content-sight.text-right.print_hide").get(3);
        Element gpb = doc.select("td.rate-content-sight.text-right.print_hide").get(5);
        Element aud = doc.select("td.rate-content-sight.text-right.print_hide").get(7);
        Element cad = doc.select("td.rate-content-sight.text-right.print_hide").get(9);
        Element sgd = doc.select("td.rate-content-sight.text-right.print_hide").get(11);
        Element chf = doc.select("td.rate-content-sight.text-right.print_hide").get(13);
        Element jpy = doc.select("td.rate-content-sight.text-right.print_hide").get(15);
        
	    Map<String, String> exchangeRate = new HashMap<>();
	    exchangeRate.put("USD", usd.text());
	    exchangeRate.put("HKD", hkd.text());
	    exchangeRate.put("GPB", gpb.text());
	    exchangeRate.put("AUD", aud.text());
	    exchangeRate.put("CAD", cad.text());
	    exchangeRate.put("SGD", sgd.text());
	    exchangeRate.put("CHF", chf.text());
	    exchangeRate.put("JPY", jpy.text());
	    
	    System.out.println(exchangeRate);
        return exchangeRate;
        
	}
	
	public static void main(String[] args) throws IOException {
		getExchangeRate();
	}
}

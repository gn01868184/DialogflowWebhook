package crawker.JPY;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class JpyCrawker {
	public static String getExchangeRate() throws IOException {
	  	
    	// URL
        String url = "http://rate.bot.com.tw/Pages/Static/UIP003.zh-TW.htm";            
        Document doc = Jsoup.connect(url).get();
        Element h1s = doc.select("td.rate-content-sight.text-right.print_hide").get(15);
        System.out.print(h1s.text());
        return h1s.text();
        
	}
	
	public static void main(String[] args) throws IOException {
		getExchangeRate();
	}
}

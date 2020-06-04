package springboot.webhook.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DialogflowOutput {
	public Map<String, List<Map<String, Map<String, List<String>>>>> output(String addText) {
		
	    List<String> text = new ArrayList<String>();
	    text.add(addText);
	    
	    Map<String, List<String>> textInside = new HashMap<>();
	    textInside.put("text", text);
	    
	    Map<String, Map<String, List<String>>> textOutside = new HashMap<>();
	    textOutside.put("text", textInside);
	    
	    List<Map<String, Map<String, List<String>>>> fulfillmentMessages = new ArrayList<Map<String, Map<String, List<String>>>>();
	    fulfillmentMessages.add(textOutside);
	    
	    Map<String, List<Map<String, Map<String, List<String>>>>> data = new HashMap<>();
	    data.put("fulfillmentMessages", fulfillmentMessages);
        
        return data;
	}
}

package springboot.webhook.controller;

import springboot.webhook.model.DialogflowOutput;
import crawker.JPY.JpyCrawker;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.IOException;
import java.util.ArrayList;

@Controller
public class WebhookController {
	@ResponseBody
    @PostMapping(path="/webhook", produces="application/json")
    public Map<String, List<Map<String, Map<String, List<String>>>>> test(@RequestBody Map<String, Object> json) {

	    System.out.println("WebHook collected JSON: " + json);

	    String text = "目前找不到日幣匯率";
	    
	    JpyCrawker jpyCrawker = new JpyCrawker();
	    try {
	    	text = jpyCrawker.getExchangeRate();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    DialogflowOutput dialogflowOutput = new DialogflowOutput();
	    Map<String, List<Map<String, Map<String, List<String>>>>> response = dialogflowOutput.output(text);
        
	    return response;
    }
}

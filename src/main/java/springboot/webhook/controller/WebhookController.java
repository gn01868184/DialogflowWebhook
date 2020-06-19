package springboot.webhook.controller;

import springboot.webhook.crawker.JpyCrawker;
import springboot.webhook.model.DialogflowOutput;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import springboot.webhook.mongodb.entity.Session;
import springboot.webhook.mongodb.entity.SessionRequest;
import springboot.webhook.mongodb.repository.SessionRepository;
import springboot.webhook.mongodb.service.SessionService;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import java.io.IOException;
import java.net.URI;

@EnableScheduling
@Controller
public class WebhookController {
    @Autowired
    private SessionService sessionService;
    @Autowired
    private SessionRepository repository;
    
	@ResponseBody
	@GetMapping(value = "/rate", produces="application/json")
	public Map<String, String> rate() throws IOException{
			JpyCrawker jpyCrawker = new JpyCrawker();
			Map<String, String> rate = new HashMap<>();
			rate = jpyCrawker.getExchangeRate();
			return rate;
	}

	@ResponseBody
    @PostMapping(path="/webhook", produces="application/json")
    public Map<String, List<Map<String, Map<String, List<String>>>>> test(@RequestBody Map<String, Object> json) {

	    System.out.println("WebHook collected JSON: " + json);
	    String intent = ((Map<String,Object>) ((Map<String,Object>) json.get("queryResult")).get("intent")).get("displayName").toString();
	    System.out.println("intent: " + intent);
	    String text = "目前維修中";
	    
	    switch (intent){
	    	case "findExchangeRate":
	    	    String contury = ((Map<String,Object>) ((Map<String,Object>) json.get("queryResult")).get("parameters")).get("exchangeRate").toString();
	    	    System.out.println("contury: " + contury);
	    	    try {
	    		    JpyCrawker jpyCrawker = new JpyCrawker();
	    		    Map<String, String> exchangeRate = new HashMap<>();
	    		    exchangeRate = jpyCrawker.getExchangeRate();
	    	    	text = "目前匯率:" + exchangeRate.get(contury).toString();
	    		} catch (IOException e) {
	    			// TODO Auto-generated catch block
	    			e.printStackTrace();
	    		}
	    		break;
	    		
	    	case "dailyNotification":
	    		String sessionId = ((Map<String,Object>) ((Map<String,Object>) ((Map<String,Object>) ((Map<String,Object>) json.get("originalDetectIntentRequest")).get("payload")).get("data")).get("sender")).get("id").toString();
	    		System.out.println("sessionId: " + sessionId);
	    		Map<String, String> sessionIdMap = new HashMap<>();
	    		sessionIdMap.put("sessionId", sessionId);
	            Session session = sessionService.createSession(sessionIdMap);
	    		text = "設定好每日通知囉";
	    		break;
	    	case "cancelDailyNotification":
	    		String cancelSessionId = ((Map<String,Object>) ((Map<String,Object>) ((Map<String,Object>) ((Map<String,Object>) json.get("originalDetectIntentRequest")).get("payload")).get("data")).get("sender")).get("id").toString();
	    		System.out.println("sessionId: " + cancelSessionId);
	            repository.deleteBySessionId(cancelSessionId);
	            text = "取消每日通知囉";
	    		break;
	    }
	    DialogflowOutput dialogflowOutput = new DialogflowOutput();
	    Map<String, List<Map<String, Map<String, List<String>>>>> response = dialogflowOutput.output(text);
	    return response;
    }
	
	    @Scheduled(cron = "0/5 * * * * *")
	    public void scheduled(){
	    	
	        List<Session> session = sessionService.getSession();
	        String rateText = "目前找不到匯率";
		    try {
			    JpyCrawker jpyCrawker = new JpyCrawker();
			    Map<String, String> exchangeRate = new HashMap<>();
			    exchangeRate = jpyCrawker.getExchangeRate();
		    	rateText = exchangeRate.get("JPY").toString();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        
	        for(int i=0;i<session.size();i++) {
	        	System.out.println("sessionId: " + session.get(i).getSessionId());
	        	
			    Map<String, Object> postText = new HashMap<>();
			    postText.put("messaging_type", "RESPONSE");
			    
			    
			    Map<String, String> id = new HashMap<>();
			    id.put("id", session.get(i).getSessionId());
			    
			    postText.put("recipient", id);
			    
			    Map<String, String> text = new HashMap<>();
			    text.put("text", "目前日幣匯率:" + rateText);

			    postText.put("message", text);
			    
			    System.out.println(postText);
			    
		        RestTemplate restTemplate=new RestTemplate();
		        String url="https://graph.facebook.com/v7.0/me/messages?access_token=EAAICNR9067sBAKlWCl6vCd3yZAZCCQZBm2iyAwhhgA8wwuwyZBScPZCjbyqFMejpeSMcMhG43GZApSbW23m690VuNUA7EuNu15aH9ZARF4u8KO9dsZAxLFyahJEHZCABZAGxra14ZAKr5S3UJQZBo71a4s2aauttlOf1SHzx9P6lQT1Npe33sahhfjtB";
		        HttpHeaders headers = new HttpHeaders();
		        headers.setContentType(MediaType.APPLICATION_JSON);
		        HttpEntity entity = new HttpEntity(postText, headers);
		        restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
	        }
	    }

	@GetMapping(value = "/findSession")
    public ResponseEntity<List<Session>> getSession() {

        List<Session> session = sessionService.getSession();
        for(int i=0;i<session.size();i++) {
        	System.out.println(session.get(i).getSessionId());
        }
        return ResponseEntity.ok(session);
    }

}

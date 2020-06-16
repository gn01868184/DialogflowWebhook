package springboot.webhook.mongodb.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import springboot.webhook.mongodb.entity.Session;
import springboot.webhook.mongodb.entity.SessionRequest;
import springboot.webhook.mongodb.repository.SessionRepository;

@Service
public class SessionService {

    @Autowired
    private SessionRepository repository;
    
	public SessionService(SessionRepository repository) {
		this.repository = repository;
	}
    
    public List<Session> getSession() {
        return repository.findAll();
    }

    public Session createSession(Map<String, String> sessionId) {
    	Session session = new Session();
    	session.setSessionId(sessionId.get("sessionId"));

        return repository.insert(session);
    }

}

package springboot.webhook.mongodb.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import springboot.webhook.mongodb.entity.Session;

public interface SessionRepository extends MongoRepository<Session, String> {
	public Session findBySessionId(String sessionId);   
	public Session deleteBySessionId(String sessionId);   
}
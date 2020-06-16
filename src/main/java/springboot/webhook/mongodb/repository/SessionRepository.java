package springboot.webhook.mongodb.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import springboot.webhook.mongodb.entity.Session;

@Repository
public interface SessionRepository extends MongoRepository<Session, String> {
	public Session findBySessionId(String sessionId);   
}
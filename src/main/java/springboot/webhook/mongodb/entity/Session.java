package springboot.webhook.mongodb.entity;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "session")
public class Session {
	
	private String id;
	private String sessionId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	@Override
	public String toString() {
		return "Session [id=" + id + ", sessionId=" + sessionId + "]";
	}

}

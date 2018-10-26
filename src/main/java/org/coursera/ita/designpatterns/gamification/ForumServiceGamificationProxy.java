package org.coursera.ita.designpatterns.gamification;

public class ForumServiceGamificationProxy implements ForumService {
	
	private ForumService service;
	private GamificationService gamification;
	
	public ForumServiceGamificationProxy(ForumService service) {
		this.service = service;
		this.gamification = new GamificationService();
	}

	@Override
	public void addTopic(String user, String topic) {		
		service.addTopic(user, topic);
		gamification.incrementPoints(user, "CREATION", 5);
		gamification.addBadge(user, "I CAN TALK");
	}

	@Override
	public void addComment(String user, String topic, String comment) {
		service.addComment(user, topic, comment);
		gamification.incrementPoints(user, "PARTICIPATION", 3);
		gamification.addBadge(user, "LET ME ADD");
	}

	@Override
	public void likeTopic(String user, String topic, String topicUser) {
		service.likeTopic(user, topic, topicUser);
		gamification.incrementPoints(user, "CREATION", 1);
	}

	@Override
	public void likeComment(String user, String topic, String comment, String commentUser) {
		service.likeComment(user, topic, comment, commentUser);
		gamification.incrementPoints(user, "PARTICIPATION", 1);
	}
		
	
}

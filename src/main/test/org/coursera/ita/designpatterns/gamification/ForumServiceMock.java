package org.coursera.ita.designpatterns.gamification;

public class ForumServiceMock implements ForumService {

	public boolean shoudThrowException = false;
	
	@Override
	public void addTopic(String user, String topic) {
		if(shoudThrowException) {
			throw new RuntimeException("Ooops!");
		}
	}

	@Override
	public void addComment(String user, String topic, String comment) {

	}

	@Override
	public void likeTopic(String user, String topic, String topicUser) {

	}

	@Override
	public void likeComment(String user, String topic, String comment, String commentUser) {

	}

}

package org.coursera.ita.designpatterns.gamification;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

public class FormServiceGamificationTests {

	private MemoryAchievementStorage storage;
	private ForumServiceGamificationProxy proxy;
	private ForumServiceMock service;

	@Before
	public void setup() {

		storage = new MemoryAchievementStorage();
		storage.addAchievementObserver(new ParticipationPointsObserver());
		storage.addAchievementObserver(new CreationPointsObserver());
		AchievementStorageFactory.setAchievementStorage(storage);

		service = new ForumServiceMock();
		proxy = new ForumServiceGamificationProxy(service);
	}

	@Test
	public void shoudAddFivePointsOfTypeCreationWhenTopicIsAdded() {

		proxy.addTopic("johnowl", "Hello world!");

		Points points = (Points) storage.getAchievement("johnowl", "CREATION");

		assertEquals(5, points.getAmount());
	}

	@Test
	public void shoudAddBadgeWhenTopicIsAdded() {

		proxy.addTopic("johnowl", "Hello world!");

		Achievement badge = storage.getAchievement("johnowl", "I CAN TALK");

		assertTrue(badge instanceof Badge);
	}

	@Test
	public void shoudAddThreePointsOfTypeParticipationWhenCommentIsAdded() {

		proxy.addTopic("johnowl", "Hello world!");
		proxy.addComment("bot", "Hello World!", "I like this.");

		Points points = (Points) storage.getAchievement("bot", "PARTICIPATION");

		assertEquals(3, points.getAmount());
	}

	@Test
	public void shoudAddBadgeWhenCommentIsAdded() {

		proxy.addTopic("johnowl", "Hello world!");
		proxy.addComment("bot", "Hello World!", "I like this.");

		Achievement badge = storage.getAchievement("bot", "LET ME ADD");

		assertTrue(badge instanceof Badge);
	}

	@Test
	public void shoudAddOnePointOfTypeCreationWhenLikeIsAdded() {

		proxy.addTopic("johnowl", "Hello world!");
		proxy.likeTopic("bot", "Hello world!", "johnowl");

		Points points = (Points) storage.getAchievement("bot", "CREATION");

		assertEquals(1, points.getAmount());
	}

	@Test
	public void shoudAddOnePointOfTypeParticipationWhenCommentIsAdded() {

		proxy.addTopic("johnowl", "Hello world!");
		proxy.addComment("bot", "Hello world!", "I like this.");
		proxy.likeComment("jack", "Hello world", "I like this.", "bot");

		Points points = (Points) storage.getAchievement("jack", "PARTICIPATION");

		assertEquals(1, points.getAmount());
	}

	@Test
	public void shoudAddTenPointsOfTypeCreationWhenTwoTopicsAreAdded() {

		proxy.addTopic("johnowl", "Hello world!");
		proxy.addTopic("johnowl", "Hello galaxy!");

		Points points = (Points) storage.getAchievement("johnowl", "CREATION");

		assertEquals(10, points.getAmount());
	}

	@Test
	public void shoudAddPointsWhenManyThingsAreMade() {

		proxy.addTopic("johnowl", "Hello world!");
		proxy.addTopic("johnowl", "Hello galaxy!");
		proxy.likeTopic("johnowl", "Hello world", "Hi there!");
		proxy.likeTopic("johnowl", "Hello world", "Hello!");
		proxy.likeTopic("johnowl", "Hello world", "Hoorray!");

		Points points = (Points) storage.getAchievement("johnowl", "CREATION");

		assertEquals(13, points.getAmount());
	}

	@Test
	public void shoudNotAddPointsWhenExceptionIsThrown() {

		service.shoudThrowException = true;

		try {
			proxy.addTopic("johnowl", "Hello world!");
			fail();
		} catch (Exception e) {
					
		}

		Achievement points = storage.getAchievement("johnowl", "CREATION");
		assertTrue(points instanceof AchievementNotFound);
	}
	
	
	@Test
	public void shoudAddInventorBadgeWhenReach100CreationPoints() {

		for(int i = 0; i < 20; i++) {
			proxy.addTopic("johnowl", "Hello world " + i);
		}			

		Points points = (Points)storage.getAchievement("johnowl", "CREATION");
		Achievement badge1 = storage.getAchievement("johnowl", "I CAN TALK");
		Achievement badge2 = storage.getAchievement("johnowl", "INVENTOR");

		assertEquals(100, points.getAmount());
		assertEquals("I CAN TALK", badge1.getName());
		assertEquals("INVENTOR", badge2.getName());
		
	}

	
	@Test
	public void shoudAddPartOfCommunityBadgeWhenReach100ParticipationPoints() {

		proxy.addTopic("johnowl", "Hello world");
		for(int i = 0; i < 34; i++) {
			proxy.addComment("johnowl", "Hello world", "Hi!");
		}

		Points points = (Points)storage.getAchievement("johnowl", "PARTICIPATION");
		Achievement badge = storage.getAchievement("johnowl", "PART OF THE COMMUNITY");

		assertEquals(102, points.getAmount());		
		assertEquals("PART OF THE COMMUNITY", badge.getName());
		
	}
}

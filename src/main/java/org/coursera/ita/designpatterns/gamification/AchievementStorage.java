package org.coursera.ita.designpatterns.gamification;

import java.util.List;

public interface AchievementStorage {
	
	void addAchievementObserver(AchievementObserver observer);
	void addAchievement(String user, Achievement a);
	List<Achievement> getAchievements(String user);
	Achievement getAchievement(String user, String achievementName);
}

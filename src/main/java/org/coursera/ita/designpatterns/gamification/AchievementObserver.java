package org.coursera.ita.designpatterns.gamification;

public interface AchievementObserver {
	void achievementUpdate(String user, Achievement a);
}

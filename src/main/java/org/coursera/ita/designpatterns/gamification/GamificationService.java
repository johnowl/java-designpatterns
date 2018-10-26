package org.coursera.ita.designpatterns.gamification;

public class GamificationService {

	private AchievementStorage storage = AchievementStorageFactory.getAchievementStorage();
	
	public void addBadge(String user, String type) {
		Achievement achievement = storage.getAchievement(user, type);
		
		if(achievementFound(achievement))
			return;
		
		storage.addAchievement(user, new Badge(type));
	}

	private boolean achievementFound(Achievement achievement) {
		return !(achievement instanceof AchievementNotFound);
	}
	
	public void incrementPoints(String user, String type, int amount) {
		Achievement achievement = storage.getAchievement(user, type);
					
		int oldAmount = 0;		
		if(achievementFound(achievement)) {
			oldAmount = ((Points)achievement).getAmount();			
		}
		
		Points points = new Points();
		points.setName(type);
		points.setAmount(oldAmount + amount);					
		
		storage.addAchievement(user, points);
		
	}
	
}

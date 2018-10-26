package org.coursera.ita.designpatterns.gamification;

public final class AchievementStorageFactory {

	private static AchievementStorage instance = null;
	
	private AchievementStorageFactory() {
		
	}
	
	public static synchronized void setAchievementStorage(AchievementStorage a) {
		instance = a;
	}
	
	public static synchronized AchievementStorage getAchievementStorage() {
		
		if(instance == null) {
			AchievementStorage storage = new MemoryAchievementStorage();
			storage.addAchievementObserver(new ParticipationPointsObserver());
			storage.addAchievementObserver(new CreationPointsObserver());
			instance = storage;
		}
		
		return instance;
	}
	
}

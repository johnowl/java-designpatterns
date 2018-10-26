package org.coursera.ita.designpatterns.gamification;

public class ParticipationPointsObserver implements AchievementObserver {
	
	private GamificationService gamification;
	private static final String BADGE_NAME = "PART OF THE COMMUNITY";
	
	@Override
	public void achievementUpdate(String user, Achievement a) {
		
		if(!isValid(a)) 
			return;
		
		if(gamification == null) {
			gamification = new GamificationService();
		}
		
		gamification.addBadge(user, BADGE_NAME);
		
	}

	private boolean isValid(Achievement a) {
		
		if(notInstanceOfPoints(a))
			return false;
		
		return isParticipationAndHasMoreThan100PointsOrIsEquals100Points(a);		
	}
	
	private boolean notInstanceOfPoints(Achievement a) {
		return !(a instanceof Points);
	}
	
	private boolean isParticipationAndHasMoreThan100PointsOrIsEquals100Points(Achievement a) {
		Points points = (Points)a;	
		return points.getName().equals("PARTICIPATION") || points.getAmount() >= 100;
	}
}

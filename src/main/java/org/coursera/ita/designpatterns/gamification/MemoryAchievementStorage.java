package org.coursera.ita.designpatterns.gamification;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MemoryAchievementStorage implements AchievementStorage {

	protected Map<String, List<Achievement>> items = new HashMap<>();
	protected List<AchievementObserver> observers = new ArrayList<>();

	@Override
	public void addAchievement(String user, Achievement newAchievement) {

		List<Achievement> list = getAchievements(user);
		Achievement existingAchievement = getAchievement(user, newAchievement.getName());
		if (existingAchievement instanceof AchievementNotFound) {
			list.add(newAchievement);
		} else {
			for (int i = 0; i < list.size(); i++) {
				if (list.get(i).getName().equals(newAchievement.getName())) {
					list.set(i, newAchievement);
				}
			}
		}

		items.put(user, list);

		for (AchievementObserver observer : observers) {
			observer.achievementUpdate(user, newAchievement);
		}
	}

	@Override
	public List<Achievement> getAchievements(String user) {
		List<Achievement> list = items.get(user);
		if (list == null) {
			return new ArrayList<>();
		}
		return list;
	}

	@Override
	public Achievement getAchievement(String user, String achievementName) {

		List<Achievement> list = getAchievements(user);
		for (Achievement a : list) {
			if (achievementName.equals(a.getName())) {
				return a;
			}
		}

		return new AchievementNotFound();
	}

	@Override
	public void addAchievementObserver(AchievementObserver observer) {
		observers.add(observer);
	}

}

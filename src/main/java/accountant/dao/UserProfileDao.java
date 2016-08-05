package accountant.dao;

import java.util.Set;

import accountant.model.UserProfile;

public interface UserProfileDao {
	Set<UserProfile> getAll();
	UserProfile findById(int id);
	UserProfile findByType(String type);
}

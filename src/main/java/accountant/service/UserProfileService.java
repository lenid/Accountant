package accountant.service;

import java.util.Set;

import accountant.model.UserProfile;

public interface UserProfileService {
	Set<UserProfile> findAll();
	UserProfile findById(int id);
	UserProfile findByType(String type);
}

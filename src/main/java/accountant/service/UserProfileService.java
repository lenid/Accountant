package accountant.service;

import accountant.constants.Profile;

import java.util.Set;

public interface UserProfileService {
	Set<Profile> findAll();
	Profile findById(int id);
	Profile findByType(String type);
}

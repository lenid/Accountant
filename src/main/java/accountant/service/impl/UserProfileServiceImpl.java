package accountant.service.impl;

import java.util.Set;

import accountant.models.db.ProfileDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import accountant.dao.ProfileDao;
import accountant.service.UserProfileService;

@Service("userProfileService")
@Transactional
public class UserProfileServiceImpl implements UserProfileService{
	
	@Autowired
    ProfileDao dao;
	
	public Set<ProfileDb> findAll() {
		return dao.getAll();
	}
	
	public ProfileDb findById(int id) {
		return dao.findById(id);
	}

	public ProfileDb findByType(String type){
		return dao.findByType(type);
	}
	
}

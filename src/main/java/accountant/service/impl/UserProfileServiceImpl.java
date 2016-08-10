package accountant.service.impl;

import accountant.constants.Profile;
import accountant.dao.ProfileDao;
import accountant.service.BaseService;
import accountant.service.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@Service("userProfileService")
@Transactional
public class UserProfileServiceImpl extends BaseService implements UserProfileService {

    @Autowired
    ProfileDao dao;

    public Set<Profile> findAll() {
        return dao.getAll().stream()
                .map((pDb) -> convert(pDb, Profile.class))
                .collect(Collectors.toSet());
    }

    public Profile findById(int id) {
        return convert(dao.findById(id), Profile.class);
    }

    public Profile findByType(String type) {
        return convert(dao.findByType(type), Profile.class);
    }

}

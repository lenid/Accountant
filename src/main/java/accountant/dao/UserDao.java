package accountant.dao;

import java.util.Set;

import accountant.constants.State;
import accountant.models.db.UserDb;

public interface UserDao {
	public void persist(UserDb user);
	public Set<UserDb> getAll();
	public Set<UserDb> getAll(State state);
	public UserDb findById(int id);
	public UserDb findBySso(String sso);
	public void update(UserDb user);
	public void delete(UserDb user);
}


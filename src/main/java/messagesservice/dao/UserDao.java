package messagesservice.dao;

import java.util.Set;

import messagesservice.model.State;
import messagesservice.model.User;

public interface UserDao {
	public void persist(User user);
	public Set<User> getAll();
	public Set<User> getAll(State state);
	public User findById(int id);
	public User findBySso(String sso);
	public void update(User user);
	public void delete(User user);
}


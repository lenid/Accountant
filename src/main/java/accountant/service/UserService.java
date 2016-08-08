package accountant.service;

import accountant.models.ui.UserUi;

import java.util.Set;


public interface UserService {
	public void persist(UserUi user);
	public Set<UserUi> getAll();
	public UserUi findById(int id);
	public UserUi findBySso(String sso);
	public void update(UserUi user);
	public void delete(int userId);
	public boolean isDuplicatedSsoId(UserUi user);
}
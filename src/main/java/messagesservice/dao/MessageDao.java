package messagesservice.dao;

import java.util.Set;

import messagesservice.model.Message;
import messagesservice.model.User;

public interface MessageDao {
	public void persist(Message message);
	public Set<Message> getAll(); // wc wd
	public Message findById(int id);
	public Set<Message> findIncoming(User user);
	public Set<Message> findOutcoming(User user);
	public void delete(Message message);
	public void markDelete(int messageId);
}

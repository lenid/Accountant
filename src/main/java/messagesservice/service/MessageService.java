package messagesservice.service;

import messagesservice.model.Message;
import messagesservice.model.User;

import java.util.Set;

public interface MessageService {
	public void persist(Message message);
	public Set<Message> findAll(); // wd
	public Message findById(int id);
	public Set<Message> findIncoming(User user);
	public Set<Message> findOutcoming(User user);
	public void delete(int messageId);
}

package messagesservice.service.impl;

import java.util.Set;

import messagesservice.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import messagesservice.dao.MessageDao;
import messagesservice.model.Message;
import messagesservice.service.MessageService;

@Service("messageService")
@Transactional
public class MessageServiceImpl implements MessageService {

	@Autowired
	private MessageDao dao;

	@Override
	public void persist(Message message) {
		dao.persist(message);
	}

	@Override
	public Set<Message> findAll() {
		return dao.getAll();
	}

	@Override
	public Message findById(int id) {
		Message message = dao.findById(id);
		message.setFrom(new User());
		message.setTo(new User());
		
		return message;
	}
	
	@Override
	public Set<Message> findIncoming(User user) {
		return null;
	}
	
	@Override
	public Set<Message> findOutcoming(User user) {
		return null;
	}

	@Override
	public void delete(int messageId) {
		dao.markDelete(messageId);
	}

}

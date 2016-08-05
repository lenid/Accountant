package messagesservice.controller;

import messagesservice.data.Notification;
import messagesservice.model.Message;
import messagesservice.model.User;
import messagesservice.service.MessageService;
import messagesservice.service.UserService;
import messagesservice.util.SecurityHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Set;

@Controller
public class MainController extends BaseController {
	
	@Autowired
	UserService userService;

	@Autowired
	MessageService messageService;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView homePage(@ModelAttribute ArrayList<Notification> notifications) {
		ModelAndView model = new ModelAndView("home");
		defaultModelInitialize(model, notifications, "main.header");
		
		User user = userService.findBySso(SecurityHelper.getSso());
		model.addObject("user", user);

		Set<Message> messages = messageService.findIncoming(user);
		messages.addAll(messageService.findOutcoming(user));
		model.addObject("messages", messages);

		model.addObject("message", new Message());
		
		return model;
	}

}

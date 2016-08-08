package accountant.controller;

import java.util.ArrayList;
import java.util.Set;

import accountant.models.db.UserDb;
import accountant.models.ui.UserUi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import accountant.data.Notification;
import accountant.service.UserProfileService;
import accountant.service.UserService;

@Controller
@RequestMapping(value = "/user")
public class UserController extends BaseController {

	@Autowired
	UserService userService;

	@Autowired
	UserProfileService userProfileService;

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView getAll(@ModelAttribute ArrayList<Notification> notifications) {
		ModelAndView model = new ModelAndView("users");
		defaultModelInitialize(model, notifications, "users.header");

		Set<UserUi> userUiSet = userService.getAll();
		model.addObject("users", userUiSet);
		model.addObject("user", new UserUi());

		return model;
	}

	@RequestMapping(value = "/{userId}", method = RequestMethod.GET)
	public @ResponseBody ModelAndView getUserAjax(@PathVariable int userId) {
		ModelAndView model = new ModelAndView("modal/userForm");

		UserUi userUi = null;
		if (userId == 0) {
			userUi = new UserUi();
			model.addObject("userHeader", "users.popup_user.header.create");
		} else {
			userUi = userService.findById(userId);
			model.addObject("userHeader", "users.popup_user.header.edit");
		}

		model.addObject("user", userUi);
		model.addObject("roles", userProfileService.findAll());

		return model;
	}

	@RequestMapping(method = RequestMethod.POST)
	public String createOrUpdateUserAjax(@ModelAttribute("user") UserUi userUi) {
		if (userService.isDuplicatedSsoId(userUi)) {

		}

		if (userUi.getId() == 0) {
			userService.persist(userUi);
		} else {
			userService.update(userUi);
		}
		return "redirect:/user";
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public String deleteUserAjax(@ModelAttribute("user") UserDb user) {
		userService.delete(user.getId());
		return "redirect:/user";
	}

}

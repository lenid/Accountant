package accountant.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import accountant.constants.Profile;
import accountant.models.db.UserDb;
import accountant.models.ui.ProfileUi;
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
import accountant.service.ProfileService;
import accountant.service.UserService;

@Controller
@RequestMapping(value = "/user")
public class UserController extends BaseController {

	public static final String JSP_KEY_USER = "user";
	public static final String JSP_KEY_USERS = "users";
	public static final String JSP_KEY_USER_HEADER = "userHeader";
	public static final String JSP_KEY_PROFILE_UI_LIST = "profileUiList";

	public static final String JSP_PAGE_USERS = "users";
	public static final String JSP_PAGE_USER_FORM = "modal/userForm";

	@Autowired
	UserService userService;

	@Autowired
	ProfileService userProfileService;

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView getAll(@ModelAttribute ArrayList<Notification> notifications) {
		ModelAndView model = new ModelAndView(JSP_PAGE_USERS);
		defaultModelInitialize(model, notifications, "users.header");

		Set<UserUi> userUiSet = userService.getAll();
		model.addObject(JSP_KEY_USERS, userUiSet);
		model.addObject(JSP_KEY_USER, new UserUi());
		model.addObject(JSP_KEY_PROFILE_UI_LIST, userProfileService.findAll());


		return model;
	}

	@RequestMapping(value = "/{userId}", method = RequestMethod.GET)
	public @ResponseBody ModelAndView getUserAjax(@PathVariable int userId) {
		ModelAndView model = new ModelAndView(JSP_PAGE_USER_FORM);

		UserUi userUi = null;
		if (userId == 0) {
			userUi = new UserUi();
			model.addObject(JSP_KEY_USER_HEADER, "users.popup_user.header.create");
		} else {
			userUi = userService.findById(userId);
			model.addObject(JSP_KEY_USER_HEADER, "users.popup_user.header.edit");
		}

		model.addObject(JSP_KEY_USER, userUi);
		model.addObject(JSP_KEY_PROFILE_UI_LIST, userProfileService.findAll());

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

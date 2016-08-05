package accountant.dbinit;

import com.fasterxml.jackson.databind.ObjectMapper;
import accountant.dbinit.DbInitConfiguration.MessageServiceDbInit;
import accountant.dbinit.DbInitConfiguration.UserProfileServiceDbInit;
import accountant.model.Message;
import accountant.model.User;
import accountant.model.UserProfile;
import accountant.model.UserProfile.Type;
import accountant.service.UserProfileService;
import accountant.service.UserService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;

public class DbDataGen {
	private static final String LOGIN = "admin";
	private static final String PASSWD = "pass";

	private UserService userService;
	private UserProfileService userProfileService;
	private UserProfileServiceDbInit userProfileDaoInit;
	private MessageServiceDbInit messageService;
	private ApplicationContext ctx;

	private DbDataGen() {
		ctx = new AnnotationConfigApplicationContext(DbInitConfiguration.class);

		userService = ctx.getBean(UserService.class);
		userProfileService = ctx.getBean(UserProfileService.class);
		userProfileDaoInit = ctx.getBean(UserProfileServiceDbInit.class);
		messageService = ctx.getBean(MessageServiceDbInit.class);
		
	}

	public static void main(String[] args) throws Throwable {
		new DbDataGen().dbInit();
//		new DbDataGen().test5();
	}

	void test4() {
//		Formatter formatter = new Formatter();
		int a = 5;
		String x = "Successfully deleted %d post(s)";
//		formatter.format(x, a);
		String str = String.format(x, a);
		System.out.println(str);
		
	}
	
	void test3() throws Throwable {
//		ShortUser u = shortUserService.findById(4);
		ObjectMapper mapper = new ObjectMapper();
//		String json = mapper.writeValueAsString(u);
//		System.out.println(json);
	}
	

	void test() throws Throwable {
		User user = userService.findBySso("user_9");

		ObjectMapper mapper = new ObjectMapper();

		System.out.println("================================================================");
		String json = mapper.writeValueAsString(user);
		System.out.println(json);

		User u = mapper.readValue(json, User.class);

		u.setLastName(new Date().toString());
		
		Message message = new Message();
		message.setFrom(userService.findBySso("user_8"));
		message.setTo(userService.findBySso("user_9"));
		message.setSubject("subject");
		message.setBody("BODY2");
		

		userService.update(u);
		
		System.out.println();
	}

	public void dbInit() {
		messageService.deleteAll();

		createUserProfile();

		createUser(LOGIN, PASSWD, "Andriy", Type.ADMIN);

		((ConfigurableApplicationContext) ctx).close();
	}

	private void createUserProfile() {
		Type[] types = Type.values();

		for (Type type : types) {
			UserProfile userProfile = userProfileService.findByType(type.toString());

			if (userProfile == null) {
				userProfile = new UserProfile();
				userProfile.setType(type.toString());
				userProfileDaoInit.save(userProfile);
			}
		}
	}

	private void createUser(String login, String passwd, String firstName, Type userType) {
		User user = userService.findBySso(login);

		if (user != null) {
			userService.delete(user.getId());
		}

		user = new User();
		user.setSsoId(login);
		user.setNewPasswd(passwd);
		user.setFirstName(firstName);
		user.setLastName("Losoviy");
		user.setEmail(login + "@mail.ru");

		UserProfile userProfile = userProfileService.findByType(userType.toString());
		user.setProfiles(new HashSet<UserProfile>(Arrays.asList(userProfile)));

		userService.persist(user);
	}

}

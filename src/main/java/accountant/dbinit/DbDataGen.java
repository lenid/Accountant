package accountant.dbinit;

import accountant.constants.Profile;
import accountant.dao.ProfileDao;
import accountant.models.db.MessageDb;
import accountant.models.db.ProfileDb;
import accountant.models.ui.ProfileUi;
import accountant.models.ui.UserUi;
import com.fasterxml.jackson.databind.ObjectMapper;
import accountant.dbinit.DbInitConfiguration.MessageServiceDbInit;
import accountant.dbinit.DbInitConfiguration.UserProfileServiceDbInit;
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
    private ProfileDao profileDao;
    private UserProfileServiceDbInit userProfileDaoInit;
    private MessageServiceDbInit messageService;
    private ApplicationContext ctx;

    private DbDataGen() {
        ctx = new AnnotationConfigApplicationContext(DbInitConfiguration.class);

        userService = ctx.getBean(UserService.class);
        userProfileService = ctx.getBean(UserProfileService.class);
        userProfileDaoInit = ctx.getBean(UserProfileServiceDbInit.class);
        messageService = ctx.getBean(MessageServiceDbInit.class);
        profileDao = ctx.getBean(ProfileDao.class);

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
        UserUi user = userService.findBySso("user_9");

        ObjectMapper mapper = new ObjectMapper();

        System.out.println("================================================================");
        String json = mapper.writeValueAsString(user);
        System.out.println(json);

        UserUi u = mapper.readValue(json, UserUi.class);

        u.setLastName(new Date().toString());

        MessageDb message = new MessageDb();
//		message.setFrom(userService.findBySso("user_8"));
//		message.setTo(userService.findBySso("user_9"));
        message.setSubject("subject");
        message.setBody("BODY2");


        userService.update(u);

        System.out.println();
    }

    public void dbInit() {
        messageService.deleteAll();

        createUserProfiles();

        createUser(LOGIN, PASSWD, "Andriy", Profile.ADMIN);

        ((ConfigurableApplicationContext) ctx).close();
    }

    private void createUserProfiles() {
        Profile[] profiles = Profile.values();

        for (Profile profile : profiles) {
            ProfileUi profileSaved = userProfileService.findByType(profile.toString());

            if (profileSaved == null) {
                ProfileDb profileDB = new ProfileDb();
                profileDB.setProfile(profile.toString());
                userProfileDaoInit.save(profileDB);
            }
        }
    }

    private void createUser(String login, String passwd, String firstName, Profile profile) {
        UserUi user = userService.findBySso(login);

        if (user != null) {
            userService.delete(user.getId());
        }

        user = new UserUi();
        user.setSsoId(login);
        user.setPasswdNew(passwd);
        user.setFirstName(firstName);
        user.setLastName("Losoviy");
        user.setEmail(login + "@mail.ru");
        user.setProfiles(new HashSet<>(Arrays.asList(new ProfileUi(profile))));

        userService.persist(user);
    }

}

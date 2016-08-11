package accountant.dbinit;

import accountant.constants.Profile;
import accountant.dbinit.DbInitConfiguration.*;
import accountant.models.db.ProfileDb;
import accountant.models.db.UserDb;
import accountant.util.SecurityHelper;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class DbDataGen {
    private static final String LOGIN = "admin";
    private static final String PASSWD = "pass";

    private ApplicationContext ctx;
    private ProfileServiceDbInit profileDao;
    private UserServiceDbInit userDao;

    private DbDataGen() {
        ctx = new AnnotationConfigApplicationContext(DbInitConfiguration.class);

        profileDao = ctx.getBean(ProfileServiceDbInit.class);
        userDao = ctx.getBean(UserServiceDbInit.class);

    }

    public static void main(String[] args) throws Throwable {
        new DbDataGen().dbInit();
    }

    public void dbInit() {
        createUserProfiles();
        createUser(LOGIN, PASSWD, "Andriy");

        ((ConfigurableApplicationContext) ctx).close();
    }

    private void createUserProfiles() {
        Profile[] profiles = Profile.values();

        for (Profile profile : profiles) {
            ProfileDb profileDB = new ProfileDb();
            profileDB.setProfile(profile.toString());
            profileDao.persist(profileDB);
        }
    }

    private void createUser(String login, String passwd, String firstName) {
        UserDb user = new UserDb();

        user.setSsoId(login);
        user.setPasswd(SecurityHelper.getEncodedPassword(passwd));
        user.setFirstName(firstName);
        user.setLastName("Losoviy");
        user.setEmail(login + "@mail.ru");
        user.setProfiles(profileDao.getAll());

        userDao.persist(user);
    }

}

/*

    void test4() {
		Formatter formatter = new Formatter();
        int a = 5;
        String x = "Successfully deleted %d post(s)";
		formatter.format(x, a);
        String str = String.format(x, a);
        System.out.println(str);

    }

    void test3() throws Throwable {
		ShortUser u = shortUserService.findById(4);
        ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(u);
		System.out.println(json);
    }

 */
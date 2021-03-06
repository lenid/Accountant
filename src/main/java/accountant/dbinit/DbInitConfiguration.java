package accountant.dbinit;

import accountant.dao.AbstractDao;
import accountant.dao.MessageDao;
import accountant.dao.UserDao;
import accountant.dao.UserProfileDao;
import accountant.dao.impl.MessageDaoImpl;
import accountant.dao.impl.UserDaoImpl;
import accountant.dao.impl.UserProfileDaoImpl;
import accountant.model.Message;
import accountant.model.UserProfile;
import accountant.service.UserProfileService;
import accountant.service.UserService;
import accountant.service.impl.UserProfileServiceImpl;
import accountant.service.impl.UserServiceImpl;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.util.Properties;
import java.util.Set;

//@Configuration - add it never!
@EnableTransactionManagement
@PropertySource(value = { "classpath:application.properties" })
public class DbInitConfiguration {

    @Autowired
    protected Environment environment;

    @Bean
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        sessionFactory.setPackagesToScan(new String[] { "accountant.model" });
        sessionFactory.setHibernateProperties(hibernateProperties());
        return sessionFactory;
     }
	
    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(environment.getRequiredProperty("jdbc.driverClassName"));
        dataSource.setUrl(environment.getRequiredProperty("jdbc.url"));
        dataSource.setUsername(environment.getRequiredProperty("jdbc.username"));
        dataSource.setPassword(environment.getRequiredProperty("jdbc.password"));
        return dataSource;
    }
    
	@Bean
    @Autowired
    public HibernateTransactionManager transactionManager(SessionFactory s) {
       HibernateTransactionManager txManager = new HibernateTransactionManager();
       txManager.setSessionFactory(s);
       return txManager;
    }
	
	@Bean
	public UserService userService() {
		return new UserServiceImpl();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public UserDao userDao() {
		return new UserDaoImpl();
	}

	@Bean
	public UserProfileService userProfileService() {
		return new UserProfileServiceImpl();
	}
	
	@Bean
	public UserProfileDao userProfileDao() {
		return new UserProfileDaoImpl();
	}
	
	@Bean
	public UserProfileServiceDbInit userProfileDaoSaver() {
		return new UserProfileServiceDbInit();
	}
	
	@Bean
	public MessageDao messageDao() {
		return new MessageDaoImpl();
	}
	
	@Bean
	public MessageServiceDbInit messageService() {
		return new MessageServiceDbInit();
	}


//	@Bean
//	public ShortUserDaoImpl shortUserDao() {
//		return new ShortUserDaoImpl();
//	}
//	
//	@Bean
//	public ShortUserService shortUserService() {
//		return new ShortUserService();
//	}
	
    protected Properties hibernateProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.dialect", environment.getRequiredProperty("hibernate.dialect"));
        properties.put("hibernate.show_sql", environment.getRequiredProperty("hibernate.show_sql"));
        properties.put("hibernate.format_sql", environment.getRequiredProperty("hibernate.format_sql"));
        properties.put("hibernate.hbm2ddl.auto", environment.getRequiredProperty("hibernate.hbm2ddl.auto"));
        return properties;        
    }
    
    @Transactional
	class UserProfileServiceDbInit extends AbstractDao<Integer, UserProfile> {

		public void save(UserProfile userProfile) {
			persist(userProfile);
		}
		
	}
    
    @Transactional
	class MessageServiceDbInit extends AbstractDao<Integer, Message> {
    	
    	public void deleteAll() {
    		Set<Message> allMessages = getAll();

    		for (Message message : allMessages) {
    			delete(message);
    		}
    	}
    	
    	@Override
    	public void persist(Message entity) {
    		super.persist(entity);
    	}
    	
	}
}


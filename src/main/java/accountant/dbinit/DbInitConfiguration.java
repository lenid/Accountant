package accountant.dbinit;

import accountant.constants.Profile;
import accountant.dao.AbstractDao;
import accountant.models.db.MessageDb;
import accountant.models.db.ProfileDb;
import accountant.models.db.UserDb;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

//@Configuration - add it never!
@EnableTransactionManagement
@PropertySource(value = { "classpath:application.properties" })
public class DbInitConfiguration {

    @Autowired
    protected Environment environment;

	@Bean
	public ProfileServiceDbInit userProfileDaoSaver() {
		return new ProfileServiceDbInit();
	}

    @Bean
    public UserServiceDbInit userServiceDbInitSaver() {
        return new UserServiceDbInit();
    }

//	@Bean
//	public MessageServiceDbInit messageService() {
//		return new MessageServiceDbInit();
//	}

    
    // Service implementation

    @Transactional
    class ProfileServiceDbInit extends AbstractDao<Integer, ProfileDb> {

        public Set<ProfileDb> getAll() {
            return super.getAll();
        }

        public void persist(ProfileDb userProfile) {
            super.persist(userProfile);
        }

    }

    @Transactional
    class UserServiceDbInit extends AbstractDao<Integer, UserDb> {

        public void persist(UserDb userDb) {
            super.persist(userDb);
        }

    }

//    @Transactional
//	class MessageServiceDbInit extends AbstractDao<Integer, MessageDb> {
//
//    	public void deleteAll() {
//    		Set<MessageDb> allMessages = getAll();
//
//    		for (MessageDb message : allMessages) {
//    			delete(message);
//    		}
//    	}
//
//    	@Override
//    	public void persist(MessageDb entity) {
//    		super.persist(entity);
//    	}
//
//	}


	// DB config

    @Bean
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        sessionFactory.setPackagesToScan(new String[] { "accountant.models" });
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

    protected Properties hibernateProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.dialect", environment.getRequiredProperty("hibernate.dialect"));
        properties.put("hibernate.show_sql", environment.getRequiredProperty("hibernate.show_sql"));
        properties.put("hibernate.format_sql", environment.getRequiredProperty("hibernate.format_sql"));
        properties.put("hibernate.hbm2ddl.auto", environment.getRequiredProperty("hibernate.hbm2ddl.auto"));
        return properties;
    }

}


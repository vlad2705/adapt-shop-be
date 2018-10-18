package cz.cvut.fel;

import cz.cvut.fel.asf.adapt.gomawe.AdaptationManager;
import cz.cvut.fel.asf.adapt.gomawe.storage.IContentUnitModelAttributeDAO;
import cz.cvut.fel.asf.adapt.gomawe.storage.IUserModelAttributeDAO;
import cz.cvut.fel.asf.adapt.gomawe.storage.IUserProfileAttributeDAO;
import cz.cvut.fel.asf.adapt.gomawe.storage.jpa.TableContentUnitModelAttributeDAOImpl;
import cz.cvut.fel.asf.adapt.gomawe.storage.jpa.TableUserModelAttributeDAOImpl;
import cz.cvut.fel.asf.adapt.gomawe.storage.jpa.TableUserProfileAttributeDAOImpl;
import cz.cvut.fel.asf.persistence.IDAO;
import cz.cvut.fel.asf.persistence.jpa.QueryEntityManagerFactory;
import cz.cvut.fel.config.ViewScope;
import cz.cvut.fel.model.AdaptContentModel;
import cz.cvut.fel.model.AdaptUserModel;
import cz.cvut.fel.repository.AccountRepository;
import cz.cvut.fel.repository.impl.AccountRepositoryImpl;
import cz.cvut.fel.repository.impl.PersonRepositoryImpl;
import org.ocpsoft.rewrite.servlet.RewriteFilter;
import org.springframework.beans.factory.config.CustomScopeConfigurer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.faces.webapp.FacesServlet;
import javax.servlet.DispatcherType;
import java.util.EnumSet;
import java.util.HashMap;

@SpringBootApplication
public class AdaptShopApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdaptShopApplication.class, args);
    }

    @Bean
    public static CustomScopeConfigurer viewScope() {
        CustomScopeConfigurer configurer = new CustomScopeConfigurer();
        configurer.setScopes(new HashMap<String, Object>() {{
            put("view", new ViewScope());
        }});
        return configurer;
    }

    @Bean
    public static QueryEntityManagerFactory querySessionFactory() {
        return new QueryEntityManagerFactory();
    }

    @Bean
    public ServletRegistrationBean servletRegistrationBean() {
        FacesServlet servlet = new FacesServlet();
        return new ServletRegistrationBean(servlet, "*.jsf");
    }

    @Bean
    public FilterRegistrationBean rewriteFilter() {
        FilterRegistrationBean rwFilter = new FilterRegistrationBean(new RewriteFilter());
        rwFilter.setDispatcherTypes(EnumSet.of(DispatcherType.FORWARD, DispatcherType.REQUEST,
                DispatcherType.ASYNC, DispatcherType.ERROR));
        rwFilter.addUrlPatterns("/*");
        return rwFilter;
    }
    
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    public IUserModelAttributeDAO userModelAttributeDAO() {
        return new TableUserModelAttributeDAOImpl();
    }
    
    @Bean
    public IUserProfileAttributeDAO userProfileAttributeDAO() {
        return new TableUserProfileAttributeDAOImpl();
    }
    
    @Bean
    public IContentUnitModelAttributeDAO contentUnitModelAttributeDAO() {
        return new TableContentUnitModelAttributeDAOImpl();
    }
    
    @Bean
    public AccountRepository accountRepository() {
        return new AccountRepositoryImpl();
    }
    
    @Bean
    public IDAO userDAO() {
        return new PersonRepositoryImpl(accountRepository());
    }
    
    @Bean
    public String userModelImplementationClass() {
        return AdaptUserModel.class.getName();
    }
    
    @Bean
    public String contentUnitModelImplementationClass() {
        return AdaptContentModel.class.getName();
    }
    
    @Bean
    public AdaptationManager adaptationManager() throws InstantiationException {
        AdaptationManager adaptationManager = new AdaptationManager();
        adaptationManager.setUserModelAttributeDAO(userModelAttributeDAO());
        adaptationManager.setUserProfileAttributeDAO(userProfileAttributeDAO());
        adaptationManager.setContentUnitModelAttributeDAO(contentUnitModelAttributeDAO());
        adaptationManager.setUserDAO(userDAO());
        adaptationManager.setUserModelImplementationClass(userModelImplementationClass());
        adaptationManager.setContentUnitModelImplementationClass(contentUnitModelImplementationClass());
        return adaptationManager;
    }
}

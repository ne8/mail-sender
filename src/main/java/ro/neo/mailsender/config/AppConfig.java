package ro.neo.mailsender.config;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactoryBean;

import java.util.Properties;

@Configuration
@ComponentScan(basePackages = "ro.neo.mailsender")
@PropertySource(value = {"classpath:application.properties"})
@EntityScan(basePackages = {"ro.neo.mailsender.entities"})
@EnableJpaRepositories(basePackages = {"ro.neo.mailsender.repository"})
public class AppConfig {
    private static final String FMTEMPLATES_LOCATION = "/fmtemplates/";

    @Autowired
    private Environment environment;

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public JavaMailSenderImpl getMailSender() {
        final JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(this.environment.getProperty("neo.mail.host"));
        mailSender.setPort(Integer.parseInt(this.environment.getProperty("neo.mail.port")));
        mailSender.setUsername(this.environment.getProperty("neo.mail.username"));
        mailSender.setPassword(this.environment.getProperty("neo.mail.password"));

        final Properties javaMailProperties = new Properties();
        javaMailProperties.put("mail.smtp.starttls.enable", this.environment.getProperty("neo.mail.smtp.starttls.enable"));
        javaMailProperties.put("mail.smtp.auth", this.environment.getProperty("neo.mail.smtp.auth"));
        javaMailProperties.put("mail.transport.protocol", this.environment.getProperty("neo.mail.transport.protocol"));
        javaMailProperties.put("mail.mail.debug", this.environment.getProperty("neo.mail.debug"));
        mailSender.setJavaMailProperties(javaMailProperties);
        return mailSender;

    }


    @Bean(name = "freemarkerConfiguration")
    public FreeMarkerConfigurationFactoryBean getFreeMarkerConfiguration() {
        final FreeMarkerConfigurationFactoryBean freeMarkerConfigurationFactoryBean = new FreeMarkerConfigurationFactoryBean();
        freeMarkerConfigurationFactoryBean.setTemplateLoaderPath(FMTEMPLATES_LOCATION);
        return freeMarkerConfigurationFactoryBean;
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}

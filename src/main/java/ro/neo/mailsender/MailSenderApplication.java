package ro.neo.mailsender;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication

public class MailSenderApplication {

    public static void main(final String[] args) {
        SpringApplication.run(MailSenderApplication.class, args);
//        final AbstractApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
//
//        final MailService mailService = (MailService) context.getBean("mailService");
//        mailService.sendEmail(createMailEntity());
    }


}

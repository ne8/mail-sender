package ro.neo.mailsender.service.impl;

import freemarker.template.Configuration;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import ro.neo.mailsender.entities.MailEntity;
import ro.neo.mailsender.repository.MailRepository;
import ro.neo.mailsender.service.MailSenderService;

import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class MailSenderServiceImpl implements MailSenderService {
    final static Logger LOGGER = Logger.getLogger(MailSenderServiceImpl.class);

    @Autowired
    private MailRepository mailRepository;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private Configuration freemarkerConfiguration;

    @Override
    public void sendEmail(final MailEntity mailEntity) {

        final MimeMessagePreparator mimeMessagePreparator = getMessagePreparator(mailEntity);
        try {
            this.mailSender.send(mimeMessagePreparator);
            LOGGER.debug("Email for recipient: " + mailEntity.getRecipient() + " sent successfully");
            if (mailEntity.getSavedTime() == null) {
                mailEntity.setSavedTime(new Date());
            }
            mailEntity.setSentDate(new Date());
            this.mailRepository.save(mailEntity);

        } catch (final MailException e) {
            LOGGER.error("Error thrown while trying to send email for recipient: " + mailEntity.getRecipient(), e);
        }
    }

    private MimeMessagePreparator getMessagePreparator(final MailEntity mailEntity) {
        final MimeMessagePreparator mimeMessagePreparator = new MimeMessagePreparator() {
            @Override
            public void prepare(final MimeMessage mimeMessage) throws Exception {
                final MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

                mimeMessageHelper.setSubject(mailEntity.getSubject());
                mimeMessageHelper.setFrom(mailEntity.getSender());
                mimeMessageHelper.setTo(mailEntity.getRecipient());


                final Map<String, Object> model = new HashMap<>();
                model.put("mailModel", mailEntity);

//                final String text = getFreeMarkerTemplateContent(model);

                LOGGER.info("Template content is:" + mailEntity.getMessage());
                mimeMessageHelper.setText(mailEntity.getMessage(), true);

            }
        };
        return mimeMessagePreparator;
    }

    private String getFreeMarkerTemplateContent(final Map<String, Object> model) {
        final StringBuilder content = new StringBuilder();
        try {
            content.append(FreeMarkerTemplateUtils
                    .processTemplateIntoString(this.freemarkerConfiguration.getTemplate("fm_mailTemplate.txt"), model));
            return content.toString();
        } catch (final Exception e) {
            LOGGER.error("Exception occured while processing fmtemplate:" + e.getMessage());
        }
        return "";
    }
}

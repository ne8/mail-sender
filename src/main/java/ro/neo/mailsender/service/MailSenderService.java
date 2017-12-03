package ro.neo.mailsender.service;

import ro.neo.mailsender.entities.MailEntity;

public interface MailSenderService {
    void sendEmail(MailEntity mailEntity);

}

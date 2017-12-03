package ro.neo.mailsender.service;

import ro.neo.mailsender.entities.MailEntity;

import java.util.List;

public interface MailService {

    List<MailEntity> getAllEmails();

    void save(MailEntity mailEntity);

    MailEntity getEmailById(long id);

    void deleteMail(long id);

    List<MailEntity> getMailBySubject(String subject);

    List<MailEntity> getMailBySender(String sender);

    List<MailEntity> getMailByRecipient(String recipient);
}

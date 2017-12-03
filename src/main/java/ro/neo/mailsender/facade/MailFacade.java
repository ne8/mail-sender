package ro.neo.mailsender.facade;

import ro.neo.mailsender.dto.MailDTO;

import java.util.List;

public interface MailFacade {
    List<MailDTO> getAllEmails();

    void saveEmail(MailDTO mailDTO);

    MailDTO getEmailById(long id);

    void deleteMail(long id);


    List<MailDTO> getMailBySubject(String subject);

    List<MailDTO> getMailBySender(String sender);

    List<MailDTO> getMailRecipient(String recipient);

}

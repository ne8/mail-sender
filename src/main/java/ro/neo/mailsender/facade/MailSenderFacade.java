package ro.neo.mailsender.facade;

import ro.neo.mailsender.dto.MailDTO;

public interface MailSenderFacade {
    void sendEmail(MailDTO mailDTO);

    void sendEmailWithId(long id);
}

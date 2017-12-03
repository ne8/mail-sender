package ro.neo.mailsender.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.neo.mailsender.entities.MailEntity;
import ro.neo.mailsender.repository.MailRepository;
import ro.neo.mailsender.service.MailService;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("mailService")
@Transactional
public class MailServiceImpl implements MailService {
    final static Logger LOGGER = Logger.getLogger(MailServiceImpl.class);


    @Autowired
    private MailRepository mailRepository;


    @Override
    public List<MailEntity> getAllEmails() {
        final List<MailEntity> mailEntityList = new ArrayList<>();
        for (final MailEntity mailEntity : this.mailRepository.findAll()) {
            mailEntityList.add(mailEntity);
        }
        return mailEntityList;
    }

    @Override
    public void save(final MailEntity mailEntity) {
        mailEntity.setSavedTime(new Date());
        this.mailRepository.save(mailEntity);
    }

    @Override
    public MailEntity getEmailById(final long id) {
        return this.mailRepository.findOne(id);
    }

    @Override
    public void deleteMail(final long id) {
        this.mailRepository.delete(id);
    }

    @Override
    public List<MailEntity> getMailBySubject(final String subject) {
        return this.mailRepository.findBySubject(subject);
    }

    @Override
    public List<MailEntity> getMailBySender(final String sender) {
        return this.mailRepository.findBySender(sender);
    }

    @Override
    public List<MailEntity> getMailByRecipient(final String recipient) {
        return this.mailRepository.findByRecipient(recipient);
    }


}

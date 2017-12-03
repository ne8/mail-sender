package ro.neo.mailsender.facade.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.neo.mailsender.dto.MailDTO;
import ro.neo.mailsender.entities.MailEntity;
import ro.neo.mailsender.facade.MailSenderFacade;
import ro.neo.mailsender.service.MailSenderService;
import ro.neo.mailsender.service.MailService;

@Service
public class MailSenderFacadeImpl implements MailSenderFacade {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private MailSenderService mailSenderService;

    @Autowired
    private MailService mailService;

    @Override
    public void sendEmail(final MailDTO mailDTO) {
        final MailEntity mailEntity = this.modelMapper.map(mailDTO, MailEntity.class);
        this.mailSenderService.sendEmail(mailEntity);
    }

    @Override
    public void sendEmailWithId(final long id) {
        final MailEntity mailEntity = this.mailService.getEmailById(id);
        this.mailSenderService.sendEmail(mailEntity);
    }

}

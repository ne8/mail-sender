package ro.neo.mailsender.facade.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.neo.mailsender.dto.MailDTO;
import ro.neo.mailsender.entities.MailEntity;
import ro.neo.mailsender.facade.MailFacade;
import ro.neo.mailsender.service.MailService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class MailFacadeImpl implements MailFacade {

    @Autowired
    private MailService mailService;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<MailDTO> getAllEmails() {
        final List<MailEntity> mailEntityList = this.mailService.getAllEmails();
        final List<MailDTO> mailDTOList = new ArrayList<>();
        for (final MailEntity mailEntity : mailEntityList) {
            final MailDTO mailDTO = this.modelMapper.map(mailEntity, MailDTO.class);
            mailDTO.setSentDate(new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss z").format(mailEntity.getSentDate()));
            mailDTOList.add(mailDTO);
        }
        return mailDTOList;
    }

    @Override
    public void saveEmail(final MailDTO mailDTO) {
        final MailEntity mailEntity = this.modelMapper.map(mailDTO, MailEntity.class);
        this.mailService.save(mailEntity);
    }

    @Override
    public MailDTO getEmailById(final long id) {
        final MailEntity mailEntity = this.mailService.getEmailById(id);
        if (mailEntity != null) {
            final MailDTO mailDTO = this.modelMapper.map(mailEntity, MailDTO.class);
            mailDTO.setSentDate(new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss z").format(mailEntity.getSentDate()));
            return mailDTO;
        } else {
            return null;
        }
    }

    @Override
    public void deleteMail(final long id) {
        this.mailService.deleteMail(id);
    }

    @Override
    public List<MailDTO> getMailBySubject(final String subject) {
        final List<MailEntity> mailEntityList = this.mailService.getMailBySubject(subject);
        final List<MailDTO> mailDTOList = new ArrayList<>();
        if (mailEntityList != null) {
            for (final MailEntity mailEntity : mailEntityList) {
                mailDTOList.add(this.modelMapper.map(mailEntity, MailDTO.class));
            }
        }
        return mailDTOList;
    }

    @Override
    public List<MailDTO> getMailBySender(final String sender) {
        final List<MailEntity> mailEntityList = this.mailService.getMailBySender(sender);
        final List<MailDTO> mailDTOList = new ArrayList<>();
        if (mailEntityList != null) {
            for (final MailEntity mailEntity : mailEntityList) {
                mailDTOList.add(this.modelMapper.map(mailEntity, MailDTO.class));
            }
        }
        return mailDTOList;

    }

    @Override
    public List<MailDTO> getMailRecipient(final String recipient) {
        final List<MailEntity> mailEntityList = this.mailService.getMailByRecipient(recipient);
        final List<MailDTO> mailDTOList = new ArrayList<>();
        if (mailEntityList != null) {
            for (final MailEntity mailEntity : mailEntityList) {
                mailDTOList.add(this.modelMapper.map(mailEntity, MailDTO.class));
            }
        }
        return mailDTOList;
    }
}

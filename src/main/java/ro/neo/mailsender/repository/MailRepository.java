package ro.neo.mailsender.repository;

import org.springframework.data.repository.CrudRepository;
import ro.neo.mailsender.entities.MailEntity;

import java.util.List;

public interface MailRepository extends CrudRepository<MailEntity, Long> {

    List<MailEntity> findBySubject(String subject);

    List<MailEntity> findBySender(String sender);

    List<MailEntity> findByRecipient(String recipient);

}

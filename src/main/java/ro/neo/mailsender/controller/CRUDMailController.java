package ro.neo.mailsender.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.neo.mailsender.dto.MailDTO;
import ro.neo.mailsender.facade.MailFacade;

import javax.validation.Valid;
import java.util.List;

@RestController
@Api(value = "Mail CRUD Sender API",
        description = "Mail CRUD Sender API supports basics CRUD operations", consumes = "application/json",
        produces = "application/json", tags = {"CRUDMailSender"})
public class CRUDMailController {
    final static Logger LOGGER = Logger.getLogger(CRUDMailController.class);

    private static final String EMAILS_URL = "/emails/";
    private static final String EMAIL_BY_ID_URL = "/emails/id/{id}/";
    private static final String EMAIL_BY_SUBJECT_URL = "/emails/subject/{subject}/";
    private static final String EMAIL_BY_SENDER_URL = "/emails/sender/{sender}/";
    private static final String EMAIL_BY_RECIPIENT_URL = "/emails/recipient/{recipient}/";


    @Autowired
    private MailFacade mailFacade;


    @ApiOperation(
            value = "Retrieves a list of emails",
            response = MailDTO.class)
    @ApiResponses({
            @ApiResponse(code = 200, responseContainer = "List", response = MailDTO.class, message = "Email list retrieved successfully")})
    @RequestMapping(method = RequestMethod.GET, value = EMAILS_URL,
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<MailDTO>> getAllEmails() {
        return new ResponseEntity<>(this.mailFacade.getAllEmails(), HttpStatus.OK);
    }


    @ApiOperation(
            value = "Saves Email to the database")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Successfully saved email")})
    @RequestMapping(method = RequestMethod.POST, value = EMAILS_URL,
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Void> saveEmail(@Valid @RequestBody final MailDTO mailDTO) {
        this.mailFacade.saveEmail(mailDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @ApiOperation(
            value = "Retrieves email by id")
    @ApiResponses({
            @ApiResponse(code = 200, response = MailDTO.class, message = "Email successfully retrieved"),
            @ApiResponse(code = 404, response = MailDTO.class, message = "Email not found")
    })
    @RequestMapping(method = RequestMethod.GET, value = EMAIL_BY_ID_URL,
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<MailDTO> getEmailById(@PathVariable("id") final long id) {
        final MailDTO mailDTO = this.mailFacade.getEmailById(id);
        if (mailDTO == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(mailDTO, HttpStatus.OK);
    }

    @ApiOperation(
            value = "Retrieves email by subject")
    @ApiResponses({
            @ApiResponse(code = 200, response = MailDTO.class, message = "Email successfully retrieved"),
            @ApiResponse(code = 404, response = MailDTO.class, message = "Email not found")
    })
    @RequestMapping(method = RequestMethod.GET, value = EMAIL_BY_SUBJECT_URL,
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<MailDTO>> getEmailBySubject(@PathVariable("subject") final String subject) {
        final List<MailDTO> mailDTOList = this.mailFacade.getMailBySubject(subject);
        if (mailDTOList == null || mailDTOList.size() == 0) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(mailDTOList, HttpStatus.OK);
    }

    @ApiOperation(
            value = "Retrieves email by sender")
    @ApiResponses({
            @ApiResponse(code = 200, response = MailDTO.class, message = "Email successfully retrieved"),
            @ApiResponse(code = 404, response = MailDTO.class, message = "Email not found")
    })
    @RequestMapping(method = RequestMethod.GET, value = EMAIL_BY_SENDER_URL,
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<MailDTO>> getEmailBySender(@PathVariable("sender") final String sender) {
        final List<MailDTO> mailDTOList = this.mailFacade.getMailBySender(sender);
        if (mailDTOList == null || mailDTOList.size() == 0) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(mailDTOList, HttpStatus.OK);
    }

    @ApiOperation(
            value = "Retrieves email by recipient")
    @ApiResponses({
            @ApiResponse(code = 200, response = MailDTO.class, message = "Email successfully retrieved"),
            @ApiResponse(code = 404, response = MailDTO.class, message = "Email not found")
    })
    @RequestMapping(method = RequestMethod.GET, value = EMAIL_BY_RECIPIENT_URL,
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<MailDTO>> getEmailByRecipient(@PathVariable("recipient") final String recipient) {
        final List<MailDTO> mailDTOList = this.mailFacade.getMailRecipient(recipient);
        if (mailDTOList == null || mailDTOList.size() == 0) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(mailDTOList, HttpStatus.OK);
    }


    @ApiOperation(
            value = "Updates email by id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Successfully updated email"),
            @ApiResponse(code = 200, message = "Email not found")
    })
    @RequestMapping(method = RequestMethod.PUT, value = EMAIL_BY_ID_URL,
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Void> updateEmail(@PathVariable("id") final long id, @Valid @RequestBody final MailDTO mailDTO) {
        LOGGER.debug("Updating email with id: " + id);
        final MailDTO currentMailDTO = this.mailFacade.getEmailById(id);

        if (currentMailDTO == null) {
            LOGGER.warn("Email with ID: " + id + " does not exist");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        currentMailDTO.setMessage(mailDTO.getMessage());
        currentMailDTO.setSubject(mailDTO.getSubject());
        currentMailDTO.setSender(mailDTO.getSender());
        currentMailDTO.setRecipient(mailDTO.getRecipient());

        this.mailFacade.saveEmail(currentMailDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(
            value = "Removes email by id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Successfully removed email"),
            @ApiResponse(code = 404, message = "Email not found")
    })
    @RequestMapping(method = RequestMethod.DELETE, value = EMAIL_BY_ID_URL,
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Void> deleteMail(@PathVariable("id") final long id) {
        LOGGER.debug("Fetching and deleting email with id: " + id);
        final MailDTO mailDTO = this.mailFacade.getEmailById(id);

        if (mailDTO == null) {
            LOGGER.warn("Unable to remove email with ID: " + id + " since it does not exist");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        this.mailFacade.deleteMail(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

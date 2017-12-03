package ro.neo.mailsender.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.neo.mailsender.dto.MailDTO;
import ro.neo.mailsender.facade.MailSenderFacade;

import javax.validation.Valid;

@RestController
@Api(value = "Mail Sender API",
        description = "Mail Sender API supports basics CRUD operations", consumes = "application/json",
        produces = "application/json", tags = {"MailSender"})
public class MailSenderController {


    private static final String SEND_EMAIL_URL = "/send/";
    private static final String SEND_EMAIL_BY_ID_URL = "/send/{id}/";

    @Autowired
    private MailSenderFacade mailSenderFacade;


    @ApiOperation(
            value = "Sends and saves an email to the database")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Successfully saved and sent email")})
    @RequestMapping(method = RequestMethod.POST, value = SEND_EMAIL_URL,
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Void> sendAndStoreEmail(@Valid @RequestBody final MailDTO mailDTO) {
        this.mailSenderFacade.sendEmail(mailDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @ApiOperation(
            value = "Sends an saved email by using the email's id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Successfully sent email")})
    @RequestMapping(method = RequestMethod.POST, value = SEND_EMAIL_BY_ID_URL,
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Void> sendEmailWithId(@PathVariable("id") final long id) {
        this.mailSenderFacade.sendEmailWithId(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}

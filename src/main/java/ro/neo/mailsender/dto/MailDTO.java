package ro.neo.mailsender.dto;

import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.NotNull;


public class MailDTO {

    private long id;

    @NotNull(message = "subject cannot be null")
    private String subject;

    @NotNull(message = "sender cannot be null")
    @Email
    private String sender;

    @NotNull(message = "recipient cannot be null")
    @Email(message = "email cannot be null")
    private String recipient;

    @NotNull(message = "message cannot be null")
    private String message;

    private String sentDate;


    public long getId() {
        return this.id;
    }

    public void setId(final long id) {
        this.id = id;
    }

    public String getSubject() {
        return this.subject;
    }

    public void setSubject(final String subject) {
        this.subject = subject;
    }

    public String getSender() {
        return this.sender;
    }

    public void setSender(final String sender) {
        this.sender = sender;
    }

    public String getRecipient() {
        return this.recipient;
    }

    public void setRecipient(final String recipient) {
        this.recipient = recipient;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(final String message) {
        this.message = message;
    }

    public String getSentDate() {

        return this.sentDate;
    }

    public void setSentDate(final String sentDate) {
        this.sentDate = sentDate;
    }
}

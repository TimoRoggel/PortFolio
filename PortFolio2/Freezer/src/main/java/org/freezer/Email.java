package org.freezer;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.util.Properties;

/**
 * Verantwoordelijk voor het opstellen en verzenden van e-mails met bijlagen.
 * Maakt gebruik van de JavaMail API om e-mailfunctionaliteit te implementeren.
 */
public class Email {
    private final String username = "service.freezerapp@gmail.com"; // Gebruikersnaam voor de e-mailaccount
    private final String appPassword = "jtcf jefi wjwp chhk"; // Wachtwoord voor de e-mailaccount (app-specifiek wachtwoord)

    /**
     * Verzendt een e-mail met een bijlage naar het opgegeven e-mailadres.
     * Maakt gebruik van SMTP instellingen voor Gmail.
     *
     * @param recipientEmail Het e-mailadres van de ontvanger.
     * @param subject Het onderwerp van de e-mail.
     * @param body De hoofdtekst van de e-mail.
     * @param attachment Het bestand dat als bijlage wordt meegestuurd.
     */
    public void sendEmailWithAttachment(String recipientEmail, String subject, String body, File attachment) {
        // SMTP-instellingen configureren
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        // Authenticatie instellen met gebruikersnaam en wachtwoord
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, appPassword);
            }
        });

        try {
            // Een nieuw e-mailbericht maken
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
            message.setSubject(subject);

            // Onderdelen van het e-mailbericht instellen: tekst en bijlage
            MimeBodyPart textPart = new MimeBodyPart();
            textPart.setText(body);

            MimeBodyPart attachmentPart = new MimeBodyPart();
            attachmentPart.attachFile(attachment);

            // Een multipart bericht maken en onderdelen toevoegen
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(textPart);
            multipart.addBodyPart(attachmentPart);

            // Het multipart bericht instellen als inhoud van het e-mailbericht
            message.setContent(multipart);

            // Het e-mailbericht verzenden
            Transport.send(message);

            System.out.println(); // Geef een lege regel weer na het verzenden van de e-mail
        } catch (MessagingException | java.io.IOException e) {
            e.printStackTrace();
        }
    }
}

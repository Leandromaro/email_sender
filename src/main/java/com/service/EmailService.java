package com.service;

import html.Email;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Properties;


/**
 * 
 * Clase que implementa la funcionalidad para el servicio de envio de email.
 * 
 * @author leandromaro
 *
 */
public class EmailService implements EmailServiceInterface {

    private String host;

    private String port;

    private String sender;

    public void setHost(String host) {
        this.host = host;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    private String APPLICATION_NAME="Envio mails de invitación";

    private final Properties properties = new Properties();

    /**
     * Crea una sesion de mail utilizando la configuracion recibida.
     * 
     * @return la sesion de mail
     */
    private Session getSession() {
        //Configuracion Gmail
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.mail.sender", "leandromaro@gmail.com");
        return Session.getDefaultInstance(properties);
    }

    /**
     * Crea un email con un asunto y texto en el cuerpo del mensaje.
     * 
     * @param from
     *            : direccion de email de quien envia
     * @param recipient
     *            : direccion de email a quien va dirigido
     * @param subject
     *            : asunto del email
     * @param text
     *            : texto que se coloca en el cuerpo del mensaje
     * 
     * @return el mensaje que se enviara por mail
     * @throws javax.mail.MessagingException
     */
    private Message createTemplateEmail(String from, String recipient,
            String subject, String text, Session session)
            throws MessagingException {
        // creo un template basico de email
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress((String) properties.get("mail.smtp.mail.sender")));
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(
                recipient));
        message.setSubject(subject);

        message.setContent(text,"text/html");
        return message;
    }

    /**
     * Agrega un archivo adjunto a un mensaje de email
     *
     * @param pathFile
     *            : ruta del archivo a adjuntar
     * @param message
     *            : mensaje en el cual se ageregara el archivo
     * @throws javax.mail.MessagingException
     * @throws java.io.IOException
     */
    private void addAttachment(String pathFile, Message message)
            throws MessagingException, IOException {

        Multipart multipart = (Multipart) message.getContent();
        // Manejo el archivo adjunto
        String filename = pathFile;
        DataSource source = new FileDataSource(filename);

        BodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setDataHandler(new DataHandler(source));
        messageBodyPart.setFileName(filename);

        // Agrego archivo
        multipart.addBodyPart(messageBodyPart);
        // Seteo nuevamente el contenido del mensaje
        message.setContent(multipart);
    }

    /*
     * (non-Javadoc)
     * 
     */
    @Override
    public void sendEmail(String from, String recipient, String subject,
            String airline, String name) throws IOException {
        try {
            // obtengo la sesion de email
            Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication("leandromaro", "PASWORD");
                }
            });
            session = getSession();
            // creo un mail basico
            Email email = new Email();
            Message message = createTemplateEmail(from, recipient, subject,
                    email.getHtml(), session);
            // envio el mail
            Transport transport = session.getTransport("smtp");

            // Transpor para Gmail
            transport.connect("smtp.gmail.com", "leandromaro@gmail.com", "PASWORD");
            //
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        } catch (MessagingException e) {
        	System.out.println("Error");
        }
    }
}

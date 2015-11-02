package com.service;


import java.io.IOException;

/**
 * 
 * Interfaz donde se declaran las operaciones a implementar por el servicio de email
 * 
 * @author leandromaro
 *
 */
public interface EmailServiceInterface {
    
    /**
     * Envia un email con un asunto y texto en el cuerpo del mensaje.
     * 
     * @param from: direccion de email de quien envia
     * @param recipient: direccion de email a quien va dirigido
     * @param subject: asunto del email
     * @param text: texto que se coloca en el cuerpo del mensaje
     */
    void sendEmail(String from, String recipient, String subject,
                   String airline, String name) throws IOException;

}

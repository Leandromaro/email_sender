package com.service.batch;

import com.service.EmailService;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by leandromaro
 */

public class Main {

    private static final String from = "leandromaro@gmail.com";

    public static void main(String [] args) {
        Map<String,String> parameterMap = new HashMap<String, String>();
        parameterMap.put("to",null);
        parameterMap.put("subject",null);
        parameterMap.put("company",null);
        parameterMap.put("name",null);
        for(String param : args){
            String[] paramSplited = param.split("=");
            if(parameterMap.containsKey(paramSplited[0])){
                parameterMap.put(paramSplited[0],paramSplited[1]);
            }
        }
        EmailService emailService = new EmailService();
        try {
            emailService.sendEmail(from,parameterMap.get("to"),parameterMap.get("subject"),parameterMap.get("company"),parameterMap.get("name"));
            System.out.println("El correo electronico se ha enviado correctamente");
        } catch (Exception e) {
            System.out.println("El correo electronico no se ha enviado!");
        }
    }
}

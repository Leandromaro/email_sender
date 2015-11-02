package html;

import java.io.*;
import java.text.MessageFormat;

public class Email {

    public static StringBuilder html = new StringBuilder();

    private BufferedReader bufferedReader = null;

    public Email() {

    }

    private void createEmail() throws IOException {
        ClassLoader classloader = Thread.currentThread()
                .getContextClassLoader();
        InputStream is = classloader.getResourceAsStream("invite.html");
        bufferedReader = new BufferedReader(new InputStreamReader(is));
        // Lectura del fichero
        String linea;
        while((linea=bufferedReader.readLine())!=null)
            html.append(linea);
    }

    public String getHtml() throws IOException {
        createEmail();
        return MessageFormat.format(html.toString(), html);
    }
}

package it.fitdiary.backend.utility.service;


import it.fitdiary.backend.entity.Utente;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.mail.internet.InternetAddress;
import java.io.FileReader;
import java.util.Date;

@Component("mailService")
@EnableAutoConfiguration
public class EmailServiceImpl implements EmailService {

    /**
     * Mail sender per inviare le email.
     */
    private JavaMailSender mailSender;

    /**
     * @param mailSend JavaMailSender
     */
    @Autowired
    public EmailServiceImpl(final JavaMailSender mailSend) {
        this.mailSender = mailSend;
    }

    private void sendEmail(final MimeMessagePreparator preparator) {
        mailSender.send(preparator); // STEP 2
    }

    /**
     * Questa funzione invia un email ad un indirzzo preso in input,
     * con relativo oggetto e testo.
     *
     * @param emailAddress indirizzo di chi deve ricevere l' indirizzo e-mail.
     * @param oggetto      oggetto da inserire nell' email.
     * @param testo        testo che deve contenere l' email.
     */
    @RequestMapping("/mail")
    @ResponseStatus(HttpStatus.CREATED)
    public void sendSimpleMessage(final String emailAddress,
                                  final String oggetto, final String testo)
            throws MailException {
        String[] destination =
                {"demarcodaniele98@gmail.com", "giaqui@gmail.com",
                        "leonardo.monaco45@gmail.com"};
        MimeMessagePreparator preparator = mimeMessage -> {
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
            message.setTo(destination);
            message.setFrom(
                    new InternetAddress("noreply@fitdiary.it"));
            message.setSubject(oggetto);
            message.setSentDate(new Date());
            message.setText(testo);
        };

        sendEmail(preparator);
    }

    /**
     * Questa funzione invia un email ad un utente preso in input,
     * con relativo oggetto e testo.
     *
     * @param newUtente utente di chi deve ricevere l' e-mail.
     * @param password  password dell'utente
     */
    @Override
    public void sendSimpleMessage(
            final Utente newUtente,
            final String password)
            throws MailException {
        var path = getClass().getClassLoader().getResource("mail.html");
        String html;
        try {
            html = IOUtils.toString(new FileReader(path.getFile()));
        } catch (Exception e) {
            System.out.println(path);
            System.out.println(e.getMessage());
            System.out.println(e.getCause());
            System.out.println("Errore durante la lettura del file");
            return;
        }
        html = html.replace("#PREPARATORE#",
                newUtente.getPreparatore().getNome());
        html = html.replace("#UTENTE#", newUtente.getNome());
        html = html.replace("#USERNAME#", newUtente.getEmail());
        html = html.replace("#PASSWORD#", password);

        String[] destination =
                {"demarcodaniele98@gmail.com", "giaqui@gmail.com",
                        "leonardo.monaco45@gmail.com"};
        String finalHtml = html;
        MimeMessagePreparator preparator = mimeMessage -> {
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
            message.setTo(destination);
            message.setFrom(
                    new InternetAddress("noreply@fitdiary.it"));
            message.setSubject(String.format(
                    "Ciao %s, il tuo personal trainer %s, "
                            + "ti ha invitato in FitDiary!",
                    newUtente.getNome(), newUtente.getPreparatore().getNome()));
            message.setSentDate(new Date());
            message.setText(finalHtml, true);
        };

        sendEmail(preparator);


    }
}

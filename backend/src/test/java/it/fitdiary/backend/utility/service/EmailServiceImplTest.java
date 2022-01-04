package it.fitdiary.backend.utility.service;


import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetup;
import it.fitdiary.BackendApplicationTest;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExternalResource;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = BackendApplicationTest.class)
public class EmailServiceImplTest {

    @Autowired
    private EmailServiceImpl emailService;

    @Rule
    public SmtpServerRule smtpServerRule = new SmtpServerRule(2525);


    @Bean
    public JavaMailSender javaMailSender() {
        return new JavaMailSenderImpl();
    }


    @Test
    public void shouldSendSingleMail() throws MessagingException, IOException {
        String to="info@memorynotfound.com";
        String subject="Spring Mail Integration Testing with JUnit and GreenMail Example";
        String content="We show how to write Integration Tests using Spring and GreenMail.";

        emailService.sendSimpleMessage(to,subject,content);

        MimeMessage[] receivedMessages = smtpServerRule.getMessages();
        assertEquals(1, receivedMessages.length);

        MimeMessage current = receivedMessages[0];

        assertEquals(subject, current.getSubject());
        assertEquals(to, current.getAllRecipients()[0].toString());
        assertTrue(String.valueOf(current.getContent()).contains(content));

    }
}

class SmtpServerRule extends ExternalResource {

    private GreenMail smtpServer;
    private int port;

    public SmtpServerRule(int port) {
        this.port = port;
    }

    @Override
    protected void before() throws Throwable {
        super.before();
        smtpServer = new GreenMail(new ServerSetup(port, null, "smtp"));
        smtpServer.start();
    }

    public MimeMessage[] getMessages() {
        return smtpServer.getReceivedMessages();
    }

    @Override
    protected void after() {
        super.after();
        smtpServer.stop();
    }
}
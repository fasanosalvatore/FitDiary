package it.fitdiary.backend.unit.utility.service;

import it.fitdiary.backend.entity.Ruolo;
import it.fitdiary.backend.entity.Utente;
import it.fitdiary.backend.utility.service.EmailServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

@ContextConfiguration(classes = {EmailServiceImpl.class})
@ExtendWith(SpringExtension.class)
class EmailServiceImplTest {
    @Autowired
    private EmailServiceImpl emailServiceImpl;

    @MockBean
    private JavaMailSender javaMailSender;

    private Utente cliente;
    private Utente preparatore;

    @BeforeEach
    void setUp() {
        Ruolo ruoloPrep = new Ruolo(1L, "PREPARATORE", null,null);
        Ruolo ruoloCli = new Ruolo(1L, "CLIENTE", null,null);
        preparatore = new Utente(1L, "Daniele", "De Marco", "fabrizio" +
                "@gmail.com", "Daniele123*", true, LocalDate.parse("2000-03-03"), "M", null, null, null,
                null, null, ruoloPrep, null, null, null,null, null);
        cliente=new Utente(1L, "Daniele", "De Marco", "fabrizio" +
                "@gmail.com", "Daniele123*", true, LocalDate.parse("2000-03-03"), "M", null, null, null,
                null, preparatore, ruoloCli, null, null, null,null, null);
    }

    @Test
    void testSendSimpleMessage_Success() throws MailException {
        doNothing().when(this.javaMailSender)
                .send((org.springframework.mail.javamail.MimeMessagePreparator) any());
        this.emailServiceImpl.sendSimpleMessage("fabrizio@gmail.com",
                "benvenuta in FitDiary", "la tua password è: Pippo123!");
        verify(this.javaMailSender).send(
                (org.springframework.mail.javamail.MimeMessagePreparator) any());
    }

    @Test
    void testSendSimpleMessage_MailAuthenticationException() throws MailException {
        doThrow(new MailAuthenticationException(
                "demarcodaniele98@gmail.com")).when(this.javaMailSender)
                .send((org.springframework.mail.javamail.MimeMessagePreparator) any());
        assertThrows(MailAuthenticationException.class,
                () -> this.emailServiceImpl.sendSimpleMessage("fabrizio@gmail.com",
                        "benvenuta in FitDiary",
                        "la tua password è: Pippo123!"));
        verify(this.javaMailSender).send(
                (org.springframework.mail.javamail.MimeMessagePreparator) any());
    }

    @Test
    void testSendSimpleMessage_SuccessWithUser() throws MailException {
        doNothing().when(this.javaMailSender)
                .send((org.springframework.mail.javamail.MimeMessagePreparator) any());

        this.emailServiceImpl.sendSimpleMessage(cliente, "Pippo123!");
        verify(this.javaMailSender).send(
                (org.springframework.mail.javamail.MimeMessagePreparator) any());
    }

    @Test
    void testSendSimpleMessage_MailAuthenticationExceptionWithUser() throws MailException {
        doThrow(new MailAuthenticationException("mail.html")).when(
                        this.javaMailSender)
                .send((org.springframework.mail.javamail.MimeMessagePreparator) any());

        assertThrows(MailAuthenticationException.class,
                () -> this.emailServiceImpl.sendSimpleMessage(cliente,
                        "Pippo123!"));
        verify(this.javaMailSender).send(
                (org.springframework.mail.javamail.MimeMessagePreparator) any());
    }
}

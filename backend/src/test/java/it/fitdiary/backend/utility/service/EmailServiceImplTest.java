package it.fitdiary.backend.utility.service;


import com.stripe.model.Application;
import it.fitdiary.BackendApplicationTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.subethamail.wiser.Wiser;
import org.subethamail.wiser.WiserMessage;

import javax.mail.internet.MimeMessage;
import java.text.MessageFormat;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;

import static it.fitdiary.backend.utility.service.WiserAssertions.assertReceivedMessage;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = BackendApplicationTest.class)
@ActiveProfiles("test")
public class EmailServiceImplTest {

    private Wiser wiser;

    @Autowired
    private WebApplicationContext wac;
    private MockMvc mockMvc;


    @Before
    public void setUp() throws Exception {
        wiser = new Wiser();
        wiser.start();
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @After
    public void tearDown() throws Exception {
        wiser.stop();
    }

    @Test
    public void send() throws Exception {
        // act
        mockMvc.perform(get("/mail"))
                .andExpect(status().isCreated());
        // assert
        assertReceivedMessage(wiser)
                .from("prova@localhosts")
                .to("prova@localhost")
                .withSubject("prova prova")
                .withContent("prova prova prova prova");
    }
}
class WiserAssertions {

    private final List<WiserMessage> messages;

    public static WiserAssertions assertReceivedMessage(Wiser wiser) {
        return new WiserAssertions(wiser.getMessages());
    }

    private WiserAssertions(List<WiserMessage> messages) {
        this.messages = messages;
    }

    public WiserAssertions from(String from) {
        findFirstOrElseThrow(m -> m.getEnvelopeSender().equals(from),
                assertionError("Nessun mittente trovato", from));
        return this;
    }

    public WiserAssertions to(String to) {
        findFirstOrElseThrow(m -> m.getEnvelopeReceiver().equals(to),
                assertionError("Messaggio non trovato!", to));
        return this;
    }

    public WiserAssertions withSubject(String subject) {
        Predicate<WiserMessage> predicate = m -> subject.equals(unchecked(getMimeMessage(m)::getSubject));
        findFirstOrElseThrow(predicate,
                assertionError("Nessun messaggio con oggetto", subject));
        return this;
    }

    public WiserAssertions withContent(String content) {
        findFirstOrElseThrow(m -> {
            ThrowingSupplier<String> contentAsString =
                    () -> ((String) getMimeMessage(m).getContent()).trim();
            return content.equals(unchecked(contentAsString));
        }, assertionError("Nessun messaggio con testo", content));
        return this;
    }

    private void findFirstOrElseThrow(Predicate<WiserMessage> predicate, Supplier<AssertionError> exceptionSupplier) {
        messages.stream().filter(predicate)
                .findFirst().orElseThrow(exceptionSupplier);
    }

    private MimeMessage getMimeMessage(WiserMessage wiserMessage) {
        return unchecked(wiserMessage::getMimeMessage);
    }

    private static Supplier<AssertionError> assertionError(String errorMessage, String... args) {
        return () -> new AssertionError(MessageFormat.format(errorMessage, args));
    }

    public static <T> T unchecked(ThrowingSupplier<T> supplier) {
        try {
            return supplier.get();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    interface ThrowingSupplier<T> {
        T get() throws Throwable;
    }
}

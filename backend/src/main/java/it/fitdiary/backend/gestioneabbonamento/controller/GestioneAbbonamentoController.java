package it.fitdiary.backend.gestioneabbonamento.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Subscription;
import com.stripe.param.SubscriptionCreateParams;
import it.fitdiary.backend.utility.ResponseHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "api/v1/abbonamento")
public class GestioneAbbonamentoController {
    /**
     * Questo metodo permette ad un prepratore di acquistare un
     * abbonamento su stripe.
     *
     * @param customerId richista http del frontend contente customer
     *                   id di stripe.
     * @return clientsecret codice con cui stripe identifica il cliente.
     * @throws StripeException exception gestita da stripe.
     */
    @PostMapping("/acquista")
    public ResponseEntity<Object> acquistaAbbonamento(
            @RequestBody final JsonNode customerId) throws StripeException {
        Stripe.apiKey = "sk_test_Cp8braM9kf167P3ya1gaFSbZ00aZ3YfXjz";
        SubscriptionCreateParams subCreateParams = SubscriptionCreateParams
                .builder()
                .setCustomer(customerId.get("customerId").asText())
                .addItem(
                        SubscriptionCreateParams
                                .Item.builder()
                                .setPrice("price_1KC0kYLzIIf1tAqOLpInZTKn")
                                .build()
                )
                .setPaymentBehavior(
                        SubscriptionCreateParams
                                .PaymentBehavior.DEFAULT_INCOMPLETE)
                .setCollectionMethod(SubscriptionCreateParams
                .CollectionMethod.CHARGE_AUTOMATICALLY)
                .addAllExpand(List.of("latest_invoice.payment_intent"))
                .build();
        Subscription subscription = Subscription.create(subCreateParams);
        Map<String, Object> response = new HashMap<>();
        response.put("subscriptionId", subscription.getId());
        response.put("clientSecret",
                subscription.getLatestInvoiceObject().getPaymentIntentObject()
                        .getClientSecret());
        return ResponseHandler.generateResponse(
                HttpStatus.CREATED, "Utente", response
        );
    }
}


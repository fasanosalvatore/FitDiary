package it.fitdiary.backend.gestioneabbonamento.controller;

import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.StripeObject;
import com.stripe.model.Subscription;
import com.stripe.param.CustomerCreateParams;
import com.stripe.param.SubscriptionCreateParams;
import it.fitdiary.backend.utility.ResponseHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class GestioneAbbonamentoController {

    @PostMapping("/acquistabbonamento")
    public ResponseEntity<Object> acquistaAbbonamento(@RequestBody com.fasterxml.jackson.databind.JsonNode payload) throws StripeException {
        SubscriptionCreateParams subCreateParams = SubscriptionCreateParams
                .builder()
                .setCustomer(payload.get("customerId").asText())
                .addItem(
                        SubscriptionCreateParams
                                .Item.builder()
                                .setPrice("price_1KC0kYLzIIf1tAqOLpInZTKn")
                                .build()
                )
                .setPaymentBehavior(SubscriptionCreateParams.PaymentBehavior.DEFAULT_INCOMPLETE)
                .setCollectionMethod(true ? SubscriptionCreateParams.CollectionMethod.CHARGE_AUTOMATICALLY : SubscriptionCreateParams.CollectionMethod.SEND_INVOICE)
                .addAllExpand(Arrays.asList("latest_invoice.payment_intent"))
                .build();
        Subscription subscription = Subscription.create(subCreateParams);
        Map<String, Object> response = new HashMap<>();
        response.put("subscriptionId", subscription.getId());
        response.put("clientSecret", subscription.getLatestInvoiceObject().getPaymentIntentObject().getClientSecret());
        return ResponseHandler.generateResponse(HttpStatus.CREATED, "Utente", StripeObject.PRETTY_PRINT_GSON.toJson(response));

    }
}

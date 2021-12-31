import React from 'react';
import {useForm} from 'react-hook-form';
import {
    CardNumberElement,
    useStripe,
    useElements, CardCvcElement, CardExpiryElement, CardElement
} from '@stripe/react-stripe-js'
import {
    Box,
    Button,
    FormLabel,
    GridItem, Heading,
    SimpleGrid, Text, useBreakpointValue, VStack
} from "@chakra-ui/react";
import {Link} from "react-router-dom";
import config from "../config.json";

export default function PaySubscriptionForm() {
    const {register, handleSubmit, getValues, formState: {errors, isSubmitting}} = useForm();
    const colSpan = useBreakpointValue({base: 2, md: 1})
    const stripe = useStripe();
    const elements = useElements();
    const urlAcquisto = `${config.SERVER_URL}/abbonamento/acquista`;

    if (!stripe || !elements) {
        return "";
    }

    async function handleOnSubmitUser(e) {
        e.preventDefault();
        const cardNumber = elements.getElement(CardNumberElement);

        const customerId = "cus_KsFIkjw8Eku2k2"; //possato dal back-end


        const request = JSON.stringify(customerId);

        console.log("REQUEST CLIENT ID: "+request)


        let newSubscriptionResp = await fetch(urlAcquisto, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: customerId
        });

        newSubscriptionResp = await newSubscriptionResp.json();

        if (!newSubscriptionResp.ok) {
            const error = (newSubscriptionResp && newSubscriptionResp.message) || newSubscriptionResp.statusText;
            return Promise.reject(error);
        }

        console.log("CLIENT SECRED: "+newSubscriptionResp)

        const {error, payementIntent} = await stripe.confirmPayment(
            newSubscriptionResp.clientSecret,
            {
                payement_method: {
                    card:cardNumber,
                },
            }
        )

        console.log("PAYEMENT INTENT: "+payementIntent);

        if (error) {

        }
    }

    const mySubmit = async (e) => {
        e.preventDefault();
    }

    return (
        <VStack w="full" h="full" p={0}>
            <Box w="full" border="1px" borderRadius={10} borderColor={"gray.200"} p={3}>
                <VStack spacing={3} alignItems="flex-start" pb={5}>
                    <Heading size="md">Dati Pagamento</Heading>
                </VStack>
                <form style={{width: "100%"}} onSubmit={handleOnSubmitUser}>
                    <SimpleGrid columns={2} columnGap={5} rowGap={5} w="full">
                        <GridItem colSpan={2} w="100%">
                            <FormLabel htmlFor="numeroCarta">Numero Carta</FormLabel>
                            <Box border="1px" borderRadius={4} borderColor={"gray.200"} p={2.5}>
                                <CardNumberElement options={{showIcon: true}}/>
                            </Box>
                        </GridItem>
                        <GridItem colSpan={colSpan} w="100%">
                            <FormLabel htmlFor="dataScadenza">Data Scadenza</FormLabel>
                            <Box border="1px" borderRadius={4} borderColor={"gray.200"} p={2.5}>
                                <CardExpiryElement/>
                            </Box>
                        </GridItem>
                        <GridItem colSpan={colSpan} w="100%">
                            <FormLabel htmlFor="CVV">CVV</FormLabel>
                            <Box border="1px" borderRadius={4} borderColor={"gray.200"} p={2.5}>
                                <CardCvcElement/>
                            </Box>
                        </GridItem>
                        <Button w="full" mt={4} colorScheme='teal' type='submit'>
                            Prosegui al pagamento
                        </Button>
                    </SimpleGrid>
                </form>
            </Box>
        </VStack>
    );
}
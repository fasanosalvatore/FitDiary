import React from 'react';

import {Heading, VStack} from "@chakra-ui/react";
import SignupForm from "../../components/SignupForm";
import {Elements} from "@stripe/react-stripe-js";
import {loadStripe} from "@stripe/stripe-js";


export default function Signup() {

    const stripePromise = loadStripe('pk_test_1IlhLnV1N2jTxmSiB1bWLKgV00QYb66jfu');

    return (
        <VStack w="full" h="full">
            <VStack w="full" h="full" p={[5, 10, 20]}>
                <VStack spacing={3} alignItems="flex-start" pb={5}>
                    <Heading size="2xl">Registrazione</Heading>
                </VStack>
                <Elements stripe={stripePromise}>
                    <SignupForm/>
                </Elements>
            </VStack>}
        </VStack>
    );
}
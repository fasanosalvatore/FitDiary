import React from 'react';
import {useForm} from 'react-hook-form';
import {Link} from "react-router-dom";
import config from './config.json'
import {
    Button,
    FormControl,
    FormErrorMessage,
    FormLabel,
    GridItem, Heading,
    Input, Radio,
    RadioGroup,
    SimpleGrid,
    Stack, Text, Tooltip, useBreakpointValue, VStack
} from "@chakra-ui/react";
import PagamentoAbbonamentoForm from "./PagamentoAbbonamentoForm";
import RegistrazioneForm from "./RegistrazioneForm";
import {
    CardCvcElement,
    CardExpiryElement,
    CardNumberElement,
    Elements,
    useElements,
    useStripe
} from "@stripe/react-stripe-js";
import {loadStripe} from "@stripe/stripe-js";


export default function RegistrazionePage() {

    const stripePromise = loadStripe('pk_test_1IlhLnV1N2jTxmSiB1bWLKgV00QYb66jfu');
    const {register, handleSubmit, getValues, formState: {errors, isSubmitting}} = useForm();

    return (
        <VStack w="full" h="full" p={[5, 10, 20]}>
            <VStack w="full" h="full" p={[5, 10, 20]}>
                <VStack spacing={3} alignItems="flex-start" pb={5}>
                    <Heading size="2xl">Registrazione</Heading>
                </VStack>
                {/*<PagamentoAbbonamentoForm/>*/}
                <Elements stripe={stripePromise}>
                    <RegistrazioneForm />
                </Elements>
            </VStack>}
        </VStack>
    );
}
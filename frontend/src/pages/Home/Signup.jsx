import React from 'react';

import {Box, Flex, Heading, Link, Text, VStack} from "@chakra-ui/react";
import SignupForm from "../../components/SignupForm";
import {Elements} from "@stripe/react-stripe-js";
import {loadStripe} from "@stripe/stripe-js";
import Logo from "../../components/Logo";
import {Link as ReactLink} from "react-router-dom";

export default function Signup() {

    const stripePromise = loadStripe('pk_test_1IlhLnV1N2jTxmSiB1bWLKgV00QYb66jfu');

    return (
        <VStack w="full" h="full" pt={10} pb={10}>
            <Box bg={"white"} borderRadius='md' pb={5}>
                <Box h={"20px"} bgGradient="linear(to-r, blue.500, blue.800)" borderTopRadius={"md"}/>
                <Flex justifyContent={"center"} wrap={"wrap"}>
                    <Text mb={0} pb={0} color={"blue.500"} fontSize={"8xl"} w={"full"} textAlign={"center"}>
                        <Link as={ReactLink} to={"/"}><Logo/></Link>
                    </Text>
                </Flex>
                <VStack w="full" h="full" pl={[5, 10, 10]} pr={[5, 10, 10]}>
                    <VStack spacing={3} alignItems="flex-start" pb={5}>
                        <Heading size="2xl">Registrazione</Heading>
                    </VStack>
                    <Elements stripe={stripePromise}>
                        <SignupForm/>
                    </Elements>
                </VStack>
            </Box>}
        </VStack>
    );
}
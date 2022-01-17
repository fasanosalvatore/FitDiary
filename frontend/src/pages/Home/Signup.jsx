import React from 'react';

import {Box, Button, Flex, Heading, Link, Text, VStack} from "@chakra-ui/react";
import SignupForm from "../../components/SignupForm";
import {Elements} from "@stripe/react-stripe-js";
import {loadStripe} from "@stripe/stripe-js";
import Logo from "../../components/Logo";
import {Link as ReactLink} from "react-router-dom";
import {GradientBar} from "../../components/GradientBar";

export default function Signup() {

    const stripePromise = loadStripe('pk_test_1IlhLnV1N2jTxmSiB1bWLKgV00QYb66jfu');

    return (
        <>
            <ReactLink to="/">
                <Button colorScheme="fitdiary" mt="5" mx="10">
                    Torna alla home
                </Button>
            </ReactLink>
            <VStack w="full" h="full" my="10">
                <Box bg={"white"} borderRadius="md" pb="5">
                    <GradientBar />
                    <Flex justifyContent={"center"} wrap={"wrap"}>
                        <Text
                            mb={0}
                            pb={0}
                            color={"blue.500"}
                            fontSize={"8xl"}
                            w={"full"}
                            textAlign={"center"}
                        >
                            <Link as={ReactLink} to={"/"}>
                                <Logo />
                            </Link>
                        </Text>
                    </Flex>
                    <VStack w="full" h="full" pl={[5, 10, 10]} pr={[5, 10, 10]}>
                        <VStack spacing={3} alignItems="flex-start" pb={5}>
                            <Heading color="gray.700" size="2xl">
                                Registrazione
                            </Heading>
                        </VStack>
                        <Elements stripe={stripePromise}>
                            <SignupForm />
                        </Elements>
                    </VStack>
                </Box>
          }
            </VStack>
        </>
    );
}
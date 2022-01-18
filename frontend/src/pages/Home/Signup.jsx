import React from 'react';

import {Box, Button, Flex, Heading, HStack, Link, Text, VStack} from "@chakra-ui/react";
import SignupForm from "../../components/SignupForm";
import {Elements} from "@stripe/react-stripe-js";
import {loadStripe} from "@stripe/stripe-js";
import Logo from "../../components/Logo";
import {Link as ReactLink} from "react-router-dom";
import {GradientBar} from "../../components/GradientBar";
import Footer from "../../components/Footer";

export default function Signup() {

    const stripePromise = loadStripe('pk_test_1IlhLnV1N2jTxmSiB1bWLKgV00QYb66jfu');

    return (
        <>
            <VStack >
                <Flex width={"full"} justify={"space-between"} align={"center"} bg={"white"} mb={5}>
                    <HStack pl={[0, 5, 10, 20]}>
                        <Link as={ReactLink} to={"/"}>
                            <Logo penColor="black" viewBox={"0 0 250 200"} boxSize={"5em"} />
                        </Link>
                        <Heading>FitDiary</Heading>
                    </HStack>
                    <Box pr={[0, 5, 10, 20]}>
                        <Link as={ReactLink} to={"/login"}>
                            <Button colorScheme='fitdiary' mr='4'>
                                Login
                            </Button>
                        </Link>
                    </Box>
                </Flex>
            <VStack w="full" h="full">
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
                <Footer width={"full"} />
            </VStack>
        </>
    );
}
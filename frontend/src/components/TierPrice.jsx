import React from 'react';
import {
    Box,
    Button,
    Heading,
    HStack,
    List,
    ListIcon,
    ListItem,
    Stack,
    Text,
    useColorModeValue,
    VStack,
} from "@chakra-ui/react";
import { FaCheckCircle } from 'react-icons/fa';
import { createBreakpoints } from '@chakra-ui/theme-tools';
import {Link as ReactLink} from "react-router-dom";

function PriceWrapper({ children }) {
    return (
        <Box
            mb={4}
            shadow="base"
            borderWidth="1px"
            alignSelf={{ base: 'center', xl: 'flex-start' }}
            borderColor={useColorModeValue('gray.200', 'gray.500')}
            borderRadius={'xl'}>
            {children}
        </Box>
    );
}

createBreakpoints({
    sm: '320px',
    md: '768px',
    lg: '960px',
    xl: '1200px',
    '2xl': '1536px',
})

export default function TierPrice(props) {
    return (
        <Box py={12}>
            <VStack spacing={2} textAlign="center">
                <Heading color="gray.800" as="h3" fontSize="4xl">
                    Scegli l'abbonamento più adatto a te
                </Heading>
            </VStack>
            <Stack
                direction={{ base: 'column', sm: 'column', xl: 'row' }}
                textAlign="center"
                justify="center"
                spacing={{ base: 4, lg: 10 }}
                py={10}>
                <PriceWrapper>
                    <Box py={4} px={12}>
                        <Text fontWeight="500" fontSize="2xl">
                            Mensile
                        </Text>
                        <HStack justifyContent="center">
                            <Text fontSize="3xl" fontWeight="600">
                                €
                            </Text>
                            <Text fontSize="5xl" fontWeight="900">
                                600
                            </Text>
                            <Text fontSize="3xl" color="gray.500">
                                /mese
                            </Text>
                        </HStack>
                    </Box>
                    <VStack
                        bg={useColorModeValue('gray.50', 'gray.700')}
                        py={4}
                        borderBottomRadius={'xl'}>
                        <List spacing={3} textAlign="start" px={12}>
                            <ListItem>
                                <ListIcon as={FaCheckCircle} color="green.500" />
                                Clienti illimitati.
                            </ListItem>
                            <ListItem>
                                <ListIcon as={FaCheckCircle} color="green.500" />
                                Protocolli illimitati.
                            </ListItem>
                            <ListItem>
                                <ListIcon as={FaCheckCircle} color="green.500" />
                                I.A. Stima progressi.
                            </ListItem>
                        </List>
                        <Box w="80%" pt={7}>
                            <Button w="full" colorScheme="red" variant="outline" isDisabled>
                                Coming soon
                            </Button>
                        </Box>
                    </VStack>
                </PriceWrapper>

                <PriceWrapper>
                    <Box position="relative">
                        <Box
                            position="absolute"
                            top="-16px"
                            left="50%"
                            style={{ transform: 'translate(-50%)' }}>
                            <Text
                                textTransform="uppercase"
                                bg={useColorModeValue('blue.300', 'blue.700')}
                                px={3}
                                py={1}
                                color={useColorModeValue('gray.900', 'gray.300')}
                                fontSize="sm"
                                fontWeight="600"
                                rounded="xl">
                                Disponbile
                            </Text>
                        </Box>
                        <Box py={4} px={12}>
                            <Text fontWeight="500" fontSize="2xl">
                                Annuale
                            </Text>
                            <HStack justifyContent="center">
                                <Text fontSize="3xl" fontWeight="600">
                                    €
                                </Text>
                                <Text fontSize="5xl" fontWeight="900">
                                    6000
                                </Text>
                                <Text fontSize="3xl" color="gray.500">
                                    /anno
                                </Text>
                            </HStack>
                        </Box>
                        <VStack
                            bg={useColorModeValue('gray.50', 'gray.700')}
                            py={4}
                            borderBottomRadius={'xl'}>
                            <List spacing={3} textAlign="start" px={12}>
                                <ListItem>
                                    <ListIcon as={FaCheckCircle} color="green.500" />
                                    Clienti illimitati.
                                </ListItem>
                                <ListItem>
                                    <ListIcon as={FaCheckCircle} color="green.500" />
                                    Protocolli illimitati.
                                </ListItem>
                                <ListItem>
                                    <ListIcon as={FaCheckCircle} color="green.500" />
                                    I.A. Stima progressi.
                                </ListItem>
                                <ListItem>
                                    <ListIcon as={FaCheckCircle} color="green.500" />
                                    Super Risparmio.
                                </ListItem>
                            </List>
                            <Box w="80%" pt={7}>
                                <ReactLink to={props.link?props.link:""}>
                                    <Button w="full" colorScheme="fitdiary">
                                        Default
                                    </Button>
                                </ReactLink>
                            </Box>
                        </VStack>
                    </Box>
                </PriceWrapper>
                <PriceWrapper>
                    <Box py={4} px={12}>
                        <Text fontWeight="500" fontSize="2xl">
                            Semestrale
                        </Text>
                        <HStack justifyContent="center">
                            <Text fontSize="3xl" fontWeight="600">
                                €
                            </Text>
                            <Text fontSize="5xl" fontWeight="900">
                                3500
                            </Text>
                            <Text fontSize="3xl" color="gray.500">
                                /6mesi
                            </Text>
                        </HStack>
                    </Box>
                    <VStack
                        bg={useColorModeValue('gray.50', 'gray.700')}
                        py={4}
                        borderBottomRadius={'xl'}>
                        <List spacing={3} textAlign="start" px={12}>
                            <ListItem>
                                <ListIcon as={FaCheckCircle} color="green.500" />
                                Clienti illimitati.
                            </ListItem>
                            <ListItem>
                                <ListIcon as={FaCheckCircle} color="green.500" />
                                Protocolli illimitati.
                            </ListItem>
                            <ListItem>
                                <ListIcon as={FaCheckCircle} color="green.500" />
                                I.A. Stima progressi.
                            </ListItem>
                        </List>
                        <Box w="80%" pt={7}>
                            <Button w="full" colorScheme="red" variant="outline" isDisabled>
                                Coming soon
                            </Button>
                        </Box>
                    </VStack>
                </PriceWrapper>
            </Stack>
        </Box>
    );
}
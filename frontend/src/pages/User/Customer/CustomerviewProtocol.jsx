import React from 'react';
import {
    Box,
    Heading,
    Text,
    Avatar,
    Flex, VStack, HStack, Button, FormLabel, Grid, GridItem, SimpleGrid, Table,
    Thead,
    Tbody,
    Tfoot,
    Tr,
    Th,
    Td,
    TableCaption,
} from '@chakra-ui/react';

import {
    RiArrowGoBackLine,
} from 'react-icons/ri';
import authService from "../../../services/auth.service";
import {getProtocollo} from "../../../fakeBackend";
import moment from "moment";


export default function CustomerviewProtocol() {
    /**const utente = authService.getCurrentUser().utente;**/
    const protocollo=getProtocollo().data;
    moment.locale("it-IT");
    return (

        <Flex wrap={"wrap"}>
            <Button leftIcon={<RiArrowGoBackLine/>}>Torna alla lista</Button>
            <Heading w={"full"} mb={5} textAlign={"center"}>Protocollo n.{protocollo.protocollo.id}</Heading>
            <Box bg={"blackAlpha.50"} rounded={20} padding={10} minW={"full"} height={500}>
                <Flex>
                    <VStack w="full" h="full" align="start">
                        <HStack w="full" h="full" align="start">
                            <Flex width="full" justify="space-between">
                                <HStack>
                                    <Heading size={"sx"} textAlign="start"> Data inizio:</Heading>
                                    <Text>{moment(protocollo.protocollo.dataCreazione).format("DD/MM/yyyy")}</Text>
                                </HStack>
                                <HStack>
                                    <Heading size={"sx"} textAlign="center"> Nome preparatore:</Heading>
                                    <Text>{protocollo.protocollo.preparatore.nome} {protocollo.protocollo.preparatore.cognome}</Text>
                                </HStack>
                                <HStack>
                                    <Heading size={"sx"} textAlign="center">Data fine:</Heading>
                                    <Text>{moment(protocollo.protocollo.dataScadenza).format("DD/MM/yyyy")}</Text>
                                </HStack>
                            </Flex>
                        </HStack>
                        <HStack w="full" h="full" align="start">
                            <Flex width="full" justify="space-between">
                                <HStack alignItems="center">
                                    <Box backgroundColor={"white"} p={2} borderRadius={15}>
                                        <Table variant={"striped"}  colorScheme={"gray"} size="md">
                                            <TableCaption>PRIMA</TableCaption>
                                            <Thead color>
                                                <Tr>
                                                    <Th></Th>
                                                    <Th></Th>
                                                </Tr>
                                            </Thead>
                                            <Tbody>
                                                <Tr>
                                                    <Td>Peso</Td>
                                                    <Td>{}Kg</Td>
                                                </Tr>
                                                <Tr>
                                                    <Td>Circonferenza Bicipite</Td>
                                                    <Td>{}cm</Td>
                                                </Tr>
                                                <Tr>
                                                    <Td>Circonferenza Addome</Td>
                                                    <Td>{}cm</Td>
                                                </Tr>
                                                <Tr>
                                                    <Td>Circonferenza Quadricipite</Td>
                                                    <Td>{}cm</Td>
                                                </Tr>
                                            </Tbody>

                                        </Table>
                                    </Box>
                                </HStack>
                                <HStack>
                                    <Heading size={"md"} textAlign="center">Dopo</Heading>
                                </HStack>
                            </Flex>
                        </HStack>
                    </VStack>
                </Flex>
            </Box>

        </Flex>


    )
        ;
}

import React from 'react';
import {
    Box,
    Heading,
    Text,
    Flex, VStack, HStack, Button, Table,
    Thead,
    Tbody,
    Tr,
    Th,
    Td,
    TableCaption, Icon,
} from '@chakra-ui/react';
import {
    RiArrowGoBackLine,
} from 'react-icons/ri';
import {getProtocollo} from "../../../fakeBackend";
import moment from "moment";
import {BsGraphUp,BsGraphDown} from "react-icons/bs";
import {useParams} from "react-router";

export default/**
*
*/
 function CustomerviewProtocol() {
    /**const utente = authService.getCurrentUser().utente;**/
    const protocollo=getProtocollo().data;
    const {id}=useParams();
    console.log(id);
    moment.locale("it-IT");
    return (

        <Flex wrap={"wrap"}>
            <Button leftIcon={<RiArrowGoBackLine/>}>Torna alla lista</Button>
            <Heading w={"full"} mb={5} textAlign={"center"}>Protocollo n.{protocollo.protocollo.id}</Heading>
            <Box bg={"blackAlpha.50"} rounded={20} padding={10} minW={"full"} height={"auto"}>
                <Flex width="full" justify="space-between">
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
                                <HStack alignItems="center" p={20}>
                                    <Box backgroundColor={"white"} p={3} borderRadius={15}>
                                        <Table variant={"unstyled"}  colorScheme={"gray"} size="md">
                                            <TableCaption>PROGRESSI</TableCaption>
                                            <Thead color>
                                                <Tr>
                                                    <Th></Th>
                                                    <Th></Th>
                                                </Tr>
                                            </Thead>
                                            <Tbody>
                                                <Tr>
                                                    <Td>Peso</Td>
                                                    <Td>80 Kg<Icon as={BsGraphUp} color='green.500' marginLeft={4}/></Td>
                                                </Tr>
                                                <Tr>
                                                    <Td>Circonferenza Bicipite{}</Td>
                                                    <Td>40 cm<Icon as={BsGraphDown} color='red.500' marginLeft={4}/></Td>
                                                </Tr>
                                                <Tr>
                                                    <Td>Circonferenza Addome</Td>
                                                    <Td>34 cm<Icon as={BsGraphDown} color='red.500' marginLeft={4}/></Td>
                                                </Tr>
                                                <Tr>
                                                    <Td>Circonferenza Quadricipite</Td>
                                                    <Td>35 cm<Icon as={BsGraphUp} color='green.500' marginLeft={4}/></Td>
                                                </Tr>
                                            </Tbody>
                                        </Table>
                                    </Box>
                                </HStack>
                                <HStack alignItems={"center"} marginTop={"auto"} marginBottom={"auto"} >
                                    <Box backgroundColor={"white"} p={3} borderRadius={15} w={400} h={310}>
                                        <VStack alignItems={"center"}>
                                            <Heading size="xs"> Vuoi visualizzare le tue schede?</Heading>
                                        </VStack>
                                    </Box>
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

import {Box, Flex, GridItem, SimpleGrid, Text, VStack} from "@chakra-ui/react";
import {GradientBar} from "./GradientBar";
import moment from "moment";
import React from "react";

export function UserInfoBox(props) {
    return <Box bg={"white"} rounded={20} pb={10} minW={{base: "100%", xl: "100%"}}
                marginLeft={0}
                marginTop={5}>
        <GradientBar inverse/>
        <Flex>
            <VStack w={"full"} alignItems={"flex-start"}>
                <SimpleGrid columns={2} w={"full"} p="5">
                    <GridItem minH={"5"} p={"3"} borderBottom={"1px"} borderColor={"gray.400"}><Text
                        fontWeight={"bold"} color={"gray.600"}>Data di Nascita </Text></GridItem>
                    <GridItem minH={"5"} p={"3"} borderBottom={"1px"} borderColor={"gray.400"}><Text
                        ml={"2"}
                        color={"gray.400"}>{moment(props.utente.dataNascita).format("DD/MM/yyyy")}</Text></GridItem>
                    <GridItem minH={"5"} p={"3"} borderBottom={"1px"} borderColor={"gray.400"}><Text
                        fontWeight={"bold"} color={"gray.600"}>Telefono </Text></GridItem>
                    <GridItem minH={"5"} p={"3"} borderBottom={"1px"} borderColor={"gray.400"}><Text
                        ml={"2"} color={"gray.400"}>{props.utente.telefono}</Text></GridItem>
                    <GridItem minH={"5"} p={"3"} borderBottom={"1px"} borderColor={"gray.400"}><Text
                        fontWeight={"bold"} color={"gray.600"}>Indirizzo</Text></GridItem>
                    <GridItem minH={"5"} p={"3"} borderBottom={"1px"} borderColor={"gray.400"}><Text
                        ml={"2"} color={"gray.400"}>{props.utente.via} <br/>{props.utente.cap} {props.utente.citta}
                    </Text></GridItem>
                    {props.utente.preparatore && (
                        <>
                            <GridItem minH={"5"} p={"3"} borderBottom={"1px"}
                                      borderColor={"gray.400"}><Text
                                fontWeight={"bold"} color={"gray.600"}>Preparatore </Text></GridItem>

                            <GridItem minH={"5"} p={"3"} borderBottom={"1px"}
                                      borderColor={"gray.400"}><Text
                                ml={"2"}
                                color={"gray.400"}>{props.utente.preparatore?.nome} {props.utente.preparatore?.cognome}</Text></GridItem>
                        </>)}
                    <GridItem minH={"5"} p={"3"} borderBottom={"1px"} borderColor={"gray.400"}><Text
                        fontWeight={"bold"} color={"gray.600"}>Sesso</Text></GridItem>
                    <GridItem minH={"5"} p={"3"} borderBottom={"1px"} borderColor={"gray.400"}><Text
                        ml={"2"}
                        color={"gray.400"}>{props.utente.sesso}</Text></GridItem>
                </SimpleGrid>
            </VStack>
        </Flex>
    </Box>;
}
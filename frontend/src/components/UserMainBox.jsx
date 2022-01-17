import {Avatar, Box, Button, ButtonGroup, Flex, Heading, HStack, Text, VStack} from "@chakra-ui/react";
import {GradientBar} from "./GradientBar";
import React, {useContext} from "react";
import {ButtonsUserActions} from "./ButtonsUserActions";
import {AuthContext} from "../context/AuthContext";
import {Link as ReactLink, Link} from "react-router-dom";

export function UserMainBox(props) {
    const authContext = useContext(AuthContext);

    return <Box bg={"white"} rounded={20} minW={{base: "100%", xl: "47%"}}>
        <GradientBar/>
        <Flex pb={10}>
            <VStack w={"full"}>
                <Avatar size={"xl"} mt={5}></Avatar>
                <Heading fontSize={{base: "xl", md: "3xl"}}
                         color={props.utente.sesso === "M" ? "blue.700" : "pink.700"}>{props.utente.nome} {props.utente.cognome}</Heading>
                <Text color={"gray.400"}>{props.utente.email}</Text>
                <HStack>
                    <ButtonGroup>

                        {!props.showMoreBtns && (
                            <Link to={"/account"}>
                                <Button bg={"fitdiary.300"} color={"white"}>Modifica</Button>
                            </Link>)}
                        {props.showMoreBtns && (
                            <Link to={`/progress?idCliente=${props.utente.id}`}>
                                <Button bg={"green.400"} color={"white"}>Progressi</Button>
                            </Link>
                        )}
                        {props.showMoreBtns && (
                            <Link to={`/protocols?idCliente=${props.utente.id}`}>
                                <Button colorScheme="fitdiary">Protocolli</Button>
                            </Link>
                        )}
                        {props.showMoreBtns && (
                        <Link to="/protocols/create">
                            <Button colorScheme={"fitdiary"} color={"white"}>Crea Protocollo</Button>
                        </Link>
                        )}
                        {authContext.isAdmin() ? (
                            <Button colorScheme="red">Elimina</Button>
                        ) : (
                            <Button colorScheme={props.utente.attivo ? "red" : "green"}>
                                {props.utente.attivo ? "Disattiva" : "Attiva"}
                            </Button>
                        )}
                    </ButtonGroup>;
                </HStack>
            </VStack>
        </Flex>
    </Box>;
}
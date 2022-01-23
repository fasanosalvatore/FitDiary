import {Avatar, Box, Button, ButtonGroup, Flex, Heading, HStack, Text, useToast, VStack} from "@chakra-ui/react";
import {GradientBar} from "./GradientBar";
import React, {useContext, useEffect, useState} from "react";
import {AuthContext} from "../context/AuthContext";
import {Link} from "react-router-dom";
import {Alert} from "./AlertDialog";
import {FetchContext} from "../context/FetchContext";


export function UserMainBox(props) {
    const authContext = useContext(AuthContext);
    const [isAlertOpen, setIsAlertOpen] = React.useState(false)
    const [toastMessage, setToastMessage] = useState(undefined);
    const onAlertClose = () => setIsAlertOpen(false)
    const fetchContext = useContext(FetchContext);
    const cancelRef = React.useRef();
    const [customer, setCustomer] = useState(props.utente);
    const toast = useToast({
        duration: 1000,
        isClosable: true,
        variant: "solid",
        containerStyle: {
            width: '100%',
            maxWidth: '100%',
        },
    })
    useEffect(() => {
        if (toastMessage) {
            const {title, body, stat} = toastMessage;

            toast({
                title,
                description: body,
                status: stat,
            });
        }
        return () => {
            setTimeout(() => {
                setToastMessage(undefined);
            }, 1000);
        }
    }, [toastMessage, toast]);

    function disableUser(id) {
        const disableUser = async () => {
            try {
                const {data} = await fetchContext.authAxios.put("utenti/" + id);
                let updatedCustomer = customer;
                updatedCustomer.attivo = data.data.cliente.attivo;
                setCustomer(updatedCustomer);
                setToastMessage({
                    title: "Completato!",
                    body: `Cliente ${data.data.cliente.attivo ? "attivato" : "disattivato"}`,
                    stat: "success"
                });
                onAlertClose();
            } catch (error) {
                setToastMessage({title: "Errore!", body: error.message, stat: "error"});
                console.log(error.message);
            }
        }
        disableUser();
    }

    return <Box bg={"white"} rounded={20} minW={{base: "100%", xl: "100%"}}>
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
                        {authContext.isTrainer() && (
                            <Alert open={isAlertOpen} leastDestructiveRef={cancelRef}
                                   onClose={onAlertClose}
                                   title={`${props.utente.attivo ? `Disattiva ${props.utente.nome}` : `Attiva ${props.utente.nome}`}`}
                                   body={`Sei sicuro di voler ${props.utente.attivo ? "disattivare" : "attivare"} il cliente ${props.utente.nome} ${props.utente.cognome}?`}
                                   buttonCancel={"Annulla"}
                                   buttonColor={`${props.utente.attivo ? "red" : "green"}`}
                                   buttonLabel={props.utente.attivo ? "Disattiva Utente" : "Attiva Utente"}
                                   buttonOk={props.utente.attivo ? "Disattiva" : "Attiva"}
                                   buttonOkText={`${props.utente.attivo ? "Disattiva" : "Attiva"}`}
                                   onClick={() => disableUser(props.utente.id)}
                            />
                        )}
                    </ButtonGroup>;
                </HStack>
            </VStack>
        </Flex>
    </Box>;
}
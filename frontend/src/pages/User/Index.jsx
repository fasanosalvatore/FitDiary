import {
    Accordion,
    AccordionButton,
    AccordionIcon,
    AccordionItem,
    AccordionPanel,
    Box,
    Button,
    ButtonGroup,
    Divider,
    Flex,
    Heading,
    Table,
    Tbody,
    Td,
    Text,
    Th,
    Thead, Tooltip,
    Tr,
    useMediaQuery,
    useToast
} from "@chakra-ui/react"
import {GradientBar} from "../../components/GradientBar";
import {AuthContext} from "../../context/AuthContext";
import {FetchContext} from "../../context/FetchContext";
import React, {useContext, useEffect, useState} from "react"
import {Link} from "react-router-dom";
import * as PropTypes from "prop-types";
import {Alert} from "../../components/AlertDialog";
import {FiUser} from "react-icons/fi";
import {CalendarIcon, DeleteIcon, LockIcon, TimeIcon, UnlockIcon} from "@chakra-ui/icons";

Alert.propTypes = {
    open: PropTypes.bool,
    leastDestructiveRef: PropTypes.any,
    onClose: PropTypes.func,
    onClick: PropTypes.func
};
export default function Index() {
    const [isMobile] = useMediaQuery("(max-width: 768px)");
    const [isAlertOpen, setIsAlertOpen] = React.useState(false)
    const onAlertClose = () => setIsAlertOpen(false)
    const authContext = useContext(AuthContext);
    const fetchContext = useContext(FetchContext);
    const [customers, setCustomers] = useState([]);
    const cancelRef = React.useRef();
    const [toastMessage, setToastMessage] = useState(undefined);
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

    useEffect(() => {
        console.log("pages/users/index");
        const getData = async () => {
            try {
                const {data} = await fetchContext.authAxios("utenti");
                if (data.data.clienti)
                    setCustomers(data.data.clienti);
                else
                    setCustomers(data.data.utenti);
            } catch (error) {
                setToastMessage({title: "Errore!", body: error.message, stat: "error"});
            }
        }
        getData();
    }, [fetchContext, setCustomers])

    function disableUser(id) {
        const disableUser = async () => {
            try {
                const {data} = await fetchContext.authAxios.put("utenti/" + id);
                let updatedCustomers = customers.map(c => {
                    if (c.id === id) {
                        c.attivo = data.data.cliente.attivo
                    }
                    return c;
                });
                setCustomers(updatedCustomers);
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

    function deleteUser(id) {
        const deleteuser = async () => {
            try {
                const {data} = await fetchContext.authAxios.delete("utenti/" + id);
                let updatedCustomers = customers.filter(c => {
                    if (c.id === id) {
                        return false
                    }
                    return true;
                }).map(c => {
                    return c
                });
                setCustomers(updatedCustomers);
                setToastMessage({title: "Completato!", body: `${data.data}`, stat: "success"});
                onAlertClose();
            } catch (error) {
                setToastMessage({title: "Errore!", body: error.message, stat: "error"});
                console.log(error.message);
            }
        }
        deleteuser();
    }

    return (
        <Flex wrap={"wrap"} p={5}>
            <Heading w={"full"} mb={5}>
                Lista Clienti
            </Heading>
            <Box bg={"white"} rounded={20} w="full">
                <GradientBar/>
                <Flex pb={10} px={5} pt={5} direction="column">
                    {customers.length === 0 ? (
                            <Text>Caricamento in corso...</Text>
                        ) :
                        isMobile
                            ?
                            <Accordion allowToggle>
                                {customers.map((c) => (
                                    <AccordionItem>
                                        <AccordionButton>
                                            <h2>
                                                <Box textStyle={"h1"} flex="1" textAlign="left">
                                                    <Text fontWeight={"bold"}
                                                          color={"gray.600"}>{c.nome} {c.cognome}</Text>
                                                </Box>
                                            </h2>
                                            <AccordionIcon/>
                                        </AccordionButton>
                                        <AccordionPanel pb={4}>
                                            <Flex>
                                                <Text color={"gray.500"} fontWeight={"bold"} mx={2}>Ruolo:</Text>
                                                <Text>{c.ruolo.nome}</Text>
                                            </Flex>
                                            <Divider my={2}/>
                                            <Flex alignItems={"center"}>
                                                <Text color={"gray.500"} fontWeight={"bold"} mx={2}>Azioni:</Text>
                                                <ButtonGroup size={"sm"}>
                                                    {!authContext.isAdmin() &&
                                                        <>
                                                            <Link to={`/protocols?idCliente=${c.id}`}>
                                                                <Button colorScheme="fitdiary">Protocolli</Button>
                                                            </Link>
                                                            <Link to={`/progress?idCliente=${c.id}`}>
                                                                <Button bg={"green.400"}
                                                                        color={"white"}>Progressi</Button>
                                                            </Link>
                                                        </>
                                                    }

                                                    {authContext.isAdmin() ? (
                                                        <Alert open={isAlertOpen} leastDestructiveRef={cancelRef}
                                                               onClose={onAlertClose}
                                                               title={`Elimina ${c.nome}`}
                                                               body={`Sei sicuro di voler eliminare l'utente ${c.nome} ${c.cognome}?`}
                                                               buttonCancel={"Annulla"} buttonColor="red"
                                                               buttonOk="Elimina"
                                                               onClick={() => deleteUser(c.id)}
                                                        />
                                                    ) : (
                                                        <Alert open={isAlertOpen} leastDestructiveRef={cancelRef}
                                                               onClose={onAlertClose}
                                                               title={`${c.attivo ? `Disattiva ${c.nome}` : `Attiva ${c.nome}`}`}
                                                               body={`Sei sicuro di voler ${c.attivo ? "disattivare" : "attivare"} il cliente ${c.nome} ${c.cognome}?`}
                                                               buttonCancel={"Annulla"}
                                                               buttonColor={`${c.attivo ? "red" : "green"}`}
                                                               buttonOk={`${c.attivo ? "Disattiva" : "Attiva"}`}
                                                               onClick={() => disableUser(c.id)}
                                                        />
                                                    )}
                                                </ButtonGroup>
                                            </Flex>
                                        </AccordionPanel>
                                    </AccordionItem>
                                ))}
                            </Accordion>
                            :
                            <Table w={"full"}>
                                <Thead>
                                    <Tr>
                                        <Th>Nome</Th>
                                        <Th>Ruolo</Th>
                                        <Th textAlign={"right"}>Azioni</Th>
                                    </Tr>
                                </Thead>
                                <Tbody>
                                    {customers.map((c) => (
                                        <Tr>
                                            <Td><Flex alignItems={"center"}>{c.nome} {c.cognome}</Flex></Td>
                                            <Td>{c.ruolo.nome}</Td>
                                            <Td textAlign={"right"}>
                                                <ButtonGroup>
                                                    {!authContext.isAdmin() &&
                                                        <>
                                                            <Link to={`/protocols?idCliente=${c.id}`}>
                                                                <Tooltip hasArrow label="Protocolli" bg="fitdiary.600">
                                                                    <Button colorScheme="fitdiary"><CalendarIcon/></Button>
                                                                </Tooltip>
                                                            </Link>
                                                            <Link to={`/progress?idCliente=${c.id}`}>
                                                                <Tooltip hasArrow label="Progressi" bg="green.400">
                                                                <Button bg={"green.400"}
                                                                        color={"white"}><TimeIcon/></Button>
                                                                </Tooltip>
                                                            </Link>
                                                            <Link to={`${c.id}`}>
                                                                <Tooltip hasArrow label="Profilo" bg="fitdiary.400">
                                                                    <Button bg={"fitdiary.400"}
                                                                            color={"white"}><FiUser/></Button>
                                                                </Tooltip>
                                                            </Link>
                                                        </>
                                                    }

                                                    {authContext.isAdmin() ? (
                                                        <Alert open={isAlertOpen} leastDestructiveRef={cancelRef}
                                                               onClose={onAlertClose}
                                                               title={`Elimina ${c.nome}`}
                                                               body={`Sei sicuro di voler eliminare l'utente ${c.nome} ${c.cognome}?`}
                                                               buttonCancel={"Annulla"} buttonColor="red"
                                                               buttonLabel={"Elimina"}
                                                               buttonOk={<DeleteIcon/>}
                                                               buttonOkText={"Elimina"}
                                                               onClick={() => deleteUser(c.id)}
                                                        />
                                                    ) : (
                                                        <Alert open={isAlertOpen} leastDestructiveRef={cancelRef}
                                                               onClose={onAlertClose}
                                                               title={`${c.attivo ? `Disattiva ${c.nome}` : `Attiva ${c.nome}`}`}
                                                               body={`Sei sicuro di voler ${c.attivo ? "disattivare" : "attivare"} il cliente ${c.nome} ${c.cognome}?`}
                                                               buttonCancel={"Annulla"}
                                                               buttonColor={`${c.attivo ? "red" : "green"}`}
                                                               buttonLabel={c.attivo ? "Disattiva Utente" : "Attiva Utente"}
                                                               buttonOk={c.attivo ? <LockIcon/> : <UnlockIcon/>}
                                                               buttonOkText={`${c.attivo ? "Disattiva" : "Attiva"}`}
                                                               onClick={() => disableUser(c.id)}
                                                        />
                                                    )}
                                                </ButtonGroup>
                                            </Td>
                                        </Tr>
                                    ))}
                                </Tbody>
                            </Table>
                    }
                </Flex>
            </Box>
        </Flex>
    );
}


import {Box, Button, ButtonGroup, Flex, Heading, Text, useToast} from "@chakra-ui/react"
import {GradientBar} from "../../components/GradientBar";
import {AuthContext} from "../../context/AuthContext";
import {FetchContext} from "../../context/FetchContext";
import React, {useContext, useEffect, useState} from "react"
import {Link} from "react-router-dom";
import * as PropTypes from "prop-types";
import {Alert} from "../../components/AlertDialog";

Alert.propTypes = {
    open: PropTypes.bool,
    leastDestructiveRef: PropTypes.any,
    onClose: PropTypes.func,
    onClick: PropTypes.func
};
export default function Index() {
    const [isAlertOpen, setIsAlertOpen] = React.useState(false)
    const onAlertClose = () => setIsAlertOpen(false)
    const authContext = useContext(AuthContext);
    const fetchContext = useContext(FetchContext);
    const [customers, setCustomers] = useState([]);
    const [toastMessage, setToastMessage] = useState(undefined);
    const cancelRef = React.useRef();
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
            const { title, body, stat } = toastMessage;

            toast({
                title,
                description: body,
                status: stat,
            });
        }
        return () => {
            setTimeout(() => {
                setToastMessage(undefined);
            },1000);
        }
    }, [toastMessage, toast]);
    
    useEffect(() => {
        console.log("pages/users/index");
        const getData = async () => {
            try {
                const { data } = await fetchContext.authAxios("utenti");
                if(data.data.clienti)
                    setCustomers(data.data.clienti);
                else
                    setCustomers(data.data.utenti);
            } catch (error) {
                setToastMessage({title: "Errore!", body: error.message, stat: "error"});
            }
        }
        getData();
    },[fetchContext, setCustomers])

    function disableUser(id) {
        console.log(id);
        const disableUser = async () => {
            try {
                const { data } = await fetchContext.authAxios.put("utenti/" + id);
                let updatedCustomers = customers.map(c => {
                    if(c.id === id) {
                        c.attivo = data.data.cliente.attivo
                    }
                    return c;
                });
                setCustomers(updatedCustomers);
                console.log(data.data.cliente.attivo)
                setToastMessage({title: "Completato!", body: `Cliente ${data.data.cliente.attivo ? "attivato" : "disattivato"}`, stat: "success"});
                onAlertClose();
            } catch (error) {
                setToastMessage({title: "Errore!", body: error.message, stat: "error"});
                console.log(error.message);
            }
        }
        disableUser();
    }

    function deleteUser(id) {
        console.log(id);
        const deleteuser = async () => {
            try {
                const { data } = await fetchContext.authAxios.delete("utenti/" + id);
                let updatedCustomers = customers.filter(c => {
                    if(c.id === id) {
                        return false
                    }
                    return true;
                }).map(c => { return c });
                setCustomers(updatedCustomers);
                console.log(data)
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
          <GradientBar />
          <Flex pb={10} px={5} pt={5} direction="column">
            {customers.length === 0 ? (
              <Text>Caricamento in corso...</Text>
            ) : (
              customers.map((c) => (

                <Box key={c.id} rounded={10} p={1} bg="gray.100" w="full" mb={5}>
                    {console.log(c)}
                    <Flex alignItems="center" justifyContent="space-between">
                        <Link to={`/customers/${c.id}`}>
                        <Text>
                            {c.nome} {c.cognome}
                        </Text>
                        </Link>
                        <Text>{c.ruolo.nome}</Text>
                        <ButtonGroup>
                            <Link to={`/protocols?idCliente=${c.id}`}>
                                <Button colorScheme="fitdiary">Protocolli</Button>
                            </Link>
                            <Link to={`/progress?idCliente=${c.id}`}>
                                <Button bg={"green.400"} color={"white"}>Progressi</Button>
                            </Link>

                            {authContext.isAdmin() ? (
                                <Alert open={isAlertOpen} leastDestructiveRef={cancelRef} onClose={onAlertClose}
                                       title={`Elimina ${c.nome}`}
                                       body={`Sei sicuro di voler eliminare l'utente ${c.nome} ${c.cognome}?`}
                                       buttonCancel={"Annulla"} buttonColor="red" buttonOk="Elimina"
                                       onClick={() => deleteUser(c.id)}
                                />
                            ) : (
                                <Alert open={isAlertOpen} leastDestructiveRef={cancelRef} onClose={onAlertClose}
                                       title={`${c.attivo ? `Disattiva ${c.nome}` : `Attiva ${c.nome}`}`}
                                       body={`Sei sicuro di voler ${c.attivo ? "disattivare" : "attivare"} il cliente ${c.nome} ${c.cognome}?`}
                                       buttonCancel={"Annulla"} buttonColor={`${c.attivo ? "red" : "green"}`} buttonOk={`${c.attivo ? "Disattiva" : "Attiva"}`}
                                       onClick={() => disableUser(c.id)}
                                />
                            )}
                        </ButtonGroup>
                    </Flex>
                </Box>
              ))
            )}
          </Flex>
        </Box>
      </Flex>
    );
}


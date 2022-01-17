import {Box, Button, ButtonGroup, Flex, Heading, Text} from "@chakra-ui/react"
import {GradientBar} from "../../components/GradientBar";
import {AuthContext} from "../../context/AuthContext";
import {FetchContext} from "../../context/FetchContext";
import React, {useContext, useEffect, useState} from "react"
import {Link} from "react-router-dom";

export default function Index() {
    const authContext = useContext(AuthContext);
    const fetchContext = useContext(FetchContext);
    const [customers, setCustomers] = useState([]);
    
    useEffect(() => {
        const getData = async () => {
            try {
                const { data } = await fetchContext.authAxios("utenti");
                console.log(data.data.clienti);
                setCustomers(data.data.clienti);
                console.log(customers);
            } catch (error) {
                console.log(error.message);
            }
        }
        getData();
    },[customers,fetchContext])

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
                                <Button colorScheme="red">Elimina</Button>
                            ) : (
                                <Button colorScheme={c.attivo ? "red" : "green"}>
                                    {c.attivo ? "Disattiva" : "Attiva"}
                                </Button>
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


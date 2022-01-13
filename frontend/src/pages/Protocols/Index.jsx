import React, { useContext, useEffect, useState } from 'react';
import {
    Box,
    Button,
    Heading,
    HStack,
    Input,
    InputGroup,
    InputLeftElement,
    Link,
    Table,
    TableCaption,
    Tbody,
    Td,
    Th,
    Thead,
    Tr,
    useToast,
    VStack,
} from '@chakra-ui/react';
import moment from "moment";
import { InfoIcon, SearchIcon } from '@chakra-ui/icons'
import { FetchContext } from "../../context/FetchContext";
import { AuthContext } from "../../context/AuthContext";
import { GradientBar } from "../../components/GradientBar";
import { Link as ReactLink } from "react-router-dom"

const urlProtocollo = "/protocols"

function Index() {
    moment.locale("it-IT");
    const authContext = useContext(AuthContext)
    const { authState } = authContext;
    const [search, setSearch] = useState("");
    const onChange = (e) => {
        setSearch(e.target.value); // e evento target chi lancia l'evento e il value è il valore
    }
    const toast = useToast({
        duration: 9000, isClosable: true, variant: "solid", containerStyle: {
            width: '100%', maxWidth: '100%',
        },

    })
    const [isLoading, setLoading] = useState(true); // ricarica la pagina quando la variabile termina
    const fetchContext = useContext(FetchContext);
    const [listProtocolli, setProtocolli] = useState();
    useEffect(() => {
        console.log( "ciao")
        const listaProtocolli = async () => {
            console.log("lista");
            try {
                let params = (new URL(document.location)).searchParams;
                console.log(params.get("idCliente"));
                const idCliente = params.get("idCliente") || "";
                const { data } = await fetchContext.authAxios("protocolli" + (idCliente !== "" ? "?clienteId=" + idCliente : ""));
                setProtocolli(data.data);
                setLoading(false); //viene settato a false per far capire di aver caricato tutti i dati
            } catch (error) {
                console.log(error);
                toast({
                    title: "ERROR", description: "NOT AUTHORIZED", status: "error"
                })
            }

        }
        listaProtocolli();
    }, [fetchContext]);

    return (
        <>
            {authState.userInfo.roles[0].toLowerCase() === "preparatore" && (
                <ReactLink to="/protocols/create">
                    <Button colorScheme={"fitdiary"} color={"white"} mx={[0, 5, 10, 20]} my={5}>
                        Crea Protocollo
                    </Button>
                </ReactLink>
            )}
            {!isLoading && listProtocolli && (
                <VStack w="full" h="full" py={5} px={[0, 5, 10, 20]}>
                    <Box bg={"white"} borderRadius="xl" pb={5} w={"full"}>
                        <GradientBar />
                        <Heading size="lg" textAlign={"center"} pt={5}>
                            Visualizzazione Protocolli
                        </Heading>
                        <Box pl={10} pr={10} pb={5} pt={5}>
                            <HStack>
                                <InputGroup>
                                    <InputLeftElement
                                        pointerEvents="none"
                                        children={<SearchIcon color="gray.300" />}
                                    />
                                    <Input
                                        className="SearchInput"
                                        type="text"
                                        onChange={onChange}
                                        placeholder="Search"
                                    />
                                </InputGroup>
                            </HStack>
                            {/* Barra di ricerca*/}
                            {listProtocolli.protocollo.length > 0 ? (
                                <>
                                    <Heading w={"full"} mb={5} textAlign={"center"}>
                                        Ecco lo storico dei protocolli di{" "}
                                        {listProtocolli.protocollo[0].cliente.nome}{" "}
                                        {listProtocolli.protocollo[0].cliente.cognome}
                                    </Heading>
                                    <Table variant={"unstyled"} colorScheme={"gray"} size="md">
                                        <TableCaption>Lista Protocolli</TableCaption>
                                        <Thead color>
                                            <Tr>
                                                <Th>ID</Th>
                                                <Th>Data Creazione</Th>
                                                <Th>Data Scadenza</Th>
                                                <Th>Azione</Th>
                                            </Tr>
                                        </Thead>
                                        <Tbody>
                                            {listProtocolli.protocollo.map(
                                                (protocol) =>
                                                    (protocol.id === parseInt(search) ||
                                                        search === "") && (
                                                        <Tr>
                                                            <Td>{protocol.id}</Td>
                                                            <Td>
                                                                {moment(protocol.dataCreazione).format(
                                                                    "DD/MM/yyyy"
                                                                )}
                                                            </Td>
                                                            <Td>
                                                                {moment(protocol.dataScadenza).format(
                                                                    "DD/MM/yyyy"
                                                                )}
                                                            </Td>
                                                            <Td>
                                                                <Link to={urlProtocollo + "/" + protocol.id}>
                                                                    {" "}
                                                                    <InfoIcon />
                                                                </Link>
                                                            </Td>
                                                        </Tr>
                                                    )
                                            )}
                                        </Tbody>
                                    </Table>
                                </>
                            ) : (
                                <Heading py={5} textAlign={"center"}>
                                    Non c'è niente qui...
                                </Heading>
                            )}
                        </Box>
                    </Box>
                </VStack>
            )}
        </>
    );
}


export default Index;


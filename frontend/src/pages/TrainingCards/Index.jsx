import React, {useContext, useEffect, useState} from 'react';
import {
    Box,
    Button,
    Flex,
    Heading,
    HStack,
    Input,
    InputGroup,
    InputLeftElement,
    Table,
    TableCaption,
    Tbody,
    Td,
    Text,
    Th,
    Thead,
    Tr,
    useToast,
} from '@chakra-ui/react';
import moment from "moment";
import {InfoIcon, SearchIcon} from '@chakra-ui/icons'
import {FetchContext} from "../../context/FetchContext";
import {AuthContext} from "../../context/AuthContext";
import {GradientBar} from "../../components/GradientBar";
import {Link as ReactLink} from "react-router-dom"

const urlProtocollo = "/protocols"

function Index() {
    moment.locale("it-IT");
    const authContext = useContext(AuthContext)
    const { authState } = authContext;
    const [search, setSearch] = useState("");
    const onChange = (e) => {
        setSearch(e.target.value); // e evento target chi lancia l'evento e il value è il valore
    }
    const [toastMessage, setToastMessage] = useState(undefined);
    const toast = useToast({
        duration: 3000,
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
                duration: 9000,
                isClosable: true
            });
        }
    }, [toastMessage, toast]);
    const [isLoading, setLoading] = useState(true); // ricarica la pagina quando la variabile termina
    const fetchContext = useContext(FetchContext);
    const [listProtocolli, setProtocolli] = useState();

    useEffect(() => {
        console.log("pages/protocols/index")
        const listaProtocolli = async () => {
            try {
                let params = (new URL(document.location)).searchParams;
                const idCliente = params.get("idCliente") || "";
                const { data } = await fetchContext.authAxios("protocolli" + (idCliente !== "" ? "?clienteId=" + idCliente : ""));
                console.log(data)
                setProtocolli(data.data);
                setLoading(false); //viene settato a false per far capire di aver caricato tutti i dati
            } catch (error) {
                setToastMessage({title:"Errore", body:error.message, stat:"error"});
            }

        }
        listaProtocolli();
    }, [fetchContext]);

    return (
        <>

            {!isLoading && listProtocolli && (
                <Flex wrap={"wrap"} p={5}>
                    <Flex w={"full"} alignItems={"center"} mb={5} justifyContent={"space-between"}>
                        <Heading w={"full"}>Lista Schede Allenamento</Heading>
                        {authState.userInfo.roles[0].toLowerCase() === "preparatore" && (
                            <ReactLink to="/TrainingCards/create">
                                <Button
                                    colorScheme={"fitdiary"}
                                    color={"white"}
                                >
                                    Crea Scheda Allenamento
                                </Button>
                            </ReactLink>
                        )}
                    </Flex>
                    <Box bg={"white"} roundedTop={20} minW={{ base: "100%", xl: "100%" }} h={"full"}>
                        <GradientBar />

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
                                    <Text fontSize="xl" my={5}>
                                        Lista dei protocolli
                                    </Text>
                                    <Table variant={"striped"} colorScheme={"gray"} size="md">
                                        <TableCaption>Lista Protocolli</TableCaption>
                                        <Thead bg="fitdiary.100">
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
                                                        <Tr key={protocol.id}>
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
                                                                <ReactLink to={urlProtocollo + "/" + protocol.id}>
                                                                    <InfoIcon />
                                                                </ReactLink>
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
                </Flex>
            )}
        </>
    );
}


export default Index;


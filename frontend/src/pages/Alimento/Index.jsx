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

const urlScheda = "/listaAlimenti"

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
    const [listAlimenti, setAlimenti] = useState();

    useEffect(() => {
        const loadlistaAlimenti = async () => {
            try {
                const { data } = await fetchContext.authAxios("alimenti/getAllAlimenti");
                console.log(data)
                setAlimenti(data.data);
                setLoading(false); //viene settato a false per far capire di aver caricato tutti i dati
            } catch (error) {
                setToastMessage({title:"Errore", body:error.message, stat:"error"});
            }

        }
        loadlistaAlimenti();
    }, [fetchContext]);

    return (
        <>

            {!isLoading && listAlimenti && (
                <Flex wrap={"wrap"} p={5}>
                    <Flex w={"full"} alignItems={"center"} mb={5} justifyContent={"space-between"}>
                        <Heading w={"full"}>Lista Alimentii</Heading>
                        {authState.userInfo.roles[0].toLowerCase() === "preparatore" && (
                            <ReactLink to="/DietCards/create">
                                <Button
                                    colorScheme={"fitdiary"}
                                    color={"white"}
                                >
                                    Aggiungi Alimenti Selezionati
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
                            {listAlimenti.lista_alimenti.length > 0 ? (
                                <>
                                    <Text fontSize="xl" my={5}>
                                        Lista degli alimenti
                                    </Text>
                                    <Table variant={"striped"} colorScheme={"gray"} size="md">
                                        <TableCaption>Lista Alimenti</TableCaption>
                                        <Thead bg="fitdiary.100">
                                            <Tr>
                                                <Th>ID</Th>
                                                <Th>Nome</Th>
                                                <Th>Kcal</Th>
                                                <Th>Proteine</Th>
                                                <Th>Grassi</Th>
                                                <Th>Carboidrati</Th>
                                                <Th>Azione</Th>
                                            </Tr>
                                        </Thead>
                                        <Tbody>
                                            {listAlimenti.lista_alimenti.map(
                                                (alimento) =>
                                                    (alimento.nome.toLowerCase().startsWith(search.toLowerCase()) ||
                                                        search === "") && (
                                                        <Tr key={alimento.id}>
                                                            <Td>{alimento.id}</Td>
                                                            <Td>{alimento.nome}</Td>
                                                            <Td>{alimento.kcal}</Td>
                                                            <Td>{alimento.proteine}</Td>
                                                            <Td>{alimento.grassi}</Td>
                                                            <Td>{alimento.carboidrati}</Td>
                                                            <Td>
                                                                <ReactLink to={urlScheda + "/" + alimento.id}>
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


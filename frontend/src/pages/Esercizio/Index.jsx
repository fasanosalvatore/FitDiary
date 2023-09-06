import React, {useContext, useEffect, useState} from 'react';
import {
    Box,
    Button,
    Flex,
    Heading,
    HStack, Image,
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

const protocol = window.location.protocol;
const domain = window.location.host;
const port = window.location.port;


const full = `${protocol}//${domain}`

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
    const [listEsercizi, setEsercizi] = useState();

    useEffect(() => {
        const loadlistaEsercizi = async () => {
            try {
                const { data } = await fetchContext.authAxios("esercizi/getAllEsercizi");
                console.log(data)
                setEsercizi(data.data);
                setLoading(false); //viene settato a false per far capire di aver caricato tutti i dati
            } catch (error) {
                setToastMessage({title:"Errore", body:error.message, stat:"error"});
            }

        }
        loadlistaEsercizi();
    }, [fetchContext]);

    return (
        <>

            {!isLoading && listEsercizi && (
                <Flex wrap={"wrap"} p={5}>
                    <Flex w={"full"} alignItems={"center"} mb={5} justifyContent={"space-between"}>
                        <Heading w={"full"}>Lista Esercizi</Heading>
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
                            {listEsercizi.esercizi.length > 0 ? (
                                <>
                                    <Text fontSize="xl" my={5}>
                                        Lista degli alimenti
                                    </Text>
                                    <Table variant={"striped"} colorScheme={"gray"} size="md">
                                        <TableCaption>Lista Alimenti</TableCaption>
                                        <Thead bg="fitdiary.100">
                                            <Tr>
                                                <Th>Immagine</Th>
                                                <Th>Nome</Th>
                                                <Th>Categoria</Th>
                                            </Tr>
                                        </Thead>
                                        <Tbody>
                                            {listEsercizi.esercizi.map((esercizio) => (esercizio.nome.toLowerCase().startsWith(search.toLowerCase()) || search === "") && (
                                                <Tr key={esercizio.id}>
                                                    <Td p={1}
                                                        m={0}>
                                                        <Image
                                                            objectFit='contain'
                                                            boxSize={100}
                                                            src={full + "/" + esercizio.pathFoto}
                                                            alt='Foto non disponibile'/>
                                                    </Td>
                                                    <Td>{esercizio.nome}</Td>
                                                    <Td>{esercizio.tipoEsercizio.nome}</Td>
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


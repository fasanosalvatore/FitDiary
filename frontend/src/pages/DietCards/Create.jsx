import React, {useContext, useEffect, useState} from 'react';
import {useForm} from 'react-hook-form';
import {
    Accordion,
    AccordionButton,
    AccordionIcon,
    AccordionItem,
    AccordionPanel,
    Box,
    Button,
    Flex,
    FormControl,
    FormErrorMessage,
    FormLabel,
    Heading,
    HStack,
    IconButton,
    Image,
    Input,
    InputGroup,
    InputLeftElement,
    Modal,
    ModalBody,
    ModalCloseButton,
    ModalContent,
    ModalFooter,
    ModalHeader,
    ModalOverlay,
    Table,
    TableCaption,
    Tbody,
    Td,
    Text,
    Th,
    Thead,
    Tooltip,
    Tr,
    useDisclosure,
    useToast
} from "@chakra-ui/react";
import {FetchContext} from "../../context/FetchContext";
import {GradientBar} from "../../components/GradientBar";
import {AddIcon, SearchIcon} from "@chakra-ui/icons";
import moment from "moment/moment";
import {AuthContext} from "../../context/AuthContext";
import {getFakeSchedaAlimentare, getSchedaAlimentare} from "../../fakeBackend";
import {useParams} from "react-router";

export default function Create() {
    const fetchContext = useContext(FetchContext);
    const {setSchedaAlimentareSubmit, handleSubmitScheda, formState: {errors, isSubmitting}} = useForm();
    const urlCreateSchedaALimentare = "schedaalimentare/create";
    const {isOpen, onOpen, onClose} = useDisclosure()
    const {handleSubmit, setValue} = useForm();
    const toast = useToast({
        duration: 3000, isClosable: true, variant: "solid", position: "top", containerStyle: {
            width: '100%', maxWidth: '100%',
        },
    })
    const authContext = useContext(AuthContext)
    const {authState} = authContext;
    const [search, setSearch] = useState("");
    const [fetchCompleted, setFetchCompleted] = useState(false); // Nuovo stato
    const [schedaAlimentare, setSchedaAlimentare] = useState();
    const {id} = useParams();
    const onChange = (e) => {
        setSearch(e.target.value); // e evento target chi lancia l'evento e il value è il valore
    }
    const [toastMessage, setToastMessage] = useState(undefined);
    const [isLoading, setLoading] = useState(true); // ricarica la pagina quando la variabile termina
    const [listAlimenti, setAlimenti] = useState();

    const days = ["Lunedi", "Martedi", "Mercoledi", "Giovedi", "Venerdi", "Sabato", "Domenica"];

    const protocol = window.location.protocol;
    const domain = window.location.host;
    const full = `${protocol}//${domain}`

    function toastParam(title, description, status) {
        return {
            title: title, description: description, status: status
        };
    }

    moment.locale("it-IT");

    useEffect(() => {
        if (toastMessage) {
            const {title, body, stat} = toastMessage;

            toast({
                title, description: body, status: stat, duration: 9000, isClosable: true
            });
        }
    }, [toastMessage, toast]);

    useEffect(() => {
        const loadlistaAlimenti = async () => {
            if (!fetchCompleted) {
                try {
                    const {data} = await fetchContext.authAxios("alimenti/getAllAlimenti");
                    setAlimenti(data.data);
                    //setLoading(false); //viene settato a false per far capire di aver caricato tutti i dati
                } catch (error) {
                    setToastMessage({title: "Errore", body: error.message, stat: "error"});
                }
            }
        }

        loadlistaAlimenti();
    }, [fetchContext, fetchCompleted]);

    useEffect(() => {
        const getSchedaAlimentare = async () => {
            if (!fetchCompleted) {
                try {
                    const {data} = await fetchContext.authAxios("schedaalimentare/getSchedaAlimentareById?idScheda=1");
                    console.log("Prendo le schede alimentari")
                    console.log(data.data.scheda_alimentare)
                    setSchedaAlimentare(data.data.scheda_alimentare);
                    setLoading(false);
                    setFetchCompleted(true); // Imposta fetchCompleted a true dopo il completamento
                } catch (error) {
                    console.log(error);
                    toast({
                        title: "ERROR", description: "NOT AUTHORIZED", status: "error"
                    })
                }
            }
        }
        getSchedaAlimentare();
    }, [fetchContext, toast, fetchCompleted])

    const onSubmit = async (values) => {
        try {
            const {data} = await fetchContext.authAxios.post(urlCreateSchedaALimentare, values);
            console.log(data);
            toast(toastParam("Sceheda Alimentare creata con successo", "Scheda aggiunta all'elenco", "success"));
        } catch (error) {
            console.log(error.response.data.message)
            toast({
                title: 'Errore', description: error.response.data.message, status: 'error',
            })
        }
    }

    return (<>
        {!isLoading && (<Flex wrap={"wrap"} p={5}>
            <Flex alignItems={"center"} mb={5}>
                <Heading w={"full"}>Crea una scheda Alimentare</Heading>
            </Flex>
            <Box bg={"white"} roundedTop={20} minW={{base: "100%", xl: "100%"}} h={"full"}>
                <GradientBar/>
                <Box pl={[0, 5, 20]} pr={[0, 5, 20]} pb={10} pt={5}>
                    <form style={{width: "100%"}} onSubmit={handleSubmit(onSubmit)}>
                        <FormControl id={"nome"} isInvalid={errors.nome} pt={5}>
                            <FormLabel htmlFor="nome">Nome delle scheda</FormLabel>
                            <Input type="text" placeholder="Scheda pettorali Avanzata"/>
                            <FormErrorMessage>{errors.nome && errors.nome.message}</FormErrorMessage>
                        </FormControl>

                        <Modal isOpen={isOpen} onClose={onClose} isCentered={true} size={"4xl"}>
                            <ModalOverlay/>
                            <ModalContent>
                                <form onSubmit={handleSubmit(onSubmit)}>
                                    <ModalHeader fontSize={'3xl'} textAlign={"center"}>Aggiungi alimenti alla
                                        scheda</ModalHeader>
                                    <ModalCloseButton/>
                                    <ModalBody align={"center"}>
                                        <Flex justify="center">
                                            <HStack align="center">
                                                <FormControl id={"elencoAlimenti"}>
                                                    <HStack>
                                                        {!isLoading && listAlimenti && (<Flex wrap={"wrap"} p={5}>
                                                            <Box bg={"white"} roundedTop={20}
                                                                 minW={{base: "100%", xl: "100%"}}
                                                                 h={"full"}>
                                                                <GradientBar/>
                                                                <Box pl={10} pr={10} pb={5} pt={5}>
                                                                    <HStack>
                                                                        <InputGroup>
                                                                            <InputLeftElement
                                                                                pointerEvents="none"
                                                                                children={<SearchIcon
                                                                                    color="gray.300"/>}
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
                                                                    {listAlimenti.lista_alimenti.length > 0 ? (<>
                                                                        <Text fontSize="xl" my={5}>
                                                                            Lista degli alimenti
                                                                        </Text>
                                                                        <Table variant={"striped"}
                                                                               alignContent={"center"}
                                                                               colorScheme={"gray"}
                                                                               size="md">
                                                                            <TableCaption>Lista
                                                                                Alimenti</TableCaption>
                                                                            <Thead bg="fitdiary.100">
                                                                                <Tr>
                                                                                    <Th>Immagine</Th>
                                                                                    <Th>Nome</Th>
                                                                                    <Th>Kcal</Th>
                                                                                    <Th>Proteine</Th>
                                                                                    <Th>Grassi</Th>
                                                                                    <Th>Carboidrati</Th>
                                                                                    <Th>Azione</Th>
                                                                                </Tr>
                                                                            </Thead>
                                                                            <Tbody>
                                                                                {listAlimenti.lista_alimenti.map((alimento) => (alimento.nome.toLowerCase().startsWith(search.toLowerCase()) || search === "") && (
                                                                                    <Tr key={alimento.id}>
                                                                                        <Td p={1}
                                                                                            m={0}>
                                                                                            <Image
                                                                                                objectFit='contain'
                                                                                                boxSize={100}
                                                                                                src={full + "/" + alimento.pathFoto}
                                                                                                alt='Foto non disponibile'/>
                                                                                        </Td>
                                                                                        <Td>{alimento.nome}</Td>
                                                                                        <Td>{alimento.kcal}</Td>
                                                                                        <Td>{alimento.proteine}</Td>
                                                                                        <Td>{alimento.grassi}</Td>
                                                                                        <Td>{alimento.carboidrati}</Td>
                                                                                        <Td><Button
                                                                                            colorScheme='fitdiary'
                                                                                            onClick={onOpen}
                                                                                            fontSize={"s"}>
                                                                                            <AddIcon/>
                                                                                        </Button></Td>
                                                                                    </Tr>))}
                                                                            </Tbody>
                                                                        </Table>
                                                                    </>) : (<Heading py={5}
                                                                                     textAlign={"center"}>
                                                                        Non c'è niente qui...
                                                                    </Heading>)}
                                                                </Box>
                                                            </Box>
                                                        </Flex>)}
                                                    </HStack>
                                                </FormControl>
                                            </HStack>
                                        </Flex>
                                    </ModalBody>
                                    <ModalFooter alignItems={"center"}>
                                    </ModalFooter>
                                </form>
                            </ModalContent>
                        </Modal>

                        <Accordion allowToggle defaultIndex={[0]} w="full" mt={"60px"}>
                            {days.map((d, i) => {
                                return (<AccordionItem key={i}>
                                        <h2>
                                            <AccordionButton>
                                                <Box flex='1' textAlign='left'>
                                                    {d}
                                                </Box>
                                                <AccordionIcon/>
                                            </AccordionButton>
                                        </h2>
                                        <AccordionPanel pb={4}>
                                            {schedaAlimentare.listaAlimenti
                                                .filter((al) => al.giornoDellaSettimana.toLowerCase() === d.toLowerCase())
                                                .map((alimento, key) => {
                                                    return (<>
                                                        <Table borderBottom={"solid 1px "}
                                                               borderColor={"blue.200"} key={key}
                                                               variant="unstyled" size="md">
                                                            <Thead>
                                                                <Tr>
                                                                    <Th w={"15%"}>Immagine</Th>
                                                                    <Th w={"15%"}>Nome</Th>
                                                                    <Th w={"14%"}>Kcal</Th>
                                                                    <Th w={"14%"}>Proteine</Th>
                                                                    <Th w={"14%"}>Grassi</Th>
                                                                    <Th w={"14%"}>Carboidrati</Th>
                                                                    <Th w={"14%"}>Quantità</Th>
                                                                </Tr>
                                                            </Thead>
                                                            <Tbody>
                                                                <Tr>
                                                                    <Td w={"15%"} isNumeric>{}</Td>
                                                                    <Td w={"15%"} isNumeric>{}</Td>
                                                                    <Td w={"14%"} isNumeric>{}</Td>
                                                                    <Td w={"14%"} isNumeric>{}</Td>
                                                                    <Td w={"14%"} isNumeric>{}</Td>
                                                                    <Td w={"14%"} isNumeric>{}</Td>
                                                                    <Td w={"14%"} isNumeric>{}</Td>
                                                                </Tr>
                                                            </Tbody>
                                                        </Table>
                                                    </>)

                                                })}
                                            <Flex pt={5}>
                                                <Button
                                                    w="full"
                                                    colorScheme='fitdiary'
                                                    onClick={onOpen}>
                                                    Aggiungi alimenti</Button>
                                            </Flex>
                                        </AccordionPanel>
                                    </AccordionItem>

                                )
                            })}

                        </Accordion>
                        <Button w="full" mt={4} colorScheme='fitdiary' isLoading={isSubmitting} type='submit'>
                            Crea e Salva scheda
                        </Button>
                    </form>
                </Box>
            </Box>
        </Flex>)}
    </>);
}
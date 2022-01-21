import React, {useCallback, useContext, useEffect, useState,} from 'react';
import {useDropzone} from 'react-dropzone';

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
    Heading,
    HStack,
    IconButton,
    Input,
    Modal,
    ModalBody,
    ModalCloseButton,
    ModalContent,
    ModalFooter,
    ModalHeader,
    ModalOverlay,
    Table,
    Tbody,
    Td,
    Text,
    Th,
    Thead,
    Tooltip,
    Tr,
    useDisclosure,
    useToast,
    VStack
} from '@chakra-ui/react';
import {CloseIcon, EditIcon} from '@chakra-ui/icons';
import {AuthContext} from "../../../context/AuthContext";
import moment from "moment";
import {useNavigate, useParams} from "react-router";
import {FetchContext} from "../../../context/FetchContext";
import {useForm} from "react-hook-form"
import {GradientBar} from "../../../components/GradientBar";

export default function View() {
    const urlProtocolli = "protocolli";
    const days = [1, 2, 3, 4, 5, 6, 7];
    const authContext = useContext(AuthContext);
    const { authState } = authContext;
    const { id } = useParams();
    const navigate = useNavigate();
    const [protocollo, setProtocolli] = useState();
    const fetchContext = useContext(FetchContext);
    const [isLoading, setLoading] = useState(true);
    const { isOpen, onOpen, onClose } = useDisclosure()
    const [selectedSchedaAllenamento, setselectedSchedaAllenamento] = useState(null);
    const {handleSubmit, setValue} = useForm();
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


    const onDropAllenamento = useCallback(acceptedSchedaAllenamento => {
        setValue("schedaAllenamento", acceptedSchedaAllenamento);
    }, [setValue])

    const {
        acceptedFiles: acceptedSchedaAllenamento,
        getRootProps: getRootPropsAllenamento,
        getInputProps: getInputPropsAllenamento,
        isDragActive: isDragActiveAllenamento
    } = useDropzone({onDrop: onDropAllenamento, maxFiles: 1})

    const onSubmit = async (values) => {
        const formData = new FormData();
        if (values.schedaAllenamento)
            formData.append("schedaAllenamento", values.schedaAllenamento[0])
        try {
            const {data} = await fetchContext.authAxios.put(urlProtocolli+"/"+id, formData)
            setProtocolli(data.data);
            setToastMessage({title:"Completato!",body:"Scheda Allenamento modificata correttamente",stat:"success"})
        } catch (error) {
            setToastMessage({title:"Errore",body:error.response.data.data,stat:"error"})
        }

    }

    useEffect(() => {
        setselectedSchedaAllenamento(acceptedSchedaAllenamento[0]);
    }, [acceptedSchedaAllenamento]);


    useEffect(() => {
        console.log("pages/protocols/trainingcards/view");
        const listaProtocolli = async () => {
            try {
                const { data } = await fetchContext.authAxios("protocolli/" + id);
                setProtocolli(data.data);
                setLoading(false);
            } catch (error) {
                setToastMessage({title:"Errore", body:error.message, stat:"error"});
            }
        }
        listaProtocolli();
    }, [fetchContext,id])
    return (
        <>
            {!isLoading && (
                <Flex wrap={"wrap"} p={5}>
                    <Flex alignItems={"center"} mb={5}>
                        <Heading w={"full"}>Allenamento</Heading>
                    </Flex>
                    <Box bg={"white"} roundedTop={20} minW={{ base: "100%", xl: "100%" }} h={"full"}>
                        <GradientBar />
                    <Box bg={"white"} rounded={20} borderBottomRadius={0} padding={10} minW={"full"} height={"auto"}>
                        <Flex width="full" justify="space-between">
                            <VStack w="full" h="full" align="start">
                                <HStack w="full" h="full" align="start">
                                    <Flex width="full" justify="space-between">
                                        <HStack>
                                            <Heading size={"sx"} textAlign="start"> Cliente:</Heading>
                                            <Text>{protocollo.protocollo.cliente.nome} {protocollo.protocollo.cliente.cognome}</Text>
                                        </HStack>
                                        <HStack>
                                            <Heading size={"sx"} textAlign="center"> Data scadenza:</Heading>
                                            <Text>{moment(protocollo.protocollo.dataScadenza).format("DD/MM/yyyy")}</Text>
                                        </HStack>
                                    </Flex>
                                </HStack>
                                <HStack w="full" h="full" align="start" pb={30}>
                                    <Flex width="full" justify="space-between">
                                        <HStack>
                                            <Heading size={"sx"} textAlign="start">Frequenza:</Heading>
                                            <Text>{protocollo.protocollo.schedaAllenamento.frequenza} volte a
                                                settimana</Text>
                                        </HStack>
                                        {
                                            authState.userInfo.roles[0].toLowerCase() === 'preparatore' && (
                                                <HStack>
                                                    <Tooltip label='Modifica Scheda' fontSize='md'>
                                                        <IconButton
                                                            colorScheme='fitdiary'
                                                            onClick={onOpen}
                                                            icon={<EditIcon/>}
                                                        />
                                                    </Tooltip>
                                                    <Modal isOpen={isOpen} onClose={onClose} isCentered={true} size={"2xl" }>
                                                        <ModalOverlay />
                                                        <ModalContent>
                                                            <form onSubmit={handleSubmit(onSubmit)}>
                                                            <ModalHeader textAlign={"center"}>Carica la nuova scheda modificata</ModalHeader>
                                                            <ModalCloseButton />
                                                            <ModalBody align={"center"}>
                                                                <Flex justify="center">
                                                                    <HStack align="center">
                                                                        <FormControl id={"schedaAllenamento"}>
                                                                            {selectedSchedaAllenamento != null && (
                                                                                <HStack>
                                                                                    <CloseIcon cursor={"pointer"} color={"red"} onClick={() => {
                                                                                        setselectedSchedaAllenamento(null);
                                                                                        setValue("schedaAllenamento", null);
                                                                                    }}/>
                                                                                    <Text>
                                                                                        {selectedSchedaAllenamento.path}
                                                                                    </Text>
                                                                                </HStack>
                                                                            )}
                                                                            {!selectedSchedaAllenamento && (
                                                                                <div {...getRootPropsAllenamento()}>
                                                                                    <Box w={"full"} bg={"gray.50"} p={5} border={"dotted"}
                                                                                         borderColor={"gray.200"}>
                                                                                        <Input {...getInputPropsAllenamento()}/>
                                                                                        {
                                                                                            isDragActiveAllenamento ?
                                                                                                <p style={{color: "gray", textAlign: "center"}}>
                                                                                                    Lascia il file qui ...
                                                                                                </p> :
                                                                                                <p style={{color: "gray", textAlign: "center"}}>
                                                                                                    Clicca e trascina un file qui, oppure clicca per
                                                                                                    selezionare un file
                                                                                                </p>
                                                                                        }
                                                                                    </Box>
                                                                                </div>
                                                                            )}
                                                                        </FormControl>
                                                                    </HStack>
                                                                </Flex>
                                                            </ModalBody>
                                                            <ModalFooter>
                                                                <Button colorScheme='fitdiary' type={"submit"}>Carica</Button>
                                                            </ModalFooter>
                                                            </form>
                                                        </ModalContent>
                                                    </Modal>
                                                </HStack>
                                            )}
                                    </Flex>
                                </HStack>
                                <Accordion allowToggle defaultIndex={[0]} w="full" mt={"60px"}>
                                    {
                                        days.map((d, i) => {
                                            if(i + 1 > protocollo.protocollo.schedaAllenamento.frequenza) return;
                                            return (
                                                <AccordionItem key={i}>
                                                    <h2>
                                                        <AccordionButton>
                                                            <Box flex='1' textAlign='left'>
                                                                {d}° Allenamento
                                                            </Box>
                                                            <AccordionIcon />
                                                        </AccordionButton>
                                                    </h2>
                                                    <AccordionPanel pb={4}>
                                                        {protocollo.protocollo.schedaAllenamento.listaEsercizi
                                                            .filter((esercizio) => esercizio.numeroAllenamento === d + "")
                                                            .map((c, key) => {
                                                                return (
                                                                    <Table borderBottom={"solid 1px "}
                                                                        borderColor={"blue.200"} key={key}
                                                                        variant="unstyled" size="md">
                                                                        <Thead>
                                                                            <Tr>
                                                                                <Th textAlign="center"
                                                                                    w={"25%"}>Esercizio</Th>
                                                                                <Th textAlign="center"
                                                                                    w={"25%"}>Serie</Th>
                                                                                <Th textAlign="center"
                                                                                    w={"25%"}>Ripetizioni</Th>
                                                                                <Th textAlign="center"
                                                                                    w={"25%"}>Recupero</Th>
                                                                            </Tr>
                                                                        </Thead>
                                                                        <Tbody>
                                                                            <Tr>
                                                                                <Td textAlign="center"
                                                                                    w={"25%"}>{c.nome} ({c.categoria})</Td>
                                                                                <Td textAlign="center" w={"25%"}
                                                                                    isNumeric>{c.serie}</Td>
                                                                                <Td textAlign="center" w={"25%"}
                                                                                    isNumeric>{c.ripetizioni}</Td>
                                                                                <Td textAlign="center" w={"25%"}
                                                                                    isNumeric>{c.recupero}</Td>
                                                                            </Tr>
                                                                        </Tbody>
                                                                    </Table>
                                                                )
                                                            })}
                                                    </AccordionPanel>
                                                </AccordionItem>
                                            )
                                        })}
                                </Accordion>
                            </VStack>
                        </Flex>
                    </Box>
                    </Box>
                </Flex>

            )}
        </>
    );
}
import React, {useCallback, useContext, useEffect, useState,} from 'react';
import {useDropzone} from 'react-dropzone';
import {
    Accordion, AccordionButton, AccordionIcon, AccordionItem, AccordionPanel, Box, Button, Flex, Heading,
    HStack, IconButton, Table, Tbody, Td, Text, Th, Thead, Tooltip, Tr, useToast, VStack,Modal, ModalOverlay,
    ModalContent, ModalHeader, ModalFooter, ModalBody, ModalCloseButton, useDisclosure,FormControl,FormLabel,Input
} from '@chakra-ui/react';
import {EditIcon,CloseIcon} from '@chakra-ui/icons';
import {RiArrowGoBackLine,} from 'react-icons/ri';
import {AuthContext} from "../../../context/AuthContext";
import moment from "moment";
import {useNavigate, useParams} from "react-router";
import {FetchContext} from "../../../context/FetchContext";
import {useForm} from "react-hook-form"

export default function View() {
    const urlSchedaAllenamento = "schedaAllenamento";
    const days = [1, 2, 3, 4, 5, 6, 7];
    const authContext = useContext(AuthContext);
    const {authState} = authContext;
    const {id} = useParams();
    const navigate = useNavigate();
    const [protocollo, setProtocolli] = useState();
    const fetchContext = useContext(FetchContext);
    const [isLoading, setLoading] = useState(true);
    const { isOpen, onOpen, onClose } = useDisclosure()
    const [selectedFileAllenamento, setselectedFileAllenamento] = useState(null);
    const {handleSubmit, register, setValue} = useForm();

    const toast = useToast({
        duration: 9000,
        isClosable: true,
        variant: "solid",
        containerStyle: {
            width: '100%',
            maxWidth: '100%',
        },

    })

    function toastParam(title, description, status) {
        return {
            title: title, description: description, status: status
        };
    }

    const onDropAllenamento = useCallback(acceptedFileAllenamento => {
        console.log(acceptedFileAllenamento)
        setValue("fileAllenamento", acceptedFileAllenamento);
    }, [setValue])

    const {
        acceptedFiles: acceptedFileAllenamento,
        getRootProps: getRootPropsAllenamento,
        getInputProps: getInputPropsAllenamento,
        isDragActive: isDragActiveAllenamento
    } = useDropzone({onDrop: onDropAllenamento, maxFiles: 1})

    const onSubmit = async (values) => {
        const formData = new FormData();
        if (values.schedaAllenamento)
            formData.append("schedaAllenamento", values.schedaAllenamento[0])
        try {
            const {data} = await fetchContext.authAxios.post(urlSchedaAllenamento, formData)
            console.log(data);
            toast(toastParam("Modifica effettuata con successo!", "Scheda allenamento modificata correttamente", data.status))
        } catch (error) {
            console.log(error.response);
            toast(toastParam("Errore", error.response.data.data, "error"))
        }

    }

    useEffect(() => {
        setselectedFileAllenamento(acceptedFileAllenamento[0]);
    }, [acceptedFileAllenamento]);


    useEffect(() => {
        const listaProtocolli = async () => {
            try {
                const {data} = await fetchContext.authAxios("protocolli/" + id);
                setProtocolli(data.data);
                setLoading(false);
            } catch (error) {
                console.log(error);
                toast({
                    title: "ERROR",
                    description: "NOT AUTHORIZED",
                    status: "error"
                })
            }
        }
        listaProtocolli();
    }, [fetchContext, toast, id])
    return (
        <>
            {!isLoading && (
                <Flex wrap={"wrap"}>
                    <Button onClick={() => {
                        navigate(-1)
                    }} ml={5} mt={5} colorScheme={"blue"} leftIcon={<RiArrowGoBackLine/>}>Torna al protocollo</Button>
                    <Heading w={"full"} mb={5} textAlign={"center"}>Scheda Allenamento</Heading>
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
                                                            colorScheme='blue'
                                                            onClick={onOpen}
                                                            icon={<EditIcon/>}
                                                        />
                                                    </Tooltip>
                                                    <Modal isOpen={isOpen} onClose={onClose} isCentered={true} size={"2xl" }>
                                                        <ModalOverlay />
                                                        <ModalContent>
                                                            <form onSubmit={handleSubmit(onSubmit)}>
                                                            <ModalHeader textAlign={"center"}>Carica la nuova scheda modificata</ModalHeader>
                                                            <ModalCloseButton/>
                                                            <ModalBody align={"center"}>
                                                                <Flex justify="center">
                                                                    <HStack align="center">
                                                                        <FormControl id={"fileAllenamento"}>
                                                                            {selectedFileAllenamento != null && (
                                                                                <HStack>
                                                                                    <CloseIcon cursor={"pointer"} color={"red"} onClick={() => {
                                                                                        setselectedFileAllenamento(null);
                                                                                        setValue("fileAllenamento", null);
                                                                                    }}/>
                                                                                    <Text>
                                                                                        {selectedFileAllenamento.path}
                                                                                    </Text>
                                                                                </HStack>
                                                                            )}
                                                                            {!selectedFileAllenamento && (
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
                                                                <Button colorScheme='blue' type={"submit"}>Carica</Button>
                                                            </ModalFooter>
                                                            </form>
                                                        </ModalContent>
                                                    </Modal>
                                                </HStack>
                                            )}
                                    </Flex>
                                </HStack>
                                <Accordion defaultIndex={[0]} w="full" mt={"60px"}>
                                    {
                                        days.map((d, i) => {
                                            return (

                                                <AccordionItem key={i}>
                                                    <h2>
                                                        <AccordionButton>
                                                            <Box flex='1' textAlign='left'>
                                                                {d}° Allenamento
                                                            </Box>
                                                            <AccordionIcon/>
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
                </Flex>

            )}
        </>
    );
}
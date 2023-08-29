import React, {useCallback, useContext, useEffect, useState} from 'react';
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
import {AuthContext} from "../../context/AuthContext";
import moment from "moment";
import {FetchContext} from "../../context/FetchContext";
import {useForm} from "react-hook-form"
import {GradientBar} from "../../components/GradientBar";
import {useParams} from "react-router";

export default function View() {
    const authContext = useContext(AuthContext);
    const { authState } = authContext;
    const { id } = useParams();
    const [protocollo, setProtocolli] = useState();
    const fetchContext = useContext(FetchContext);
    const [isLoading, setLoading] = useState(true);
    const urlProtocolli = "protocolli";
    const { isOpen, onOpen, onClose } = useDisclosure()
    const [selectedSchedaAlimentare, setselectedSchedaAlimentare] = useState(null);
    const { handleSubmit, setValue } = useForm();

    const days = ["Lunedi", "Martedi", "Mercoledi", "Giovedi", "Venerdi", "Sabato", "Domenica"];

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

    const onDropAlimentare = useCallback(acceptedSchedaAlimentare => {
        console.log(acceptedSchedaAlimentare)
        setValue("schedaAlimentare", acceptedSchedaAlimentare);
    }, [setValue])

    const {
        acceptedFiles: acceptedSchedaAlimentare,
        getRootProps: getRootPropsAlimentare,
        getInputProps: getInputPropsAlimentare,
        isDragActive: isDragActiveAlimentare
    } = useDropzone({ onDrop: onDropAlimentare, maxFiles: 1 })

    const onSubmit = async (values) => {
        const formData = new FormData();
        console.log(values)
        if (values.schedaAlimentare)
            formData.append("schedaAlimentare", values.schedaAlimentare[0])
        try {
            const { data } = await fetchContext.authAxios.put(urlProtocolli + "/" + id, formData)
            console.log(data);
            setProtocolli(data.data);
            toast(toastParam("Modifica effettuata con successo!", "Scheda Alimentare modificata correttamente", data.status))
        } catch (error) {
            console.log(error.response);
            toast(toastParam("Errore", error.response.data.data, "error"))
        }
    }

    useEffect(() => {
        console.log("pages/protocols/dietcard/view");
        setselectedSchedaAlimentare(acceptedSchedaAlimentare[0]);
    }, [acceptedSchedaAlimentare]);

    useEffect(() => {
        const listaProtocolli = async () => {
            try {
                const { data } = await fetchContext.authAxios("protocolli/" + id);
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
    }, [fetchContext, id, toast])

    return (
        <>
            {!isLoading && (
                <Flex wrap={"wrap"} p={5}>
                    <Flex alignItems={"center"} mb={5}>
                        <Heading w={"full"}>Alimentazione</Heading>
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
                                            <Heading size={"sx"} textAlign="start">Kcal totali:</Heading>
                                            <Text>{protocollo.protocollo.schedaAlimentare.kcalAssunte}</Text>
                                        </HStack>

                                        {
                                            authState.userInfo.roles[0].toLowerCase() === 'preparatore' && (

                                                <HStack>
                                                    <Tooltip label='Modifica Scheda' fontSize='md'>
                                                        <IconButton
                                                            onClick={onOpen}
                                                            colorScheme='fitdiary'
                                                            icon={<EditIcon />}
                                                        />
                                                    </Tooltip>
                                                    <Modal isOpen={isOpen} onClose={onClose} isCentered={true} size={"2xl"}>
                                                        <ModalOverlay />
                                                        <ModalContent>
                                                            <form onSubmit={handleSubmit(onSubmit)}>
                                                                <ModalHeader textAlign={"center"}>Carica la nuova scheda modificata</ModalHeader>
                                                                <ModalCloseButton />
                                                                <ModalBody align={"center"}>
                                                                    <Flex justify="center">
                                                                        <HStack align="center">
                                                                            <FormControl id={"schedaAlimentare"}>
                                                                                {selectedSchedaAlimentare != null && (
                                                                                    <HStack>
                                                                                        <CloseIcon cursor={"pointer"} color={"red"} onClick={() => {
                                                                                            setselectedSchedaAlimentare(null);
                                                                                            setValue("schedaAlimentare", null);
                                                                                        }} />
                                                                                        <Text>
                                                                                            {selectedSchedaAlimentare.path}
                                                                                        </Text>
                                                                                    </HStack>
                                                                                )}
                                                                                {!selectedSchedaAlimentare && (
                                                                                    <div {...getRootPropsAlimentare()}>
                                                                                        <Box w={"full"} bg={"gray.50"} p={5} border={"dotted"}
                                                                                            borderColor={"gray.200"}>
                                                                                            <Input {...getInputPropsAlimentare()} />
                                                                                            {
                                                                                                isDragActiveAlimentare ?
                                                                                                    <p style={{ color: "gray", textAlign: "center" }}>
                                                                                                        Lascia il file qui ...
                                                                                                    </p> :
                                                                                                    <p style={{ color: "gray", textAlign: "center" }}>
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
                                            return (

                                                <AccordionItem key={i}>
                                                    <h2>
                                                        <AccordionButton>
                                                            <Box flex='1' textAlign='left'>
                                                                {d}
                                                            </Box>
                                                            <AccordionIcon />
                                                        </AccordionButton>
                                                    </h2>
                                                    <AccordionPanel pb={4}>
                                                        {protocollo.protocollo.schedaAlimentare.listaAlimenti
                                                            .filter((alimento) => alimento.giorno === (i + 1) + "")
                                                            .map((c, key) => {

                                                                return (
                                                                    <Table borderBottom={"solid 1px "}
                                                                        borderColor={"blue.200"} key={key}
                                                                        variant="unstyled" size="md">
                                                                        <Thead>
                                                                            <Tr>
                                                                                <Th textAlign="center"
                                                                                    w={"33%"}>{c.pasto}</Th>
                                                                                <Th textAlign="center"
                                                                                    w={"33%"}>Quantità</Th>
                                                                                <Th textAlign="center"
                                                                                    w={"33%"}>Kcal</Th>
                                                                            </Tr>
                                                                        </Thead>
                                                                        <Tbody>
                                                                            <Tr>
                                                                                <Td textAlign="center"
                                                                                    w={"33%"}>{c.nome}</Td>
                                                                                <Td textAlign="center" w={"33%"}
                                                                                    isNumeric>{c.grammi}</Td>
                                                                                <Td textAlign="center" w={"33%"}
                                                                                    isNumeric>{c.kcal}</Td>
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
        </>);
}
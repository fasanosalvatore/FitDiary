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
    const [schedaAlimentare, setSchedaAlimentare] = useState();
    const fetchContext = useContext(FetchContext);

    const [isLoading, setLoading] = useState(true);
    const [fetchCompleted, setFetchCompleted] = useState(false); // Nuovo stato

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

    useEffect(() => {
        const getSchedaAlimentare = async () => {
            if (!fetchCompleted) {
                try {
                    const {data} = await fetchContext.authAxios("schedaalimentare/getSchedaAlimentareById?idScheda=" + id);
                    setSchedaAlimentare(data.data.scheda_alimentare);
                    setLoading(false);
                    setFetchCompleted(true); // Imposta fetchCompleted a true dopo il completamento
                } catch (error) {
                    console.log(error);
                    toast({
                        title: "ERROR",
                        description: "NOT AUTHORIZED",
                        status: "error"
                    })
                }
            }
        }
        getSchedaAlimentare();
    }, [fetchContext, id, toast,fetchCompleted])

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
                                            <Heading size={"sx"} textAlign="start"> Nome scheda:</Heading>
                                            <Text>{schedaAlimentare.nome} </Text>
                                        </HStack>

                                    </Flex>

                                </HStack>
                                <HStack w="full" h="full" align="start" pb={30}>
                                    <Flex width="full" justify="space-between">
                                        <HStack>
                                            <Heading size={"sx"} textAlign="start">Kcal totali:</Heading>
                                            <Text>{schedaAlimentare.kcalAssunte}</Text>
                                        </HStack>
                                        {
                                            authState.userInfo.roles[0].toLowerCase() === 'preparatore' && (

                                                <HStack>
                                                    <Tooltip label='Modifica Scheda' fontSize='md'>
                                                        <IconButton
                                                            colorScheme='fitdiary'
                                                            icon={<EditIcon />}
                                                        />
                                                    </Tooltip>
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
                                                        {schedaAlimentare.listaAlimenti
                                                            .filter((al) =>
                                                                al.giornoDellaSettimana.toLowerCase() === d.toLowerCase() )
                                                            .map((alimento, key) => {

                                                                return (
                                                                    <Table borderBottom={"solid 1px "}
                                                                        borderColor={"blue.200"} key={key}
                                                                        variant="unstyled" size="md">
                                                                        <Thead>
                                                                            <Tr>
                                                                                <Th pasto="center"
                                                                                    w={"25%"}>{alimento.pasto}</Th>
                                                                                <Th textAlign="center"
                                                                                    w={"15%"}>Quantità</Th>
                                                                                <Th textAlign="center"
                                                                                    w={"15%"}>Kcal</Th>
                                                                                <Th textAlign="center"
                                                                                    w={"15%"}>Proteine</Th>
                                                                                <Th textAlign="center"
                                                                                    w={"15%"}>Grassi</Th>
                                                                                <Th textAlign="center"
                                                                                    w={"15%"}>Carboidrati</Th>
                                                                            </Tr>
                                                                        </Thead>
                                                                        <Tbody>
                                                                            <Tr>
                                                                                <Td textAlign="center"
                                                                                    w={"25%"}>{alimento.alimento.nome}</Td>
                                                                                <Td textAlign="center" w={"25%"}
                                                                                    isNumeric>{alimento.grammi}</Td>
                                                                                <Td textAlign="center" w={"15%"}
                                                                                    isNumeric>{alimento.alimento.kcal}</Td>
                                                                                <Td textAlign="center" w={"15%"}
                                                                                    isNumeric>{alimento.alimento.proteine}</Td>
                                                                                <Td textAlign="center" w={"15%"}
                                                                                    isNumeric>{alimento.alimento.grassi}</Td>
                                                                                <Td textAlign="center" w={"15%"}
                                                                                    isNumeric>{alimento.alimento.carboidrati}</Td>
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
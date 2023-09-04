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
    IconButton, Image,
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
import {CloseIcon, DeleteIcon, EditIcon} from '@chakra-ui/icons';
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
    const [listAlimenti, setAlimenti] = useState();

    const [isLoading, setLoading] = useState(true);
    const [fetchCompleted, setFetchCompleted] = useState(false); // Nuovo stato

    const days = ["Lunedi", "Martedi", "Mercoledi", "Giovedi", "Venerdi", "Sabato", "Domenica"];
    const [toastMessage, setToastMessage] = useState(undefined);
    const toast = useToast({
        duration: 9000,
        isClosable: true,
        variant: "solid",
        containerStyle: {
            width: '100%',
            maxWidth: '100%',
        },

    })

    const protocol = window.location.protocol;
    const domain = window.location.host;
    const full = `${protocol}//${domain}`

    function toastParam(title, description, status) {
        return {
            title: title, description: description, status: status
        };
    }

    useEffect(() => {
        if (toastMessage) {
            const {title, body, stat} = toastMessage;

            toast({
                title, description: body, status: stat, duration: 1000, isClosable: true
            });
        }
    }, [toastMessage, toast]);


    let vettPasti = [
        {"ID": 0, "Nome": "Colazione","ID_DB":"COLAZIONE"},
        {"ID": 1, "Nome": "Spuntino Mattina","ID_DB":"SPUNTINO_COLAZIONE"},
        {"ID": 2, "Nome": "Pranzo","ID_DB":"PRANZO"},
        {"ID": 3, "Nome": "Spuntino Pomeriggio","ID_DB":"SPUNTINO_PRANZO"},
        {"ID": 4, "Nome": "Cena","ID_DB":"CENA"},
        {"ID": 5, "Nome": "Spuntino Sera","ID_DB":"SPUNTINO_CENA"},
        {"ID": 6, "Nome": "Extra","ID_DB":"EXTRA"},
    ];

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
                                    {days.map((d, i) => {
                                        return (<AccordionItem key={i}>
                                                <h2>
                                                    <AccordionButton>
                                                        <Box flex='1' textAlign='left' fontWeight={"extrabold"} fontSize={"xl"}>
                                                            {d}
                                                        </Box>
                                                        <AccordionIcon/>
                                                    </AccordionButton>
                                                </h2>
                                                <AccordionPanel pb={4}>
                                                    {vettPasti.map((pasto, index) => {
                                                        return (
                                                            <div key={index}>
                                                                <Text fontSize={"21"} color={"blue"}
                                                                      fontWeight={"semibold"}>{pasto.Nome}</Text>
                                                                {schedaAlimentare.listaAlimenti.
                                                                filter((t) => pasto.ID_DB == t.pasto).map((al, key) => {
                                                                    let alimento = al.alimento;
                                                                    return (
                                                                        <>
                                                                            <Table borderBottom={"solid 1px "}
                                                                                   borderColor={"blue.200"} key={key}
                                                                                   variant="unstyled" size="md">
                                                                                <Thead>
                                                                                    <Tr>
                                                                                        <Th>Immagine</Th>
                                                                                        <Th>Nome</Th>
                                                                                        <Th>Kcal</Th>
                                                                                        <Th>Proteine</Th>
                                                                                        <Th>Grassi</Th>
                                                                                        <Th>Carboidrati</Th>
                                                                                        <Th>Grammi</Th>
                                                                                        <Th>Azioni</Th>
                                                                                    </Tr>
                                                                                </Thead>
                                                                                <Tbody>
                                                                                    <Tr>
                                                                                        <Td
                                                                                            p={1}
                                                                                            m={0}>
                                                                                            <Image
                                                                                                objectFit='contain'
                                                                                                boxSize={100}
                                                                                                src={full + "/" + alimento.pathFoto}
                                                                                                alt='Foto non disponibile'/>
                                                                                        </Td>
                                                                                        <Td maxWidth={100}>{alimento.nome}</Td>
                                                                                        <Td maxWidth={100}>{parseInt(alimento.kcal)}</Td>
                                                                                        <Td maxWidth={100}>{parseInt(alimento.proteine)}</Td>
                                                                                        <Td maxWidth={100}>{parseInt(alimento.grassi)}</Td>
                                                                                        <Td maxWidth={100}>{parseInt(alimento.carboidrati)}</Td>
                                                                                        <Td maxWidth={100}>{parseInt(al.grammi)}</Td>
                                                                                        <Td>
                                                                                            <IconButton></IconButton>
                                                                                        </Td>
                                                                                    </Tr>
                                                                                </Tbody>
                                                                            </Table>
                                                                        </>
                                                                    );
                                                                })}
                                                            </div>
                                                        );
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
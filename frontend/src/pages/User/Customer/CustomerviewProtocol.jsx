import React, {useContext, useEffect, useState} from 'react';
import {
    Box,
    Heading,
    Text,
    Flex,
    VStack,
    HStack,
    Button,
    Table,
    Thead,
    Tbody,
    Tr,
    Th,
    Td,
    TableCaption,
    Icon,
    Image,
    useToast,
} from '@chakra-ui/react';
import {
    RiArrowGoBackLine,
} from 'react-icons/ri';
import moment from "moment";
import {BsGraphUp, BsGraphDown} from "react-icons/bs";
import {useNavigate, useParams} from "react-router";
import {FetchContext} from "../../../context/FetchContext";

export default function CustomerviewProtocol() {
    const toast = useToast({
        duration: 9000,
        isClosable: true,
        variant: "solid",
        containerStyle: {
            width: '100%',
            maxWidth: '100%',
        },

    })
    const [isLoading, setLoading] = useState(true); // ricarica la pagina quando la variabile termina
    const fetchContext = useContext(FetchContext);
    const [protocollo, setProtocolli] = useState();
    const [report, setReport] = useState();
    let history = useNavigate();

    useEffect(() => {
        const listaProtocolli = async () => {
            try {
                const {data} = await fetchContext.authAxios("protocolli/" + id);
                console.log(data);
                setProtocolli(data.data);
                setLoading(false); //viene settato a false per far capire di aver caricato tutti i dati

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
        /*
        Quando sarà pronto il report dal backend lo utilizzeremo per prendere i dati
        const report = async () => {
            try {
                const {data} = await fetchContext.authAxios("");// path da aggiungere quando il backend sarà pronto
                console.log(data);
                setReport(data.data);
                setLoading(false);
            } catch (error) {
                console.log(error);
                toast({
                    title: "FAIL",
                    description: "fail data loading",
                    status: "fail"
                })
            }
        }
        report();
        */
    }, []);


    const {id} = useParams();
    console.log(id);
    moment.locale("it-IT");
    return (
        <>
            {!isLoading && (
                <Flex wrap={"wrap"}>
                    <Button leftIcon={<RiArrowGoBackLine/>} onClick={()=>history(-1)}>Torna alla lista protocolli</Button>
                    <Heading w={"full"} mb={5} textAlign={"center"}>Protocollo n.{protocollo.protocollo.id}</Heading>
                    <Box bg={"blackAlpha.50"} rounded={20} padding={10} minW={"full"} height={"auto"}>
                        <Flex width="full" justify="space-between">
                            <VStack w="full" h="full" align="start">
                                <HStack w="full" h="full" align="start">
                                    <Flex width="full" justify="space-between">
                                        <HStack>
                                            <Heading size={"sx"} textAlign="start"> Data inizio:</Heading>
                                            <Text>{moment(protocollo.protocollo.dataCreazione).format("DD/MM/yyyy")}</Text>
                                        </HStack>
                                        <HStack>
                                            <Heading size={"sx"} textAlign="center"> Nome preparatore:</Heading>
                                            <Text>{protocollo.protocollo.preparatore.nome} {protocollo.protocollo.preparatore.cognome}</Text>
                                        </HStack>
                                        <HStack>
                                            <Heading size={"sx"} textAlign="center">Data fine:</Heading>
                                            <Text>{moment(protocollo.protocollo.dataScadenza).format("DD/MM/yyyy")}</Text>
                                        </HStack>
                                    </Flex>
                                </HStack>

                                <HStack w="full" h="full" align="start">
                                    <Flex width="full" justify="space-between">
                                        <HStack alignItems="center" p={20}>
                                            <Box backgroundColor={"white"} p={3} borderRadius={15}>
                                                <Table variant={"unstyled"} colorScheme={"gray"} size="md">
                                                    <TableCaption>PROGRESSI</TableCaption>
                                                    <Thead color>
                                                        <Tr>
                                                            <Th></Th>
                                                            <Th></Th>
                                                        </Tr>
                                                    </Thead>
                                                    <Tbody>
                                                        <Tr>
                                                            <Td>Peso</Td>
                                                            <Td>{/*report. */}Kg<Icon as={BsGraphUp} color='green.500'
                                                                        marginLeft={4}/></Td>
                                                        </Tr>
                                                        <Tr>
                                                            <Td>Circonferenza Bicipite{}</Td>
                                                            <Td>{/*report. */}cm<Icon as={BsGraphDown} color='red.500'
                                                                        marginLeft={4}/></Td>
                                                        </Tr>
                                                        <Tr>
                                                            <Td>Circonferenza Addome</Td>
                                                            <Td>{/*report. */}cm<Icon as={BsGraphDown} color='red.500'
                                                                        marginLeft={4}/></Td>
                                                        </Tr>
                                                        <Tr>
                                                            <Td>Circonferenza Quadricipite</Td>
                                                            <Td>{/*report. */}cm<Icon as={BsGraphUp} color='green.500'
                                                                        marginLeft={4}/></Td>
                                                        </Tr>
                                                    </Tbody>
                                                </Table>
                                            </Box>
                                        </HStack>
                                        <HStack alignItems={"center"} marginTop={"auto"} marginBottom={"auto"}>
                                            <Box backgroundColor={"white"} p={3} borderRadius={15} w={400} h={310}>
                                                <VStack alignItems={"center"}>
                                                    <Heading size="xs"> Vuoi visualizzare le tue schede?</Heading>
                                                </VStack>
                                                <VStack marginTop={5}>
                                                    <Image
                                                        boxSize='70px'
                                                        objectFit='cover'
                                                        src='https://cdn-icons-png.flaticon.com/512/1719/1719695.png'>
                                                    </Image>
                                                    <Button>Vedi Allenamento {/* PASSA SCHEDA ALLENAMENTO */}
                                                    </Button>
                                                </VStack>
                                                <VStack marginTop={5}>
                                                    <Image
                                                        boxSize='50px'
                                                        objectFit='cover'
                                                        src='https://cdn-icons.flaticon.com/png/512/562/premium/562678.png?token=exp=1641581408~hmac=db43c7fac2d0e6178461bb7b44618bd2'>
                                                    </Image>
                                                    <Button>Vedi Alimentazione {/* PASSA SCHEDA ALIMENTAZIONE */}</Button>
                                                </VStack>
                                            </Box>
                                        </HStack>
                                    </Flex>
                                </HStack>
                                <HStack>
                                    <Heading size="s">Hai completato il protocollo?</Heading>
                                    <Button>Inserisci report</Button>
                                </HStack>
                            </VStack>
                        </Flex>
                    </Box>
                </Flex>
            )}
        </>


    );
}

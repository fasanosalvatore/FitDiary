import React, {useContext, useEffect, useState} from 'react';
import {
    Box,
    Button,
    Divider,
    Flex,
    Heading,
    HStack,
    Icon,
    Image,
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
    Tr,
    useDisclosure,
    useToast,
    VStack
} from '@chakra-ui/react';
import {ArrowLeftIcon, ArrowRightIcon} from '@chakra-ui/icons';
import {RiArrowGoBackLine} from 'react-icons/ri';
import moment from "moment";
import {BsGraphDown, BsGraphUp} from "react-icons/bs";
import {useNavigate, useParams} from "react-router";
import {FetchContext} from "../../context/FetchContext";
import dish from "../../images/dish.png";
import training from "../../images/dumbbell.png";
import photo from "../../images/photos.png";

export default function View() {
    const { isOpen, onOpen, onClose } = useDisclosure()
    const toast = useToast({
        duration: 3000,
        isClosable: true,
        variant: "solid",
        containerStyle: {
            width: '100%',
            maxWidth: '100%',
        },

    })
    const [toastMessage, setToastMessage] = useState(undefined);
    const [isLoading, setLoading] = useState(true); // ricarica la pagina quando la variabile termina
    const fetchContext = useContext(FetchContext);
    const [protocollo, setProtocolli] = useState();
    const [idCliente,setIdCliente]= useState();
    const [report,setReport]=useState();
    const { id } = useParams();

    let history = useNavigate();

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

    useEffect(() => {
        console.log("pages/protocols/view");
        const listaProtocolli = async () => {
            try {
                const { data } = await fetchContext.authAxios("protocolli/" + id);
                setProtocolli(data.data);
                setLoading(false); //viene settato a false per far capire di aver caricato tutti i dati
            } catch (error) {
                setToastMessage({title: "Error", body: error.message, stat: "error"});
            }
        }
        listaProtocolli();

    }, [fetchContext,id]);

    useEffect(() => {
        const report = async () => {
            try {
                if(protocollo){
                    const {data} = await fetchContext.authAxios("reports/search?data=" + protocollo.protocollo.dataScadenza + "&clienteId=" + protocollo.protocollo.cliente.id);// path da aggiungere quando il backend sarà pronto
                    console.log(data);
                    setReport(data.data);
                    setLoading(false);
                }
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
    },[fetchContext,protocollo]);

    moment.locale("it-IT");
    const navigate = useNavigate();
    return (
        <>
            {!isLoading && (
                <Flex wrap={"wrap"}>
                    <Button ml={5} mt={5} colorScheme={"fitdiary"} leftIcon={<RiArrowGoBackLine />}
                        onClick={() => history(-1)}>Torna al protocollo</Button>
                    <Heading w={"full"} mb={5} textAlign={"center"}>Protocollo n.{protocollo.protocollo.id}</Heading>
                    <Box bg={"white"} rounded={20} borderBottomRadius={0} padding={10} minW={"full"} height={"auto"}>
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
                                                    {report ?
                                                    <Tbody>
                                                        <Tr>
                                                            <Td>Peso</Td>
                                                            <Td>{report.report.peso}Kg<Icon as={BsGraphUp} color='green.500'
                                                                marginLeft={4} /></Td>
                                                        </Tr>
                                                        <Tr>
                                                            <Td>Circonferenza Bicipite{ }</Td>
                                                            <Td>{report.report.crfBicipite}cm<Icon as={BsGraphDown} color='red.500'
                                                                marginLeft={4} /></Td>
                                                        </Tr>
                                                        <Tr>
                                                            <Td>Circonferenza Addome</Td>
                                                            <Td>{report.report.crfAddome}cm<Icon as={BsGraphDown} color='red.500'
                                                                marginLeft={4} /></Td>
                                                        </Tr>
                                                        <Tr>
                                                            <Td>Circonferenza Quadricipite</Td>
                                                            <Td>{report.report.crfQuadricipite}cm<Icon as={BsGraphUp} color='green.500'
                                                                marginLeft={4} /></Td>
                                                        </Tr>
                                                    </Tbody>
                                                        : <Text>il report non e' stato creato</Text>}
                                                </Table>
                                            </Box>
                                        </HStack>

                                        <HStack alignItems={"center"} marginTop={"auto"} marginBottom={"auto"}>
                                            <Box backgroundColor={"white"} p={3} borderRadius={15} w={450} h={310} mr={30} ml={25}>
                                                <VStack alignItems={"center"}>
                                                    <Heading size="xs" mb={5}> Vuoi visualizzare le tue schede?</Heading>
                                                </VStack>
                                            <Flex justify="center">
                                                <HStack >
                                                    <VStack alignItems={"center"}>
                                                        <Image
                                                            boxSize='70px'
                                                            htmlHeight='10px'
                                                            objectFit='cover'
                                                            src={training}>
                                                        </Image>
                                                        <Button colorScheme='fitdiary' onClick={() => {
                                                            navigate("/trainingcard/" + protocollo.protocollo.id)
                                                        }}>Vedi Allenamento
                                                        </Button>
                                                    </VStack>
                                                    <VStack alignItems={"center"}>
                                                        <Image
                                                            boxSize='70px'
                                                            objectFit='cover'
                                                            src={dish}>
                                                        </Image>
                                                        <Button colorScheme='fitdiary' onClick={() => {
                                                            navigate("/dietcard/" + protocollo.protocollo.id)
                                                        }}>Vedi Alimentazione</Button>
                                                    </VStack>
                                                </HStack>
                                                </Flex>
                                                <Divider mt={5}></Divider>
                                                <VStack alignItems={"center"}>
                                                    <Heading size="xs" mb={5} mt={5}> Qui puoi visionare le foto del report</Heading>
                                                </VStack>
                                                <Flex justify="center">
                                                <HStack alignItems={"center"}>
                                                    <VStack align="center">
                                                        <Image
                                                            boxSize='60px'
                                                            objectFit='cover'
                                                            src={photo}>
                                                        </Image>
                                                        <Button colorScheme='fitdiary' onClick={onOpen}>Visualizza Foto</Button>
                                                        <Modal colorScheme='blue' isOpen={isOpen} onClose={onClose} isCentered={true} size={"2xl" }>
                                                            <ModalOverlay />
                                                            <ModalContent>
                                                                <ModalHeader textAlign={"center"}>Foto del cliente</ModalHeader>
                                                                <ModalCloseButton/>
                                                                <ModalBody align={"center"}>
                                                                    <Flex justify="center">
                                                                        <HStack align="center">

                                                                            <Button variant='ghost' textAlign="center" align="start" leftIcon={<ArrowLeftIcon/>}></Button>
                                                                            {report ? report.report.immaginiReports.map((img,i)=> {
                                                                                <Image boxSize={550} src={img.url}
                                                                                       alt='Dan Abramov'/>
                                                                            }): " "}
                                                                            <Button variant='ghost' textAlign="center" align="end" leftIcon={<ArrowRightIcon/>}></Button>
                                                                        </HStack>
                                                                    </Flex>
                                                                </ModalBody>
                                                                <ModalFooter>
                                                                </ModalFooter>
                                                            </ModalContent>
                                                        </Modal>
                                                    </VStack>
                                                </HStack>
                                                </Flex>
                                            </Box>
                                        </HStack>
                                    </Flex>
                                </HStack>
                                <HStack>

                                </HStack>
                                <HStack>
                                    <Heading size="s">Hai completato il protocollo?</Heading>
                                    <Button onClick={() => {
                                        navigate("/reports/create")
                                    }} colorScheme='fitdiary'>Inserisci report</Button>
                                </HStack>
                            </VStack>
                        </Flex>
                    </Box>
                </Flex>
            )}
        </>
    );
}

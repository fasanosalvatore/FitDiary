import React, { useContext, useEffect, useState } from 'react';
import {
    Box,
    Button,
    Flex,
    Heading,
    HStack,
    Icon,
    Image,
    Table,
    TableCaption,
    Tbody,
    Td,
    Text,
    Th,
    Thead,
    Tr,
    useToast,
    VStack,
    Modal,
    ModalOverlay,
    ModalContent,
    ModalHeader,
    ModalFooter,
    ModalBody,
    ModalCloseButton,
    useDisclosure,Divider
} from '@chakra-ui/react';
import{ArrowRightIcon,ArrowLeftIcon} from '@chakra-ui/icons';
import {RiArrowGoBackLine} from 'react-icons/ri';
import moment from "moment";
import {BsGraphDown, BsGraphUp} from "react-icons/bs";
import {useNavigate, useParams} from "react-router";
import {FetchContext} from "../../context/FetchContext";
import dish from "../../images/dish.png";
import training from "../../images/dumbbell.png";
import photo from "../../images/photos.png";
import {getReport} from "../../fakeBackend";

export default function View() {
    const report=getReport;
    const { isOpen, onOpen, onClose } = useDisclosure()
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
    const { id } = useParams();

    let history = useNavigate();

    useEffect(() => {
        const listaProtocolli = async () => {
            try {
                const { data } = await fetchContext.authAxios("protocolli/" + id);
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
    }, [fetchContext, toast, id]);

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
                                                    <Tbody>
                                                        <Tr>
                                                            <Td>Peso</Td>
                                                            <Td>{/*report. */}Kg<Icon as={BsGraphUp} color='green.500'
                                                                marginLeft={4} /></Td>
                                                        </Tr>
                                                        <Tr>
                                                            <Td>Circonferenza Bicipite{ }</Td>
                                                            <Td>{/*report. */}cm<Icon as={BsGraphDown} color='red.500'
                                                                marginLeft={4} /></Td>
                                                        </Tr>
                                                        <Tr>
                                                            <Td>Circonferenza Addome</Td>
                                                            <Td>{/*report. */}cm<Icon as={BsGraphDown} color='red.500'
                                                                marginLeft={4} /></Td>
                                                        </Tr>
                                                        <Tr>
                                                            <Td>Circonferenza Quadricipite</Td>
                                                            <Td>{/*report. */}cm<Icon as={BsGraphUp} color='green.500'
                                                                marginLeft={4} /></Td>
                                                        </Tr>
                                                    </Tbody>
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
                                                        <Modal isOpen={isOpen} onClose={onClose} isCentered={true} size={"2xl" }>
                                                            <ModalOverlay />
                                                            <ModalContent>
                                                                <ModalHeader textAlign={"center"}>Foto del cliente</ModalHeader>
                                                                <ModalCloseButton/>
                                                                <ModalBody align={"center"}>
                                                                    <Flex justify="center">
                                                                        <HStack align="center">
                                                                            <Button variant='ghost' textAlign="center" align="start" leftIcon={<ArrowLeftIcon/>}></Button>
                                                                            <Image boxSize={550} src='https://res.cloudinary.com/hdjxm4zyg/image/upload/s--J9CYotxd--/v1641863408/evssjeyaofzzdrf8yywq.jpg' alt='Dan Abramov' />
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
                                    <Button colorScheme='fitdiary'>Inserisci report</Button>
                                </HStack>
                            </VStack>
                        </Flex>
                    </Box>
                </Flex>
            )}
        </>
    );
}

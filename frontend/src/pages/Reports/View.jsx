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
import photo from "../../images/photos.png";
import {Carousel} from "react-responsive-carousel";
import "react-responsive-carousel/lib/styles/carousel.min.css";

export default function View() {
    const { isOpen, onOpen, onClose } = useDisclosure()
    const [isLoading, setLoading] = useState(true); // ricarica la pagina quando la variabile termina
    const fetchContext = useContext(FetchContext);
    const [report,setReport]=useState();
    const { id } = useParams();

    let history = useNavigate();
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
            });
        }
        return () => {
            setTimeout(() => {
                setToastMessage(undefined);
            },1000);
        }
    }, [toastMessage, toast]);

    useEffect(() => {
        console.log("pages/protocols/view");
        const listaProtocolli = async () => {
            try {
                const { data } = await fetchContext.authAxios("reports/" + id);
                setReport(data.data);
                setLoading(false); //viene settato a false per far capire di aver caricato tutti i dati
            } catch (error) {
                setToastMessage({title: "Error", body: error.message, stat: "error"});
            }
        }
        listaProtocolli();

    }, [fetchContext,id]);

    moment.locale("it-IT");
    const navigate = useNavigate();
    return (
        <>
            {!isLoading && (
                <Flex wrap={"wrap"}>
                    <Button ml={5} mt={5} colorScheme={"fitdiary"} leftIcon={<RiArrowGoBackLine />}
                        onClick={() => history(-1)}>Torna ai Progressi</Button>
                    <Heading w={"full"} mb={5} textAlign={"center"}>Report n.{report.report.id}</Heading>
                    <Box bg={"white"} rounded={20} borderBottomRadius={0} padding={10} minW={"full"} height={"auto"}>
                        <Flex width="full" justify="space-between">
                            <VStack w="full" h="full" align="start">
                                <HStack w="full" h="full" align="start">
                                    <Flex width="full" justify="space-between">
                                        <HStack>
                                            <Heading size={"sx"} textAlign="start"> Data Creazione:</Heading>
                                            <Text>{moment(report.report.dataCreazione).format("DD/MM/yyyy")}</Text>
                                        </HStack>
                                    </Flex>
                                </HStack>

                                <HStack w="full" h="full" align="start">
                                    <Flex width="full" justify="center">
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
                                    </Flex>
                                </HStack>
                                <Heading w={"full"} mb={5} textAlign={"center"}>Foto</Heading>
                                <Flex justify="center">
                                    <VStack>
                                        <HStack alignItems={"center"} width={{base:"80%",sm:"60%",md:"50%"}}>
                                            <Carousel infiniteLoop={true} emulateTouch={true}>
                                                {report ? report.report.immaginiReports.map((img,i)=> {
                                                    return <Image boxSize={550} h={"auto"} w={200} src={img.url} alt='Foto non disponibile'/>
                                                }): " "}
                                            </Carousel>
                                        </HStack>
                                    </VStack>
                                </Flex>
                            </VStack>
                        </Flex>
                    </Box>
                </Flex>
            )}
        </>
    );
}

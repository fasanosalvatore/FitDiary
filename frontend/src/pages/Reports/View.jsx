import React, {useContext, useEffect, useState} from 'react';
import {
    Box,
    Flex,
    Heading,
    HStack,
    Icon,
    Image,
    Table,
    Tbody,
    Td,
    Text,
    Th,
    Thead,
    Tr,
    useToast,
    VStack
} from '@chakra-ui/react';
import moment from "moment";
import {BsGraphDown, BsGraphUp} from "react-icons/bs";
import {useParams} from "react-router";
import {FetchContext} from "../../context/FetchContext";
import {Carousel} from "react-responsive-carousel";
import "react-responsive-carousel/lib/styles/carousel.min.css";
import {GradientBar} from "../../components/GradientBar";

export default function View() {
    const [isLoading, setLoading] = useState(true); // ricarica la pagina quando la variabile termina
    const fetchContext = useContext(FetchContext);
    const [report,setReport]=useState();
    const { id } = useParams();

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
    return (
        <>
            {!isLoading && (
                <Flex wrap={"wrap"} p={5}>
                    <Flex alignItems={"center"} mb={5}>
                        <Heading w={"full"}>Report n.{report.report.id}</Heading>
                    </Flex>
                    <Box bg={"white"} roundedTop={20} minW={{ base: "100%", xl: "100%" }} h={"full"}>
                        <GradientBar />

                    <Box bg={"white"} rounded={20} borderBottomRadius={0} padding={{base:0,md:10}} minW={"full"} height={"auto"}>
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
                                        <HStack alignItems="center"  px={{base:0,lg:20}} py={{base:0,lg:10}} w={"full"}>
                                            <Box backgroundColor={"white"} borderRadius={15} w={"full"}>
                                                <Table variant={"striped"} colorScheme={"gray"}  size="md" w={"full"}>
                                                    <Thead>
                                                        <Tr >
                                                            <Th textAlign={"start"} fontSize={{base:15,md:20}} fontWeight={800}>Caratteristiche</Th>
                                                            <Th textAlign={"start"} fontSize={{base:15,md:20}} fontWeight={800}>Valori</Th>
                                                            <Th textAlign={"start"} fontSize={{base:15,md:20}} fontWeight={800}>Andamento</Th>
                                                        </Tr>
                                                    </Thead>
                                                    {report ?
                                                    <Tbody>
                                                        <Tr w={"full"}>
                                                            <Td fontSize={{base:20,md:15}} fontWeight={500} >Peso</Td>
                                                            <Td fontSize={{base:20,md:15}} fontWeight={400} >{report.report.peso}Kg</Td>
                                                            <Td fontSize={{base:20,md:15}} fontWeight={400} textAlign={"center"}><Icon as={BsGraphUp} color='green.500'  /></Td>
                                                        </Tr>
                                                        <Tr>
                                                            <Td fontSize={{base:20,md:15}} fontWeight={500} >Circonferenza Bicipite{ }</Td>
                                                            <Td fontSize={{base:20,md:15}} fontWeight={400}  >{report.report.crfBicipite}cm</Td>
                                                            <Td fontSize={{base:20,md:15}} fontWeight={400}  textAlign={"center"}><Icon as={BsGraphDown} color='red.500'/></Td>
                                                        </Tr>
                                                        <Tr>
                                                            <Td fontSize={{base:20,md:15}} fontWeight={500} >Circonferenza Addome</Td>
                                                            <Td fontSize={{base:20,md:15}} fontWeight={400} >{report.report.crfAddome}cm</Td>
                                                            <Td fontSize={{base:20,md:15}} fontWeight={400} textAlign={"center"}><Icon as={BsGraphDown} color='red.500' /></Td>
                                                        </Tr>
                                                        <Tr >
                                                            <Td fontSize={{base:20,md:15}} fontWeight={500} >Circonferenza Quadricipite</Td>
                                                            <Td fontSize={{base:20,md:15}} fontWeight={400} >{report.report.crfQuadricipite}cm</Td>
                                                            <Td fontSize={{base:20,md:15}} fontWeight={400} textAlign={"center"}><Icon as={BsGraphUp} color='green.500'/></Td>
                                                        </Tr>
                                                    </Tbody>
                                                        : <Text>il report non e' stato creato</Text>}
                                                </Table>
                                            </Box>
                                        </HStack>
                                    </Flex>
                                </HStack>
                                <>
                                <Flex justify="center" w={"full"}>
                                    {report.report.immaginiReports.length > 0 && (
                                    <VStack alignContent={"center"}>
                                        <Heading w={"full"} mb={5} textAlign={"center"}>Foto</Heading>
                                        <HStack width={{base:"80%",sm:"60%",md:"50%"}}>
                                            <Carousel infiniteLoop={true} emulateTouch={true} dynamicHeight={true} >
                                                {report ? report.report.immaginiReports.map((img,i)=> {
                                                    return(
                                                    <Box bg={"fitdiary.50"} borderRadius={"md"}>
                                                        <Image objectFit='contain'  boxSize={550} src={img.url} alt='Foto non disponibile'/>
                                                    </Box>)
                                                }): " "}
                                            </Carousel>
                                        </HStack>
                                    </VStack>
                                        )}
                                </Flex>
                                </>
                            </VStack>
                        </Flex>
                    </Box>
                    </Box>
                </Flex>
            )}
        </>
    );
}

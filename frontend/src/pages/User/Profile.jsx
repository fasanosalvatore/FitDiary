import {
    Avatar,
    Box,
    Button,
    Flex,
    GridItem,
    Heading,
    HStack,
    Icon,
    Link,
    SimpleGrid,
    Text,
    VStack
} from "@chakra-ui/react";
import React, {useContext, useEffect, useState} from "react";
import {FetchContext} from "../../context/FetchContext";
import moment from "moment";
import {GradientBar} from "../../components/GradientBar";

const urlGetInfo = `utenti/profilo`;
export default function Profile() {
    const [utente, setUtente] = useState({nome: "pippo"});
    const [isLoading, setLoading] = useState(true);
    const fetchContext = useContext(FetchContext);

    useEffect(() => {
        const getInfoUtente = async () => {
            try {
                const {data} = await fetchContext.authAxios.get(urlGetInfo)
                setUtente(data.data.utente);
                setLoading(false);
            } catch (error) {
                console.log("error", error);
            }
        };
        getInfoUtente();
    }, [fetchContext]);

    return (
        <>
            {!isLoading && (
                <Flex wrap={"wrap"} p={5}>
                    <Heading w={"full"} mb={5}>Profilo Utente</Heading>
                    <Box bg={"white"} rounded={20} minW={{base: '100%', xl: '48%'}}>
                        <GradientBar/>
                        <Flex pb={10}>
                            <VStack w={"full"}>
                                <Avatar size={"xl"} mt={5}></Avatar>
                                <Heading fontSize={"3xl"}
                                         color={utente.sesso === "M" ? "blue.700" : "pink.700"}>{utente.nome} {utente.cognome}</Heading>
                                <Text color={"gray.400"}>{utente.email}</Text>
                                <HStack>
                                    <Link href={"/account"}><Button bg={"blue.200"}>Modifica</Button></Link>
                                    <Link href={"/reports"}><Button bg={"green.200"}>Progressi</Button></Link>
                                </HStack>
                            </VStack>
                        </Flex>
                    </Box>
                    <Box bg={"white"} rounded={20} pb={10} minW={{base: '100%', xl: '48%'}}
                         marginLeft={[0, 0, 0, 0, 5]}
                         marginTop={[5, 5, 5, 5, 0]}>
                        <GradientBar inverse/>
                        <Flex>
                            <VStack w={"full"} alignItems={"flex-start"}>
                                <SimpleGrid columns={2} w={"full"}>
                                    <GridItem minH={"5"} p={"3"} borderBottom={"1px"} borderColor={"gray.400"}><Text
                                        fontWeight={"bold"} color={"gray.600"}>Data di Nascita </Text></GridItem>
                                    <GridItem minH={"5"} p={"3"} borderBottom={"1px"} borderColor={"gray.400"}><Text
                                        ml={"2"}
                                        color={"gray.400"}>{moment(utente.dataNascita).format("DD/MM/yyyy")}</Text></GridItem>
                                    <GridItem minH={"5"} p={"3"} borderBottom={"1px"} borderColor={"gray.400"}><Text
                                        fontWeight={"bold"} color={"gray.600"}>Telefono </Text></GridItem>
                                    <GridItem minH={"5"} p={"3"} borderBottom={"1px"} borderColor={"gray.400"}><Text
                                        ml={"2"} color={"gray.400"}>{utente.telefono}</Text></GridItem>
                                    <GridItem minH={"5"} p={"3"} borderBottom={"1px"} borderColor={"gray.400"}><Text
                                        fontWeight={"bold"} color={"gray.600"}>Indirizzo</Text></GridItem>
                                    <GridItem minH={"5"} p={"3"} borderBottom={"1px"} borderColor={"gray.400"}><Text
                                        ml={"2"} color={"gray.400"}>{utente.via} <br/>{utente.cap} {utente.citta}
                                    </Text></GridItem>
                                    {utente.preparatore && (
                                        <>
                                            <GridItem minH={"5"} p={"3"} borderBottom={"1px"}
                                                      borderColor={"gray.400"}><Text
                                                fontWeight={"bold"} color={"gray.600"}>Preparatore </Text></GridItem>

                                            <GridItem minH={"5"} p={"3"} borderBottom={"1px"}
                                                      borderColor={"gray.400"}><Text
                                                ml={"2"}
                                                color={"gray.400"}>{utente.preparatore?.nome} {utente.preparatore?.cognome}</Text></GridItem>
                                        </>)}
                                    <GridItem minH={"5"} p={"3"} borderBottom={"1px"} borderColor={"gray.400"}><Text
                                        fontWeight={"bold"} color={"gray.600"}>Sesso</Text></GridItem>
                                    <GridItem minH={"5"} p={"3"} borderBottom={"1px"} borderColor={"gray.400"}><Text
                                        ml={"2"}
                                        color={"gray.400"}>{utente.sesso}</Text></GridItem>
                                </SimpleGrid>
                            </VStack>
                        </Flex>
                    </Box>
                    <Box bg={"white"} rounded={20} padding={10} minW={{base: '100%', xl: '98%'}} mt={5}>
                        <Flex>
                            <VStack>
                                <Icon></Icon>
                            </VStack>
                        </Flex>
                    </Box>
                </Flex>
            )}</>
    )
}
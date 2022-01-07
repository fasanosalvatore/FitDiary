import {
    Avatar,
    Box, Button,
    Flex,
    GridItem,
    Heading,
    HStack, Icon,
    SimpleGrid,
    Link,
    Text,
    VStack
} from "@chakra-ui/react";
import authService from "../../../services/auth.service";
import {privateFetch} from "../../../util/fetch";
import {useEffect, useState} from "react";

export default function CustomerProfile() {
    const urlGetInfo = `utenti/profilo`;
    /*const [utente, setUtente] = useState({
        "nome": "",
        "cognome": "",
        "email": "",
        "dataNascita": "",
        "sesso": "",
        "telefono": "",
        "via": "",
        "cap": "",
        "citta": "",
    });*/
    const [utente, setUtente] = useState({});
    const [isLoading, setLoading] = useState(true);

    useEffect(() => {
        getInfoUtente();
    }, []);

    const getInfoUtente = () => {
        privateFetch(urlGetInfo).then((resp)=>{
            setUtente(resp.data.data.utente);
            console.log(resp.data.data.utente)
            setLoading(false);
        });
    };

    console.log(utente);
    if(isLoading){
        return <div>Loading...</div>
    }
    return (
        <Flex wrap={"wrap"}>
            <Heading w={"full"} mb={5}>Profilo Utente</Heading>
            <Box bg={"blackAlpha.50"} rounded={20} padding={10} minW={{ base: '100%', xl: '49%' }}>
                <Flex>
                    <VStack w={"full"}>
                        <Avatar size={"xl"}></Avatar>
                        <Heading fontSize={"3xl"} color={utente.sesso === "M" ? "blue.700" : "pink.700"} >{utente.nome} {utente.cognome}</Heading>
                        <Text color={"gray.400"}>{utente.email}</Text>
                        <HStack>
                            <Link href={"/customer/account"}><Button bg={"blue.200"}>Modifica</Button></Link>
                            <Link href={"/reports"}><Button bg={"green.200"}>Progressi</Button></Link>
                        </HStack>
                    </VStack>
                </Flex>
            </Box>
                <Box bg={"blackAlpha.50"} rounded={20} padding={10} minW={{ base: '100%', xl: '49%' }} marginLeft={[0, 0, 0, 0,5]}
                     marginTop={[5, 5, 5, 5,0]}>
                    <Flex>
                        <VStack w={"full"} alignItems={"flex-start"}>
                            <SimpleGrid columns={2} w={"full"}>
                                <GridItem minH={"5"} p={"3"} borderBottom={"1px"} borderColor={"gray.400"}><Text
                                    fontWeight={"bold"} color={"gray.600"}>Data di Nascita </Text></GridItem>
                                <GridItem minH={"5"} p={"3"} borderBottom={"1px"} borderColor={"gray.400"}><Text
                                    ml={"2"} color={"gray.400"}>{utente.dataNascita}</Text></GridItem>
                                <GridItem minH={"5"} p={"3"} borderBottom={"1px"} borderColor={"gray.400"}><Text
                                    fontWeight={"bold"} color={"gray.600"}>Telefono </Text></GridItem>
                                <GridItem minH={"5"} p={"3"} borderBottom={"1px"} borderColor={"gray.400"}><Text
                                    ml={"2"} color={"gray.400"}>{utente.telefono}</Text></GridItem>
                                <GridItem minH={"5"} p={"3"} borderBottom={"1px"} borderColor={"gray.400"}><Text
                                    fontWeight={"bold"} color={"gray.600"}>Indirizzo</Text></GridItem>
                                <GridItem minH={"5"} p={"3"} borderBottom={"1px"} borderColor={"gray.400"}><Text
                                    ml={"2"} color={"gray.400"}>{utente.via} <br/>{utente.cap} {utente.citta}
                                </Text></GridItem>
                                <GridItem minH={"5"} p={"3"} borderBottom={"1px"} borderColor={"gray.400"}><Text
                                    fontWeight={"bold"} color={"gray.600"}>Preparatore </Text></GridItem>
                                <GridItem minH={"5"} p={"3"} borderBottom={"1px"} borderColor={"gray.400"}><Text
                                    ml={"2"}
                                    color={"gray.400"}>{utente.preparatore.nome} {utente.preparatore.cognome}</Text></GridItem>
                                <GridItem minH={"5"} p={"3"} borderBottom={"1px"} borderColor={"gray.400"}><Text
                                    fontWeight={"bold"} color={"gray.600"}>Sesso</Text></GridItem>
                                <GridItem minH={"5"} p={"3"} borderBottom={"1px"} borderColor={"gray.400"}><Text
                                    ml={"2"}
                                    color={"gray.400"}>{utente.sesso}</Text></GridItem>
                            </SimpleGrid>
                        </VStack>
                    </Flex>
                </Box>
            <Box bg={"blackAlpha.50"} rounded={20} padding={10} minW={"350"} mt={5} w={"full"}>
                <Flex>
                    <VStack>
                        <Icon></Icon>
                    </VStack>
                </Flex>
            </Box>
        </Flex>
    )
}
import React, {useContext, useEffect, useState,} from 'react';
import {
    Box, Heading, Text, Flex, VStack, HStack, Button, Table, Tfoot,Thead, Tbody, Tr, Th, Td, TableCaption, Icon, Image,Divider, Accordion,
    AccordionItem,
    AccordionButton,
    AccordionPanel,
    AccordionIcon,IconButton,Tooltip,useToast
} from '@chakra-ui/react';
import{
    EditIcon
}from '@chakra-ui/icons';
import {
    RiArrowGoBackLine,
} from 'react-icons/ri';
import {getProtocollo} from "../../../fakeBackend";
import {AuthContext} from "../../../context/AuthContext";
import moment from "moment";
import {useNavigate, useParams} from "react-router";
import {FetchContext} from "../../../context/FetchContext";

export default function TrainerViewTrainingCard() {
    const days=[1,2,3,4,5,6,7];
    const authContext=useContext(AuthContext);
    const {authState} = authContext;
    const {id} = useParams();
    const navigate=useNavigate();
    const [protocollo, setProtocolli] = useState();
    const fetchContext = useContext(FetchContext);
    const [isLoading, setLoading] = useState(true);

    const toast = useToast({
        duration: 9000,
        isClosable: true,
        variant: "solid",
        containerStyle: {
            width: '100%',
            maxWidth: '100%',
        },

    })

    useEffect(() => {
        const listaProtocolli = async () => {
            try{
                const {data} = await fetchContext.authAxios("protocolli/" + id);
                setProtocolli(data.data);
                setLoading(false);
            } catch (error) {
                console.log(error);
                toast({
                    title: "ERROR",
                    description: "NOT AUTHORIZED",
                    status: "error"
                })}
        }
        listaProtocolli();
    },[])
    return(
        <>
            {!isLoading && (
        <Flex wrap={"wrap"}>
            <Button onClick={()=>{navigate(-1)}} ml={5} mt={5} colorScheme={"blue"} leftIcon={<RiArrowGoBackLine/>}>Torna al protocollo</Button>
            <Heading w={"full"} mb={5} textAlign={"center"}>Scheda Allenamento</Heading>
            <Box bg={"white"} rounded={20} borderBottomRadius={0} padding={10} minW={"full"} height={"auto"}>
                <Flex width="full" justify="space-between">
                    <VStack w="full" h="full" align="start" >
                        <HStack w="full" h="full" align="start" >
                            <Flex width="full" justify="space-between">
                                <HStack>
                                    <Heading size={"sx"} textAlign="start"> Cliente:</Heading>
                                    <Text>{protocollo.protocollo.cliente.nome} {protocollo.protocollo.cliente.cognome}</Text>
                                </HStack>
                                <HStack >
                                    <Heading size={"sx"} textAlign="center"> Data scadenza:</Heading>
                                    <Text>{moment(protocollo.protocollo.dataScadenza).format("DD/MM/yyyy")}</Text>
                                </HStack>
                            </Flex>
                        </HStack>
                        <HStack w="full" h="full" align="start" pb={30}>
                            <Flex width="full" justify="space-between">
                                <HStack>
                                    <Heading size={"sx"} textAlign="start" >Frequenza:</Heading>
                                    <Text>{protocollo.protocollo.schedaAllenamento.frequenza} volte a settimana</Text>
                                </HStack>
                                {
                                    authState.userInfo.roles[0].toLowerCase()==='preparatore' && (
                                        <HStack>
                                            <Tooltip label='Modifica Scheda' fontSize='md'>
                                                <IconButton
                                                    colorScheme='blue'
                                                    icon={<EditIcon />}
                                                />
                                            </Tooltip>
                                        </HStack>
                                    )}
                            </Flex>
                        </HStack>
                        <Accordion defaultIndex={[0]}  w="full" mt={"60px"} >
                            {
                                days.map((d, i) => {
                                    return (

                                        <AccordionItem key={i}>
                                            <h2>
                                                <AccordionButton>
                                                    <Box flex='1' textAlign='left'>
                                                        {d}° Allenamento
                                                    </Box>
                                                    <AccordionIcon />
                                                </AccordionButton>
                                            </h2>
                                            <AccordionPanel pb={4}>
                                                {protocollo.protocollo.schedaAllenamento.listaEsercizi
                                                    .filter((esercizio) => esercizio.numeroAllenamento === d + "")
                                                    .map((c,key) => {
                                                        return (
                                                            <Table borderBottom={"solid 1px "} borderColor={"blue.200"} key={key} variant="unstyled" size="md" >
                                                                <Thead>
                                                                    <Tr>
                                                                        <Th textAlign="center" w={"25%"}>Esercizio</Th>
                                                                        <Th textAlign="center" w={"25%"}>Serie</Th>
                                                                        <Th textAlign="center" w={"25%"}>Ripetizioni</Th>
                                                                        <Th textAlign="center" w={"25%"}>Recupero</Th>
                                                                    </Tr>
                                                                </Thead>
                                                                <Tbody>
                                                                    <Tr>
                                                                        <Td textAlign="center" w={"25%"}>{c.nome} ({c.categoria})</Td>
                                                                        <Td textAlign="center" w={"25%"} isNumeric>{c.serie}</Td>
                                                                        <Td textAlign="center" w={"25%"} isNumeric>{c.ripetizioni}</Td>
                                                                        <Td textAlign="center" w={"25%"} isNumeric>{c.recupero}</Td>
                                                                    </Tr>
                                                                </Tbody>
                                                            </Table>
                                                        )})}
                                            </AccordionPanel>
                                        </AccordionItem>
                                    )})}
                        </Accordion>
                    </VStack>
                </Flex>
            </Box>
        </Flex>

    )}
                </>
                );
}
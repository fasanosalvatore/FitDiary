import WrapperBox from "./WrapperBox";
import {Box, Flex, Heading, HStack, Text, useToast, VStack} from "@chakra-ui/react";
import {FaBook, FaUsers} from "react-icons/fa";
import {GiMeal} from "react-icons/gi";
import {CgGym} from "react-icons/cg";
import {useContext, useEffect, useState} from "react";
import {FetchContext} from "../context/FetchContext";
import Data from "moment";
import moment from "moment";

export default function DashboardTrainer() {
    const [isLoading, setLoading] = useState(true); // ricarica la pagina quando la variabile termina
    const fetchContext = useContext(FetchContext);
    const [customerList, setCustomerList] = useState();
    const [protocolliList, setProtocolliList] = useState();

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
            }, 1000);
        }
    }, [toastMessage, toast]);

    useEffect(() => {
        console.log("pages/protocols/view");
        const getInfo = async () => {
            try {
                const { data: dataCust } = await fetchContext.authAxios("utenti");
                const { data: dataProt } = await fetchContext.authAxios("protocolli");

                console.log(dataProt)

                setCustomerList(dataCust.data.clienti)
                setProtocolliList(dataProt.data.protocollo)

                setLoading(false); //viene settato a false per far capire di aver caricato tutti i dati
            } catch (error) {
                setToastMessage({ title: "Error", body: error.message, stat: "error" });
            }
        }
        getInfo();

    }, [fetchContext,setCustomerList,setProtocolliList]);

    function getClientiAttivi() {
        return customerList.filter(c => c.attivo === true);
    }

    function getClientiDisattivi() {
        return customerList.filter(c => c.attivo === false);
    }

    function getProtocolliAttivi() {
        return protocolliList.filter(c =>
            moment(c.dataScadenza) > Data.now()
        );
    }

    function getProtocolliDisattivi() {
        return protocolliList.filter(c =>
            moment(c.dataScadenza) < Data.now()
        );
    }

    function getDieteAttive() {
        return protocolliList.filter(c =>{
            if((moment(c.dataScadenza) > Data.now()) && (c.schedaAlimentare !== null)){
                return true
            }
            return false
        });
    }

    function getDieteDisattive() {
        return protocolliList.filter(c =>
            (moment(c.dataScadenza) < Data.now()) && (c.schedaAlimentare !== null)
        );
    }

    function getAllenamentiAttivi() {
        return protocolliList.filter(c =>{
            if((moment(c.dataScadenza) > Data.now()) && (c.schedaAllenamento !== null)){
                return true
            }
            return false
        });
    }

    function getAllenamentiDisattivi() {
        return protocolliList.filter(c =>
            (moment(c.dataScadenza) < Data.now()) && (c.schedaAllenamento !== null)
        );
    }



    return (
        <WrapperBox>
            {!isLoading &&(
            <Flex>
                <Flex w={"full"} m={5} rounded={10} wrap={"wrap"} bg={"fitdiary.800"} boxShadow={"2xl"} alignContent={"center"} justifyContent={"center"}>
                    <Heading w={"full"} ml={"5"} color={"white"} textAlign={"center"}>Statistiche</Heading>
                    <Box m={5} rounded={5} p={5} bg={"white"} w={"200px"}>
                        <VStack alignItems={"center"}>
                            <HStack fontSize={"2xl"}>
                                <HStack>
                                    <FaUsers/>
                                    <Text>Clienti</Text>
                                </HStack>
                                <Text>{customerList.length}</Text>
                            </HStack>
                            <HStack>
                                <Text fontSize={"xs"}>Attivi</Text>
                                <Text fontSize={"xs"}>{getClientiAttivi().length}</Text>
                                <Text fontSize={"xs"}>Disattivati</Text>
                                <Text fontSize={"xs"}>{getClientiDisattivi().length}</Text>
                            </HStack>
                        </VStack>
                    </Box>
                    <Box m={5} rounded={5} p={5} bg={"white"} w={"200px"}>
                        <VStack alignItems={"center"}>
                            <HStack fontSize={"2xl"}>
                                <HStack>
                                    <FaBook/>
                                    <Text>Protocolli</Text>
                                </HStack>
                                <Text>{protocolliList.length}</Text>
                            </HStack>
                            <HStack>
                                <Text fontSize={"xs"}>Attivi</Text>
                                <Text fontSize={"xs"}>{getProtocolliAttivi().length}</Text>
                                <Text fontSize={"xs"}>Scaduti</Text>
                                <Text fontSize={"xs"}>{getProtocolliDisattivi().length}</Text>
                            </HStack>
                        </VStack>
                    </Box>
                    <Box m={5} rounded={5} p={5} bg={"white"} w={"200px"}>
                        <VStack alignItems={"center"}>
                            <HStack fontSize={"2xl"}>
                                <HStack>
                                    <GiMeal/>
                                    <Text>Diete</Text>
                                </HStack>
                                <Text>{getDieteAttive().length + getDieteDisattive().length}</Text>
                            </HStack>
                            <HStack>
                                <Text fontSize={"xs"}>Attive</Text>
                                <Text fontSize={"xs"}>{getDieteAttive().length}</Text>
                                <Text fontSize={"xs"}>Scadute</Text>
                                <Text fontSize={"xs"}>{getDieteDisattive().length}</Text>
                            </HStack>
                        </VStack>
                    </Box>
                    <Box m={5} rounded={5} p={5} bg={"white"} w={"200px"}>
                        <VStack alignItems={"center"}>
                            <HStack fontSize={"2xl"}>
                                <HStack>
                                    <CgGym/>
                                    <Text>Schede</Text>
                                </HStack>
                                <Text>{getAllenamentiAttivi().length + getAllenamentiDisattivi().length}</Text>
                            </HStack>
                            <HStack>
                                <Text fontSize={"xs"}>Attivi</Text>
                                <Text fontSize={"xs"}>{getAllenamentiAttivi().length}</Text>
                                <Text fontSize={"xs"}>Scaduti</Text>
                                <Text fontSize={"xs"}>{getAllenamentiDisattivi().length}</Text>
                            </HStack>
                        </VStack>
                    </Box>
                </Flex>
            </Flex>
            )}
        </WrapperBox>
    )
}
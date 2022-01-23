import WrapperBox from "./WrapperBox";
import {
    Accordion,
    AccordionButton,
    AccordionItem,
    Box,
    Flex,
    GridItem,
    Heading,
    HStack,
    Image,
    Text,
    useBreakpointValue,
    useToast,
    VStack
} from "@chakra-ui/react";
import {AccordionMeal} from "./AccordionMeal";
import meal from "../images/dish.png";
import React, {useContext, useEffect, useState} from "react";
import {FetchContext} from "../context/FetchContext";
import {useNavigate} from "react-router-dom";
import _ from "lodash";
import {AccordionTraining} from "./AccordionTraining";
import training from "../images/dumbbell.png";
import {FaBook, FaUser} from "react-icons/fa";
import {CgGym} from "react-icons/cg";
import moment from "moment";

const urlGetInfo = `utenti/profilo`;

export default function DashboardCustomer() {
    const [isLoading, setLoading] = useState(true); // ricarica la pagina quando la variabile termina
    const fetchContext = useContext(FetchContext);
    const [user, setUser] = useState();
    const [protocollo, setProtocollo] = useState();
    const [report, setReport] = useState();
    const [protocolliList, setProtocolliList] = useState([]);
    const [reportList, setReportList] = useState([]);
    const colSpan = useBreakpointValue({ base: 2, xl: 1 })
    const navigate = useNavigate();

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
                const { data: dataProt } = await fetchContext.authAxios("protocolli");
                const { data: dataReport } = await fetchContext.authAxios("reports");
                const { data: dataUser } = await fetchContext.authAxios(urlGetInfo);

                console.log()

                setReportList(dataReport.data.report)
                setReport(dataReport.data.report[dataReport.data.report.length-1]);

                console.log(dataReport.data.report[dataReport.data.report.length-1])

                setProtocolliList(dataProt.data.protocollo)
                setProtocollo(dataProt.data.protocollo[dataProt.data.protocollo.length-1]);

                console.log(dataUser.data.utente)
                setUser(dataUser.data.utente)

                setLoading(false); //viene settato a false per far capire di aver caricato tutti i dati
            } catch (error) {
                setToastMessage({ title: "Error", body: error.message, stat: "error" });
            }
        }
        getInfo();

    }, [fetchContext,setReport,setReportList,setProtocolliList,setProtocollo,setUser]);
    return (

        <WrapperBox >
            {!isLoading &&(
            <Flex >
                <Flex w={"full"} m={5} rounded={10} wrap={"wrap"} bg={"fitdiary.800"} boxShadow={"2xl"} alignContent={"center"} justifyContent={"center"}>
                    <Heading w={"full"} ml={"5"} color={"white"} textAlign={"center"} >Statistiche</Heading>
                    <Box m={5} rounded={5} p={5} bg={"white"} w={"200px"} >
                        <VStack alignItems={"center"}>
                            <HStack fontSize={"2xl"}>
                                <HStack>
                                    <FaUser/>
                                    <Text>Preparatore</Text>
                                </HStack>
                            </HStack>
                            <HStack>
                                <Text fontSize={"xs"}>{user.preparatore.nome} {user.preparatore.cognome}</Text>
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
                                <Text fontSize={"xs"}>Scadenza</Text>
                                <Text fontSize={"xs"}>{protocollo && moment(protocollo.dataScadenza).format("DD/MM/YYYY")}</Text>
                            </HStack>
                        </VStack>
                    </Box>
                    <Box m={5} rounded={5} p={5} bg={"white"} w={"200px"}>
                        <VStack alignItems={"center"}>
                            <HStack fontSize={"2xl"}>
                                <HStack>
                                    <CgGym/>
                                    <Text>Reports</Text>
                                </HStack>
                                <Text>{reportList.length}</Text>
                            </HStack>
                            <HStack>
                                <Text fontSize={"xs"}>Ultimo report</Text>
                                <Text fontSize={"xs"}>{report && moment(report.dataCreazione).format("DD/MM/YYYY")}</Text>
                            </HStack>
                        </VStack>
                    </Box>
                </Flex>
            </Flex>
            )}
            {!isLoading && protocollo && protocollo.schedaAlimentare ? (
                <GridItem p={5} colSpan={colSpan}>
                    <Heading size={"md"} mb={2} textAlign={"center"}>Il pasto del giorno</Heading>
                    <AccordionMeal schedaAlimentare={protocollo.schedaAlimentare} nome="Spuntino" />
                    <AccordionMeal schedaAlimentare={protocollo.schedaAlimentare} nome="Colazione" />
                    <AccordionMeal schedaAlimentare={protocollo.schedaAlimentare} nome="Pranzo" />
                    <AccordionMeal schedaAlimentare={protocollo.schedaAlimentare} nome="Cena" />
                    <Accordion>
                        <AccordionItem>
                            <AccordionButton onClick={() => navigate("/dietcards/" + protocollo.id)}>
                                <Image boxSize='30px' htmlHeight='10px' objectFit='cover' src={meal} mr={3} />
                                <Text color={"fitdiary.900"}>Vedi l'alimentazione completa</Text>
                            </AccordionButton>
                        </AccordionItem>
                    </Accordion>
                </GridItem>
            ) : (<Text p={5}>Niente da mangiare oggi....</Text>)}
            {!isLoading && protocollo && protocollo.schedaAllenamento ? (
                <GridItem p={5} colSpan={colSpan}>
                    <Heading size={"md"} mb={2} textAlign={"center"}>L'allenamento</Heading>
                    {_(protocollo.schedaAllenamento.frequenza).times(i => {
                        return (<AccordionTraining key={i} schedaAllenamento={protocollo.schedaAllenamento} nome={"Allenamento " + (i + 1)} numeroAllenamento={(i + 1).toString()} />)
                    })}
                    <Accordion>
                        <AccordionItem>
                            <AccordionButton onClick={() => navigate("/trainingcards/" + protocollo.id)}>
                                <Image boxSize='30px' htmlHeight='10px' objectFit='cover' src={training} mr={3} />
                                <Text color={"fitdiary.900"}>Vedi la scheda completa</Text>
                            </AccordionButton>
                        </AccordionItem>
                    </Accordion>
                </GridItem>
            ) : (<Text p={5}>Non hai allenamenti oggi....</Text>)}
        </WrapperBox>
    )
}
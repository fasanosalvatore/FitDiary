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
import {useParams} from "react-router";
import {useNavigate} from "react-router-dom";
import _ from "lodash";
import {AccordionTraining} from "./AccordionTraining";
import training from "../images/dumbbell.png";
import {FaBook, FaUser} from "react-icons/fa";
import {GiMeal} from "react-icons/gi";
import {CgGym} from "react-icons/cg";

export default function DashboardCustomer() {
    const [isLoading, setLoading] = useState(true); // ricarica la pagina quando la variabile termina
    const fetchContext = useContext(FetchContext);
    const [protocollo, setProtocollo] = useState();
    const [report, setReport] = useState();
    const { id } = useParams();
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
        const listaProtocolli = async () => {
            try {
                const { data } = await fetchContext.authAxios("protocolli");
                setProtocollo(data.data.protocollo[data.data.protocollo.length-1]);
                setLoading(false); //viene settato a false per far capire di aver caricato tutti i dati
            } catch (error) {
                setToastMessage({ title: "Error", body: error.message, stat: "error" });
            }
        }
        listaProtocolli();

    }, [fetchContext, id]);
    return (
        <WrapperBox>
            <Flex>
                <Flex m={5} rounded={10} wrap={"wrap"} bg={"fitdiary.800"} boxShadow={"2xl"}>
                    <Heading w={"full"} ml={"5"} color={"white"} >Statistiche</Heading>
                    <Box m={5} rounded={5} p={5} bg={"white"} w={"200px"}>
                        <VStack alignItems={"start"}>
                            <HStack fontSize={"2xl"}>
                                <HStack>
                                    <FaUser/>
                                    <Text>Preparatore</Text>
                                </HStack>
                            </HStack>
                            <HStack>
                                <Text fontSize={"xs"}>Scolpito Nella Roccia</Text>
                            </HStack>
                        </VStack>
                    </Box>
                    <Box m={5} rounded={5} p={5} bg={"white"} w={"200px"}>
                        <VStack alignItems={"start"}>
                            <HStack fontSize={"2xl"}>
                                <HStack>
                                    <FaBook/>
                                    <Text>Protocolli</Text>
                                </HStack>
                                <Text>3</Text>
                            </HStack>
                            <HStack>
                                <Text fontSize={"xs"}>Attivi</Text>
                                <Text fontSize={"xs"}>2</Text>
                                <Text fontSize={"xs"}>Scaduti</Text>
                                <Text fontSize={"xs"}>1</Text>
                            </HStack>
                        </VStack>
                    </Box>
                    <Box m={5} rounded={5} p={5} bg={"white"} w={"200px"}>
                        <VStack alignItems={"start"}>
                            <HStack fontSize={"2xl"}>
                                <HStack>
                                    <GiMeal/>
                                    <Text>Diete</Text>
                                </HStack>
                                <Text>3</Text>
                            </HStack>
                            <HStack>
                                <Text fontSize={"xs"}>Attivi</Text>
                                <Text fontSize={"xs"}>2</Text>
                                <Text fontSize={"xs"}>Scaduti</Text>
                                <Text fontSize={"xs"}>1</Text>
                            </HStack>
                        </VStack>
                    </Box>
                    <Box m={5} rounded={5} p={5} bg={"white"} w={"200px"}>
                        <VStack alignItems={"start"}>
                            <HStack fontSize={"2xl"}>
                                <HStack>
                                    <CgGym/>
                                    <Text>Schede</Text>
                                </HStack>
                                <Text>3</Text>
                            </HStack>
                            <HStack>
                                <Text fontSize={"xs"}>Attivi</Text>
                                <Text fontSize={"xs"}>2</Text>
                                <Text fontSize={"xs"}>Scaduti</Text>
                                <Text fontSize={"xs"}>1</Text>
                            </HStack>
                        </VStack>
                    </Box>
                    <Box m={5} rounded={5} p={5} bg={"white"} w={"200px"}>
                        <VStack alignItems={"start"}>
                            <HStack fontSize={"2xl"}>
                                <HStack>
                                    <CgGym/>
                                    <Text>Reports</Text>
                                </HStack>
                                <Text>3</Text>
                            </HStack>
                            <HStack>
                                <Text fontSize={"xs"}>Ultimo report</Text>
                                <Text fontSize={"xs"}>21/02/2021</Text>
                            </HStack>
                        </VStack>
                    </Box>
                </Flex>
            </Flex>
            {!isLoading && protocollo.schedaAlimentare ? (
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
            ) : (<Text>Niente da mangiare oggi....</Text>)}
            {!isLoading && protocollo.schedaAllenamento ? (
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
            ) : (<Text>Non hai allenamenti oggi....</Text>)}
        </WrapperBox>
    )
}
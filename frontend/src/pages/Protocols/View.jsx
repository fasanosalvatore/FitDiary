import React, {useContext, useEffect, useState} from 'react';
import {
    Accordion,
    AccordionButton,
    AccordionItem,
    Box,
    Button,
    ButtonGroup,
    Flex,
    GridItem,
    Heading,
    Image,
    SimpleGrid,
    Text,
    Tooltip,
    useBreakpointValue,
    useToast,
    VStack
} from '@chakra-ui/react';
import moment from "moment";
import {useNavigate, useParams} from "react-router";
import {FetchContext} from "../../context/FetchContext";
import {GradientBar} from "../../components/GradientBar";
import training from "../../images/dumbbell.png";
import meal from "../../images/dish.png";
import _ from "lodash";
import {Link as ReactLink} from "react-router-dom";
import {AccordionMeal} from "../../components/AccordionMeal";
import {AccordionTraining} from "../../components/AccordionTraining";
import {ViewProtocolTitle} from "../../components/ViewProtocolTitle";
import {AuthContext} from "../../context/AuthContext";

export default function View() {
    const [isLoading, setLoading] = useState(true); // ricarica la pagina quando la variabile termina
    const fetchContext = useContext(FetchContext);
    const [protocollo, setProtocollo] = useState();
    const [report, setReport] = useState();
    const { id } = useParams();
    const colSpan = useBreakpointValue({ base: 2, xl: 1 })
    const authContext = useContext(AuthContext);

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
                const { data } = await fetchContext.authAxios("protocolli/" + id);
                setProtocollo(data.data.protocollo);
                console.log(data.data);
                setLoading(false); //viene settato a false per far capire di aver caricato tutti i dati
            } catch (error) {
                setToastMessage({ title: "Error", body: error.message, stat: "error" });
            }
        }
        listaProtocolli();

    }, [fetchContext, id]);

    useEffect(() => {
        const report = async () => {
            try {
                if (protocollo) {
                    const { data } = await fetchContext.authAxios("reports/search?data=" + protocollo.dataScadenza + "&clienteId=" + protocollo.cliente.id);// path da aggiungere quando il backend sarà pronto
                    console.log(data);
                    setReport(data.data);
                    setLoading(false);
                }
            } catch (error) {
                setToastMessage({ title: "Error", body: error.message, stat: "error" });
            }
        }
        report();
    }, [fetchContext, protocollo]);

    moment.locale("it-IT");
    const navigate = useNavigate();
    return (
        <>
            {!isLoading && (
                <Flex wrap={"wrap"} p={5}>
                    <Flex alignItems={"center"} mb={5}>
                        <Heading w={"full"}>Protocollo {protocollo.id}</Heading>
                    </Flex>
                    <Box bg={"white"} roundedTop={20} minW={{ base: "100%", xl: "100%" }} h={"full"}>
                        <GradientBar />
                        <ViewProtocolTitle protocollo={protocollo} authContext={authContext}/>
                        <SimpleGrid columns={2} p={5}>
                            {protocollo.schedaAlimentare ? (
                                <GridItem colSpan={colSpan}>
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
                            {protocollo.schedaAllenamento ? (
                                <GridItem colSpan={colSpan}>
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
                        </SimpleGrid>
                        <Flex p={5}>
                            <VStack>
                                <ButtonGroup>
                                    {report && report !== null && (
                                        <ReactLink to={`/reports/${report.id}`}>
                                            <Button colorScheme={"fitdiary"}>Visualizza Report</Button>
                                        </ReactLink>
                                    )}
                                    <Tooltip label="Hai completato il protocollo?">
                                        <Button onClick={() => {
                                            navigate("/reports/create")
                                        }} colorScheme='fitdiary'>Inserisci report</Button>
                                    </Tooltip>
                                </ButtonGroup>
                            </VStack>
                        </Flex>
                    </Box>
                </Flex>
            )}
        </>
    );
}

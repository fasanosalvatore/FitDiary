import {
    Accordion,
    AccordionButton,
    AccordionIcon,
    AccordionItem,
    AccordionPanel,
    Box,
    Flex,
    Text
} from "@chakra-ui/react";
import React from "react";

function getAllenamenti(schedaAlimentare, numeroAllenamento) {
    return schedaAlimentare.listaEsercizi.filter(c => c.numeroAllenamento === numeroAllenamento);
}

export function AccordionTraining(props) {
    const allenamenti = getAllenamenti(props.schedaAllenamento, props.numeroAllenamento);
    return allenamenti.length <= 0 ? "" :
        (
            <Accordion allowToggle>
                <AccordionItem>
                    <AccordionButton>
                        <h2>
                            <Box textStyle={"h1"} flex="1" textAlign="left">
                                <Text fontWeight={"bold"}
                                      color={"gray.600"}>{props.nome}</Text>
                            </Box>
                        </h2>
                        <AccordionIcon/>
                    </AccordionButton>
                    <AccordionPanel pb={4}>
                        {allenamenti.map(c => {
                            return (
                                <Flex key={c.id}>
                                    <Text>{c.nome}</Text>
                                    <Text ml={3} fontWeight={"bold"}>Rip:</Text>
                                    <Text ml={3}>{c.ripetizioni}</Text>
                                    <Text ml={3} fontWeight={"bold"}>Serie:</Text>
                                    <Text ml={3}>{c.serie}</Text>
                                    <Text ml={3} fontWeight={"bold"}>Rec:</Text>
                                    <Text ml={3}>{c.recupero}</Text>
                                </Flex>
                            )
                        })}
                    </AccordionPanel>
                </AccordionItem>
            </Accordion>)
}
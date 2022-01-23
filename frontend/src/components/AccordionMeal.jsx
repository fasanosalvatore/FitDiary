import {Accordion, AccordionButton, AccordionIcon, AccordionItem, AccordionPanel, Box, Text} from "@chakra-ui/react";
import React from "react";

function getPasti(schedaAlimentare, nomePasto) {
    return schedaAlimentare.listaAlimenti.filter(c => c.pasto === nomePasto).filter(c => parseInt(c.giorno) === new Date().getDay() + 1);
}

export function AccordionMeal(props) {
    const pasti = getPasti(props.schedaAlimentare, props.nome);
    return pasti.length <= 0 ? "" :
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
                        {pasti.map(c => {
                            return (<Text key={c.id}>{c.nome} {c.grammi}</Text>)
                        })}
                    </AccordionPanel>
                </AccordionItem>
            </Accordion>)
}
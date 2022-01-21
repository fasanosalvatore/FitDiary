import {Flex, Heading, Text} from "@chakra-ui/react";
import moment from "moment";
import React from "react";

export function ViewProtocolTitle(props) {
    return <Flex w="full" h="full" p={5} justifyContent={"space-between"} flexWrap={"wrap"}>
        <Flex>
            <Heading size={"sx"} textAlign="start">Inizio:</Heading>
            <Text ml={2}>{moment(props.protocollo.dataCreazione).format("DD/MM/yyyy")}</Text>
        </Flex>
        <Flex ml={[0, 5]}>
            <Heading size={"sx"} textAlign="center">Preparatore:</Heading>
            <Text ml={2}>{props.protocollo.preparatore.nome} {props.protocollo.preparatore.cognome}</Text>
        </Flex>
        <Flex>
            <Heading size={"sx"} textAlign="center">Fine:</Heading>
            <Text ml={2}>{moment(props.protocollo.dataScadenza).format("DD/MM/yyyy")}</Text>
        </Flex>
    </Flex>;
}
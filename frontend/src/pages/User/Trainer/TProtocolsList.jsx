import React, {useState} from 'react';
import Icon, {
    Table,
    Thead,
    Tbody,
    Tr,
    Th,
    Td,
    TableCaption, VStack, Heading, Button, HStack,
} from '@chakra-ui/react';

import {getProtocolList} from "../../../fakeBackend";
import moment from "moment";

import {Link} from "react-router-dom";
import {InfoIcon, SearchIcon} from '@chakra-ui/icons'
import authservice from "../../../services/auth.service";
{/*Un cliente non deve vedere le liste di tutti i protocolli di un preparatore/ */}

function TProtocolsList() {
    const listProtocolli = getProtocolList().data;
    moment.locale("it-IT");
    const preparatore = listProtocolli.protocollo[0].preparatore;
    const [search,setSearch] =useState("");
    const onChange=(e)=>{
        setSearch(e.target.value); // e evento target chi lancia l'evento e il value è il valore
    }
    return (

        <VStack>
            <HStack>
                <span className="SearchSpan">
        <SearchIcon/>
      </span>
                <input
                    className="SearchInput"
                    type="text"
                    onChange={onChange}
                    placeholder="Search"
                />
            </HStack>
            <Heading w={"full"} mb={5} textAlign={"center"}>Ecco la lista dei protocolli
                del preparatore {preparatore.nome} {preparatore.cognome}</Heading>
            <Table variant={"unstyled"} colorScheme={"gray"} size="md">
                <TableCaption>Lista Protocolli Preparatore</TableCaption>
                <Thead color>
                    <Tr>
                        <Th>ID</Th>
                        <Th>Data Creazione</Th>
                        <Th>Data Scadenza</Th>
                        <Th>Azione</Th>
                    </Tr>
                </Thead>
                <Tbody>
                    {listProtocolli.protocollo.map((protocol, i) => {
                        if(protocol.preparatore === parseInt(search)|| search ===""){
                            return (
                                <Tr>
                                    <Td>{protocol.id}</Td>
                                    <Td>{moment(protocol.dataCreazione).format("DD/MM/yyyy")}</Td>
                                    <Td>{moment(protocol.dataScadenza).format("DD/MM/yyyy")}</Td>
                                    <Td><Link to={"/customer/protocol/"+protocol.id}> <InfoIcon/></Link></Td>
                                </Tr>
                            );}
                    })}
                </Tbody>
            </Table>
        </VStack>
    );
}

export default TProtocolsList;


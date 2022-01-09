import React, {useContext, useEffect, useState} from 'react';
import {
    Table,
    Thead,
    Tbody,
    Tr,
    Th,
    Td,
    TableCaption, VStack, Heading, HStack, useToast,
} from '@chakra-ui/react';

import {getProtocolList} from "../../../fakeBackend";
import moment from "moment";
import {Link, useParams} from "react-router-dom";
import {InfoIcon, SearchIcon} from '@chakra-ui/icons'
import {FetchContext} from "../../../context/FetchContext";

function ProtocolsList() {
    moment.locale("it-IT");
    const {id} = useParams();
    const [search, setSearch] = useState("");
    const onChange = (e) => {
        setSearch(e.target.value); // e evento target chi lancia l'evento e il value è il valore
    }

    const toast = useToast({
        duration: 9000,
        isClosable: true,
        variant: "solid",
        containerStyle: {
            width: '100%',
            maxWidth: '100%',
        },

    })

    const [isLoading, setLoading] = useState(true); // ricarica la pagina quando la variabile termina
    const fetchContext = useContext(FetchContext);
    const [listProtocolli, setProtocolli] = useState();
    useEffect(() => {
        const listaProtocolli = async () => {
            try {
                const {data} = await fetchContext.authAxios("protocolli");
                console.log(data);
                setProtocolli(data.data);
                setLoading(false); //viene settato a false per far capire di aver caricato tutti i dati

            } catch (error) {
                console.log(error);
                toast({
                    title: "ERROR",
                    description: "NOT AUTHORIZED",
                    status: "error"
                })
            }

        }
        listaProtocolli();
    }, []);

    return (
        <>
            {!isLoading && (
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
                    {/* Barra di ricerca*/}


                    {(listProtocolli.protocollo)?
                        <Heading w={"full"} mb={5} textAlign={"center"}>Ecco lo storico dei protocolli
                            di {listProtocolli.protocollo[0].cliente.nome} {listProtocolli.protocollo[0].cliente.cognome}</Heading>
                        : <Heading>Ecco la lista dei protocolli</Heading>}

                    <Table variant={"unstyled"} colorScheme={"gray"} size="md">
                        <TableCaption>Lista Protocolli</TableCaption>
                        <Thead color>
                            <Tr>
                                <Th>ID</Th>
                                <Th>Data Creazione</Th>
                                <Th>Data Scadenza</Th>
                                <Th>Azione</Th>
                            </Tr>
                        </Thead>
                        <Tbody>
                            {listProtocolli.protocollo.map((protocol) => (protocol.id === parseInt(search) || search === "") && (
                                <Tr>
                                    <Td>{protocol.id}</Td>
                                    <Td>{moment(protocol.dataCreazione).format("DD/MM/yyyy")}</Td>
                                    <Td>{moment(protocol.dataScadenza).format("DD/MM/yyyy")}</Td>
                                    <Td><Link to={"/customer/protocol/" + protocol.id}> <InfoIcon/></Link></Td>
                                </Tr>
                            ))}
                        </Tbody>
                    </Table>
                </VStack>
            )}
        </>
    );
}


export default ProtocolsList;


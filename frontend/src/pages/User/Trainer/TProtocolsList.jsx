import React, {useState, useContext, useEffect} from 'react';
import Icon, {
    Table,
    Thead,
    Tbody,
    Tr,
    Th,
    Td,
    TableCaption, VStack, Heading, Button, HStack, useToast,
} from '@chakra-ui/react';


import moment from "moment";
import {Link} from "react-router-dom";
import {InfoIcon, SearchIcon} from '@chakra-ui/icons'
import {FetchContext} from "../../../context/FetchContext";
import {useParams} from "react-router";

{/*Un cliente non deve vedere le liste di tutti i protocolli di un preparatore/ */
}

function TProtocolsList() {
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
        position: "top",
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
                const {data} = await fetchContext.authAxios.get("protocolli?clienteId="+id);
                console.log(data);
                setProtocolli(data.data);
                setLoading(false); //viene settato a false per far capire di aver caricato tutti i dati

            } catch (error) {
                console.log(error);
                toast({title:"ERROR",
                description: "NOT AUTHORIZED",
                status: "error"})
            }

        }
        listaProtocolli();
    }, []);




    return (
        <>
        {!isLoading && (
        <VStack>
            {/* se la pagina è ancora in caricamento non mostrare quello che sta giu'*/}

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
            {(listProtocolli.protocollo)?
                <Heading w={"full"} mb={5} textAlign={"center"}> Ecco la lista dei protocolli
                del preparatore {listProtocolli.protocollo[0].preparatore.nome} {listProtocolli.protocollo[0].preparatore.cognome} per il cliente {listProtocolli.protocollo[0].cliente.nome} {listProtocolli.protocollo[0].cliente.cognome} </Heading>
            :<Heading>Ecco la lista dei protocolli</Heading>}
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
                if(protocol.id === parseInt(search)|| search ===""){
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
        )}
            </>
    );
}

export default TProtocolsList;


import React, {useContext, useEffect, useState} from 'react';
import {useForm} from 'react-hook-form';
import {
    Accordion,
    AccordionButton,
    AccordionIcon,
    AccordionItem,
    AccordionPanel,
    Box,
    Button,
    Flex,
    FormControl,
    FormErrorMessage,
    FormLabel,
    Heading,
    HStack,
    IconButton,
    Image,
    Input,
    InputGroup,
    InputLeftElement,
    Modal,
    ModalBody,
    ModalCloseButton,
    ModalContent,
    ModalFooter,
    ModalHeader,
    ModalOverlay,
    Table,
    TableCaption,
    Tbody,
    Td,
    Text,
    Th,
    Thead,
    Tooltip,
    Select,
    Tr,
    useDisclosure,
    useToast, Toast
} from "@chakra-ui/react";
import {FetchContext} from "../../context/FetchContext";
import {GradientBar} from "../../components/GradientBar";
import {AddIcon, SearchIcon, DeleteIcon} from "@chakra-ui/icons";
import moment from "moment/moment";
import {AuthContext} from "../../context/AuthContext";
import {useParams} from "react-router";

export default function Edit() {
    const fetchContext = useContext(FetchContext);
    const urlEditSchedaAllenamento = "schedaAllenamento/modificaScheda";
    const {isOpen, onOpen, onClose} = useDisclosure()
    const {handleSubmit, formState: {errors, isSubmitting}} = useForm();
    const toast = useToast({
        duration: 1000, isClosable: true, variant: "solid", position: "top", containerStyle: {
            width: '100%', maxWidth: '100%',
        },
    })
    const {id} = useParams();
    const [schedaAllenamento, setSchedaAllenamento] = useState();
    const authContext = useContext(AuthContext)
    const {authState} = authContext;
    const [search, setSearch] = useState("");
    const [fetchCompleted, setFetchCompleted] = useState(false); // Nuovo stato
    const [indexGiorno, setIndexGiorno] = useState(0);
    const [nomeScheda, setNomeScheda] = useState("");
    const [frequenzaScheda, setFrequenzaScheda] = useState(0);
    const onChange = (e) => {
        setSearch(e.target.value); // e evento target chi lancia l'evento e il value è il valore
    }
    const [toastMessage, setToastMessage] = useState(undefined);
    const [isLoading, setLoading] = useState(true); // ricarica la pagina quando la variabile termina
    const [listEsercizi, setEsercizi] = useState();

    const protocol = window.location.protocol;
    const domain = window.location.host;
    const full = `${protocol}//${domain}`

    function toastParam(title, description, status) {
        return {
            title: title, description: description, status: status
        };
    }

    moment.locale("it-IT");

    useEffect(() => {
        if (toastMessage) {
            const {title, body, stat} = toastMessage;

            toast({
                title, description: body, status: stat, duration: 1000, isClosable: true
            });
        }
    }, [toastMessage, toast]);

    useEffect(() => {
        const loadlistaEsercizi = async () => {
            try {
                const { data } = await fetchContext.authAxios("esercizi/getAllEsercizi");
                setEsercizi(data.data);

            } catch (error) {
                setToastMessage({title:"Errore", body:error.message, stat:"error"});
            }
        }
        const getSchedaAllenamento = async () => {
            if (!fetchCompleted) {
                try {
                    const {data} = await fetchContext.authAxios("schedaAllenamento/getSchedaAllenamentoById?idScheda=" + id);
                    let scheda=data.data.scheda_allenamento;
                    let nome=scheda.nome;
                    let freq=scheda.frequenza;
                    setNomeScheda(nome);
                    setFrequenzaScheda(freq);
                    let tmpList=data.data.scheda_allenamento.listaEsercizi;

                    const raggruppatoPerGiorno = {};

                    tmpList.forEach(elemento => {
                        const giorno = elemento.giornoDellaSettimana;
                        if (!raggruppatoPerGiorno[giorno]) {
                            raggruppatoPerGiorno[giorno] = [];
                        }
                        raggruppatoPerGiorno[giorno].push(elemento);
                    });
                    const listaEs = Object.values(raggruppatoPerGiorno);
                    while(listaEs.length<7)
                    {
                        listaEs.push([]);
                    }
                    setSchedaAllenamento(listaEs);
                    setLoading(false);
                    setFetchCompleted(true); // Imposta fetchCompleted a true dopo il completamento
                } catch (error) {
                    toast({
                        title: "ERROR",
                        description: "NOT AUTHORIZED",
                        status: "error"
                    })
                }
            }
        }
        loadlistaEsercizi();
        getSchedaAllenamento();
    }, [fetchContext,fetchCompleted]);


    function addEsercizio(esercizio, serie, ripetizioni,recupero, descrizione) {
        let esiste=false;
        let i=0;
        let eserciziGiorno= schedaAllenamento[indexGiorno];
        while(i<eserciziGiorno.length && !esiste)
        {
            let esercizioObj=eserciziGiorno[i];
            if(esercizioObj.esercizio.id==esercizio.id)
            {
                esiste=true;
            }
            i++;
        }

        if(!esiste)
        {
            let objTest={};
            objTest.esercizio=esercizio;
            objTest.serie=serie;
            objTest.giornoDellaSettimana=indexGiorno;
            objTest.ripetizioni=ripetizioni;
            objTest.recupero=recupero;
            objTest.descrizione=descrizione;

            let tmp=schedaAllenamento;
            tmp[indexGiorno].push(objTest);
            setSchedaAllenamento(tmp);

            toast(toastParam("Operazione eseguita!", "Esercizio aggiunto con successo", "success"));
        }
        else
        {
            toast(toastParam("Attenzione!", "Hai già inserito questo esercizio", "warning"));
        }
    }

    function formatData(inputData) {
        const formattedData = {
            name: nomeScheda,
            istanzeEsercizi: [],
            frequenza:frequenzaScheda,
            schedaId:id
        };

        if (inputData && Array.isArray(inputData)) {
            const instances = inputData;
            instances.forEach((instance) => {
                for(let i=0;i<instance.length;i++)
                {
                    let obj=instance[i];
                    if (obj.esercizio) {
                        formattedData.istanzeEsercizi.push({
                            serie:obj.serie,
                            giornoDellaSettimana:obj.giornoDellaSettimana,
                            ripetizioni:obj.ripetizioni,
                            recupero:obj.recupero,
                            descrizione:obj.descrizione,
                            idEsercizio:obj.esercizio.id
                        });
                    }
                }
            });
        }

        return formattedData;
    }

    const onSubmit = async (values) => {
        let filterScheda=[];
        for(let i=0;i<frequenzaScheda;i++)
        {
            filterScheda[i]=schedaAllenamento[i];
        }
        try {
            if(nomeScheda.length <0){
                toast(toastParam("Attenzione!", "Inserisci un nome valido", "error"));
                throw new Error()
            }
            let numeroEserciziScheda = 0
            let numGiorni=0;
            filterScheda.forEach(el =>{
                numeroEserciziScheda += el.length;
                if(el.length>0)
                {
                    numGiorni++;
                }
            })
            if(numGiorni != frequenzaScheda)
            {
                throw new Error("Inserisci almeno un allenamento per ogni giorno");
            }
            if(numeroEserciziScheda <= 0) {
                toast(toastParam("Attenzione!", "Inserisci almeno un esercizio", "error"));
                throw new Error()
            }

            let formattedScheda = formatData(filterScheda);
            const {data} = await fetchContext.authAxios.post(urlEditSchedaAllenamento, formattedScheda);
            setSchedaAllenamento([[],[],[],[],[],[],[]]);
            document.getElementById("textScheda").value="";
            toast(toastParam("Sceheda Allenamento creata con successo", "Scheda aggiunta all'elenco", "success"));
        }
        catch (error) {
            alert(error);
        }
    }

    return (<>
        {!isLoading && (<Flex wrap={"wrap"} p={5}>
            <Flex alignItems={"center"} mb={5}>
                <Heading w={"full"}>Modifca scheda allenamento</Heading>
            </Flex>
            <Box bg={"white"} roundedTop={20} minW={{base: "100%", xl: "100%"}} h={"full"}>
                <GradientBar/>
                <Box pl={[0, 5, 20]} pr={[0, 5, 20]} pb={10} pt={5}>
                    <form style={{width: "100%"}} onSubmit={handleSubmit(onSubmit)}>
                        <FormControl id={"nome"} isInvalid={errors.nome} isRequired={"required"} pt={5}>
                            <FormLabel htmlFor="nome">Nome delle scheda</FormLabel>
                            <Input required={"true"} type="text" id={"textScheda"} placeholder="Nome scheda" value={nomeScheda}
                                   onChange={(e) => {
                                       let newNome = e.target.value;
                                       setNomeScheda(newNome)
                                   }}/>
                            <FormErrorMessage>{errors.nome && errors.nome.message}</FormErrorMessage>
                        </FormControl>
                        <FormControl id={"frequenzaScheda"} isInvalid={errors.nome} isRequired={"required"} pt={5}>
                            <FormLabel htmlFor="frequenzaScheda">Frequenza Settiamanale</FormLabel>
                            <Input required={"true"} type="number" placeholder="3" defaultValue={3} value={frequenzaScheda}
                                   onChange={(e) => {
                                       let newValue = e.target.value;
                                       if(newValue>7) {
                                           newValue=7;
                                       }
                                       setFrequenzaScheda(newValue)
                                   }}/>
                            <FormErrorMessage>{errors.nome && errors.nome.message}</FormErrorMessage>
                        </FormControl>

                        <Modal isOpen={isOpen} onClose={onClose} isCentered={true} size={"5xl"}>
                            <ModalOverlay/>
                            <ModalContent>
                                <ModalHeader fontSize={'3xl'} textAlign={"center"}>Aggiungi allenamenti alla scheda</ModalHeader>
                                <ModalCloseButton/>
                                <ModalBody align={"center"}>
                                    <Flex justify="center">
                                        <HStack align="center">
                                            <HStack>
                                                {!isLoading && listEsercizi && (
                                                    <Flex wrap={"wrap"} p={5}>
                                                        <Flex w={"full"} alignItems={"center"} mb={5} justifyContent={"space-between"}>
                                                            <Heading w={"full"}>Lista Esercizi</Heading>
                                                        </Flex>
                                                        <Box bg={"white"} roundedTop={20} minW={{ base: "100%", xl: "100%" }} h={"full"}>
                                                            <GradientBar />
                                                            <Box pl={10} pr={10} pb={5} pt={5}>
                                                                <HStack>
                                                                    <InputGroup>
                                                                        <InputLeftElement
                                                                            pointerEvents="none"
                                                                            children={<SearchIcon color="gray.300" />}
                                                                        />
                                                                        <Input
                                                                            className="SearchInput"
                                                                            type="text"
                                                                            onChange={onChange}
                                                                            placeholder="Inserisci il nome di un esercizio"
                                                                        />
                                                                    </InputGroup>
                                                                </HStack>
                                                                {/* Barra di ricerca*/}
                                                                {listEsercizi.esercizi.length > 0 ? (
                                                                    <>
                                                                        <Text fontSize="xl" my={5}>
                                                                            Lista degli esercizi
                                                                        </Text>
                                                                        <Table variant={"striped"} colorScheme={"gray"} size="md">
                                                                            <TableCaption>Lista Esercizi</TableCaption>
                                                                            <Thead bg="fitdiary.100">
                                                                                <Tr>
                                                                                    <Th>Foto</Th>
                                                                                    <Th>Nome</Th>
                                                                                    <Th>Categoria</Th>
                                                                                    <Th>Azione</Th>
                                                                                </Tr>
                                                                            </Thead>
                                                                            <Tbody>
                                                                                {listEsercizi.esercizi.map((esercizio) => (esercizio.nome.toLowerCase().startsWith(search.toLowerCase()) || search === "") && (
                                                                                        <Tr key={esercizio.id}>
                                                                                            <Td p={1}
                                                                                                m={0}>
                                                                                                <Image
                                                                                                    objectFit='fill'
                                                                                                    boxSize={120}
                                                                                                    src={full + "/" + esercizio.pathFoto}
                                                                                                    alt='Foto non disponibile'/>
                                                                                            </Td>
                                                                                            <Td>{esercizio.nome}</Td>
                                                                                            <Td>{esercizio.tipoEsercizio.nome}</Td>
                                                                                            <Td><Button
                                                                                                colorScheme='fitdiary'
                                                                                                onClick={() => {
                                                                                                    if (esercizio.id> 0) {
                                                                                                        addEsercizio(esercizio, 3, 3,60,"");
                                                                                                    } else {
                                                                                                        toast(toastParam("Attenzione!", "Seleziona un esercizio", "error"))
                                                                                                    }
                                                                                                }}
                                                                                                fontSize={"s"}>
                                                                                                <AddIcon/>
                                                                                            </Button></Td>
                                                                                        </Tr>
                                                                                    )
                                                                                )}
                                                                            </Tbody>
                                                                        </Table>
                                                                    </>
                                                                ) : (
                                                                    <Heading py={5} textAlign={"center"}>
                                                                        Non c'è niente qui...
                                                                    </Heading>
                                                                )}
                                                            </Box>
                                                        </Box>
                                                    </Flex>
                                                )}
                                            </HStack>
                                        </HStack>
                                    </Flex>
                                </ModalBody>
                                <ModalFooter alignItems={"center"}>
                                </ModalFooter>
                            </ModalContent>
                        </Modal>

                        <Accordion allowToggle defaultIndex={[0]} w="full" mt={"60px"}>
                            {Array.from({length: frequenzaScheda}, (_, d) => {
                                return (
                                    <AccordionItem key={d}>
                                        <h2>
                                            <AccordionButton>
                                                <Box flex='1' textAlign='center' fontWeight={"extrabold"}
                                                     fontSize={"xl"}>
                                                    {"Giorno " + (d+1)}
                                                </Box>
                                                <AccordionIcon/>
                                            </AccordionButton>
                                        </h2>
                                        <AccordionPanel pb={4}>
                                            {schedaAllenamento[d]
                                                .map((istanzaEsercizio, key) => {
                                                    let esercizio = istanzaEsercizio.esercizio;
                                                    return (
                                                        <>
                                                            <Table borderBottom={"solid 1px "}
                                                                   borderColor={"blue.200"}
                                                                   key={key}
                                                                   variant="unstyled" size="md">
                                                                <Thead>
                                                                    <Tr>
                                                                        <Th>Immagine</Th>
                                                                        <Th>Nome</Th>
                                                                        <Th>Serie</Th>
                                                                        <Th>Ripetizioni</Th>
                                                                        <Th>Recupero</Th>
                                                                        <Th>Descrizione</Th>
                                                                        <Th>Azioni</Th>
                                                                    </Tr>
                                                                </Thead>
                                                                <Tbody>
                                                                    <Tr>
                                                                        <Td
                                                                            p={1}
                                                                            m={0}>
                                                                            <Image
                                                                                objectFit='contain'
                                                                                boxSize={100}
                                                                                src={full + "/" + esercizio.pathFoto}
                                                                                alt='Foto non disponibile'/>
                                                                        </Td>
                                                                        <Td maxWidth={100}>
                                                                            <Text fontWeight={"bold"}>{esercizio.tipoEsercizio.nome}</Text>
                                                                            <Text>{esercizio.nome}</Text>
                                                                        </Td>
                                                                        <Td maxWidth={100}>
                                                                            <Input
                                                                                w={20}
                                                                                min={1}
                                                                                max={10000}
                                                                                type={"number"}
                                                                                defaultValue={istanzaEsercizio.serie}
                                                                                onChange={(e) => {
                                                                                    let value=e.target.value;
                                                                                    if(value.length>0)
                                                                                    {
                                                                                        let tmp=schedaAllenamento;
                                                                                        tmp[d][key].serie=value;
                                                                                        setSchedaAllenamento(tmp);
                                                                                    }
                                                                                }}/>
                                                                                </Td>
                                                                        <Td maxWidth={100}>
                                                                            <Input
                                                                                w={20}
                                                                                min={1}
                                                                                max={100}
                                                                                type={"number"}
                                                                                defaultValue={istanzaEsercizio.ripetizioni}
                                                                                onChange={(e) => {
                                                                                    let value=e.target.value;
                                                                                    if(value.length>0)
                                                                                    {
                                                                                        let tmp=schedaAllenamento;
                                                                                        tmp[d][key].ripetizioni=value;
                                                                                        setSchedaAllenamento(tmp);
                                                                                    }
                                                                                }}/>
                                                                            </Td>
                                                                        <Td maxWidth={100}>
                                                                            <Input
                                                                                w={20}
                                                                                min={1}
                                                                                max={10000}
                                                                                type={"number"}
                                                                                defaultValue={istanzaEsercizio.recupero}
                                                                                onChange={(e) => {
                                                                                    let value=e.target.value;
                                                                                    if(value.length>0)
                                                                                    {
                                                                                        let tmp=schedaAllenamento;
                                                                                        tmp[d][key].recupero=value;
                                                                                        setSchedaAllenamento(tmp);
                                                                                    }
                                                                                }}/>
                                                                            </Td>
                                                                        <Td maxWidth={512}>
                                                                            <Input
                                                                                w={280}
                                                                                h={100}
                                                                                type={"text"}
                                                                                defaultValue={istanzaEsercizio.descrizione}
                                                                                onChange={(e) => {
                                                                                    let value=e.target.value;
                                                                                    if(value.length>0)
                                                                                    {
                                                                                        let tmp=schedaAllenamento;
                                                                                        tmp[d][key].descrizione=value;
                                                                                        setSchedaAllenamento(tmp);
                                                                                    }
                                                                                }}/>
                                                                        </Td>
                                                                        <Td>
                                                                            <IconButton colorScheme={"red"}
                                                                                        onClick={() => {
                                                                                            if (window.confirm("Sei sicuro di voler eliminare l'alimento?")) {
                                                                                                schedaAllenamento[d].splice(key, 1);
                                                                                                let newV = [...schedaAllenamento];
                                                                                                setSchedaAllenamento(newV);
                                                                                            }
                                                                                        }}
                                                                                        aria-label={"Pulsante che elimina elemento"}>
                                                                                <DeleteIcon/>
                                                                            </IconButton>
                                                                        </Td>
                                                                    </Tr>
                                                                </Tbody>
                                                            </Table>
                                                        </>
                                                    );
                                                })}
                                            <Flex pt={5}>
                                                <Button
                                                    w="full"
                                                    colorScheme='fitdiary'
                                                    onClick={() => {
                                                        onOpen();
                                                        setIndexGiorno(d);
                                                    }}>
                                                    Aggiungi Esercizi</Button>
                                            </Flex>
                                        </AccordionPanel>
                                    </AccordionItem>
                                )
                            })}

                        </Accordion>
                        <Button w="full" mt={4} colorScheme='fitdiary' isLoading={isSubmitting} type='submit'>
                            Salva Scheda
                        </Button>
                    </form>
                </Box>
            </Box>
        </Flex>)}
    </>);
}
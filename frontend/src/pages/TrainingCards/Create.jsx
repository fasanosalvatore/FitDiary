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

export default function Create() {
    const fetchContext = useContext(FetchContext);
    const urlCreateSchedaAllenamento = "schedaAllenamento/creaScheda";
    const {isOpen, onOpen, onClose} = useDisclosure()
    const {handleSubmit, formState: {errors, isSubmitting}} = useForm();
    const toast = useToast({
        duration: 1000, isClosable: true, variant: "solid", position: "top", containerStyle: {
            width: '100%', maxWidth: '100%',
        },
    })
    const authContext = useContext(AuthContext)
    const {authState} = authContext;
    const [search, setSearch] = useState("");
    const [fetchCompleted, setFetchCompleted] = useState(false); // Nuovo stato
    let [schedaAllenamento, setSchedaAllenamento] = useState([[], [], [], [], [], [], []]);
    const [indexGiorno, setIndexGiorno] = useState(0);
    const [nomeScheda, setNomeScheda] = useState("");
    const [frequenzaScheda, setFrequenzaScheda] = useState(3);
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
                console.log(data)
                setEsercizi(data.data);
                setLoading(false); //viene settato a false per far capire di aver caricato tutti i dati
            } catch (error) {
                setToastMessage({title:"Errore", body:error.message, stat:"error"});
            }

        }
        loadlistaEsercizi();
    }, [fetchContext]);
    
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
        console.log(schedaAllenamento)
    }

    function formatData(inputData) {
        const formattedData = {
            name: nomeScheda,
            istanzeEsercizi: [],
            frequenza:frequenzaScheda
        };

        if (inputData && Array.isArray(inputData[0])) {
            const instances = inputData[0];
            instances.forEach((instance) => {
                if (instance && instance.esercizio) {
                    formattedData.istanzeEsercizi.push({
                        serie:instance.serie,
                        ripetizioni:instance.ripetizioni,
                        recupero:instance.recupero,
                        descrizione:instance.descrizione,
                        idEsercizio:instance.esercizio.id
                    });
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
        console.log(filterScheda);
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


            let formattedScheda = formatData(filterScheda)
            const {data} = await fetchContext.authAxios.post(urlCreateSchedaAllenamento, formattedScheda);
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
                <Heading w={"full"}>Crea una scheda Allenamento</Heading>
            </Flex>
            <Box bg={"white"} roundedTop={20} minW={{base: "100%", xl: "100%"}} h={"full"}>
                <GradientBar/>
                <Box pl={[0, 5, 20]} pr={[0, 5, 20]} pb={10} pt={5}>
                    <form style={{width: "100%"}} onSubmit={handleSubmit(onSubmit)}>
                        <FormControl id={"nome"} isInvalid={errors.nome} isRequired={"required"} pt={5}>
                            <FormLabel htmlFor="nome">Nome delle scheda</FormLabel>
                            <Input required={"true"} type="text" id={"textScheda"} placeholder="Scheda pettorali e deltoidi" name={"nome"}
                                   onChange={(e) => {
                                       let newNome = e.target.value;
                                       setNomeScheda(newNome)
                                   }}/>
                            <FormErrorMessage>{errors.nome && errors.nome.message}</FormErrorMessage>
                        </FormControl>
                        <FormControl id={"frequenzaScheda"} isInvalid={errors.nome} isRequired={"required"} pt={5}>
                            <FormLabel htmlFor="frequenzaScheda">Frequenza Settiamanale</FormLabel>
                            <Input required={"true"} type="number" min={1} max={7} placeholder="3" defaultValue={3} name={"frequenzaScheda"}
                                   onChange={(e) => {
                                       let newValue = e.target.value;
                                       if(newValue > 7) {
                                           e.target.value=7
                                           newValue=7
                                       }
                                       if(newValue < 1) {
                                           newValue=1
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
                            {Array.from({length: parseInt(frequenzaScheda)}, (_, d) => {
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
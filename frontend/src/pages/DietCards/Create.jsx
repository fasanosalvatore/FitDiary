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
import {getFakeSchedaAlimentare, getSchedaAlimentare} from "../../fakeBackend";
import {useParams} from "react-router";

export default function Create() {
    const fetchContext = useContext(FetchContext);
    const {setSchedaAlimentareSubmit, handleSubmitScheda, formState: {errors, isSubmitting}} = useForm();
    const urlCreateSchedaALimentare = "schedaalimentare/create";
    const {isOpen, onOpen, onClose} = useDisclosure()
    const {handleSubmit, setValue} = useForm();
    const toast = useToast({
        duration: 1000, isClosable: true, variant: "solid", position: "top", containerStyle: {
            width: '100%', maxWidth: '100%',
        },
    })
    const onAddAlimento=useState("");
    const authContext = useContext(AuthContext)
    const {authState} = authContext;
    const [search, setSearch] = useState("");
    const [fetchCompleted, setFetchCompleted] = useState(false); // Nuovo stato
    let [schedaAlimentare, setSchedaAlimentare] = useState([[],[],[],[],[],[],[]]);
    const [indexGiorno, setIndexGiorno] = useState(0);
    const {id} = useParams();
    const onChange = (e) => {
        setSearch(e.target.value); // e evento target chi lancia l'evento e il value è il valore
    }
    const [toastMessage, setToastMessage] = useState(undefined);
    const [isLoading, setLoading] = useState(true); // ricarica la pagina quando la variabile termina
    const [listAlimenti, setAlimenti] = useState();

    const days = ["Lunedi", "Martedi", "Mercoledi", "Giovedi", "Venerdi", "Sabato", "Domenica"];

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
        const loadlistaAlimenti = async () => {
            if (!fetchCompleted) {
                try {
                    const {data} = await fetchContext.authAxios("alimenti/getAllAlimenti");
                    setAlimenti(data.data);
                    setLoading(false); //viene settato a false per far capire di aver caricato tutti i dati
                } catch (error) {
                    setToastMessage({title: "Errore", body: error.message, stat: "error"});
                }
            }
        }

        loadlistaAlimenti();
    }, [fetchContext, fetchCompleted]);



    let vettPasti=[
        {"ID":1,"Nome":"Colazione"},
        {"ID":2,"Nome":"Spuntino Mattina"},
        {"ID":3,"Nome":"Pranzo"},
        {"ID":4,"Nome":"Spuntino Pomeriggio"},
        {"ID":5,"Nome":"Cena"},
        {"ID":6,"Nome":"Spuntino Sera"},
        {"ID":7,"Nome":"Extra"},
    ];

    function addAlimento(alimento,pasto,qnt)
    {
        let esiste=false;
        let i=0;
        let pastiGiorno=schedaAlimentare[indexGiorno];
        while(i<pastiGiorno.length && !esiste)
        {
            let pastoObj=pastiGiorno[i];
            if(pastoObj.Pasto==pasto && pastoObj.alimento.id==alimento.id)
            {
                esiste=true;
            }
            i++;
        }

        if(!esiste)
        {
            let objTest={};
            objTest.alimento=alimento;
            objTest.Pasto=pasto;
            objTest.Qnt=qnt;

            let tmp=schedaAlimentare;
            tmp[indexGiorno].push(objTest);
            setSchedaAlimentare(tmp);

            toast(toastParam("Operazione eseguita!", "Alimento aggiunto con successo", "success"));
        }
        else
        {
            toast(toastParam("Attenzione!", "Hai già inserito questo alimento", "warning"));
        }
    }


    const onSubmit = async (values) => {
        try {
            const {data} = await fetchContext.authAxios.post(urlCreateSchedaALimentare, values);
            toast(toastParam("Sceheda Alimentare creata con successo", "Scheda aggiunta all'elenco", "success"));
        } catch (error) {
            console.log(error.response.data.message)
            toast({
                title: 'Errore', description: error.response.data.message, status: 'error',
            })
        }
    }

    return (<>
        {!isLoading && (<Flex wrap={"wrap"} p={5}>
            <Flex alignItems={"center"} mb={5}>
                <Heading w={"full"}>Crea una scheda Alimentare</Heading>
            </Flex>
            <Box bg={"white"} roundedTop={20} minW={{base: "100%", xl: "100%"}} h={"full"}>
                <GradientBar/>
                <Box pl={[0, 5, 20]} pr={[0, 5, 20]} pb={10} pt={5}>
                    <form style={{width: "100%"}} onSubmit={handleSubmit(onSubmit)}>
                        <FormControl id={"nome"} isInvalid={errors.nome} pt={5}>
                            <FormLabel htmlFor="nome">Nome delle scheda</FormLabel>
                            <Input type="text" placeholder="Dieta di Martina"/>
                            <FormErrorMessage>{errors.nome && errors.nome.message}</FormErrorMessage>
                        </FormControl>

                        <Modal isOpen={isOpen} onClose={onClose} isCentered={true} size={"5xl"}>
                            <ModalOverlay/>
                            <ModalContent>
                                    <ModalHeader fontSize={'3xl'} textAlign={"center"}>Aggiungi alimenti alla
                                        scheda</ModalHeader>
                                    <ModalCloseButton/>
                                    <ModalBody align={"center"}>
                                        <Flex justify="center">
                                            <HStack align="center">
                                                    <HStack>
                                                        {!isLoading && listAlimenti && (<Flex wrap={"wrap"} p={5}>
                                                            <Box bg={"white"} roundedTop={20}
                                                                 minW={{base: "100%", xl: "100%"}}
                                                                 h={"full"}>
                                                                <GradientBar/>
                                                                <Box pl={10} pr={10} pb={5} pt={5}>
                                                                    <HStack>
                                                                        <InputGroup>
                                                                            <InputLeftElement
                                                                                pointerEvents="none"
                                                                                children={<SearchIcon
                                                                                    color="gray.300"/>}
                                                                            />
                                                                            <Input
                                                                                className="SearchInput"
                                                                                type="text"
                                                                                onChange={onChange}
                                                                                placeholder="Cerca alimento"
                                                                            />
                                                                        </InputGroup>
                                                                    </HStack>
                                                                    {/* Barra di ricerca*/}
                                                                    <Text fontWeight={"bold"} mt={"4"} align={"left"}>Seleziona un pasto:</Text>
                                                                    <Select placeholder='Seleziona pasto' id={"selectPasto"}>
                                                                        <option value='1'>Colazione</option>
                                                                        <option value='2'>Spuntino Mattina</option>
                                                                        <option value='3'>Pranzo</option>
                                                                        <option value='4'>Spuntino Pomeriggio</option>
                                                                        <option value='5'>Cena</option>
                                                                        <option value='6'>Spuntino Serale</option>
                                                                        <option value='7'>Extra</option>
                                                                    </Select>
                                                                    {listAlimenti.lista_alimenti.length > 0 ? (<>
                                                                        <Text fontSize="xl" my={5}>
                                                                            Lista degli alimenti
                                                                        </Text>

                                                                        <Table variant={"striped"}
                                                                               alignContent={"center"}
                                                                               colorScheme={"gray"}
                                                                               size="md">
                                                                            <TableCaption>Lista
                                                                                Alimenti</TableCaption>
                                                                            <Thead bg="fitdiary.100">
                                                                                <Tr>
                                                                                    <Th>Immagine</Th>
                                                                                    <Th>Nome</Th>
                                                                                    <Th>Kcal</Th>
                                                                                    <Th>Proteine</Th>
                                                                                    <Th>Grassi</Th>
                                                                                    <Th>Carboidrati</Th>
                                                                                    <Th>Azione</Th>
                                                                                </Tr>
                                                                            </Thead>
                                                                            <Tbody>
                                                                                {listAlimenti.lista_alimenti.map((alimento) => (alimento.nome.toLowerCase().startsWith(search.toLowerCase()) || search === "") && (
                                                                                    <Tr key={alimento.id}>
                                                                                        <Td p={1}
                                                                                            m={0}>
                                                                                            <Image
                                                                                                objectFit='contain'
                                                                                                boxSize={100}
                                                                                                src={full + "/" + alimento.pathFoto}
                                                                                                alt='Foto non disponibile'/>
                                                                                        </Td>
                                                                                        <Td>{alimento.nome}</Td>
                                                                                        <Td>{alimento.kcal}</Td>
                                                                                        <Td>{alimento.proteine}</Td>
                                                                                        <Td>{alimento.grassi}</Td>
                                                                                        <Td>{alimento.carboidrati}</Td>
                                                                                        <Td><Button
                                                                                            colorScheme='fitdiary'
                                                                                            onClick={()=>{
                                                                                                let idPasto=document.getElementById("selectPasto").value;
                                                                                                if(idPasto.length>0)
                                                                                                {
                                                                                                    addAlimento(alimento,idPasto,150);
                                                                                                }
                                                                                                else
                                                                                                {
                                                                                                    toast(toastParam("Attenzione!", "Seleziona un pasto", "error"))
                                                                                                }
                                                                                            }}
                                                                                            fontSize={"s"}>
                                                                                            <AddIcon/>
                                                                                        </Button></Td>
                                                                                    </Tr>))}
                                                                            </Tbody>
                                                                        </Table>
                                                                    </>) : (<Heading py={5}
                                                                                     textAlign={"center"}>
                                                                        Non c'è niente qui...
                                                                    </Heading>)}
                                                                </Box>
                                                            </Box>
                                                        </Flex>)}
                                                    </HStack>
                                            </HStack>
                                        </Flex>
                                    </ModalBody>
                                    <ModalFooter alignItems={"center"}>
                                    </ModalFooter>
                            </ModalContent>
                        </Modal>

                        <Accordion allowToggle defaultIndex={[0]} w="full" mt={"60px"}>
                            {days.map((d, i) => {
                                return (<AccordionItem key={i}>
                                        <h2>
                                            <AccordionButton>
                                                <Box flex='1' textAlign='left' fontWeight={"extrabold"} fontSize={"xl"}>
                                                    {d}
                                                </Box>
                                                <AccordionIcon/>
                                            </AccordionButton>
                                        </h2>
                                        <AccordionPanel pb={4}>
                                            {vettPasti.map((pasto, index) => {
                                                return (
                                                    <div key={index}>
                                                        <Text fontSize={"21"} color={"blue"} fontWeight={"semibold"}>{pasto.Nome}</Text>
                                                        {schedaAlimentare[i].filter((t) => pasto.ID == t.Pasto).map((al, key) => {
                                                            let alimento = al.alimento;
                                                            let caloreCalc = (alimento.kcal / 100) * al.Qnt;
                                                            console.log(caloreCalc);
                                                            return (
                                                                <>
                                                                    <Table borderBottom={"solid 1px "}
                                                                           borderColor={"blue.200"} key={key}
                                                                           variant="unstyled" size="md">
                                                                        <Thead>
                                                                            <Tr>
                                                                                <Th>Immagine</Th>
                                                                                <Th >Nome</Th>
                                                                                <Th >Kcal</Th>
                                                                                <Th >Proteine</Th>
                                                                                <Th >Grassi</Th>
                                                                                <Th >Carboidrati</Th>
                                                                                <Th >Grammi</Th>
                                                                                <Th >Azioni</Th>
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
                                                                                        src={full + "/" + alimento.pathFoto}
                                                                                        alt='Foto non disponibile'/>
                                                                                </Td>
                                                                                <Td maxWidth={100}>{alimento.nome}</Td>
                                                                                <Td maxWidth={100}>{parseInt(caloreCalc)}</Td>
                                                                                <Td maxWidth={100}>{parseInt(alimento.proteine)}</Td>
                                                                                <Td maxWidth={100}>{parseInt(alimento.grassi)}</Td>
                                                                                <Td maxWidth={100}>{parseInt(alimento.carboidrati)}</Td>
                                                                                <Td maxWidth={100}>
                                                                                    <FormControl id={"nome"} isInvalid={errors.nome} pt={5}>
                                                                                        <Input
                                                                                            placeholder={al.Qnt}
                                                                                            w={20}
                                                                                            min={1}
                                                                                            max={2000}
                                                                                            type={"number"} defaultValue={al.Qnt} onChange={(e)=>{
                                                                                            if(e.target.value >0) {
                                                                                                if(e.target.value >2000) {
                                                                                                    e.target.value = 2000
                                                                                                }
                                                                                                schedaAlimentare[i][key].Qnt=e.target.value;
                                                                                            }
                                                                                            else {
                                                                                                schedaAlimentare[i][key].Qnt=100;

                                                                                            }
                                                                                            let newV=[...schedaAlimentare];
                                                                                            setSchedaAlimentare(newV)
                                                                                        }}/>
                                                                                        <FormErrorMessage>{errors.nome && errors.nome.message}</FormErrorMessage>
                                                                                    </FormControl>

                                                                                </Td>
                                                                                <Td>
                                                                                    <IconButton colorScheme={"red"} onClick={()=>{
                                                                                        if(window.confirm("Sei sicuro di voler eliminare l'alimento?")) {
                                                                                            schedaAlimentare[i].splice(key,1);
                                                                                            let newV=[...schedaAlimentare];
                                                                                            setSchedaAlimentare(newV);
                                                                                        }
                                                                                    }} aria-label={"Pulsante che elimina elemento"}>
                                                                                        <DeleteIcon/>
                                                                                    </IconButton>
                                                                                </Td>
                                                                            </Tr>
                                                                        </Tbody>
                                                                    </Table>
                                                                </>
                                                            );
                                                        })}
                                                    </div>
                                                );
                                            })}
                                            <Flex pt={5}>
                                                <Button
                                                    w="full"
                                                    colorScheme='fitdiary'
                                                    onClick={()=>{
                                                        onOpen();
                                                        setIndexGiorno(i);
                                                    }}>
                                                    Aggiungi alimenti</Button>
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
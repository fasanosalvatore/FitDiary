import {
  Box,
  Button,
  Flex,
  FormControl,
  FormErrorMessage,
  FormLabel,
  GridItem,
  Heading,
  HStack,
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
  SimpleGrid,
  Table,
  TableCaption,
  Tbody,
  Td,
  Text,
  Th,
  Thead,
  Tr, useDisclosure,
  useToast,
  VStack
} from "@chakra-ui/react";
import {useForm} from "react-hook-form"
import {Link as ReactLink} from "react-router-dom";
import React, {useCallback, useContext, useEffect, useState} from "react";
import {useDropzone} from 'react-dropzone';
import {FetchContext} from "../../context/FetchContext";
import { GiMeal } from "react-icons/gi";
import {IoIosFitness} from "react-icons/io";
import {AddIcon, CloseIcon, InfoIcon, SearchIcon} from "@chakra-ui/icons";
import Select from "react-select";
import {GradientBar} from "../../components/GradientBar";
import {useNavigate} from "react-router";

const urlProtocolli = "protocolli";
const urlUtenti = "utenti";
const urlSchedaAllenamento = "/trainingcards"
const urlSchedaAlimentazione = "/dietcards"

const Create = () => {
  const fetchContext = useContext(FetchContext);
  const navigate = useNavigate();
  const [options, setOptions] = useState([{}]);
  const [isLoading, setisLoading] = useState(false);
  const [selectedSchedaAllenamento, setselectedSchedaAllenamento] = useState(null);
  const [selectedSchedaAlimentare, setselectedSchedaAlimentare] = useState(null);
  const { register, handleSubmit, setValue, formState: { errors} } = useForm();
  const [toastMessage, setToastMessage] = useState(undefined);
  const [search, setSearch] = useState("");

  const {isOpen:isOpenSchedaAlimentare,onOpen: onOpenSchedaAlimentare,onClose: onCloseSchedaAlimentare} = useDisclosure()

  const [listSchedeAlimentari, setListSchedeAlimentari] = useState();
  const [listSchedeAllenamento, setListSchedeAllenamento] = useState();

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
      },1000);
    }
  }, [toastMessage, toast]);

  const onChange = (e) => {
    setSearch(e.target.value); // e evento target chi lancia l'evento e il value è il valore
  }

  const onSubmit = async (values) => {
    const formData = new FormData();
    formData.append("dataScadenza", values.dataScadenza)
    formData.append("idCliente", values.idCliente)
    if (values.schedaAllenamento)
      formData.append("schedaAllenamento", values.schedaAllenamento[0])
    if (values.schedaAlimentare)
      formData.append("schedaAlimentare", values.schedaAlimentare[0])
    try {
      const { data } = await fetchContext.authAxios.post(urlProtocolli, formData)
      console.log(data)
      setToastMessage({title:"Creato!",body:"Protocollo creato correttamente",stat:"success"})
      navigate("/protocols")
    } catch (error) {
      setToastMessage({title:"Errore",body:error.response.data.data,stat:"error"})
    }

  }

  useEffect(() => {
    setisLoading(true);
    const getUsers = async () => {
      try {
        const { data: { data: { clienti } } } = await fetchContext.authAxios(urlUtenti);
        setOptions(
          clienti.map((e) => {
            return { value: e.id, label: e.nome };
          })
        );
        setisLoading(false);
      } catch (error) {
        setToastMessage({title:"Error",body:error.message,stat:"error"})
      }
    }
    const loadlistaSchedeAllenamento = async () => {
      try {
        const {data} = await fetchContext.authAxios("schedaAllenamento/getMySchedeAllenamento");
        setListSchedeAllenamento(data.data.response);
      } catch (error) {
        setToastMessage({title: "Errore", body: error.message, stat: "error"});
      }
    }
    const loadlistaSchedeAlimentari = async () => {
      try {
        const { data } = await fetchContext.authAxios("schedaalimentare/getMySchedeAlimentari");
        setListSchedeAlimentari(data.data.response);
        setisLoading(false); //viene settato a false per far capire di aver caricato tutti i dati
      } catch (error) {
        setToastMessage({title:"Errore", body:error.message, stat:"error"});
      }

    }
    getUsers();
    loadlistaSchedeAllenamento();
    loadlistaSchedeAlimentari();
  }, [fetchContext])


  function selectSchedaAlimentare(idSchedaAlimentare){

  }

  function selectSchedaAllenamento(idSchedaAllenamento){

  }

  //Verifica se una data inserita è precedenta alla odierna
  function isValidDate(value) {
    var date = new Date();
    date.setHours(23, 59, 59, 59);
    return (!isNaN(Date.parse(value)) && (new Date(value) > date) ? true : "Inserisci una data valida");
  }

  return (
    <>
      <Flex wrap={"wrap"} p={5}>
        <Flex alignItems={"center"} mb={5}>
          <Heading w={"full"}>Crea Protocollo</Heading>
        </Flex>
        <Box bg={"white"} roundedTop={20} minW={{ base: "100%", xl: "100%" }} h={"full"}>
          <GradientBar />

          <Modal isOpen={isOpenSchedaAlimentare} onClose={onCloseSchedaAlimentare} isCentered={true} size={"5xl"}>
            <ModalOverlay/>
            <ModalContent>
              <ModalHeader fontSize={'3xl'} textAlign={"center"}>Aggiungi alimenti alla
                scheda</ModalHeader>
              <ModalCloseButton/>
              <ModalBody align={"center"}>
                <Flex justify="center">
                  <HStack align="center">
                    <HStack>
                      {!isLoading && listSchedeAlimentari && (<Flex wrap={"wrap"} p={5}>
                        <Box bg={"white"} roundedTop={20}
                             minW={{base: "100%", xl: "100%"}}
                             h={"full"}>
                          <GradientBar/>
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
                                    placeholder="Search"
                                />
                              </InputGroup>
                            </HStack>
                            {/* Barra di ricerca*/}
                            {listSchedeAlimentari.schede_alimentari.length > 0 ? (
                                <>
                                  <Text fontSize="xl" my={5}>
                                    Lista dei protocolli
                                  </Text>
                                  <Table variant={"striped"} colorScheme={"gray"} size="md">
                                    <TableCaption>Lista Schede Alimentari</TableCaption>
                                    <Thead bg="fitdiary.100">
                                      <Tr>
                                        <Th>ID</Th>
                                        <Th>Nome</Th>
                                        <Th>Kcal</Th>
                                        <Th>Azione</Th>
                                      </Tr>
                                    </Thead>
                                    <Tbody>
                                      {listSchedeAlimentari.schede_alimentari.map(
                                          (scheda) =>
                                              (scheda.id === parseInt(search) ||
                                                  search === "") && (
                                                  <Tr key={scheda.id}>
                                                    <Td>{scheda.id}</Td>
                                                    <Td>{scheda.nome}</Td>
                                                    <Td>{scheda.kcalAssunte}</Td>
                                                    <Td>
                                                      <ReactLink to={urlSchedaAlimentazione + "/" + scheda.id}>
                                                        <InfoIcon />
                                                      </ReactLink>
                                                    </Td>
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
                      </Flex>)}
                    </HStack>
                  </HStack>
                </Flex>
              </ModalBody>
              <ModalFooter alignItems={"center"}>
              </ModalFooter>
            </ModalContent>
          </Modal>

          <VStack spacing={3} alignItems="center" pb={5} mt={5}>
            <form onSubmit={handleSubmit(onSubmit)} style={{ width: "100%" }}>
              <SimpleGrid columns={2} columnGap={5} rowGap={5} pl={[0, 5, 10]} pr={[0, 5, 10]} w="full">
                <GridItem colSpan={2}>
                  <FormControl id={"idCliente"}>
                    <FormLabel>Cliente</FormLabel>
                    <Select options={options} isLoading={isLoading} onChange={(e) => {
                      setValue("idCliente", e.value)
                    }} />
                  </FormControl>
                </GridItem>
                <GridItem colSpan={2}>
                  <FormControl id={"dataScadenza"} isInvalid={errors.dataScadenza}>
                    <FormLabel>Data Scadenza</FormLabel>
                    <Input type="date" {...register("dataScadenza", {
                      required: "La data di scadenza è obbligatoria",
                      validate: value => {
                        return isValidDate(value)
                      }
                    })} />
                    <FormErrorMessage>{errors.dataScadenza && errors.dataScadenza.message}</FormErrorMessage>
                  </FormControl>
                </GridItem>
                <GridItem colSpan={1}>
                  <FormControl id={"schedaAlimentare"}>
                    <Box w={"80%"} bg={"gray.50"} p={5} border={"linen"}
                         borderColor={"gray.200"} borderRadius={"xl"} justifyContent={"center"}>
                    <FormLabel textAlign={"center"}>Scheda Alimentare</FormLabel>
                    {selectedSchedaAlimentare != null && (
                      <HStack>
                        <CloseIcon cursor={"pointer"} color={"red"} onClick={() => {
                          setselectedSchedaAlimentare(null);
                          setValue("schedaAlimentare", null);
                        }} />
                        <Text>
                          {selectedSchedaAlimentare.path}
                        </Text>
                      </HStack>)}
                      <Flex direction="column" alignItems="center">
                        <GiMeal size={"40%"} color="#00a9ff" />
                      </Flex>
                      <Flex direction="column" alignItems="center">
                          <Button colorScheme="fitdiary"  w="80%" margin="0.1rem">
                            <ReactLink to={"../DietCards/create"} >
                            Crea nuova
                            </ReactLink>
                          </Button>
                        <Button background="#8BC0FF" color="white" w="80%" margin="0.1rem"
                                onClick={() => {
                                  onOpenSchedaAlimentare();
                                }}>
                          Seleziona esistente
                        </Button>
                      </Flex>
                    </Box>
                  </FormControl>
                </GridItem>
                <GridItem colSpan={1}>
                  <FormControl id={"schedaAllenamento"}>
                    <Box w={"80%"} bg={"gray.50"} p={5} border={"linen"}
                         borderColor={"gray.200"} borderRadius={"xl"} justifyContent={"center"}>
                      <FormLabel textAlign={"center"}>Scheda Allenamento</FormLabel>
                      {selectedSchedaAlimentare != null && (
                          <HStack>
                            <CloseIcon cursor={"pointer"} color={"red"} onClick={() => {
                              setselectedSchedaAlimentare(null);
                              setValue("schedaAlimentare", null);
                            }} />
                            <Text>
                              {selectedSchedaAlimentare.path}
                            </Text>
                          </HStack>)}
                      <Flex direction="column" alignItems="center">
                        <IoIosFitness size={"40%"} color="#00a9ff" />
                      </Flex>
                      <Flex direction="column" alignItems="center">
                        <Button colorScheme="fitdiary" w="80%" margin="0.1rem">
                          <ReactLink to={"../TrainingCards/create"} >
                            Crea nuova
                          </ReactLink>
                        </Button>
                        <Button background="#8BC0FF" color="white" w="80%" margin="0.1rem">
                          Seleziona esistente
                        </Button>
                      </Flex>
                    </Box>
                  </FormControl>
                </GridItem>
                <GridItem colSpan={1} >
                  <Button background="#BDC7D3" color="white" type={"submit"} w="full">Annulla</Button>
                </GridItem>
                <GridItem colSpan={1} >
                  <Button colorScheme="fitdiary" type={"submit"} w="full">Carica</Button>
                </GridItem>
              </SimpleGrid>
            </form>
          </VStack>
        </Box>
      </Flex>
    </>
  )
}

export default Create;

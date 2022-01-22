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
    Input,
    SimpleGrid,
    Text,
    useToast,
    VStack
} from "@chakra-ui/react";
import {useForm} from "react-hook-form"
import React, {useCallback, useContext, useEffect, useState} from "react";
import {useDropzone} from 'react-dropzone';
import {FetchContext} from "../../context/FetchContext";
import {CloseIcon} from "@chakra-ui/icons";
import Select from "react-select";
import {GradientBar} from "../../components/GradientBar";
import {useNavigate} from "react-router";

const urlProtocolli = "protocolli";
const urlUtenti = "utenti";

const Create = () => {
  const fetchContext = useContext(FetchContext);
  const navigate = useNavigate();
  const [options, setOptions] = useState([{}]);
  const [isLoading, setisLoading] = useState(false);
  const [selectedSchedaAllenamento, setselectedSchedaAllenamento] = useState(null);
  const [selectedSchedaAlimentare, setselectedSchedaAlimentare] = useState(null);
  const { register, handleSubmit, setValue, formState: { errors} } = useForm();
  const [toastMessage, setToastMessage] = useState(undefined);
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

  const onDropAllenamento = useCallback(acceptedSchedaAllenamento => {
    console.log(acceptedSchedaAllenamento)
    setValue("schedaAllenamento", acceptedSchedaAllenamento);
  }, [setValue])
  const onDropAlimentare = useCallback(acceptedSchedaAlimentare => {
    console.log(acceptedSchedaAlimentare)
    setValue("schedaAlimentare", acceptedSchedaAlimentare);
  }, [setValue])
  const {
    acceptedFiles: acceptedSchedaAllenamento,
    getRootProps: getRootPropsAllenamento,
    getInputProps: getInputPropsAllenamento,
    isDragActive: isDragActiveAllenamento
  } = useDropzone({ onDrop: onDropAllenamento, maxFiles: 1 })
  const {
    acceptedFiles: acceptedSchedaAlimentare,
    getRootProps: getRootPropsAlimentare,
    getInputProps: getInputPropsAlimentare,
    isDragActive: isDragActiveAlimentare
  } = useDropzone({ onDrop: onDropAlimentare, maxFiles: 1 })

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
      setToastMessage({title:"Creato!",body:"Protocollo creato correttamente",stat:"success"})
      navigate("/protocols")
    } catch (error) {
      setToastMessage({title:"Errore",body:error.response.data.data,stat:"error"})
    }

  }

  useEffect(() => {
    setselectedSchedaAllenamento(acceptedSchedaAllenamento[0]);
  }, [acceptedSchedaAllenamento]);

  useEffect(() => {
    setisLoading(true);
    const getUsers = async () => {
      try {
        const { data: { data: { clienti } } } = await fetchContext.authAxios(urlUtenti);
        console.log(clienti);
        setOptions(
          clienti.map((e) => {
            return { value: e.id, label: e.nome };
          })
        );
        setisLoading(false);
      } catch (error) {
        console.log(error)
        setToastMessage({title:"Error",body:error.message,stat:"error"})
      }
    }
    getUsers();
  }, [fetchContext])

  useEffect(() => {
    setselectedSchedaAlimentare(acceptedSchedaAlimentare[0]);
  }, [acceptedSchedaAlimentare]);

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
                <GridItem colSpan={2}>
                  <FormControl id={"schedaAlimentare"}>
                    <FormLabel>Scheda Alimentare</FormLabel>
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
                    {!selectedSchedaAlimentare && (
                      <div {...getRootPropsAlimentare()}>
                        <Box w={"full"} bg={"gray.50"} p={5} border={"dotted"}
                          borderColor={"gray.200"}>
                          <Input {...getInputPropsAlimentare()} />
                          {
                            isDragActiveAlimentare ?
                              <p style={{ color: "gray", textAlign: "center" }}>
                                Lascia il file qui ...
                              </p> :
                              <p style={{ color: "gray", textAlign: "center" }}>
                                Clicca e trascina un file qui, oppure clicca per
                                selezionare un file
                              </p>
                          }
                        </Box>
                      </div>
                    )}
                  </FormControl>
                </GridItem>
                <GridItem colSpan={2}>
                  <FormControl id={"schedaAllenamento"}>
                    <FormLabel>Scheda Allenamento</FormLabel>
                    {selectedSchedaAllenamento != null && (
                      <HStack>
                        <CloseIcon cursor={"pointer"} color={"red"} onClick={() => {
                          setselectedSchedaAllenamento(null);
                          setValue("schedaAllenamento", null);
                        }} />
                        <Text>
                          {selectedSchedaAllenamento.path}
                        </Text>
                      </HStack>
                    )}
                    {!selectedSchedaAllenamento && (
                      <div {...getRootPropsAllenamento()}>
                        <Box w={"full"} bg={"gray.50"} p={5} border={"dotted"}
                          borderColor={"gray.200"}>
                          <Input {...getInputPropsAllenamento()} />
                          {
                            isDragActiveAllenamento ?
                              <p style={{ color: "gray", textAlign: "center" }}>
                                Lascia il file qui ...
                              </p> :
                              <p style={{ color: "gray", textAlign: "center" }}>
                                Clicca e trascina un file qui, oppure clicca per
                                selezionare un file
                              </p>
                          }
                        </Box>
                      </div>
                    )}
                  </FormControl>
                </GridItem>
                <GridItem colSpan={2} >
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

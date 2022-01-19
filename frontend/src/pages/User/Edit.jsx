import React, {useContext, useEffect, useState} from 'react';
import {useForm} from 'react-hook-form';

import {
    Box,
    Button,
    Divider,
    FormControl,
    FormErrorMessage,
    FormLabel,
    GridItem,
    Heading,
    Input,
    InputGroup,
    InputLeftElement,
    InputRightElement,
    SimpleGrid,
    Tooltip,
    useBreakpointValue,
    useToast,
    VStack
} from "@chakra-ui/react";
import {AtSignIcon, PhoneIcon, ViewIcon, ViewOffIcon} from "@chakra-ui/icons";
import {FetchContext} from "../../context/FetchContext";
import {GradientBar} from "../../components/GradientBar";

const urlGetInfo = `utenti/profilo`;

export default function Edit() {
    const urlEditInfo = `utenti`;
    const fetchContext = useContext(FetchContext);
    const {register, handleSubmit, getValues, setValue, formState: {errors, isSubmitting}} = useForm();
    const colSpan = useBreakpointValue({base: 2, md: 1})
    const [showP, setShowP] = React.useState(false)
    const [showCP, setShowCP] = React.useState(false)
    const toast = useToast({
        duration: 3000,
        isClosable: true,
        variant: "solid",
        position: "top",
        containerStyle: {
            width: '100%',
            maxWidth: '100%',
        },
    })
    const [isLoading, setLoading] = useState(true);

    useEffect(() => {
        console.log("pages/users/edit");
        const getInfoUtente = async () => {
            try {
                const {data} = await fetchContext.authAxios.get(urlGetInfo)
                const fields = ['nome', 'cognome', 'email', 'dataNascita', 'telefono', 'via', 'cap', 'citta'];
                fields.forEach(field => setValue(field, data.data.utente[field]));
                setLoading(false);
            } catch (error) {
                console.log("error", error);
            }
        };
        getInfoUtente();
    }, [setValue, fetchContext])


    //Chiamata API inserimento dati personali utente
    const onSubmit = async values => {
        console.log("submitting values");
        console.log(values);
        try {
            const {data} = await fetchContext.authAxios.put(urlEditInfo, values)
            console.log(data);
            toast({
                title: "Effettuato",
                description: "Dati aggiornati correttamente",
                status: data.status
            })
        } catch (error) {
            console.log(error.response)
            toast({
                title: 'Errore',
                description: error.response.data.message,
                status: 'error',
            })
        }
    }

    function isValidDate(value) {
        var date = new Date();
        date.setHours(0, 0, 0, 0);
        return (!isNaN(Date.parse(value)) && (new Date(value) <= date) ? true : "Inserisci una data valida");
    }

    //nome,cognome,email,password,dataNascita
    return (
        <>
            {!isLoading && (
                <VStack w="full" h="full" py={5} px={[0, 5, 10, 20]}>
                    <Heading size="lg" textAlign={"center"} pt={5}>Modifica Dati Personali</Heading>
                    <Box bg={"white"} borderRadius='xl' pb={5} w={"full"}>
                        <GradientBar/>
                        <Box pl={10} pr={10} pb={5} pt={5}>
                            <form style={{width: "100%"}} onSubmit={handleSubmit(onSubmit)}>
                                <SimpleGrid vcolumns={2} columnGap={5} rowGap={5} w="full">
                                    <GridItem colSpan={colSpan} w="100%">
                                        <FormControl id={"nome"} isInvalid={errors.nome}>
                                            <FormLabel htmlFor="nome">Nome</FormLabel>
                                            <Input type="text" placeholder="Mario" {...register("nome", {
                                                maxLength: {value: 50, message: "Il nome è troppo lungo"},
                                                pattern: {
                                                    value: /^([a-zA-Z\u0080-\u024F]+(?:. |-| |'))*[a-zA-Z\u0080-\u024F]*$/i,
                                                    message: "Formato nome non valido"
                                                }
                                            })} />
                                            <FormErrorMessage>{errors.nome && errors.nome.message}</FormErrorMessage>
                                        </FormControl>
                                    </GridItem>
                                    <GridItem colSpan={colSpan}>
                                        <FormControl id={"cognome"} isInvalid={errors.cognome}>
                                            <FormLabel>Cognome</FormLabel>
                                            <Input type="text" placeholder="Rossi" {...register("cognome", {
                                                maxLength: {value: 50, message: "Il cognome è troppo lungo"},
                                                pattern: {
                                                    value: /^([a-zA-Z\u0080-\u024F]+(?:. |-| |'))*[a-zA-Z\u0080-\u024F]*$/i,
                                                    message: "Formato cognome non valido"
                                                }
                                            })} />
                                            <FormErrorMessage>{errors.cognome && errors.cognome.message}</FormErrorMessage>
                                        </FormControl>
                                    </GridItem>
                                    <GridItem colSpan={2} w="100%">
                                        <FormControl id={"dataNascita"} isInvalid={errors.dataNascita}>
                                            <FormLabel>Data di Nascita</FormLabel>
                                            <Input type="date" placeholder="2001-01-05" {...register("dataNascita", {
                                                validate: value => {
                                                    return isValidDate(value)
                                                }
                                            })} />
                                            <FormErrorMessage>{errors.dataNascita && errors.dataNascita.message}</FormErrorMessage>
                                        </FormControl>
                                    </GridItem>
                                    <GridItem colSpan={2}>

                                        <FormControl id={"email"} isInvalid={errors.email}>
                                            <FormLabel>Email</FormLabel>
                                            <InputGroup>
                                                <InputLeftElement
                                                    pointerEvents='none'
                                                    children={<AtSignIcon color='gray.300'/>}
                                                />
                                                <Input type="text" placeholder="Email" {...register("email", {
                                                    required: "Il campo email è obbligatorio",
                                                    pattern: {value: /^\S+@\S+$/i, message: "Formato email non valido"}
                                                })} />
                                            </InputGroup>
                                            <FormErrorMessage>{errors.email && errors.email.message}</FormErrorMessage>
                                        </FormControl>
                                    </GridItem>
                                    <GridItem colSpan={2} w="100%">
                                        <FormControl id={"telefono"} isInvalid={errors.telefono}>
                                            <FormLabel>Numero di telefono</FormLabel>
                                            <InputGroup>
                                                <InputLeftElement
                                                    pointerEvents='none'
                                                    children={<PhoneIcon color='gray.300'/>}
                                                />
                                                <Input type="text" placeholder="3332957615"{...register("telefono", {
                                                    minLenght: {
                                                        value: 4,
                                                        message: "lunghezza numero telefono troppo corta"
                                                    },
                                                    maxLenght: {
                                                        value: 15,
                                                        message: "lunghezza numero telefono troppo corta"
                                                    },
                                                    pattern: {
                                                        value: /^[+03][0-9]{3,14}$/i,
                                                        message: "Formato numero di telefono non valido"
                                                    }
                                                })}/>
                                            </InputGroup>
                                            <FormErrorMessage>{errors.telefono && errors.telefono.message}</FormErrorMessage>
                                        </FormControl>
                                    </GridItem>
                                    <GridItem colSpan={2} w="100%">
                                        <FormControl id={"citta"} isInvalid={errors.citta}>
                                            <FormLabel htmlFor="citta"> Città</FormLabel>
                                            <Input type="text" placeholder="Roma"{...register("citta", {
                                               required:  "E' richiesto il nome della Città",
                                                maxLength: {
                                                    value: 20,
                                                    message: "Il nome della città è troppo lungo"
                                                },
                                                pattern: {
                                                    value: /^([a-zA-Z\u0080-\u024F]+(?:. |-| |'))*[a-zA-Z\u0080-\u024F]*$/i,
                                                    message: "Formato citta non valido"
                                                }
                                            })} />
                                            <FormErrorMessage>{errors.citta && errors.citta.message}</FormErrorMessage>
                                        </FormControl>
                                    </GridItem>
                                    <GridItem colSpan={colSpan} w="100%">
                                        <FormControl id={"via"} isInvalid={errors.via}>
                                            <FormLabel htmlFor="via">Via</FormLabel>
                                            <Input type="text" placeholder=" Via Roma"{...register("via", {
                                                required:"la via è richiesta",
                                                maxLength: {
                                                    value: 50,
                                                    message: "Il nome della via è troppo lungo"
                                                },pattern: {
                                                    value: /^[#.0-9a-zA-Z\s,-]+$/i,
                                                    message: "Formato via non valido"
                                                }
                                            })} />
                                            <FormErrorMessage>{errors.via && errors.via.message}</FormErrorMessage>
                                        </FormControl>
                                    </GridItem>
                                    <GridItem colSpan={colSpan} w="100%">
                                        <FormControl id={"cap"} isInvalid={errors.cap}>
                                            <FormLabel htmlFor="cap">CAP</FormLabel>
                                            <Input type="text" placeholder={"84020"} {...register("cap", {
                                                maxLength: {
                                                    value: 5,
                                                    message: "Formato del CAP non valido"
                                                }, pattern: {value: /^[0-9]{5}$/i, message: "Formato nome non valido"}
                                            })} />
                                            <FormErrorMessage>{errors.cap && errors.cap.message}</FormErrorMessage>
                                        </FormControl>
                                    </GridItem>
                                    <GridItem colSpan={2}>
                                        <Divider/>
                                    </GridItem>
                                    <GridItem colSpan={colSpan}>
                                        <FormControl id={"password"} isInvalid={errors.password}>
                                            <FormLabel>Password</FormLabel>
                                            <Tooltip
                                                label={"La password deve contenere: Una lettera maiuscola, minuscola, un numero e un carattere speciale"}
                                                aria-label='A tooltip'>
                                                <InputGroup>
                                                    <Input type={showP ? 'text' : 'password'}
                                                           placeholder="Password" {...register("password", {
                                                        maxLength: {value: 255, message: "Password troppo lunga"},
                                                        pattern: {
                                                            value: /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?^#()<>+&.])[A-Za-z\d@$!%*?^#()<>+&.]{8,}$/i,
                                                            message: "Formato password non valido"
                                                        }
                                                    })} />
                                                    <InputRightElement width='2.5rem'>
                                                        <Button bg={"transparent"} h='1.75rem' size='sm'
                                                                onClick={() => setShowP(!showP)}>
                                                            {showP ?
                                                                <ViewOffIcon color='gray.900'/> :
                                                                <ViewIcon color='gray.900'/>
                                                            }
                                                        </Button>
                                                    </InputRightElement>
                                                </InputGroup>
                                            </Tooltip>
                                            <FormErrorMessage>{errors.password && errors.password.message}</FormErrorMessage>
                                        </FormControl>
                                    </GridItem>
                                    <GridItem colSpan={colSpan}>
                                        <FormControl id={"confermaPassword"} isInvalid={errors.confermaPassword}>
                                            <FormLabel>Conferma Password</FormLabel>
                                            <InputGroup>
                                                <Input type={showCP ? 'text' : 'password'}
                                                       placeholder="Conferma Password" {...register("confermaPassword", {
                                                    validate: value => {
                                                        if (value === getValues("password")) {
                                                            return true
                                                        } else {
                                                            return "Le password non coincidono"
                                                        }
                                                    }
                                                })} />
                                                <InputRightElement width='2.5rem'>
                                                    <Button bg={"transparent"} h='1.75rem' size='sm'
                                                            onClick={() => setShowCP(!showCP)}>
                                                        {showCP ? <ViewOffIcon color='gray.900'/> :
                                                            <ViewIcon color='gray.900'/>}
                                                    </Button>
                                                </InputRightElement>
                                            </InputGroup>
                                            <FormErrorMessage>{errors.confermaPassword && errors.confermaPassword.message}</FormErrorMessage>
                                        </FormControl>
                                    </GridItem>
                                    <GridItem colSpan={2} w="100%">
                                        <Button size="lg" w="full" mt={4} colorScheme='fitdiary' isLoading={isSubmitting}
                                                type='submit'>
                                            Modifica dati Personali
                                        </Button>
                                    </GridItem>
                                </SimpleGrid>
                            </form>
                        </Box>
                    </Box>
                </VStack>)}

        </>
    );
}
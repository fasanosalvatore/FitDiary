import React, {useEffect, useState} from 'react';
import {useForm} from 'react-hook-form';

import {
    Button,
    FormControl,
    FormErrorMessage,
    FormLabel,
    GridItem, Heading,
    Input,
    SimpleGrid,
    Tooltip, useBreakpointValue, useToast, VStack
} from "@chakra-ui/react";
import config from "../../../config.json";
import AuthService from "../../../services/auth.service";
import {privateFetch} from "../../../util/fetch";


export default function CustomerEditInfo() {
    const urlEditInfo = `utenti/cliente`;
    const urlGetInfo = `utenti/profilo`;
    const {register, handleSubmit,getValues, setValue, formState: {errors, isSubmitting}} = useForm();
    const colSpan = useBreakpointValue({base: 2, md: 1})
    const [currentUser, setCurrentUser] = useState(null)
    const toast = useToast({
        duration: 9000,
        isClosable: true,
        variant:"solid",
        position:"top",
        containerStyle: {
            width: '100%',
            maxWidth: '100%',
        },
    })

    useEffect(() => {
        privateFetch(urlGetInfo).then(resp =>{
            const currentUser = resp;
            console.log(currentUser)
            if (currentUser !== null && currentUser !== {}) {
                const fields = ['nome', 'cognome', 'email', 'dataNascita', 'telefono', 'via', 'cap', 'citta'];
                fields.forEach(field => setValue(field, currentUser.data.data.utente[field]));
            }
        })
    }, [])


    //Chiamata API inserimento dati personali utente
    const onSubmit = async values => {
        console.log("submitting values");
        console.log(values);
        //console.log(accessToken)
        try{
            await privateFetch.put(urlEditInfo, values)
        }catch (error) {
        console.log(error.response)
        toast({
            title: 'Errore',
            description: error.response.data.message,
            status: 'error',
        })
    }
    }

    function isValidDate(value) {
        return (!isNaN(Date.parse(value)) && (new Date(value) < Date.now()) ? true : "Inserisci una data valida");
    }

    //nome,cognome,email,password,dataNascita
    return (
        <VStack w="full" h="full" p={[5, 10, 20]}>
            <VStack spacing={3} alignItems="flex-start" pb={5}>
                <Heading size="lg">Modifica Dati Personali</Heading>
            </VStack>
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
                            <Input type="text" placeholder="Email" {...register("email", {
                                required: "Il campo email è obbligatorio",
                                pattern: {value: /^\S+@\S+$/i, message: "Formato email non valido"}
                            })} />
                            <FormErrorMessage>{errors.email && errors.email.message}</FormErrorMessage>
                        </FormControl>
                    </GridItem>
                    <GridItem colSpan={colSpan}>
                        <FormControl id={"password"} isInvalid={errors.password}>
                            <FormLabel>Password</FormLabel>
                            <Tooltip
                                label={"La password deve contenere: Una lettera maiuscola, minuscola, un numero e un carattere speciale"}
                                aria-label='A tooltip'>
                                <Input type="password" placeholder="Password" {...register("password", {
                                    maxLength: {value: 255, message: "Password troppo lunga"},
                                    pattern: {
                                        value: /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?^#()<>+&.])[A-Za-z\d@$!%*?^#()<>+&.]{8,}$/i,
                                        message: "Formato password non valido"
                                    }
                                })} />
                            </Tooltip>
                            <FormErrorMessage>{errors.password && errors.password.message}</FormErrorMessage>
                        </FormControl>
                    </GridItem>
                    <GridItem colSpan={colSpan}>
                        <FormControl id={"confermaPassword"} isInvalid={errors.confermaPassword} isRequired>
                            <FormLabel>Conferma Password</FormLabel>
                            <Input type="password" placeholder="Conferma Password" {...register("confermaPassword", {
                                required: "Il campo conferma password è obbligatorio",
                                validate: value => {
                                    if (value === getValues("password")) {
                                        return true
                                    } else {
                                        return "Le password non coincidono"
                                    }
                                }
                            })} />
                            <FormErrorMessage>{errors.confermaPassword && errors.confermaPassword.message}</FormErrorMessage>
                        </FormControl>
                    </GridItem>
                    {/*
                    <GridItem colSpan={colSpan} w="100%">
                        <FormControl id={"altezza"} isInvalid={errors.altezza}>
                            <FormLabel htmlFor="altezza">Altezza</FormLabel>
                            <Tooltip
                                label={"Altezza in centimeri"}
                                aria-label='A tooltip'>
                                <Input type="number" placeholder={"178"} min="50" max="300" {...register("altezza", {
                                    maxLength: {
                                        value: 3,
                                        message: "valore altezza troppo grande"
                                    }, pattern: {value: /^[0-9]{1,3}$/i, message: "Formato altezza non valido"},
                                    required: "L'altezza è obbligatoria"
                                })} />
                            </Tooltip>
                            <FormErrorMessage>{errors.altezza && errors.altezza.message}</FormErrorMessage>
                        </FormControl>
                    </GridItem>*/}

                    <GridItem colSpan={2} w="100%">
                        <FormControl id={"telefono"} isInvalid={errors.telefono}>
                            <FormLabel>Numero di telefono</FormLabel>
                            <Input type="text" placeholder="3332957615"{...register("telefono", {
                                minLenght: {
                                    value: 4,
                                    message: "Formato del numero di telefono non valido"
                                },
                                maxLenght: {
                                    value: 15,
                                    message: "Formato del numero di telefono non valido"
                                },
                                pattern: {
                                    value: /^[+03][0-9]{3,14}$/i,
                                    message: "Formato numero di telefono non valido"
                                }
                            })}/>
                            <FormErrorMessage>{errors.telefono && errors.telefono.message}</FormErrorMessage>
                        </FormControl>
                    </GridItem>

                    <GridItem colSpan={2} w="100%">
                        <FormControl id={"citta"} isInvalid={errors.citta}>
                            <FormLabel htmlFor="citta"> Città</FormLabel>
                            <Input type="text" placeholder="Roma"{...register("citta", {
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
                                maxLength: {
                                    value: 50,
                                    message: "Il nome della via è troppo lungo"
                                },
                                pattern: {
                                    value: /^([a-zA-Z\u0080-\u024F]+(?:. |-| |'))*[a-zA-Z\u0080-\u024F]*$/i,
                                    message: "Formato nome non valido"
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
                    <GridItem colSpan={2} w="100%">
                        <Button size="lg" w="full" mt={4} colorScheme='teal' isLoading={isSubmitting} type='submit'>
                            Modifica dati Personali
                        </Button>
                    </GridItem>
                </SimpleGrid>
            </form>
        </VStack>
    );
}
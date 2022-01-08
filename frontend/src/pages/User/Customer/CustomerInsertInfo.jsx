import {useForm} from 'react-hook-form';
import React, {useContext, useEffect, useState} from 'react';
import {
    Button,
    FormControl,
    FormErrorMessage,
    FormLabel,
    GridItem, Heading,
    Input,
    SimpleGrid,
    Stack, Text, Tooltip, useBreakpointValue, useToast, VStack
} from "@chakra-ui/react";
import {FetchContext} from "../../../context/FetchContext";


export default function CustomerInsertInfo() {
    const urlEditInfo = `utenti/cliente`;
    const urlGetInfo = `utenti/profilo`;
    const {register, handleSubmit, getValues, setValue, formState: {errors, isSubmitting}} = useForm();
    const colSpan = useBreakpointValue({base: 2, md: 1})
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

    const [isLoading, setLoading] = useState(true);
    const fetchContext = useContext(FetchContext);

    useEffect(() => {
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
    }, [])


    //Chiamata API inserimento dati personali utente
    const onSubmit = async values => {
        console.log("submitting values");
        console.log(values);
        try {
            const {data} = await fetchContext.authAxios.post(urlEditInfo,values)
            console.log(data);
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
        return (!isNaN(Date.parse(value)) && (new Date(value) < Date.now()) ? true : "Inserisci una data valida");
    }

    return (

        <VStack w="full" h="full" p={[5, 10, 20]}>
            <VStack spacing={3} alignItems="flex-start" pb={5}>
                <Heading size="lg">Inserimento Dati Personali </Heading>
            </VStack>
            <form style={{width: "100%"}} onSubmit={handleSubmit(onSubmit)}>
                <SimpleGrid>
                    <GridItem colSpan={colSpan} w="100%">
                        <FormControl id={"dataNascita"} isInvalid={errors.dataNascita}>
                            <FormLabel>Data di Nascita</FormLabel>
                            <Input type="date" placeholder="2001-01-05" {...register("dataNascita", {
                                required: "La data di nascita è obbligatoria",
                                validate: value => {
                                    return isValidDate(value)
                                }
                            })} />
                            <FormErrorMessage>{errors.dataNascita && errors.dataNascita.message}</FormErrorMessage>
                        </FormControl>
                    </GridItem>

                    <GridItem colSpan={colSpan} w="100%">
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

                    <GridItem colSpan={colSpan} w="100%">
                        <FormControl id={"citta"} isInvalid={errors.citta}>
                            <FormLabel htmlFor="citta"> Città</FormLabel>
                            <Input type="text" placeholder="Roma"{...register("citta", {
                                maxLength: {
                                    value: 20,
                                    message: "Il nome della città è troppo lungo"
                                }, pattern: {value: /^([a-zA-Z\u0080-\u024F]+(?:. |-| |'))*[a-zA-Z\u0080-\u024F]*$/i, message: "Formato citta non valido"}
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
                                }, pattern: {value: /^([a-zA-Z\u0080-\u024F]+(?:. |-| |'))*[a-zA-Z\u0080-\u024F]*$/i, message: "Formato nome non valido"}
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
                    <GridItem colSpan={colSpan} w="100%">
                        <Button size="lg" w="full" mt={4} colorScheme='teal' isLoading={isSubmitting} type='submit'>
                            Modifica dati Personali
                        </Button>
                    </GridItem>
                </SimpleGrid>
            </form>
        </VStack>
    );
}
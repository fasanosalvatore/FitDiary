import {useForm} from 'react-hook-form';
import React, {useContext, useEffect, useState} from 'react';
import {
    Button,
    FormControl,
    FormErrorMessage,
    FormLabel,
    GridItem,
    Heading,
    Input, InputGroup, InputLeftElement,
    SimpleGrid,
    useBreakpointValue,
    useToast,
    Box,
    VStack
} from "@chakra-ui/react";
import {FetchContext} from "../../../context/FetchContext";
import {PhoneIcon} from "@chakra-ui/icons";
import {GradientBar} from "../../../components/GradientBar";


export default function CustomerInsertInfo() {
    const urlEditInfo = `utenti`;
    const {register, handleSubmit, formState: {errors, isSubmitting}} = useForm();
    const colSpan = useBreakpointValue({base: 2, md: 1})
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
            const {title, body, stat} = toastMessage;

            toast({
                title,
                description: body,
                status: stat,
            });
        }
        return () => {
            setTimeout(() => {
                setToastMessage(undefined);
            }, 1000);
        }
    }, [toastMessage, toast]);

    const fetchContext = useContext(FetchContext);


    //Chiamata API inserimento dati personali utente
    const onSubmit = async values => {
        console.log("submitting values");
        console.log(values);
        try {
            const {data} = await fetchContext.authAxios.put(urlEditInfo, values)
            console.log(data);
            toast({
                title: "Effettuato",
                description: "Dati inseriti correttamente",
                status: data.status
            })
        } catch (error) {
            console.log(error.response)
            setToastMessage({title: "Errore", body: error.response.data.message, stat: "error"})
        }
    }

    function isValidDate(value) {
        var date = new Date();
        date.setHours(0, 0, 0, 0);
        return (!isNaN(Date.parse(value)) && (new Date(value) <= date) ? true : "Inserisci una data valida");
    }

    return (
        <>
            <VStack w="full" h="full" py={5} px={[0, 5, 10, 20]}>
                <Box pb={5} w={"full"} backgroundColor={"white"} borderRadius={5}>
                    <GradientBar/>
                    <VStack spacing={3} alignItems="center" pb={5} mt={5}>
                <Heading size="lg" textAlign={"center"}>Inserimento Dati Personali </Heading>
                    <form style={{width: "100%"}} onSubmit={handleSubmit(onSubmit)}>
                        <SimpleGrid vcolumns={2} columnGap={5} rowGap={5} pl={[0, 5, 10]} pr={[0, 5, 10]} w="full">
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
                                        required: "E' richiesto il nome della Città",
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
                                        required: "la via è richiesta",
                                        maxLength: {
                                            value: 50,
                                            message: "Il nome della via è troppo lungo"
                                        }, pattern: {
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
                            <GridItem colSpan={2} w="100%">
                                <Button size="lg" w="full" mt={4} colorScheme='fitdiary' isLoading={isSubmitting}
                                        type='submit'>
                                    Inserimento dati Personali
                                </Button>
                            </GridItem>
                        </SimpleGrid>
                    </form>
                    </VStack>
                </Box>
            </VStack>
        </>
    );
}
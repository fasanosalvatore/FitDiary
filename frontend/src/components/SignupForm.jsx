import React from 'react';
import {useForm} from 'react-hook-form';
import config from '../config.json'
import {Link, useNavigate} from "react-router-dom";
import {
    Box,
    Button,
    FormControl,
    FormErrorMessage,
    FormLabel,
    GridItem, Heading,
    Input, Radio,
    RadioGroup,
    SimpleGrid,
    Stack, Text, Tooltip, useBreakpointValue, useColorModeValue, useToast, VStack
} from "@chakra-ui/react";
import {
    CardCvcElement,
    CardExpiryElement,
    CardNumberElement,
    Elements,
    useElements,
    useStripe
} from "@stripe/react-stripe-js";
import TierPrice from "./TierPrice";

export default function SignupForm() {
    const urlCreate = `${config.SERVER_URL}/utenti/preparatore`;
    const urlAcquisto = `${config.SERVER_URL}/abbonamento/acquista`;
    const {register, handleSubmit, getValues, formState: {errors, isSubmitting}} = useForm();
    const colSpan = useBreakpointValue({base: 2, md: 1})
    const stripe = useStripe();
    const elements = useElements();
    const toast = useToast()
    const navigate = useNavigate();

    function sleep(ms) {
        return new Promise(resolve => setTimeout(resolve, ms));
    }


    if (!stripe || !elements) {
        return "";
    }

    //helper function
    //Gestione risposta API crezione utente
    function handleResponse(response) {
        console.log("handling");
        return response.text().then(text => {
            const resp = JSON.parse(text);
            if (!response.ok) {
                const error = (resp && resp.message) || response.statusText;
                return Promise.reject(error);
            }
            console.log("handling Payment ");
            handlePayment(resp.data.response.customerId).then(() => {
                console.log("redirecting Payment ");
                navigate("/login");
            }).catch(handleFail)
        });
    }

    //Gestione FAIL
    function handleFail(data) {
        console.log(data)
        toast({
            title: 'Registrazione Fallita',
            description: data,
            status: 'error',
            duration: 9000,
            isClosable: true,
        })
    }

    //Chiamata API creazione utente
    function onSubmit(values) {

        console.log("submitting");
        const requestOptions = {
            method: "POST",
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify(values)
        }
        return fetch(urlCreate, requestOptions)
            .then(handleResponse)
            .catch(handleFail)
    }

    //Verifica se una data inserita è precedenta alla odierna
    function isValidDate(value) {
        return (!isNaN(Date.parse(value)) && (new Date(value) < Date.now()) ? true : "Inserisci una data valida");
    }

    //Gestione pagamento

    async function handlePayment(customerId) {

        let newSubscriptionResp = await fetch(urlAcquisto, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: customerId
        });

        newSubscriptionResp = await newSubscriptionResp.json();

        console.log(newSubscriptionResp.data.Utente.clientSecret)

        const client_Secret = newSubscriptionResp.data.Utente.clientSecret;

        await stripe.confirmCardPayment(
            client_Secret,
            {
                payment_method: {
                    card: elements.getElement(CardNumberElement),
                },
            }).then(function (result) {
            //console.log(JSON.stringify(result,undefined,2));
            if (result.error) {
                toast({
                    title: 'Pagamento Fallito',
                    description: "pagamento non riuscito",
                    status: 'error',
                    duration: 9000,
                    isClosable: true,
                })
                console.log(result.error)
            }
            if (result.paymentIntent) {
                toast({
                    title: 'Pagamento Completato',
                    description: "pagamento riuscito",
                    status: 'success',
                    duration: 9000,
                    isClosable: true,
                })
            }
        })
    }


    return (
        <form style={{width: "100%"}} onSubmit={handleSubmit(onSubmit)}>
            <SimpleGrid columns={2} columnGap={5} rowGap={5} w="full">
                <GridItem colSpan={colSpan} w="100%">
                    <FormControl id={"nome"} isInvalid={errors.nome} isRequired>
                        <FormLabel htmlFor="nome">Nome</FormLabel>
                        <Input type="text" placeholder="Mario" {...register("nome", {
                            required: "Il nome è obbligatorio",
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
                    <FormControl id={"cognome"} isInvalid={errors.cognome} isRequired>
                        <FormLabel>Cognome</FormLabel>
                        <Input type="text" placeholder="Rossi" {...register("cognome", {
                            required: "Il cognome è obbligatorio",
                            maxLength: {value: 50, message: "Il cognome è troppo lungo"},
                            pattern: {
                                value: /^([a-zA-Z\u0080-\u024F]+(?:. |-| |'))*[a-zA-Z\u0080-\u024F]*$/i,
                                message: "Formato cognome non valido"
                            }
                        })} />
                        <FormErrorMessage>{errors.cognome && errors.cognome.message}</FormErrorMessage>
                    </FormControl>
                </GridItem>
                <GridItem colSpan={colSpan}>
                    <FormControl id={"dataNascita"} isInvalid={errors.dataNascita} isRequired>
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
                <GridItem colSpan={colSpan}>
                    <FormControl id={"sesso"} isInvalid={errors.sesso} isRequired>
                        <FormLabel>Sesso</FormLabel>
                        <RadioGroup>
                            <Stack direction="row">
                                <Radio {...register("sesso", {required: "Il campo sesso è obbligatorio"})} type="radio"
                                       value="M" py={2} pr={5}>Maschio</Radio>
                                <Radio {...register("sesso", {required: "Il campo sesso è obbligatorio"})} type="radio"
                                       value="F" pl={5} pr={5}>Femmina</Radio>
                            </Stack>
                        </RadioGroup>
                        <FormErrorMessage>{errors.sesso && errors.sesso.message}</FormErrorMessage>
                    </FormControl>
                </GridItem>
                <GridItem colSpan={2}>
                    <FormControl id={"email"} isInvalid={errors.email} isRequired>
                        <FormLabel>Email</FormLabel>
                        <Input type="text" placeholder="Email" {...register("email", {
                            required: "Il campo email è obbligatorio",
                            pattern: {value: /^\S+@\S+$/i, message: "Formato email non valido"}
                        })} />
                        <FormErrorMessage>{errors.email && errors.email.message}</FormErrorMessage>
                    </FormControl>
                </GridItem>
                <GridItem colSpan={colSpan}>
                    <FormControl id={"password"} isInvalid={errors.password} isRequired>
                        <FormLabel>Password</FormLabel>
                        <Tooltip
                            label={"La password deve contenere: Una lettera maiuscola, minuscola, un numero e un carattere speciale"}
                            aria-label='A tooltip'>
                            <Input type="password" placeholder="Password" {...register("password", {
                                required: "Il campo password è obbligatorio",
                                maxLength: {value: 255, message: "Password troppo lunga"},
                                pattern: {
                                    value: /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?^#()<>+&\.])[A-Za-z\d@$!%*?^#()<>+&.]{8,}$/i,
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
                <GridItem colSpan={2} w="100%">
                    <Box border="1px" borderRadius="10px" padding="2%" borderColor={"lightgray"} >
                        <SimpleGrid columns={2} columnGap={5} rowGap={5} w="full">
                            <GridItem colSpan={2} w="100%">
                                <Heading as="h4" fontSize="xl">
                                    Dati pagamento
                                </Heading>
                            </GridItem>
                            <GridItem colSpan={2} w="100%">
                                <FormLabel htmlFor="numeroCarta">Numero Carta</FormLabel>
                                <Box border="1px" borderRadius={4} borderColor={"gray.200"} p={2.5}>
                                    <CardNumberElement options={{showIcon: true}}/>
                                </Box>
                            </GridItem>
                            <GridItem colSpan={colSpan} w="100%">
                                <FormLabel htmlFor="dataScadenza">Data Scadenza</FormLabel>
                                <Box border="1px" borderRadius={4} borderColor={"gray.200"} p={2.5}>
                                    <CardExpiryElement/>
                                </Box>
                            </GridItem>
                            <GridItem colSpan={colSpan} w="100%">
                                <FormLabel htmlFor="CVV">CVV</FormLabel>
                                <Box border="1px" borderRadius={4} borderColor={"gray.200"} p={2.5}>
                                    <CardCvcElement/>
                                </Box>
                            </GridItem>
                        </SimpleGrid>
                    </Box>
                        <TierPrice/>
                    <GridItem colSpan={2}>
                        <Button w="full" mt={4} colorScheme='teal' type='submit'>
                            Registrati e Paga
                        </Button>
                        <Text align={"center"} fontSize={"large"}>Hai gia un account? <Link
                            to="/login">Login</Link></Text>
                    </GridItem>
                </GridItem>
            </SimpleGrid>
        </form>
    );
}
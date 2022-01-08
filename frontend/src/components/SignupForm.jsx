import React, {useEffect, useState} from 'react';
import {useForm} from 'react-hook-form';
import {Link, useNavigate} from "react-router-dom";
import {
    Box,
    Button,
    FormControl,
    FormErrorMessage,
    FormLabel,
    GridItem,
    Heading,
    Input, InputGroup, InputLeftElement, InputRightElement,
    Radio,
    RadioGroup,
    SimpleGrid,
    Stack,
    Text,
    Tooltip,
    useBreakpointValue,
    useToast
} from "@chakra-ui/react";
import {
    CardCvcElement, CardExpiryElement, CardNumberElement, useElements, useStripe
} from "@stripe/react-stripe-js";
import TierPrice from "./TierPrice";
import {publicFetch} from "../util/fetch";
import {AtSignIcon, PhoneIcon, ViewIcon, ViewOffIcon} from "@chakra-ui/icons";

export default function SignupForm() {
    const urlSignup = 'utenti/preparatore';
    const urlBuy = 'abbonamento/acquista';
    const {register, handleSubmit, getValues, formState: {errors, isSubmitting}} = useForm();
    const [signupIsLoading, setSignupIsLoading] = useState(false);
    const [signupIsEnabled, setSignupIsEnable] = useState(false);
    const [cardComplete, setCardComplete] = useState({cardNumber: false, cardExpiry: false, cardCvc: false})
    const colSpan = useBreakpointValue({base: 2, md: 1})
    const stripe = useStripe();
    const elements = useElements();
    const toast = useToast({
        duration: 9000, isClosable: true, variant: "solid", position: "top", containerStyle: {
            width: '100%', maxWidth: '100%',
        },
    })
    const [showP, setShowP] = React.useState(false)
    const handleClickP = () => setShowP(!showP)
    const [showCP, setShowCP] = React.useState(false)
    const handleClickCP = () => setShowCP(!showCP)

    function toastParam(title, description, status) {
        return {
            title: title, description: description, status: status
        };
    }

    const navigate = useNavigate();

    function handleCardElementOnChange(e) {
        setCardComplete({...cardComplete, [e.elementType]: e.complete})
    }

    useEffect(() => {
        setSignupIsEnable(cardComplete.cardNumber && cardComplete.cardExpiry && cardComplete.cardCvc);
    }, [cardComplete])


//Chiamata API creazione utente
    const onSubmit = async values => {
        if (!stripe || !elements) {
            return "";
        }
        setSignupIsLoading(true);
        try {
            const {data: data2} = await publicFetch.post(urlSignup, values)
            const {data: {response: {utente, ...customerId}}} = data2;
            const {data: {data: {Utente: {clientSecret}}}} = await publicFetch.post(urlBuy, customerId);
            const stripePayment = await stripe.confirmCardPayment(clientSecret, {
                payment_method: {
                    card: elements.getElement(CardNumberElement),
                },
            });
            if (stripePayment.error) {
                toast(toastParam('Pagamento Fallito', 'Pagamento non riuscito', 'error'))
            } else if (stripePayment.paymentIntent) {
                toast(toastParam('Pagamento Completato', "Verrai ridirezionato al login", 'success'))
                setTimeout(() => {
                    navigate("/login");
                }, 1500);
            } else {
                toast(toastParam('Pagamento Sospeso', 'Contattare gli amministratori', 'warning'))
            }
        } catch (error) {
            setSignupIsLoading(false);
            console.log(error);
            toast({
                title: 'Errore', description: error.response.data.message, status: 'error',
            })
        }
    }

    //Verifica se una data inserita è precedenta alla odierna
    function isValidDate(value) {
        return (!isNaN(Date.parse(value)) && (new Date(value) < Date.now()) ? true : "Inserisci una data valida");
    }

    return (<form style={{width: "100%"}} onSubmit={handleSubmit(onSubmit)}>
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
                        required: "La data di nascita è obbligatoria", validate: value => {
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
            <GridItem colSpan={colSpan}>
                <FormControl id={"password"} isInvalid={errors.password}>
                    <FormLabel>Password</FormLabel>
                    <Tooltip
                        label={"La password deve contenere: Una lettera maiuscola, minuscola, un numero e un carattere speciale"}
                        aria-label='A tooltip'>
                        <InputGroup>
                            <Input type={showP ? 'text' : 'password'} placeholder="Password" {...register("password", {
                                required: "Il campo password è obbligatorio",
                                maxLength: {value: 255, message: "Password troppo lunga"},
                                pattern: {
                                    value: /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?^#()<>+&.])[A-Za-z\d@$!%*?^#()<>+&.]{8,}$/i,
                                    message: "Formato password non valido"
                                }
                            })} />
                            <InputRightElement width='2.5rem'>
                                <Button bg={"transparent"} h='1.75rem' size='sm' onClick={handleClickP}>
                                    {showP ? <ViewOffIcon color='gray.900'/> :
                                        <ViewIcon color='gray.900'/>}
                                </Button>
                            </InputRightElement>
                        </InputGroup>
                    </Tooltip>
                    <FormErrorMessage>{errors.password && errors.password.message}</FormErrorMessage>
                </FormControl>
            </GridItem>
            <GridItem colSpan={colSpan}>
                <FormControl id={"confermaPassword"} isInvalid={errors.confermaPassword} isRequired>
                    <FormLabel>Conferma Password</FormLabel>
                    <InputGroup>
                        <Input type={showCP ? 'text' : 'password'}
                               placeholder="Conferma Password" {...register("confermaPassword", {
                            required: "Il campo conferma password è obbligatorio",
                            validate: value => {
                                if (value === getValues("password")) {
                                    return true
                                } else {
                                    return "Le password non coincidono"
                                }
                            }
                        })} />
                        <InputRightElement width='2.5rem'>
                            <Button bg={"transparent"} h='1.75rem' size='sm' onClick={handleClickCP}>
                                {showCP ? <ViewOffIcon color='gray.900'/> :
                                    <ViewIcon color='gray.900'/>}
                            </Button>
                        </InputRightElement>
                    </InputGroup>
                    <FormErrorMessage>{errors.confermaPassword && errors.confermaPassword.message}</FormErrorMessage>
                </FormControl>
            </GridItem>
            <GridItem colSpan={2} w="100%">
                <Box border="1px" borderRadius="10px" padding="2%" borderColor={"lightgray"}>
                    <SimpleGrid columns={2} columnGap={5} rowGap={5} w="full">
                        <GridItem colSpan={2} w="100%">
                            <Heading as="h4" fontSize="xl">
                                Dati pagamento
                            </Heading>
                        </GridItem>
                        <GridItem colSpan={2} w="100%">
                            <FormLabel htmlFor="numeroCarta">Numero Carta</FormLabel>
                            <Box border="1px" borderRadius={4} borderColor={"gray.200"} p={2.5}>
                                <CardNumberElement options={{showIcon: true}} onChange={handleCardElementOnChange}/>
                            </Box>
                        </GridItem>
                        <GridItem colSpan={colSpan} w="100%">
                            <FormLabel htmlFor="dataScadenza">Data Scadenza</FormLabel>
                            <Box border="1px" borderRadius={4} borderColor={"gray.200"} p={2.5}>
                                <CardExpiryElement onChange={handleCardElementOnChange}/>
                            </Box>
                        </GridItem>
                        <GridItem colSpan={colSpan} w="100%">
                            <FormLabel htmlFor="CVV">CVV</FormLabel>
                            <Box border="1px" borderRadius={4} borderColor={"gray.200"} p={2.5}>
                                <CardCvcElement onChange={handleCardElementOnChange}/>
                            </Box>
                        </GridItem>
                    </SimpleGrid>
                </Box>
                <GridItem colSpan={2}>
                    <TierPrice/>
                </GridItem>
                <GridItem colSpan={2}>
                    <Button w="full" mt={4} colorScheme='teal' type='submit'
                            isLoading={signupIsLoading}
                            isDisabled={!signupIsEnabled}>
                        Registrati e Paga
                    </Button>
                    <Text align={"center"} fontSize={"large"}>Hai gia un account? <Link
                        to="/login">Login</Link></Text>
                </GridItem>
            </GridItem>
        </SimpleGrid>
    </form>);
}
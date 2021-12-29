import React from 'react';
import { useForm } from 'react-hook-form';
import { Link } from "react-router-dom";
import {
    Button,
    FormControl,
    FormErrorMessage,
    FormLabel,
    GridItem, Heading,
    Input, Radio,
    RadioGroup,
    SimpleGrid,
    Stack, Text, Tooltip, useBreakpointValue, VStack
} from "@chakra-ui/react";

export default function RegistrazioneForm() {
    const { register, handleSubmit, getValues, formState: { errors, isSubmitting } } = useForm();
    const colSpan = useBreakpointValue({base: 2, md: 1})


    const mySubmit = async (e) => {
        e.preventDefault();

        await handleSubmit(onSubmit)(e);
    }

    function onSubmit(values) {
        return new Promise((resolve) => {
            setTimeout(() => {
                const request = JSON.stringify(values, null, 2);
                console.log(request)
                fetch("http://localhost:8080/api/v1/utenti/create", {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: request
                })
                    .then(response => response.json())
                    .then(data => console.log(data))
                resolve()
            }, 1500)
        })
    }
    console.log(errors);

    function isValidDate(value) {
        return (!isNaN(Date.parse(value)) && (new Date(value) < Date.now()) ? true : "Inserisci una data valida");
    }

    return (
        <VStack w="full" h="full" p={[5,10,20]} >
            <VStack spacing={3} alignItems="flex-start" pb={5}>
                <Heading size="2xl">Registrazione</Heading>
            </VStack>
            <form style={{width: "100%"}} onSubmit={mySubmit}>
                <SimpleGrid columns={2} columnGap={5} rowGap={5} w="full">
                    <GridItem colSpan={colSpan} w="100%">
                        <FormControl id={"nome"} isInvalid={errors.nome}>
                            <FormLabel htmlFor="nome">Nome</FormLabel>
                            <Input type="text" placeholder="Mario" {...register("nome", {required:"Il nome è obbligatorio", maxLength: {value: 50, message: "Il nome è troppo lungo" }, pattern: {value: /^[a-z ,.'-]+$/i, message: "Formato nome non valido"}})} />
                            <FormErrorMessage>{errors.nome && errors.nome.message}</FormErrorMessage>
                        </FormControl>
                    </GridItem>
                    <GridItem colSpan={colSpan}>
                        <FormControl id={"cognome"} isInvalid={errors.cognome}>
                            <FormLabel>Cognome</FormLabel>
                            <Input type="text" placeholder="Rossi" {...register("cognome", {required:"Il cognome è obbligatorio", maxLength: {value: 50, message: "Il cognome è troppo lungo"}, pattern: {value: /^[a-z ,.'-]+$/i, message: "Formato cognome non valido"} })} />
                            <FormErrorMessage>{errors.cognome && errors.cognome.message}</FormErrorMessage>
                        </FormControl>
                    </GridItem>
                    <GridItem colSpan={colSpan}>
                        <FormControl id={"dataNascita"} isInvalid={errors.dataNascita}>
                            <FormLabel>Data di Nascita</FormLabel>
                            <Input type="date" placeholder="2001-01-05" {...register("dataNascita", {required:"La data di nascita è obbligatoria",validate:value => {return isValidDate(value)}})} />
                            <FormErrorMessage>{errors.dataNascita && errors.dataNascita.message }</FormErrorMessage>
                        </FormControl>
                    </GridItem>
                    <GridItem colSpan={colSpan}>
                        <FormControl id={"sesso"} isInvalid={errors.sesso}>
                            <FormLabel>Sesso</FormLabel>
                            <RadioGroup>
                                <Stack direction="row">
                                    <Radio {...register("sesso", { required:"Il campo sesso è obbligatorio" })} type="radio" value="M" py={2} pr={5}>Maschio</Radio>
                                    <Radio {...register("sesso", { required:"Il campo sesso è obbligatorio" })} type="radio" value="F" pl={5} pr={5}>Femmina</Radio>
                                </Stack>
                            </RadioGroup>
                            <FormErrorMessage>{errors.sesso && errors.sesso.message }</FormErrorMessage>
                        </FormControl>
                    </GridItem>
                    <GridItem colSpan={2}>
                        <FormControl id={"email"} isInvalid={errors.email}>
                            <FormLabel>Email</FormLabel>
                            <Input type="text" placeholder="Email" {...register("email", {required:"Il campo email è obbligatorio", pattern: {value: /^\S+@\S+$/i, message:"Formato email non valido"}})} />
                            <FormErrorMessage>{errors.email && errors.email.message }</FormErrorMessage>
                        </FormControl>
                    </GridItem>
                    <GridItem colSpan={colSpan}>
                        <FormControl id={"password"} isInvalid={errors.password}>
                            <FormLabel>Password</FormLabel>
                            <Tooltip label={"La password deve contenere: Una lettera maiuscola, minuscola, un numero e un carattere speciale"} aria-label='A tooltip'>
                                <Input type="password" placeholder="Password" {...register("password", {required:"Il campo password è obbligatorio", maxLength: {value: 255, message: "Password troppo lunga"}, pattern: {value: /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?^#()<>+&])[A-Za-z\d@$!%*?^#()<>+&]{8,}$/i, message: "Formato password non valido"}})} />
                            </Tooltip>
                            <FormErrorMessage>{errors.password && errors.password.message }</FormErrorMessage>
                        </FormControl>
                    </GridItem>
                    <GridItem colSpan={colSpan}>
                        <FormControl id={"confermaPassword"} isInvalid={errors.confermaPassword}>
                            <FormLabel>Conferma Password</FormLabel>
                            <Input type="password" placeholder="Conferma Password" {...register("confermaPassword", {required:"Il campo conferma password è obbligatorio", validate:value => {if (value === getValues("password")) {return true} else {return "Le password non coincidono"} } })} />
                            <FormErrorMessage>{errors.confermaPassword && errors.confermaPassword.message }</FormErrorMessage>
                        </FormControl>
                    </GridItem>
                    <GridItem colSpan={2} >
                        <Button w="full" mt={4} colorScheme='teal' isLoading={isSubmitting} type='submit'>
                            Prosegui al pagamento
                        </Button>
                        <Text align={"center"} fontSize={"large"}>Hai gia un account? <Link to="/login">Login</Link></Text>
                    </GridItem>
                </SimpleGrid>
            </form>
        </VStack>
    );
}
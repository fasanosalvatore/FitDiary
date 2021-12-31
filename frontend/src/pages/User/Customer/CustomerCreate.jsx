import React from 'react';
import {useForm} from 'react-hook-form';
import {
    Button,
    FormControl,
    FormErrorMessage,
    FormLabel,
    Heading,
    Input, VStack
} from "@chakra-ui/react";

export default function CustomerCreate() {
    const {register, handleSubmit, formState: {errors, isSubmitting}} = useForm();

    const mySubmit = async (e) => {
        e.preventDefault();
        await handleSubmit(onSubmit)(e);
    }

    function onSubmit(values) {
        return new Promise((resolve) => {
            setTimeout(() => {
                const request = JSON.stringify(values, null, 2);
                console.log(request)
                fetch("http://localhost:8080/api/v1/utenti/createcliente", {
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

    return (
        <VStack w="full" h="full" p={[5, 10, 20]}>
            <VStack spacing={3} alignItems="flex-start" pb={5}>
                <Heading size="2xl">Iscrizione Cliente</Heading>
            </VStack>
            <form style={{width: "100%"}} onSubmit={mySubmit}>
                <FormControl id={"nome"} isInvalid={errors.nome}>
                    <FormLabel htmlFor="nome">Nome</FormLabel>
                    <Input type="text" placeholder="Mario" {...register("nome", {
                        required: "Il nome è obbligatorio",
                        maxLength: {value: 50, message: "Il nome è troppo lungo"},
                        pattern: {value: /^[a-z ,.'-]+$/i, message: "Formato nome non valido"}
                    })} />
                    <FormErrorMessage>{errors.nome && errors.nome.message}</FormErrorMessage>
                </FormControl>
                <FormControl id={"cognome"} isInvalid={errors.cognome}>
                    <FormLabel htmlFor="cognome">Cognome</FormLabel>
                    <Input type="text" placeholder="Rossi" {...register("cognome", {
                        required: "Il cognome è obbligatorio",
                        maxLenght: {value: 50, message: "Il cognome è troppo lungo"},
                        pattern: {value: /^[a-z ,.'-]+$/i, message: "Formato cognome non valido"}
                    })} />
                    <FormErrorMessage>{errors.cognome && errors.cognome.message}</FormErrorMessage>
                </FormControl>
                <FormControl id={"email"} isInvalid={errors.email}>
                    <FormLabel>Email</FormLabel>
                    <Input type="text" placeholder="Email" {...register("email", {
                        required: "Il campo email è obbligatorio",
                        pattern: {value: /^\S+@\S+$/i, message: "Formato email non valido"}
                    })} />
                    <FormErrorMessage>{errors.email && errors.email.message}</FormErrorMessage>
                </FormControl>
                <Button w="full" mt={4} colorScheme='teal' isLoading={isSubmitting} type='submit'>
                    Completa Iscrizione
                </Button>
            </form>
        </VStack>
    )
}
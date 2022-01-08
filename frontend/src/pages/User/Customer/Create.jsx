import React, {useContext} from 'react';
import {useForm} from 'react-hook-form';
import {
    Button,
    FormControl,
    FormErrorMessage,
    FormLabel,
    Heading,
    Input, toast, useToast, VStack
} from "@chakra-ui/react";
import {FetchContext} from "../../../context/FetchContext";

export default function CustomerCreate() {
    const fetchContext = useContext(FetchContext);
    const {register, handleSubmit, formState: {errors, isSubmitting}} = useForm();
    const urlCreateCustomer = "utenti/createcliente";
    const toast = useToast({
        duration: 3000, isClosable: true, variant: "solid", position: "top", containerStyle: {
            width: '100%', maxWidth: '100%',
        },
    })
    function toastParam(title, description, status) {
        return {
            title: title, description: description, status: status
        };
    }
    const onSubmit = async (values) => {
        try {
            const {data} = await fetchContext.authAxios.post(urlCreateCustomer, values);
            console.log(data);
            toast(toastParam("Cliente creato con successo", "","success"));
        } catch (error) {
            console.log(error);
        }

    }

    return (
        <VStack w="full" h="full" p={[5, 10, 20]}>
            <VStack spacing={3} alignItems="flex-start" pb={5}>
                <Heading size="2xl">Invita Cliente</Heading>
            </VStack>
            <form style={{width: "100%"}} onSubmit={handleSubmit(onSubmit)}>
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
                    Invia Credenziali
                </Button>
            </form>
        </VStack>
    )
}

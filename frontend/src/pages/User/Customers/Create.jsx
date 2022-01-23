import React, {useContext} from 'react';
import {useForm} from 'react-hook-form';
import {Box, Button, Flex, FormControl, FormErrorMessage, FormLabel, Heading, Input, useToast} from "@chakra-ui/react";
import {FetchContext} from "../../../context/FetchContext";
import {GradientBar} from "../../../components/GradientBar";

export default function Create() {
    const fetchContext = useContext(FetchContext);
    const {register, handleSubmit, formState: {errors, isSubmitting}} = useForm();
    const urlCreateCustomer = "utenti";
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
            toast(toastParam("Cliente creato con successo", "Credenziali inviate via mail", "success"));
        } catch (error) {
            console.log(error.response.data.message)
            toast({
                title: 'Errore',
                description: error.response.data.message,
                status: 'error',
            })
        }
    }

    return (
        <Flex wrap={"wrap"} p={5}>
            <Flex alignItems={"center"} mb={5}>
                <Heading w={"full"}>Invita Nuovo Cliente</Heading>
            </Flex>
            <Box bg={"white"} roundedTop={20} minW={{ base: "100%", xl: "100%" }} h={"full"}>
                <GradientBar />
                <Box pl={[0, 5, 20]} pr={[0, 5, 20]} pb={10} pt={5}>
                    <form style={{width: "100%"}} onSubmit={handleSubmit(onSubmit)}>
                        <FormControl id={"nome"} isInvalid={errors.nome} pt={5}>
                            <FormLabel htmlFor="nome">Nome</FormLabel>
                            <Input type="text" placeholder="Mario" {...register("nome", {
                                required: "Il nome è obbligatorio",
                                maxLength: {value: 50, message: "Il nome è troppo lungo"},
                                pattern: {value: /^[a-z ,.'-]+$/i, message: "Formato nome non valido"}
                            })} />
                            <FormErrorMessage>{errors.nome && errors.nome.message}</FormErrorMessage>
                        </FormControl>
                        <FormControl id={"cognome"} isInvalid={errors.cognome} pt={5}>
                            <FormLabel htmlFor="cognome">Cognome</FormLabel>
                            <Input type="text" placeholder="Rossi" {...register("cognome", {
                                required: "Il cognome è obbligatorio",
                                maxLength: {value: 50, message: "Il cognome è troppo lungo"},
                                pattern: {value: /^[a-z ,.'-]+$/i, message: "Formato cognome non valido"}
                            })} />
                            <FormErrorMessage>{errors.cognome && errors.cognome.message}</FormErrorMessage>
                        </FormControl>
                        <FormControl id={"email"} isInvalid={errors.email} pt={5}>
                            <FormLabel>Email</FormLabel>
                            <Input type="text" placeholder="Email" {...register("email", {
                                required: "Il campo email è obbligatorio",
                                pattern: {value: /^\S+@\S+$/i, message: "Formato email non valido"}
                            })} />
                            <FormErrorMessage>{errors.email && errors.email.message}</FormErrorMessage>
                        </FormControl>
                        <Button w="full" mt={4} colorScheme='fitdiary' isLoading={isSubmitting} type='submit'>
                            Invia Credenziali
                        </Button>
                    </form>
                </Box>
            </Box>
        </Flex>
    )
}

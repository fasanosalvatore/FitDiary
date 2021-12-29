import React from 'react';
import {useForm} from 'react-hook-form';
import { Link } from "react-router-dom";
import {
    Button,
    FormControl,
    FormErrorMessage,
    FormLabel,
    GridItem, Heading,
    Input,
    SimpleGrid, Text, Tooltip,VStack
}
    from "@chakra-ui/react";

export default function Login() {
    const {register, handleSubmit,formState: {errors, isSubmitting}} = useForm();

    const mySubmit = async (e) => {
        e.preventDefault();

        await handleSubmit(onSubmit)(e);
    }

    function onSubmit(values) {
        return new Promise((resolve) => {
            setTimeout(() => {
                const request = JSON.stringify(values, null, 2);
                console.log(request)
                fetch("http://localhost:8080/api/v1/utenti/login", {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: request
                })
                    .then(response => response.json())   // restituisce un JSON
                    .then(data => console.log(data))
                resolve()
            }, 1500)
        })
    }

    console.log(errors);

    return (
        <VStack w="full" h="full" p={[5, 10, 20]}>
            <VStack spacing={3} alignItems="flex-start" pb={5}>
                <Heading size="2xl">Login</Heading>
            </VStack>
            <form style={{width: "100%"}} onSubmit={mySubmit}>
                <SimpleGrid columns={2} columnGap={5} rowGap={5} w="full">
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
                    <GridItem colSpan={2}>
                        <FormControl id={"password"} isInvalid={errors.password}>
                            <FormLabel>Password</FormLabel>
                            <Tooltip
                                label={"La password deve contenere: Una lettera maiuscola, minuscola, un numero e un carattere speciale"}
                                aria-label='A tooltip'>
                                <Input type="password" placeholder="Password" {...register("password", {
                                    required: "Il campo password è obbligatorio",
                                    maxLength: {value: 255, message: "Password troppo lunga"},
                                    pattern: {
                                        value: /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?^#()<>+&])[A-Za-z\d@$!%*?^#()<>+&]{8,}$/i,
                                        message: "Formato password non valido"
                                    }
                                })} />
                            </Tooltip>
                            <FormErrorMessage>{errors.password && errors.password.message}</FormErrorMessage>
                        </FormControl>
                    </GridItem>
                    <GridItem colSpan={2} >
                        <Button w="full" mt={4} colorScheme='teal' isLoading={isSubmitting} type='submit'>
                            Login
                        </Button>
                        <Text align={"center"} fontSize={"large"}>Non hai ancora un account su FitDiary? <Link to={"/registrati"}>Registrati</Link></Text>
                    </GridItem>
                </SimpleGrid>
            </form>
        </VStack>
    );


}
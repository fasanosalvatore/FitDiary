import React, {useContext} from 'react';
import {useForm} from 'react-hook-form';
import {Link, useNavigate} from "react-router-dom";
import AuthService from "../../services/auth.service.js";
import {
    Button,
    FormControl,
    FormErrorMessage,
    FormLabel,
    GridItem, Heading,
    Input,
    SimpleGrid, Text, VStack, useToast, Box
} from "@chakra-ui/react";
import {AuthContext} from "../../context/AuthContext";
import {publicFetch} from "../../util/fetch";

export default function Login() {
    const authContext = useContext(AuthContext);
    const {register, handleSubmit, formState: {errors, isSubmitting}} = useForm();
    const [isSuccessfullySubmitted, setIsSuccessfullySubmitted] = React.useState(false);
    const toast = useToast()
    const navigate = useNavigate();

    const onSubmit = async values => {
        console.log("submitting");
        try {
            const formData = new FormData();
            formData.append("email", values.email);
            formData.append("password", values.password);
            const {data:{data}} = await publicFetch.post('utenti/login',formData);
            setIsSuccessfullySubmitted(true);
            authContext.setAuthState(data);
            toast({
                title: 'Accesso eseguito!',
                description: "Verrai riderizionato a breve!",
                status: 'success',
                duration: 2000
            })
            setTimeout((resolve) => {
                navigate("/dashboard");
                resolve();
            }, 2000)
        } catch (error) {
            console.log(error.response);
            toast({
                title: 'Errore',
                description: error.response.data.data,
                status: 'error',
            })
        }
    }

    return (

        <VStack w="full" h="full" p={[5, 10, 20]}>
            <Box bg={"white"} borderRadius='xl' pb={5} w={"full"}>
                <Box h={"20px"} bgGradient="linear(to-r, blue.500, blue.800)" borderTopRadius={"md"}/>
                <VStack spacing={3} alignItems="center" pb={5} mt={5}>
                    <Heading size="2xl">Login</Heading>
                </VStack>
                <Box pl={20} pr={20} pb={5} pt={5}>
                    <form style={{width: "100%"}} onSubmit={handleSubmit(onSubmit)}>
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
                                    <Input type="password" placeholder="Password" {...register("password", {
                                        required: "Il campo password è obbligatorio",
                                        maxLength: {value: 255, message: "Password troppo lunga"},
                                    })} />
                                    <FormErrorMessage>{errors.password && errors.password.message}</FormErrorMessage>
                                </FormControl>
                            </GridItem>
                            <GridItem colSpan={2}>
                                <Button w="full" mt={4} colorScheme='teal'
                                        isLoading={isSubmitting || isSuccessfullySubmitted} type='submit'>
                                    Login
                                </Button>
                                <Text align={"center"} fontSize={"large"}>Non hai ancora un account su FitDiary?
                                    <Link to={"/signup"}> Registrati</Link></Text>
                            </GridItem>
                        </SimpleGrid>
                    </form>
                </Box>
            </Box>
        </VStack>
    );


}
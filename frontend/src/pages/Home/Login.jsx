import React, {useContext} from 'react';
import {useForm} from 'react-hook-form';
import {Link as ReactLink, useNavigate} from "react-router-dom";
import {
    Box,
    Button, Flex,
    FormControl,
    FormErrorMessage,
    FormLabel,
    GridItem,
    Heading, HStack,
    Input,
    InputGroup,
    InputRightElement,
    Link,
    SimpleGrid,
    Text,
    useToast,
    VStack
} from "@chakra-ui/react";
import {AuthContext} from "../../context/AuthContext";
import {publicFetch} from "../../util/fetch";
import {ViewIcon, ViewOffIcon} from "@chakra-ui/icons";
import {GradientBar} from "../../components/GradientBar";
import Footer from "../../components/Footer";
import Logo from "../../components/Logo";
import {FetchContext} from "../../context/FetchContext";

export default function Login() {
    const authContext = useContext(AuthContext);
    const fetchContext = useContext(FetchContext);
    const { register, handleSubmit, formState: { errors, isSubmitting } } = useForm();
    const [isSuccessfullySubmitted, setIsSuccessfullySubmitted] = React.useState(false);
    const toast = useToast({
        duration: 30000, isClosable: true, variant: "solid", position: "top", containerStyle: {
            width: '100%', maxWidth: '100%',
        },
    })
    const navigate = useNavigate();
    const [showP, setShowP] = React.useState(false)
    const handleClickP = () => setShowP(!showP)

    const onSubmit = async values => {
        console.log("submitting");
        try {
            const formData = new FormData();
            formData.append("email", values.email);
            formData.append("password", values.password);
            const { data } = await publicFetch.post('utenti/login', formData);
            console.log(data);
            setIsSuccessfullySubmitted(true);
            authContext.setAuthState(data.data);
            toast({
                title: 'Accesso eseguito!',
                description: "Verrai riderizionato a breve!",
                status: 'success',
            })
            setTimeout(async () => {
                const { data } = await fetchContext.authAxios("utenti/profilo");
                console.log(data.data.utente.ruolo)
                if(data.data.utente.dataAggiornamento === data.data.utente.dataCreazione
                    && data.data.utente.ruolo.nome.toLowerCase() === "cliente"
                )
                    navigate("/insertinfo");
                else
                    navigate("/dashboard");
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
        <VStack >
            <Flex width={"full"} justify={"space-between"} align={"center"} bg={"white"}>
                <HStack pl={[0, 5, 10, 20]}>
                    <Link as={ReactLink} to={"/"}>
                    <Logo penColor="black" viewBox={"0 0 250 200"} boxSize={"5em"} />
                    </Link>
                    <Heading>FitDiary</Heading>
                </HStack>
                <Box pr={[0, 5, 10, 20]}>
                    <Link as={ReactLink} to={"/signup"}>
                        <Button colorScheme='fitdiary' mr='4'>
                            Registrati
                        </Button>
                    </Link>
                </Box>
            </Flex>
        <VStack w="full" h="full" p={[5, 10, 20]}>

            <Box bg={"white"} borderRadius='xl' pb={5} w={"full"}>
                <GradientBar />
                <VStack spacing={3} alignItems="center" pb={5} mt={5}>
                    <Heading size="2xl">Login</Heading>
                </VStack>
                <Box pl={20} pr={20} pb={5} pt={5}>
                    <form style={{ width: "100%" }} onSubmit={handleSubmit(onSubmit)}>
                        <SimpleGrid columns={2} columnGap={5} rowGap={5} w="full">
                            <GridItem colSpan={2}>
                                <FormControl id={"email"} isInvalid={errors.email}>
                                    <FormLabel>Email</FormLabel>
                                    <Input type="text" placeholder="Email" {...register("email", {
                                        required: "Il campo email è obbligatorio",
                                        pattern: { value: /^\S+@\S+$/i, message: "Formato email non valido" }
                                    })} />
                                    <FormErrorMessage>{errors.email && errors.email.message}</FormErrorMessage>
                                </FormControl>
                            </GridItem>
                            <GridItem colSpan={2}>
                                <FormControl id={"password"} isInvalid={errors.password}>
                                    <FormLabel>Password</FormLabel>
                                    <InputGroup>
                                        <Input type={showP ? 'text' : 'password'}
                                            placeholder="Password" {...register("password", {
                                                required: "Il campo password è obbligatorio",
                                                maxLength: { value: 255, message: "Password troppo lunga" },
                                            })} />
                                        <InputRightElement width='2.5rem'>
                                            <Button bg={"transparent"} h='1.75rem' size='sm' onClick={handleClickP}>
                                                {showP ? <ViewOffIcon color='gray.900' /> :
                                                    <ViewIcon color='gray.900' />}
                                            </Button>
                                        </InputRightElement>
                                    </InputGroup>
                                    <FormErrorMessage>{errors.password && errors.password.message}</FormErrorMessage>
                                </FormControl>
                            </GridItem>
                            <GridItem colSpan={2}>
                                <Button w="full" mt={4} colorScheme='fitdiary'
                                    isLoading={isSubmitting || isSuccessfullySubmitted}
                                    type='submit'>
                                    Login
                                </Button>
                                <Text mt={3} fontSize="medium" align={"center"}>Non hai ancora un account su FitDiary? <Link color="fitdiary.900" as={ReactLink} to={"/signup"}>Registrati</Link>
                                </Text>
                            </GridItem>
                        </SimpleGrid>
                    </form>
                </Box>
            </Box>

        </VStack>
            <Footer width={"full"} />
        </VStack>
    );


}
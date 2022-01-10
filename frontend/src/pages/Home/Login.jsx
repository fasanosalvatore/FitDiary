import React, {useContext} from 'react';
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
    Input,
    InputGroup,
    InputRightElement,
    SimpleGrid,
    Text,
    useToast,
    VStack
} from "@chakra-ui/react";
import {AuthContext} from "../../context/AuthContext";
import {publicFetch} from "../../util/fetch";
import {ViewIcon, ViewOffIcon} from "@chakra-ui/icons";
import {GradientBar} from "../../components/GradientBar";

export default function Login() {
    const authContext = useContext(AuthContext);
    const {register, handleSubmit, formState: {errors, isSubmitting}} = useForm();
    const [isSuccessfullySubmitted, setIsSuccessfullySubmitted] = React.useState(false);
    const toast = useToast({
        duration: 3000, isClosable: true, variant: "solid", position: "top", containerStyle: {
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
            const {data: {data}} = await publicFetch.post('utenti/login', formData);
            setIsSuccessfullySubmitted(true);
            authContext.setAuthState(data);
            toast({
                title: 'Accesso eseguito!',
                description: "Verrai riderizionato a breve!",
                status: 'success',
            })
            setTimeout(() => {
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

        <VStack w="full" h="full" p={[5, 10, 20]}>
            <Box bg={"white"} borderRadius='xl' pb={5} w={"full"}>
                <GradientBar/>
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
                                    <InputGroup>
                                        <Input type={showP ? 'text' : 'password'}
                                               placeholder="Password" {...register("password", {
                                            required: "Il campo password è obbligatorio",
                                            maxLength: {value: 255, message: "Password troppo lunga"},
                                        })} />
                                        <InputRightElement width='2.5rem'>
                                            <Button bg={"transparent"} h='1.75rem' size='sm' onClick={handleClickP}>
                                                {showP ? <ViewOffIcon color='gray.900'/> :
                                                    <ViewIcon color='gray.900'/>}
                                            </Button>
                                        </InputRightElement>
                                    </InputGroup>
                                    <FormErrorMessage>{errors.password && errors.password.message}</FormErrorMessage>
                                </FormControl>
                            </GridItem>
                            <GridItem colSpan={2}>
                                <Button w="full" mt={4} colorScheme='teal'
                                        isLoading={isSubmitting || isSuccessfullySubmitted}
                                        type='submit'>
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
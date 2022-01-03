import React from 'react';
import {useForm} from 'react-hook-form';
import {Link, useHistory, useNavigate, withRouter} from "react-router-dom";
import AuthService from "../../services/auth.service.js";
import {
    Button,
    FormControl,
    FormErrorMessage,
    FormLabel,
    GridItem, Heading,
    Input,
    SimpleGrid, Text, Tooltip, VStack,useToast
} from "@chakra-ui/react";

import config from "../../config.json";

export default function Login() {
    const {register, handleSubmit, formState: {errors, isSubmitting}} = useForm();
    const urlLogin=`${config.SERVER_URL}/utenti/login`;
    const toast=useToast();
    const navigate = useNavigate();
    function onSubmit(values) {
       const resp={
           method: "POST",
           headers: {'Content-Type': 'application/json'},
           body: JSON.stringify(values)
       }
       AuthService.login(values.email,values.password).then( () => {
           navigate("/customer/me");
       },error =>{
          /*toast({
              title: 'Account not created.',
              description: "We've not created your account "+ error.message,
              status: 'failed',
              duration: 9000,
              isClosable: true,
          })*/
       } )

    }

    console.log(errors);

    return (
        <VStack w="full" h="full" p={[5, 10, 20]}>
            <VStack spacing={3} alignItems="flex-start" pb={5}>
                <Heading size="2xl">Login</Heading>
            </VStack>
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
                    <GridItem colSpan={2}>
                        <Button w="full" mt={4} colorScheme='teal' isLoading={isSubmitting} type='submit'>
                            Login
                        </Button>
                        <Text align={"center"} fontSize={"large"}>Non hai ancora un account su FitDiary? <Link
                            to={"/registrati"}>Registrati</Link></Text>
                    </GridItem>
                </SimpleGrid>
            </form>
        </VStack>
    );


}
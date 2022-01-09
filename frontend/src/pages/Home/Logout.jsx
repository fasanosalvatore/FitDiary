import {Text, useToast} from "@chakra-ui/react";
import {useContext, useEffect} from "react";
import {AuthContext} from "../../context/AuthContext";

export default function Logout() {
    const authContext = useContext(AuthContext);
    const toast = useToast({
        duration: 3000, isClosable: true, variant: "solid", position: "top", containerStyle: {
            width: '100%', maxWidth: '100%',
        },
    })
    useEffect(() => {
        toast({
            title: 'Logout eseguito!',
            description: "Verrai riderizionato a breve!",
            status: 'success',
        })
        setTimeout(() => {
            authContext.logout();
        },2000)
    })

    return (
        <>
            <Text>Successfull Logout</Text>
        </>
    );
}